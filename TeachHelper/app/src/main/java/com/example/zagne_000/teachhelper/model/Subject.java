package com.example.zagne_000.teachhelper.model;

public class Subject {
    String id_subject;
    String name_subject;

    public Subject(String id_subject, String name_subject){
        this.id_subject = id_subject;
        this.name_subject = name_subject;
    }

    public String getId_subject() {
        return id_subject;
    }

    public String getName_subject() {
        return name_subject;
    }

    public void setId_subject(String id_subject) {
        this.id_subject = id_subject;
    }

    public void setName_subject(String name_subject) {
        this.name_subject = name_subject;
    }

    @Override
    public String toString() {
        return  id_subject + ". " + "Предмет: " + name_subject;
    }
}
