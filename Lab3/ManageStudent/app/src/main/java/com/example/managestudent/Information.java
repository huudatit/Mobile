package com.example.managestudent;

public class Information {
    private String name, id, classAc;

    public Information(String id, String name, String classAc) {
        this.id = id;
        this.name = name;
        this.classAc = classAc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassAc() {
        return classAc;
    }

    public void setClassAc(String classAc) {
        this.classAc = classAc;
    }
}