package ru.clevertec.check.repository.csv.dao;

import ru.clevertec.check.exception.BadRequestException;
import ru.clevertec.check.exception.CheckException;
import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.repository.dao.Dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class CSVDaoDiscountCardImpl implements Dao<DiscountCard> {

    private final String pathToFile;

    protected CSVDaoDiscountCardImpl(String pathToFile) {
        this.pathToFile = pathToFile;
    }


    @Override
    public DiscountCard get(int id) throws CheckException {
        try(BufferedReader reader = new BufferedReader(new FileReader(pathToFile))) {
            Optional<DiscountCard> discountCard = reader.lines()
                    .skip(1)
                    .filter(s -> {
                        String[] data = s.split(";");
                        int currentId = Integer.parseInt(data[0]);
                        return currentId == id;
                    })
                    .map(s -> {
                        String[] data = s.split(";");
                        DiscountCard.Builder builder = new DiscountCard.Builder();
                        builder.setId(id);
                        builder.setNumber(data[1]);
                        builder.setDiscount(Integer.parseInt(data[2]));
                        return builder.build();
                    })
                    .findAny();
            if (discountCard.isEmpty()) {
                throw new BadRequestException(String.format("WRONG CARD ID %d IN PARAMS", id));
            }
            return discountCard.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DiscountCard> getAll() {
        try(BufferedReader reader = new BufferedReader(new FileReader(pathToFile))) {
            return reader.lines()
                    .skip(1)
                    .map(s -> {
                        String[] data = s.split(";");
                        DiscountCard.Builder builder = new DiscountCard.Builder();
                        builder.setId(Integer.parseInt(data[0]));
                        builder.setNumber(data[1]);
                        builder.setDiscount(Integer.parseInt(data[2]));
                        return builder.build();
                    })
                    .toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
