package nitin.gupta.room.no13.coreJavaPrectise.StreamAPI;


@FunctionalInterface
interface Test1 {
    void display1();
}

@FunctionalInterface
interface Test2 extends Test1 {
    void display1();
}

@FunctionalInterface
interface Test3 extends Test2, Test1 {
    void display1();
}

@FunctionalInterface
interface ShortToByteFunction {
    byte applyAsByte(short s);
}

public class FunctionalInterfaceTesting {
    static void main() {
        // Implementing Test3 (which transitively satisfies Test1 and Test2)
        Test3 t3 = () -> System.out.println("Displaying from Test3");
        t3.display1();

        // Implementing ShortToByteFunction
        ShortToByteFunction converter = (short s) -> (byte) (s & 0xFF);
        byte result = converter.applyAsByte((short) 258);
        System.out.println("Result: " + result); // Output: 2
    }
}
