package com.tlg.model;

import javax.persistence.*;

@MappedSuperclass
public class AbstractEntity {

    protected int id;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
