package wp.example.chess;

public class ChessUtils {
    public String strExtend(String str, String signImitation) {

        int strLenght = str.length();

        for (int i = 0; i <=  10 - strLenght; i++) {
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

//        String outputStr = "";
//        int cntGapCharOnLeftSide = (12 - str.length()) / 2;
//        int cntGapCharOnRightSide = (12 - str.length() - cntGapCharOnLeftSide);
//
//        for (int i = 0; i < cntGapCharOnLeftSide; i++) {
//            outputStr = outputStr + " ";
//        }
//
//        outputStr = outputStr + str;
//
//        for (int i = 0; i < cntGapCharOnRightSide; i++) {
//            outputStr = outputStr + " ";
//        }
//        return "|" + outputStr + "|"
