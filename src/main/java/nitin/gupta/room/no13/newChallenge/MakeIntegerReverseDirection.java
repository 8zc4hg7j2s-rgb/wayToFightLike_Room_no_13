package nitin.gupta.room.no13.newChallenge;

public class MakeIntegerReverseDirection {
    public static void main(String[] args) {
        int k = Integer.MAX_VALUE;
        System.out.println(k);
        String reverse = String.valueOf(k).chars()
                .mapToObj(c -> (char) c)
                .map(String::valueOf)
                .reduce((a, b) ->  b + a)
                .get();
        System.out.println(reverse);
    }
}
