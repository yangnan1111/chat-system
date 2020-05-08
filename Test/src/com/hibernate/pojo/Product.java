package com.hibernate.pojo;

import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "tb_product")

public class Product {
    private String id;
    private String name;
    private String data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
