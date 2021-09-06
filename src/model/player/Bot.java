package model.player;

import controller.DecorationController;
import model.field.Cell;
import model.field.Field;
import model.field.FieldManager;
import model.field.Status;

public class Bot extends Player {

    public Bot(Field field, String name) {
        super.setClassField(field);
        super.setName(name);
    }

    public Bot() {
    }

    @Override
    public void makeStep(Field targetField) {
        FieldManager fm = new FieldManager();
        DecorationController decorationController = new DecorationController();

        while (true) {
            Cell targetCell = fm.getRandomLiveCell(targetField);
            targetField.getLivesCells().remove(targetCell);
            Status resultOfShot = fm.processingShot(targetCell);
            targetCell.setStatus(resultOfShot);
            decorationController.showStatus(targetCell, false);

            if (resultOfShot == Status.MISS) {
                return;
            } else if (resultOfShot == Status.UNDAMAGED) {
                fm.checkDeadShips(targetField.getShips());
            }
        }
    }
}
