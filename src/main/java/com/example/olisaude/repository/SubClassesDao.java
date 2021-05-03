package com.example.olisaude.repository;

import java.util.List;

public interface SubClassesDao<T> {

    List<T> listBySuperId(Long id);
}
