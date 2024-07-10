package ru.clevertec.check.service;

import ru.clevertec.check.exception.CheckException;
import ru.clevertec.check.model.Product;
import ru.clevertec.check.repository.dao.Dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ProductService {

    private final Dao<Product> productDao;

    private final Map<Integer, Product> products;

    public ProductService(Dao<Product> productDao) {
        this.productDao = productDao;
        this.products = new HashMap<>();
    }

    public void load() throws CheckException {
        for (Product product : productDao.getAll()) {
            products.put(product.getId(), product);
        }
    }

    public boolean isProductExist(int id) {
        return products.containsKey(id);
    }

    public Optional<Product> getProduct(int id) {
        return Optional.ofNullable(products.get(id));
    }

    public void addProduct(Product product) throws CheckException {
        productDao.add(product);
        products.put(product.getId(), product);
    }

    public void deleteProduct(int id) throws CheckException {
        products.remove(id);
        productDao.delete(id);
    }

    public void updateProduct(Product product) throws CheckException {
        products.put(product.getId(), product);
        productDao.update(product);
    }

}
