package ru.clevertec.check.model;

public class CheckPosition {

    private final Integer productId;
    private final Integer quantity;
    private final Float price;
    private final Float discount;
    private final Float priceWithDiscount;
    private final DiscountType discountType;

    private CheckPosition(Integer productId, Integer quantity, Float price, Float discount, Float priceWithDiscount, DiscountType discountType) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.priceWithDiscount = priceWithDiscount;
        this.discountType = discountType;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Float getPrice() {
        return price;
    }

    public Float getDiscount() {
        return discount;
    }

    public Float getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public enum DiscountType {
        WHIOLESALE,
        DISCOUNT_CARD,
        NO_DISOUNT
    }

    public static class Builder {
        private Integer productId;
        private Integer quantity;
        private Float price;
        private Float discount;
        private Float priceWithDiscount;
        private DiscountType discountType = DiscountType.NO_DISOUNT;

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public void setPrice(Float price) {
            this.price = price;
        }

        public void setDiscount(Float discount) {
            this.discount = discount;
        }

        public void setPriceWithDiscount(Float priceWithDiscount) {
            this.priceWithDiscount = priceWithDiscount;
        }

        public void setDiscountType(DiscountType discountType) {
            this.discountType = discountType;
        }

        public CheckPosition build() {
            return new CheckPosition(productId, quantity, price, discount, priceWithDiscount, discountType);
        }
    }
}
