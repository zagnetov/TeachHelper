package com.example.zagne_000.teachhelper.model;

public class Group {
    String id_group;
    String groupnumber;

    public Group(String id_group, String groupnumber){
        this.id_group = id_group;
        this.groupnumber = groupnumber;
    }

    public String getId_group() {
        return id_group;
    }

    public void setId_group(String id_group) {
        this.id_group = id_group;
    }

    public String getGroupnumber() {
        return groupnumber;
    }

    public void setGroupnumber(String groupnumber) {
        this.groupnumber = groupnumber;
    }

    @Override
    public String toString() {
        return  id_group + ". " + "Группа:" + groupnumber;
    }
}
