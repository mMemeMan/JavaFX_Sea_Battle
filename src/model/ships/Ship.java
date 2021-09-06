package model.ships;

import model.field.Cell;
import model.field.Status;

import java.util.ArrayList;

public abstract class Ship {
    protected ArrayList<Cell> cells = new ArrayList();

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public void addCell(Cell cell) {
        cells.add(cell);
    }

    //проверка жив ли корабль
    public boolean doUDead() {
        for (int i = 0; i < cells.size(); i++) {
            if (cells.get(i).getStatus() == Status.SHIP) {
                return false;
            }
        }
        return true;
    }
}
