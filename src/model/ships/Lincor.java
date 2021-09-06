package model.ships;

import java.util.ArrayList;

public class Lincor extends Ship {
    private static final int SIZE = 4;

    public Lincor() {
        super.cells = new ArrayList<>(SIZE);
    }

    public static int getSIZE() {
        return SIZE;
    }
}
