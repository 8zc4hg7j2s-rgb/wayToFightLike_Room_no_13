package map;

import nitin.gupta.room.no13.datastructure.map.PMMPinCustomMap;

public class PMMPinCustomMapTest {

    public static void main(String[] args) {
        PMMPinCustomMap<Integer,Integer> bucket = new PMMPinCustomMap<>(10);
        bucket.put(1, 1);
        bucket.put(11, 2);
        bucket.put(21, 3);
        bucket.put(2, 1);
        bucket.put(3, 2);
        bucket.put(7, 3);
        bucket.put(17, 1);
        bucket.put(13, 2);
        bucket.put(24, 3);
        bucket.printAll();
    }
}
