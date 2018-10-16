package com.example.zagne_000.teachhelper.model;

import java.sql.Time;
import java.sql.Date;

public class Pair {
    private String id_pair;
    private Group group;
    private Subject subject;
    private Date date;
    private String time;
    private String topic;

    public Pair(String id_pair, Group group, Subject subject, Date date, String time, String topic){
        this.setId_pair(id_pair);
        this.setGroup(group);
        this.setSubject(subject);
        this.setDate(date);
        this.setTime(time);
        this.setTopic(topic);
    }

    public String getId_pair() {
        return id_pair;
    }

    public void setId_pair(String id_pair) {
        this.id_pair = id_pair;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
            return getId_pair() + ". " + "Группа: " + getGroup().getGroupnumber() + " Предмет: " + getSubject().getName_subject()
                    + " Дата: " + getDate().toString() + " Время: " + getTime().toString() + " Тема: " + getTopic();

    }
}
