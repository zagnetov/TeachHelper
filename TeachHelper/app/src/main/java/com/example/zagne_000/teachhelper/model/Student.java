package com.example.zagne_000.teachhelper.model;

public class Student {
    private String id_student;
    private String firstname;
    private String secondname;
    private Group group;

    public Student(String id_student, String firstname, String secondname, Group group){
        this.setId_student(id_student);
        this.setFirstname(firstname);
        this.setSecondname(secondname);
        this.setGroup(group);
    }



    @Override
    public String toString() {
        return  getId_student() + ". " + "Имя: " + getFirstname() + " Фамилия: " + getSecondname() + " Группа: " + getGroup().getGroupnumber();
    }

    public String getId_student() {
        return id_student;
    }

    public void setId_student(String id_student) {
        this.id_student = id_student;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
