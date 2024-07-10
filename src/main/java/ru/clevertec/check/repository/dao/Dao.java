package ru.clevertec.check.repository.dao;

import ru.clevertec.check.exception.CheckException;

import java.util.List;

public interface Dao<T> {

    T get(int id) throws CheckException;
    List<T> getAll() throws CheckException;

}
