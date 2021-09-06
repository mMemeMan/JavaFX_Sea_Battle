package model.field;


import javafx.scene.control.Button;

public class Cell {

    private int x;
    private int y;
    /*
    v - void, пусто
    s - ship, корабль
    d - dead, убит корабль
    m - miss, промах
     */
    private Status status = Status.VOID;

    public Button getButton() {
        return button;
    }

    private Button button;

    public Cell(int x, int y, Button button) {
        this.x = x;
        this.y = y;
        this.button = button;
    }

    public Status getStatus() {
        return status;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
