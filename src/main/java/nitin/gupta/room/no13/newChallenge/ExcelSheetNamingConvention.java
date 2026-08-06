package nitin.gupta.room.no13.newChallenge;

public class ExcelSheetNamingConvention {

    public static void main(String[] args) {
        System.out.println(getExcelColumnName(53));   // BA
        System.out.println(getExcelColumnName(1));    // A
        System.out.println(getExcelColumnName(26));   // Z
        System.out.println(getExcelColumnName(27));   // AA
        System.out.println(getExcelColumnName(702));  // ZZ
        System.out.println(getExcelColumnName(703));  // AAA
    }

    static String getExcelColumnName(int n) {
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            n--;                              // shift to 0-based for this "digit"
            int remainder = n % 26;
            sb.append((char) ('A' + remainder));
            n /= 26;
        }
        return sb.reverse().toString();
    }
}