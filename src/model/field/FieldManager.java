package model.field;

import controller.DecorationController;
import model.ships.Ship;

import java.util.ArrayList;
import java.util.List;

public class FieldManager {

    //проверка живи ли корабли
    public boolean shipsIsAlive(Field field) {
        for (int i = 0; i < field.getShips().size(); i++) {
            if (field.getShips().get(i).doUDead() == false) {
                return true;
            }
        }
        return false;
    }

    //получить случайную Cell
    public Cell getRandomLiveCell(Field field) {
        int randomNum = (int) (Math.random() * field.getLivesCells().size());
        Cell cell = field.getLivesCells().get(randomNum);
        return cell;
    }

    //создать ключ к Cell имея ее X и Y
    public String createKey(int x, int y) {
        String key = Integer.toString(x) + Integer.toString(y);
        return key;
    }

    //создать случайный ключ
    public String createRandomKey(Field field) {
        int randomX = (int) (Math.random() * field.getWidth());
        int randomY = (int) (Math.random() * field.getHeight());
        String key = createKey(randomX, randomY);
        return key;
    }

    //проверка не липнут ли корабли
    public boolean checkNeighbors(Field field, Cell cell, ArrayList<Cell> exceptionsKeys) {
        FieldManager fm = new FieldManager();

        int x = cell.getX();
        int y = cell.getY();
        //ключи соседних клеток
        ArrayList<String> neighborsKeys = new ArrayList();

        neighborsKeys.add(fm.createKey(x, y + 1));
        neighborsKeys.add(fm.createKey(x + 1, y + 1));
        neighborsKeys.add(fm.createKey(x + 1, y));
        neighborsKeys.add(fm.createKey(x + 1, y - 1));
        neighborsKeys.add(fm.createKey(x, y - 1));
        neighborsKeys.add(fm.createKey(x - 1, y - 1));
        neighborsKeys.add(fm.createKey(x - 1, y));
        neighborsKeys.add(fm.createKey(x - 1, y + 1));

        for (int i = 0; i < neighborsKeys.size(); i++) {
            if (field.getCells().get(neighborsKeys.get(i)) != null) {
                boolean result = checkExKeys(field.getCells().get(neighborsKeys.get(i)), exceptionsKeys);
                if (result == false) {
                    if (field.getCells().get(neighborsKeys.get(i)).getStatus() != Status.VOID) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //проверка: не является ли точка исключением
    private boolean checkExKeys(Cell cell, ArrayList<Cell> exceptionsKeys) {
        for (int j = 0; j < exceptionsKeys.size(); j++) {
            if (cell.equals(exceptionsKeys.get(j))) {
                return true;
            }
        }
        return false;
    }

    //обработка выстрела по точке
    public Status processingShot(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getStatus()) {
            case VOID:
                cell.setStatus(Status.MISS);
                return Status.MISS;
            case SHIP:
                cell.setStatus(Status.UNDAMAGED);
                return Status.UNDAMAGED;
            case MISS:
                return Status.MISS;
            case UNDAMAGED:
                return Status.UNDAMAGED;
            case DEAD:
                return Status.DEAD;
        }
        return null;
    }

    public void checkDeadShips(ArrayList<Ship> ships) {
        DecorationController decorationController = new DecorationController();
        List<Ship> deadShips = new ArrayList();
        for (Ship ship : ships) {
            if (ship.doUDead()) {
                deadShips.add(ship);
            }
        }
        if (!deadShips.isEmpty()) {
            for (Ship ship : deadShips) {
                for (Cell cell : ship.getCells()) {
                    cell.setStatus(Status.DEAD);
                    decorationController.showStatus(cell, false);
                }
            }
        }
    }
}
