package com.example.zagne_000.teachhelper.model;

public class Pair_result {
    private Pair pair;
    private Student student;
    private boolean presence;
    private boolean byillnes;
    private int mark;

    Pair_result(Pair pair, Student student, boolean presence, boolean byillnes, int mark){
        this.setPair(pair);
        this.setStudent(student);
        this.setPresence(presence);
        this.setByillnes(byillnes);
        this.setMark(mark);
    }


    public Pair getPair() {
        return pair;
    }

    public void setPair(Pair pair) {
        this.pair = pair;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public boolean isPresence() {
        return presence;
    }

    public void setPresence(boolean presence) {
        this.presence = presence;
    }

    public boolean isByillnes() {
        return byillnes;
    }

    public void setByillnes(boolean byillnes) {
        this.byillnes = byillnes;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
