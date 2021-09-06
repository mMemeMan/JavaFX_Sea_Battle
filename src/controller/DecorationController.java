package controller;

import model.field.Cell;

public class DecorationController {

    public void showStatus(Cell cell, boolean showShips) {
        switch (cell.getStatus()) {
            case SHIP:
                if (showShips) {
                    cell.getButton().setId("ship_button");
                    break;
                }
            case VOID:
                cell.getButton().setId("empty_button");
                break;
            case MISS:
                cell.getButton().setVisible(false);
                break;
            case UNDAMAGED:
                cell.getButton().setId("undamaged_button");
                break;
            case DEAD:
                cell.getButton().setId("dead_button");
        }
    }
}
