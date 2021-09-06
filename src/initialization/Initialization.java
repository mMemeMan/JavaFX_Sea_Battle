package initialization;

import controller.DecorationController;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import model.field.Cell;
import model.field.Field;
import model.field.FieldManager;
import model.field.Status;
import model.player.Bot;
import model.player.Player;
import model.ships.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import static initialization.Direction.*;

public class Initialization {
    Direction direction;


    public Field initField(ObservableList<Node> children) {
        FieldManager fm = new FieldManager();
        DecorationController decorationController = new DecorationController();
        LinkedList gridChildren = new LinkedList(children);
        Field field = new Field();

        final int FIRST = 0;
        for (int y = 0; y < field.getHeight(); y++) {
            for (int x = 0; x < field.getWidth(); x++) {
                String key = fm.createKey(x, y);
                Cell cell = new Cell(x, y, (Button) gridChildren.get(FIRST));
                decorationController.showStatus(cell, false);
                field.getCells().put(key, cell);
                field.getLivesCells().add(cell);
                //на случай перезапуска поля
                ((Button) gridChildren.get(FIRST)).setVisible(true);
                gridChildren.remove(FIRST);
            }
        }
        return field;
    }

    //автоматическая растановка
    public void automaticallyInitShips(Field field, boolean showShips) {
        for (int i = 0; i < field.getCountLincor(); i++) {
            while (true) {
                Ship lincor = new Lincor();
                if (randomShip(field, lincor, Lincor.getSIZE(), showShips)) break;
            }
        }
        for (int i = 0; i < field.getCountCruiser(); i++) {
            while (true) {
                Ship cruiser = new Cruiser();
                if (randomShip(field, cruiser, Cruiser.getSIZE(), showShips)) break;
            }
        }
        for (int i = 0; i < field.getCountDestroyer(); i++) {
            while (true) {
                Ship destroyer = new Destroyer();
                if (randomShip(field, destroyer, Destroyer.getSIZE(), showShips)) break;
            }
        }
        for (int i = 0; i < field.getCountBoat(); i++) {
            while (true) {
                Ship boat = new Boat();
                if (randomShip(field, boat, Boat.getSIZE(), showShips)) break;
            }
        }
    }

    private boolean randomShip(Field field, Ship ship, int size, boolean showShips) {
        FieldManager fm = new FieldManager();
        Cell cell = field.getCells().get(fm.createRandomKey(field));
        Object randomDirection = direction.getRandomInstance();
        boolean result = putShip(field, ship, cell, size, randomDirection, showShips);
        if (result) {
            field.getShips().add(ship);
            return true;
        }
        return false;
    }


    //разместить корабль на карту
    public boolean putShip(Field field, Ship ship, Cell cell, int length, Object direction, boolean showShips) {
        FieldManager fm = new FieldManager();
        HashMap<String, Cell> cells = field.getCells();
        ArrayList<Cell> verifiedCells = new ArrayList();
        //попытка поставить корабль
        for (int i = 0; i < length; i++) {
            if (cell == null || cell.getStatus() != Status.VOID || fm.checkNeighbors(field, cell, verifiedCells) == false) {
                return false;
            }
            verifiedCells.add(cell);
            int x = cell.getX();
            int y = cell.getY();

            if (UP.equals(direction)) {
                y++;
            } else if (RIGHT.equals(direction)) {
                x++;
            } else if (DOWN.equals(direction)) {
                y--;
            } else if (LEFT.equals(direction)) {
                x--;
            }
            cell = cells.get(fm.createKey(x, y));
        }
        //если удалось
        for (int i = 0; i < verifiedCells.size(); i++) {
            verifiedCells.get(i).setStatus(Status.SHIP);
            ship.addCell(verifiedCells.get(i));
            new DecorationController().showStatus(verifiedCells.get(i), showShips);
        }
        return true;
    }

    public Player initPlayer(Field field, String name) {
        Player player = new Player(field, name);
        return player;
    }

    public Player initBot(Field field, String name) {
        Player bot = new Bot(field, name);
        return bot;
    }
}
