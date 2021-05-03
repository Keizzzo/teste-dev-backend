package com.example.olisaude.repository;

import com.example.olisaude.util.ObjectResponse;

import java.util.List;

public interface Dao<T> {

    Long insert(T obj);
    T update(ObjectResponse<T, Long> objResponse);
    T find(Long id);
    List<ObjectResponse<T, Long>> list();

}
