package ru.clevertec.check.params;

import ru.clevertec.check.exception.BadRequestException;
import ru.clevertec.check.exception.CheckException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequiredParams {

    private final String[] params;

    private Float cardBalance;
    private Map<Integer, Integer> products;
    private String pathToProductFile;
    private String saveToFile;

    public RequiredParams(String[] params) {
        this.params = params;
        this.cardBalance = Float.NaN;
        this.products = new HashMap<>();
    }

    public void process() throws CheckException {
        for (String param : params) {
            if (param.startsWith("saveToFile=")) {
                this.saveToFile = param.substring("saveToFile=".length());
            }
        }
        for (String param : params) {
            if (param.startsWith("balanceDebitCard=")) {
                String debitCardBalanceStr = param.substring("balanceDebitCard=".length());
                if (!debitCardBalanceStr.matches("^([+-]?\\d*\\.?\\d*)$")) {
                    throw new BadRequestException("WRONG DEBIT CARD BALANCE");
                }
                this.cardBalance = Float.parseFloat(debitCardBalanceStr);
            } else if (param.matches("^(\\d*-\\d*)$")) {
                String[] data = param.split("-");
                int id = Integer.parseInt(data[0]);
                int quantity = Integer.parseInt(data[1]);
                this.products.put(
                        id,
                        this.products.getOrDefault(id, 0) + quantity);
            } else if (param.startsWith("pathToFile=")) {
                this.pathToProductFile = param.substring("pathToFile=".length());
            }
        }
        if (Float.isNaN(cardBalance)) {
            throw new BadRequestException("NO CARD BALANCE IN ARGS");
        } else if (products.isEmpty()) {
            throw new BadRequestException("NO PRODUCTS IN ARGS (OR WRONG FORMAT)");
        } else if (Objects.isNull(pathToProductFile)) {
            throw new BadRequestException("WRONG pathToFile");
        } else if (Objects.isNull(saveToFile)) {
            throw new BadRequestException("WRONG saveToFile");
        }
    }

    public Float getCardBalance() {
        return cardBalance;
    }


    public Map<Integer, Integer> getProducts() {
        return products;
    }

    public String getPathToProductFile() {
        return pathToProductFile;
    }

    public String getSaveToFile() {
        return saveToFile;
    }
}
