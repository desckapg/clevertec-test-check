package ru.clevertec.check.repository.csv.dao;

import ru.clevertec.check.exception.BadRequestException;
import ru.clevertec.check.exception.CheckException;
import ru.clevertec.check.model.Product;
import ru.clevertec.check.repository.dao.Dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class CSVDaoProductImpl implements Dao<Product> {

    private final String pathToFile;

    protected CSVDaoProductImpl(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    @Override
    public Product get(int id) throws CheckException {
        try(BufferedReader reader = new BufferedReader(new FileReader(pathToFile))) {
            Optional<Product> product = reader.lines()
                    .skip(1)
                    .filter(s -> {
                        String[] data = s.split(";");
                        int currentId = Integer.parseInt(data[0]);
                        System.out.println(currentId);
                        return currentId == id;
                    })
                    .map(s -> {
                        String[] data = s.split(";");
                        Product.Builder builder = new Product.Builder();
                        builder.setId(id);
                        builder.setDescription(data[1]);
                        builder.setPrice(Float.parseFloat(data[2]));
                        builder.setQuantityInStock(Integer.parseInt(data[3]));
                        builder.setWholesale(Boolean.parseBoolean(data[4]));
                        return builder.build();
                    })
                    .findAny();
            if (product.isEmpty()) {
                throw new BadRequestException(String.format("WRONG PRODUCT ID %d IN PARAMS", id));
            }
            return product.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> getAll() {
        try(BufferedReader reader = new BufferedReader(new FileReader(pathToFile))) {
            return reader.lines().
                    skip(1)
                    .map(s -> {
                        String[] data = s.split(";");
                        Product.Builder builder = new Product.Builder();
                        builder.setId(Integer.parseInt(data[0]));
                        builder.setDescription(data[1]);
                        builder.setPrice(Float.parseFloat(data[2]));
                        builder.setQuantityInStock(Integer.parseInt(data[3]));
                        builder.setWholesale(Boolean.parseBoolean(data[4]));
                        return builder.build();
                    })
                    .toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
