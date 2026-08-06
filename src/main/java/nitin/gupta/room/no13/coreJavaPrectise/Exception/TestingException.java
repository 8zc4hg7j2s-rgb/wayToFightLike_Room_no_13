package nitin.gupta.room.no13.coreJavaPrectise.Exception;

public class TestingException {
    public static void main(String[] args) {
        try {
            throw new CustomException();
        } catch (CustomException ex) {
            System.out.println(2);
            throw new ArrayIndexOutOfBoundsException("Testing 1");
        } catch (RuntimeException ex) {
            System.out.println(1);
            throw new ArrayIndexOutOfBoundsException("Testing 2");
        } catch (Exception ex) {
            System.out.println(3);
            throw new ArrayIndexOutOfBoundsException("Testing 3");
        } finally {
            System.out.println(4);
            // throw new ArrayIndexOutOfBoundsException("Testing 4");
        }
    }
}