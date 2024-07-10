package ru.clevertec.check.model;

import java.util.Objects;

public class DiscountCard {

    private Integer id;
    private final Integer number;
    private final Integer discount;

    private DiscountCard(Integer id, Integer number, Integer discount) {
        this.id = id;
        this.number = number;
        this.discount = discount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getDiscount() {
        return discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DiscountCard that = (DiscountCard) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DiscountCard{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", discount=" + discount +
                '}';
    }

    public static class Builder {

        private Integer id;
        private Integer number;
        private Integer discount;

        public void setId(Integer id) {
            this.id = id;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }

        public void setDiscount(Integer discount) {
            this.discount = discount;
        }

        public DiscountCard build() {
            return new DiscountCard(id, number, discount);
        }
    }

}
