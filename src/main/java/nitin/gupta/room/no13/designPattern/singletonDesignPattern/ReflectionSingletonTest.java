package nitin.gupta.room.no13.designPattern.singletonDesignPattern;

import java.lang.reflect.Constructor;

public class ReflectionSingletonTest {

    public static void main(String[] args) {
        DemoSingleton instanceOne = DemoSingleton.getInstance();
        DemoSingleton instanceTwo = null;
        try {
            Constructor[] constructors = DemoSingleton.class.getDeclaredConstructors();
            for (Constructor constructor : constructors) {
                // This code will destroy the singleton pattern
                constructor.setAccessible(true);
                instanceTwo = (DemoSingleton) constructor.newInstance();
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(instanceOne.hashCode());
        System.out.println(instanceTwo.hashCode());
    }

}
