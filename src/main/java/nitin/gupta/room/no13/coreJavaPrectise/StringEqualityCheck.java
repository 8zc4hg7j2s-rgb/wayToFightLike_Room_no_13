package nitin.gupta.room.no13.coreJavaPrectise;

public class StringEqualityCheck {
    public static void main(String[] args) {
        String s = "java";
        Object object = s;
        String k = new String("java");
        if (object.equals(s)) {
            System.out.println("A");
        } else {
            System.out.println("B");
        }
        if (s == object) {
            System.out.println("C");
        } else {
            System.out.println("D");
        }
        if (k.equals(s)) {
            System.out.println("C");
        } else {
            System.out.println("D");
        }
        if (k == s) {
            System.out.println("C");
        } else {
            System.out.println("D");
        }
    }
}
