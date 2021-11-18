package com.sber.javaschool.finalproject.crud.persistance.repository;

import com.sber.javaschool.finalproject.crud.persistance.model.CrudEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudRepository extends JpaRepository<CrudEntity, Long> {
}
