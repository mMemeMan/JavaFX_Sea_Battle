package model.ships;

import java.util.ArrayList;

public class Destroyer extends Ship {
    private static final int SIZE = 2;

    public Destroyer() {
        super.cells = new ArrayList<>(SIZE);
    }

    public static int getSIZE() {
        return SIZE;
    }
}
