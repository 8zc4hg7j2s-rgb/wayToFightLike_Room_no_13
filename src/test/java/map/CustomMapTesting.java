package map;

import nitin.gupta.room.no13.datastructure.map.CustomMap;

public class CustomMapTesting {
    static void main() {
        CustomMap<Integer, Integer> bucket = new CustomMap<>(10);
        bucket.put(1, 1);
        bucket.put(11, 2);
        bucket.put(21, 3);
        bucket.printAll();
    }
}
