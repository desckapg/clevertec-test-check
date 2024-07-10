package ru.clevertec.check.model;

import java.time.LocalDateTime;
import java.util.List;

public class Check {

    private final LocalDateTime dateTime;

    private final List<CheckPosition> positions;
    private final String discountCardNumber;
    private final Integer cardDiscount;
    private final Float totalPrice;
    private final Float totalDiscount;
    private final Float totalPriceWithDiscount;

    private Check(List<CheckPosition> positions, String discountCardNumber, Integer cardDiscount,
                  Float totalPrice, Float totalDiscount, Float totalPriceWithDiscount) {
        this.dateTime = LocalDateTime.now();
        this.discountCardNumber = discountCardNumber;
        this.cardDiscount = cardDiscount;
        this.positions = positions;
        this.totalPrice = totalPrice;
        this.totalDiscount = totalDiscount;
        this.totalPriceWithDiscount = totalPriceWithDiscount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public List<CheckPosition> getPositions() {return positions;}

    public Float getTotalPrice() {
        return totalPrice;
    }

    public Float getTotalDiscount() {
        return totalDiscount;
    }

    public Float getTotalPriceWithDiscount() {
        return totalPriceWithDiscount;
    }

    public String getDiscountCardNumber() {
        return discountCardNumber;
    }

    public Integer getCardDiscount() {
        return cardDiscount;
    }

    public static class Builder {

        private List<CheckPosition> positions;
        private String discountCardNumber;
        private Integer cardDiscount;
        private Float totalPrice;
        private Float totalDiscount;
        private Float totalPriceWithDiscount;

        public void setPositions(List<CheckPosition> positions) {
            this.positions = positions;
        }

        public void setTotalPrice(Float totalPrice) {
            this.totalPrice = totalPrice;
        }

        public void setTotalDiscount(Float totalDiscount) {
            this.totalDiscount = totalDiscount;
        }

        public void setTotalPriceWithDiscount(Float totalPriceWithDiscount) {
            this.totalPriceWithDiscount = totalPriceWithDiscount;
        }

        public void setDiscountCardNumber(String discountCardNumber) {
            this.discountCardNumber = discountCardNumber;
        }

        public void setCardDiscount(Integer cardDiscount) {
            this.cardDiscount = cardDiscount;
        }

        public Check build() {
            return new Check(positions, discountCardNumber, cardDiscount, totalPrice, totalDiscount, totalPriceWithDiscount);
        }
    }
}
