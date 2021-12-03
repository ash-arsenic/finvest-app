package com.example.finvest;

public class FundsModal {
    private String name;
    private String references;
    private Integer color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReferences() {
        return references;
    }

    public void setReferences(String references) {
        this.references = references;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public FundsModal(String name, String references, Integer color) {
        this.name = name;
        this.references = references;
        this.color = color;

    }
}
