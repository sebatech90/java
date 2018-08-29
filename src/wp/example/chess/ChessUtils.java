package wp.example.chess;

public class ChessUtils {
    public String strExtend(String str, String signImitation, boolean drawOnlyBottom) {

        int strLenght = str.length();
        int limit = 10;
        if (drawOnlyBottom) {limit = 13;}

        for (int i = 0; i <=  limit - strLenght; i++) {
            str = str + signImitation;
        }
        return str;
    }

    public String[][] reverseArray(String[][] array) {

        int len_row = array.length;

        String newArr[][] = new String[len_row][];

        int j = 0;
        for (int i = array.length - 1; i >= 0; i--) {
            for (int k = 0; k < array[i].length; k++) {
                newArr[j][k] = array[i][k];
                j++;
            }
        }
        return newArr;
    }
}

