package model.player;

import model.field.Field;

public class Player {
    private String name;
    private Field field;

    public Player(Field field, String name) {
        this.field = field;
        this.name = name;
    }

    public Player() {
    }

    public Field getClassField() {
        return field;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //false - промах
    //true - попадание
    public void makeStep(Field targetField) {}

    public void setClassField(Field field) {
        this.field = field;
    }
}
