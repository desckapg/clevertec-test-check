package ru.clevertec.check.core;

import ru.clevertec.check.CheckRunner;
import ru.clevertec.check.exception.BadRequestException;
import ru.clevertec.check.exception.CheckException;
import ru.clevertec.check.exception.NotEnoughMoneyException;
import ru.clevertec.check.model.Check;
import ru.clevertec.check.model.CheckPosition;
import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.model.Product;
import ru.clevertec.check.params.OptionalParams;
import ru.clevertec.check.params.RequiredParams;
import ru.clevertec.check.service.ProductService;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CheckFormer {

    private final RequiredParams requiredParams;
    private final OptionalParams optionalParams;

    public CheckFormer(RequiredParams requiredParams, OptionalParams optionalParams) {
        this.requiredParams = requiredParams;
        this.optionalParams = optionalParams;
    }

    public Check form() throws CheckException {
        Check.Builder checkBuilder = new Check.Builder();
        Map<Integer, Integer> products = requiredParams.getProducts();
        float cardBalance = requiredParams.getCardBalance();
        float cardDiscount = calculateDiscount();

        if (cardDiscount > 0) {
            checkBuilder.setDiscountCardNumber(optionalParams.getDiscountCardNumber());
            checkBuilder.setCardDiscount((int) (cardDiscount * 100));
        }
        List<CheckPosition> positions = new LinkedList<>();
        float totalPrice = 0.0f;
        float totalDiscount = 0.0f;
        float totalPriceWithDiscount = 0.0f;

        ProductService productService = CheckRunner.getProductService();
        for (Map.Entry<Integer, Integer> entry : products.entrySet()) {
            Optional<Product> product = productService.getProduct(entry.getKey());
            if (product.isEmpty()) {
                throw new BadRequestException(String.format("PRODUCT WITH ID %d NOT FOUND", entry.getValue()));
            }
            int quantity = entry.getValue();

            if (quantity > product.get().getQuantityInStock()) {
                throw new BadRequestException(String.format("NOT ENOUGH PRODUCTS IN STOCK WITH ID %d", product.get().getId()));
            }

            float positionPrice = product.get().getPrice() * quantity;
            float positionDiscount = 0.0f;

            CheckPosition.Builder checkPositionBuilder = new CheckPosition.Builder();

            if (product.get().getWholesale() && quantity > 5) {
                checkPositionBuilder.setDiscountType(CheckPosition.DiscountType.WHIOLESALE);
                positionDiscount = 0.1f * positionPrice;
            } else if (cardDiscount > 0) {
                checkPositionBuilder.setDiscountType(CheckPosition.DiscountType.DISCOUNT_CARD);
                positionDiscount = cardDiscount * positionPrice;
            }

            float positionPriceWithDiscount = positionPrice - positionDiscount;

            totalPrice += positionPrice;
            totalDiscount += positionDiscount;
            totalPriceWithDiscount += positionPriceWithDiscount;

            checkPositionBuilder.setProductId(product.get().getId());
            checkPositionBuilder.setQuantity(quantity);
            checkPositionBuilder.setPrice(positionPrice);
            checkPositionBuilder.setDiscount(positionDiscount);
            checkPositionBuilder.setPriceWithDiscount(positionPriceWithDiscount);
            positions.add(checkPositionBuilder.build());
        }

        if (cardBalance < totalPriceWithDiscount) {
            throw new NotEnoughMoneyException("");
        }

        checkBuilder.setPositions(positions);
        checkBuilder.setTotalPrice(totalPrice);
        checkBuilder.setTotalDiscount(totalDiscount);
        checkBuilder.setTotalPriceWithDiscount(totalPriceWithDiscount);

        return checkBuilder.build();

    }

    public Float calculateDiscount() {
        if (optionalParams.isDiscountCardPresent()) {
            Optional<DiscountCard> discountCard = CheckRunner.getDiscountCardService().getDiscountCard(optionalParams.getDiscountCardNumber());
            return discountCard.map(card -> card.getDiscount() / 100f).orElse(0.02f);
        }
        return 0.0f;
     }
}
