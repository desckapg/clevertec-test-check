package ru.clevertec.check.model;

import java.util.Objects;

public class Product {

    private Integer id;
    private final String description;
    private final Float price;
    private final Integer quantityInStock;
    private final Boolean wholesale;

    private Product(Integer id, String description,
                    Float price, Integer quantityInStock,
                    Boolean wholesale) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.wholesale = wholesale;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Float getPrice() {
        return price;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public Boolean getWholesale() {
        return wholesale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantityInStock=" + quantityInStock +
                ", wholesale=" + wholesale +
                '}';
    }

    public static class Builder {
        private Integer id;
        private String description;
        private Float price;
        private Integer quantityInStock;
        private Boolean wholesale;

        public void setId(Integer id) {
            this.id = id;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setPrice(Float price) {
            this.price = price;
        }

        public void setQuantityInStock(Integer quantityInStock) {
            this.quantityInStock = quantityInStock;
        }

        public void setWholesale(Boolean wholesale) {
            this.wholesale = wholesale;
        }

        public Product build() {
            return new Product(id, description, price, quantityInStock, wholesale);
        }
    }

}
