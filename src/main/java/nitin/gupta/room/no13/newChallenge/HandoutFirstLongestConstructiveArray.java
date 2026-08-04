package nitin.gupta.room.no13.newChallenge;

public class HandoutFirstLongestConstructiveArray {
    public static void main(String[] args) {
        int[] intArray = {0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1};

        int maxLength = 0;
        int maxStartIndex = -1;

        int currentLength = 0;
        int currentStartIndex = 0;

        for (int i = 0; i < intArray.length; i++) {
            if (intArray[i] == 1) {
                // If starting a new run of 1s, record the start index
                if (currentLength == 0) {
                    currentStartIndex = i;
                }
                currentLength++;

                // Strictly greater than (>) guarantees we keep the FIRST longest run found
                if (currentLength > maxLength) {
                    maxLength = currentLength;
                    maxStartIndex = currentStartIndex;
                }
            } else {
                // Reset length counter on 0
                currentLength = 0;
            }
        }

        System.out.println("First longest sequence length: " + maxLength);
        System.out.println("Starts at index: " + maxStartIndex);

        // Print the target subarray
        System.out.print("Subarray: ");
        for (int i = maxStartIndex; i < maxStartIndex + maxLength; i++) {
            System.out.print(intArray[i] + " ");
        }
    }
}
