package model.field;

import model.ships.Ship;

import java.util.ArrayList;
import java.util.HashMap;

public class Field implements Standard {

    private HashMap<String, Cell> cells = new HashMap(HEIGHT * WIDTH);

    private ArrayList<Cell> livesCells = new ArrayList<>();

    private ArrayList<Ship> ships = new ArrayList(COUNT_SHIPS);

    public HashMap<String, Cell> getCells() {
        return cells;
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public ArrayList<Cell> getLivesCells() {
        return livesCells;
    }

    public void setLivesCells(ArrayList<Cell> livesCells) {
        this.livesCells = livesCells;
    }

    public int getWidth() {
        return WIDTH;
    }


    @Override
    public int getCountLincor() {
        return LINCOR;
    }

    @Override
    public int getCountCruiser() {
        return CRUISER;
    }

    @Override
    public int getCountDestroyer() {
        return DESTROYER;
    }

    @Override
    public int getCountBoat() {
        return BOAT;
    }

}
