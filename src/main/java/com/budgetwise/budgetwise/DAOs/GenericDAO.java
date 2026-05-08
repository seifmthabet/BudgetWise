package com.budgetwise.budgetwise.DAOs;

import java.util.List;

/**
 * GenericDAO component.
 */
public interface GenericDAO<T> {

    void save(T entity);
    T findById(int id);
    List<T> findAll();
    void update(T entity);
    void delete(int id);

}
