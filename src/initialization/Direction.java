package initialization;

import java.util.ArrayList;
import java.util.List;

public enum Direction {

    UP(),
    RIGHT(),
    DOWN(),
    LEFT();

     /*
                         0
                         |
        direction   3 -- d -- 1
                         |
                         2
     */


    public static Object getRandomInstance() {
        List arrayList = new ArrayList();
        arrayList.add(UP);
        arrayList.add(RIGHT);
        arrayList.add(DOWN);
        arrayList.add(LEFT);
        int randomNum = (int) (Math.random() * arrayList.size());
        return arrayList.get(randomNum);
    }
}
