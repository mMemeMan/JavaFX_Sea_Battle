package model.ships;

import java.util.ArrayList;

public class Cruiser extends Ship{
    private static final int SIZE = 3;

    public Cruiser() {
        super.cells = new ArrayList<>(SIZE);
    }

    public static int getSIZE() {
        return SIZE;
    }
}
