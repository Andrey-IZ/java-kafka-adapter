package com.sber.javaschool.finalproject.crud.persistance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CrudEntity implements Cloneable {

    @Id
    @GeneratedValue
    private long id;

    private String title;

    private String body;

    @Override
    public CrudEntity clone() {
        try {
            return (CrudEntity) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}