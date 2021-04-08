package com.example.wally.domain;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity<ID> {

    @Id
    @GeneratedValue
    @Column(name="_id", nullable=false)
    private Long ID;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "ID=" + ID +
                '}';
    }
}
