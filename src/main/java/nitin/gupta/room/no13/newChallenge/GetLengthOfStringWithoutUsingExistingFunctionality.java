package nitin.gupta.room.no13.newChallenge;

public class GetLengthOfStringWithoutUsingExistingFunctionality {
    public static void main(String[] args) {
        String  str = "jfaskfasda";
        int counter=0;
        try{
            for(; str.charAt(counter)!='\0'; counter++) ;
        }catch(Exception e){
            //ArrayIndexOutOfBoundsException
            System.out.println("Length: " + counter);
        }
    }
}
