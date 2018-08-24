package wp.example.chess;
import java.lang.Math;
import java.util.HashMap;

public class ChessDashboard {
    private static String PLAYERS[] = {"WHITE", "BLACK"};

    private static String INIT_PIECES_ROWS[][] = {
            {"ROOK", "KNIGHT", "BISHOP", "QUEEN", "KING", "BISHOP", "KNIGHT", "ROOK"},
            {"PAWN", "PAWN", "PAWN", "PAWN", "PAWN", "PAWN", "PAWN", "PAWN"}
    };
    private static int INIT_ROWS_POSITIONS[][] = {
            {0, 1},
            {7, 6}
    };
    private static int fieldsContent[][] = new int[8][8];
    //stringBuilder s = new stringBuilder();

    public ChessPiece pieces[] = new ChessPiece[33];

    static final HashMap<String, String> PIECE_MOVEMENT_VECTOR = new HashMap<String, String>();
    /* substrings define allowed finished moves:
    0 - through diagonal,
    1 - change row,
    2 - change col,
    3 - reverse move

    available shapes of move trace:
    5 - diagonal,
    6 - line,
    7 - trace like "L" shape

    9 - max stride of move size
    */

    static {
        PIECE_MOVEMENT_VECTOR.put("KING", "1111-110-1");
        PIECE_MOVEMENT_VECTOR.put("ROOK", "0111-010-8");
        PIECE_MOVEMENT_VECTOR.put("BISHOP","1001-100-8");
        PIECE_MOVEMENT_VECTOR.put("QUEEN","1111-110-8");
        PIECE_MOVEMENT_VECTOR.put("KNIGHT", "0111-001-3");
        PIECE_MOVEMENT_VECTOR.put("PAWN", "0101-010-2");
    }

    public ChessDashboard() {
        int j = 1;

        for (int row = 0; row < INIT_ROWS_POSITIONS.length; row++) {
            for (int col = 0; col < INIT_ROWS_POSITIONS[row].length; col++) {

                int dashboardRow = INIT_ROWS_POSITIONS[row][col];

                for (int dashboardCol = 0; dashboardCol < INIT_PIECES_ROWS[col].length; dashboardCol++) {

                    String pieceName = INIT_PIECES_ROWS[col][dashboardCol] + "_" + PLAYERS[row].substring(0,1);
                    //System.out.println(pieceName);

                    //Create piece object and get id
                    pieces[j] = new ChessPiece(INIT_PIECES_ROWS[col][dashboardCol], pieceName, PLAYERS[row]);
                    fieldsContent[dashboardRow][dashboardCol] = pieces[j].getId();
                    j++;
                }
            }
        }
    }

    public void setDashboardFieldId(int row, int col, int pieceId) {
        this.fieldsContent[row][col] = pieceId;
    }

    public int getDashboardFieldId(int row, int col) {
        if (row <= fieldsContent.length && col <= fieldsContent[row].length) {
            return this.fieldsContent[row][col];
        } else {
            return 32;
        }
    }

    public void clearDashboardField(int row, int col) {
        this.fieldsContent[row][col] = 0;
    }

    public void moveDashboardField(int row, int col, int row_new, int col_new) {

        int fromFieldId = getDashboardFieldId(row, col);
        int toFieldId = getDashboardFieldId(row_new, col_new);

        System.out.println("Content target field " + toFieldId);

        //Check pieces rules
        String currMove = (getMovementChangeVector(row, col, row_new, col_new));
        String currTrace = (getMovementTrace(row, col, row_new, col_new));

        System.out.println("currMove:" + currMove);
        System.out.println("currTrace:" + currTrace);

        //Check dashboard rules
        if (row == row_new && col == col_new) {
            System.out.println("Rule 0: It is not possible to move piece into the same field");
        } else if (fromFieldId == 0) {
            System.out.println("Rule 1: There is no piece on the indicated field!");
        } else if (fromFieldId == 32 || toFieldId == 32) {
            System.out.println("Rule 2: One of the indicated field not exists on dashboard!");
        } else if (toFieldId != 0 && pieces[fromFieldId].getColor() == pieces[toFieldId].getColor()) {
            System.out.println("Rule 3: It is not possible to move piece into field with the same colour!");
        } else {

            if (
                isCorrectMovementVector(currMove, pieces[fromFieldId].getType()) &&
                isCorrectMovementTrace(currTrace, pieces[fromFieldId].getType()) &&
                isCorrectMovementStride(row, col, row_new, col_new, pieces[fromFieldId].getType())
                && isMovementCollision(row, col, row_new, col_new, currMove) == 0
            ) {
                setDashboardFieldId(row_new, col_new, fromFieldId);
                clearDashboardField(row, col);

//                if (pieces[fromFieldId].getColor() != pieces[toFieldId].getColor()) {
//                    clearDashboardField(row, col);
//                }

            } else {
                System.out.println("Rule 4: It is not possible to move piece in this place!");

                System.out.println("isMovementCollision " + isMovementCollision(row, col, row_new, col_new, currMove));
                System.out.println("isCorrectMovementVector " + isCorrectMovementVector(currMove, pieces[fromFieldId].getType()));
                System.out.println("isCorrectMovementTrace " + isCorrectMovementTrace(currTrace, pieces[fromFieldId].getType()));
                System.out.println("isCorrectMovementStride " + isCorrectMovementStride(row, col, row_new, col_new, pieces[fromFieldId].getType()));
            }
        }
    }

    public void showDashboardFieldsId() {
        for (int row = 0; row < fieldsContent.length; row++) {
            for (int k = 0; k < fieldsContent[row].length; k++) {
                System.out.print(fieldsContent[row][k] + "\t");
            }
            System.out.println();
        }
    }

    public int findKings() {
        int cntKings = 0;

        for (int i = 1; i < pieces.length; i++) {
            if ( pieces[i].getType().equals("KING") ) {
                cntKings++;
            }
        }
        return cntKings;
    }

    public void showDashboardFields() {

        ChessUtils customStr = new ChessUtils();
        String fieldContent;
        String signsImitationFields[] = {" ", ":"};
        int startSign = 0;

        for (int row = fieldsContent.length - 1; row >= 0; row--) {
            startSign = 1 - startSign;
            String endField = "";

            for (int k = 0; k < fieldsContent[row].length; k++) {
                startSign = 1 - startSign;
                String lastSignInRow = "";

                if (fieldsContent[row][k] != 0) {
                    fieldContent = customStr.strExtend(pieces[fieldsContent[row][k]].getName(), signsImitationFields[startSign]);
                    //System.out.println(fieldContent);
                } else {
                    fieldContent = customStr.strExtend("", signsImitationFields[startSign]);
                }

                if (k == fieldsContent[row].length - 1) {lastSignInRow = "|";}
                System.out.print("|" + fieldContent + lastSignInRow);
                endField = endField + "|" + customStr.strExtend("", signsImitationFields[startSign]);
            }

            endField = endField + "|";
            System.out.println("\n" + endField + "\n" + endField + "\n" + endField + "\n" + endField);
        }
    }

    String getMovementChangeVector (int row, int col, int row_new, int col_new) {
        StringBuilder mv = new StringBuilder();

        int diffRow = row_new - row;
        int diffCol = col_new - col;

        if (row != row_new && col != col_new && Math.abs(diffRow) == Math.abs(diffCol)) {
            mv.append("100");
        } else if (row == row_new) {
            mv.append("001");
        } else if (col == col_new) {
            mv.append("010");
        } else {
            //Knight`s vector
            mv.append("011");
        }

        if (diffRow < 0 || diffCol < 0) {mv.append("1");}

        String moveVector = mv.toString();
        return moveVector;
    }

    String getMovementTrace (int row, int col, int row_new, int col_new) {
        StringBuilder mt = new StringBuilder();

        if (row != row_new && col != col_new && Math.abs(row_new - row) == Math.abs(col_new - col)) {
            mt.append("100");
        } else if ( (Math.abs(row_new - row) == 2 && (Math.abs(col_new - col) == 1)) ||  (Math.abs(row_new - row) == 1 && (Math.abs(col_new - col) == 2)) ) {
            mt.append("001");
        } else if (row == row_new || col == col_new) {
            mt.append("010");
        } else {
            mt.append("000");
        }
        String moveTrace = mt.toString();
        return moveTrace;
    }

    boolean isCorrectMovementVector(String movementVector, String pieceType) {
        String availableDirections = PIECE_MOVEMENT_VECTOR.get(pieceType).substring(0,3);

        System.out.println("Try move " + availableDirections + " by " + pieceType);

        for (int i = 0; i < availableDirections.length(); i++) {
            System.out.println(availableDirections.substring(i, i + 1) + " " + movementVector.substring(i, i + 1));

            if (availableDirections.substring(i, i + 1).equals(movementVector.substring(i, i + 1))
                    && availableDirections.substring(i, i + 1).equals("1")) {
                return true;
            }
        }
        return false;
    }

    boolean isCorrectMovementTrace (String movementVector, String pieceType) {
        String availableDirections = PIECE_MOVEMENT_VECTOR.get(pieceType).substring(5,8);
        System.out.println("Try trace " + availableDirections + " by " + pieceType);
        for (int i = 0; i < availableDirections.length(); i++) {

            System.out.println(availableDirections.substring(i, i + 1) + " " + movementVector.substring(i, i + 1));

            if (availableDirections.substring(i, i + 1).equals(movementVector.substring(i, i + 1))
                    && availableDirections.substring(i, i + 1).equals("1")) {
                return true;
            }
        }
        return false;
    }

    boolean isCorrectMovementStride (int row, int col, int row_new, int col_new, String pieceType) {
        String maxStride = PIECE_MOVEMENT_VECTOR.get(pieceType).substring(9);
        int maxStrideInt = Integer.parseInt(maxStride);
        int triedStride;

        System.out.println("maxStrideInt " + maxStrideInt);

        if (Math.abs(row_new - row) > Math.abs(col_new - col)) {
            triedStride = Math.abs(row_new - row);
        } else {
            triedStride = Math.abs(col_new - col);
        }

        if (pieceType.equals("KNIGHT")) {
            //Checked in isCorrectMovementTrace
            return true;
        } else {
            if (triedStride <= maxStrideInt) {
                return true;
            } else {
                return false;
            }
        }
    }

    int isMovementCollision (int row, int col, int row_new, int col_new, String moveType) {

        String moveTypeDirection = moveType.substring(0,3);
        String moveDirection = moveType.substring(3);
        int incrementStepValue;
        int targetField;

        if (moveDirection.equals("1")) {
            incrementStepValue = 1;
        } else {
            incrementStepValue = 1;
        }

        if (moveTypeDirection.equals("100") || moveTypeDirection.equals("010")) {
            targetField = row_new;
        } else {
            targetField = col_new;
        }

        for (int i  = row + 1, j = col + 1; i < targetField; i = i + incrementStepValue, j = j + incrementStepValue) {

            switch (moveTypeDirection) {
                case "100":
                    if(getDashboardFieldId(i,j) != 0) {return getDashboardFieldId(i,j);}
                case "001":
                    if(getDashboardFieldId(row,j) != 0) {return getDashboardFieldId(row,j);}
                case "010":
                    if(getDashboardFieldId(i,col) != 0) {
                        System.out.println("Check collission for move: " + moveTypeDirection + " " +
                        getDashboardFieldId(i,col) + " " + pieces[fieldsContent[i][col]].getName() + " " + i + col);
                        return getDashboardFieldId(i,col);
                    }
                case "011":
            }
            //getDashboardFieldId();
        }

        return 0;
    }
}
