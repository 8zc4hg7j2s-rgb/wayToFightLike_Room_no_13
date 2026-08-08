package map;

import nitin.gupta.room.no13.datastructure.map.CustomMapPr1;

public class CustomMapPr1Testing {
    static void main() {
        CustomMapPr1<Integer, Integer> bucket = new CustomMapPr1<>(10);
        bucket.put(1, 1);
        bucket.put(11, 2);
        bucket.put(21, 3);
        bucket.put(2, 1);
        bucket.put(13, 2);
        bucket.put(25, 3);
        bucket.printAll();
        bucket.remove(1);
        bucket.remove(2);
        bucket.get(11);
        bucket.printAll();
    }
}
