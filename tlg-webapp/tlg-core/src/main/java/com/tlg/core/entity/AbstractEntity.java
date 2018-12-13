package com.tlg.core.entity;

import javax.persistence.*;


@MappedSuperclass
public class AbstractEntity {

    protected long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}
