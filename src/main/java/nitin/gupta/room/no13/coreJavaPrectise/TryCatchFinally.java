package nitin.gupta.room.no13.coreJavaPrectise;

public class TryCatchFinally {
    public static void main(String[] args) {
        TryCatchFinally tryCatchFinally = new TryCatchFinally();
        int value = tryCatchFinally.testing(5);
        int value11 = tryCatchFinally.testing(0);
        System.out.println("value  ::" + value);
        System.out.println("value1  ::" + value11);
    }

    private int testing(int a) {
        try {
            System.out.println("aaaaaa");
            return 1000 / a;
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
            return 5;
        } finally {
            return 10;
        }
    }
}
