package wp.example.chess;
import java.lang.Math;
import java.util.HashMap;

public class ChessDashboard {
    private static String PLAYERS[] = {"WHITE", "BLACK"};

    private static String PIECE_TYPES_IN_INITIAL_ORDER[][] = {
            {"ROOK", "KNIGHT", "BISHOP", "QUEEN", "KING", "BISHOP", "KNIGHT", "ROOK"},
            {"PAWN", "PAWN", "PAWN", "PAWN", "PAWN", "PAWN", "PAWN", "PAWN"}
    };
    private static int PIECE_ROWS_INITIAL_LOCATION[][] = {
            {0, 1},
            {7, 6}
    };
    private static int dashboardFieldsContent[][] = new int[8][8];
    //stringBuilder s = new stringBuilder();

    public ChessPiece pieces[] = new ChessPiece[32];

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

    static final HashMap<String, Integer> PIECE_MOVEMENT_CONVERSION = new HashMap<String, Integer>();

    static {
        PIECE_MOVEMENT_CONVERSION.put("A", 0);
        PIECE_MOVEMENT_CONVERSION.put("B", 1);
        PIECE_MOVEMENT_CONVERSION.put("C", 2);
        PIECE_MOVEMENT_CONVERSION.put("D", 3);
        PIECE_MOVEMENT_CONVERSION.put("E", 4);
        PIECE_MOVEMENT_CONVERSION.put("F", 5);
        PIECE_MOVEMENT_CONVERSION.put("G", 6);
        PIECE_MOVEMENT_CONVERSION.put("H", 7);
    }

    public ChessDashboard() {
        int objNum = 0;

        for (int i = 0; i < PIECE_ROWS_INITIAL_LOCATION.length; i++) {
            for (int j = 0; j < PIECE_ROWS_INITIAL_LOCATION[i].length; j++) {

                int dashboardRow = PIECE_ROWS_INITIAL_LOCATION[i][j];
                int dashboardCol = 0;

                for (String piece : PIECE_TYPES_IN_INITIAL_ORDER[j]) {
                    //Create piece object and get id
                    String pieceName = piece + "_" + PLAYERS[i].substring(0,1);
                    //pieces[objNum] = new ChessPiece(piece, pieceName, PLAYERS[i]);

                    ChessPiece createdPiece = new ChessPiece(piece, pieceName, PLAYERS[i]);
                    dashboardFieldsContent[dashboardRow][dashboardCol] = createdPiece.getId();
                    pieces[createdPiece.getId() - 1] = createdPiece;
                    dashboardCol++;
                    objNum++;
                }
            }
        }
    }

    public void setDashboardInitialRow(int playerNum, int groupNumRow, int dashboardCol, int objNumber) {

        //System.out.println("playerNum " + playerNum + "groupNumRow " + groupNumRow);
        int dashPosRow = PIECE_ROWS_INITIAL_LOCATION[playerNum][groupNumRow];

        System.out.println("dashPosRow " + dashPosRow + " dashPosCol " + dashboardCol);
        dashboardFieldsContent[dashPosRow][dashboardCol] = pieces[objNumber].getId();
    }

    public int getDashboardFieldId(int row, int col) {
        if (row <= dashboardFieldsContent.length && col <= dashboardFieldsContent[row].length) {
            return this.dashboardFieldsContent[row][col];
        } else {
            return 32;
        }
    }

    public String[] getPlayerType () {
        return PLAYERS;
    }

    public void setDashboardFieldId(int row, int col, int pieceId) {
        this.dashboardFieldsContent[row][col] = pieceId;
    }

    public void clearDashboardField(int row, int col) {
        this.dashboardFieldsContent[row][col] = 0;
    }

    public void movePiece(String fromField, String toField, String currPlayer) throws ArrayIndexOutOfBoundsException,
            ArithmeticException, IllegalArgumentException  {

        String dashboardFromRow = fromField.substring(1,2);
        String dashboardFromCol = fromField.substring(0,1);
        String dashboardToRow = toField.substring(1,2);
        String dashboardToCol = toField.substring(0,1);

        if (
                fromField != null && !fromField.isEmpty()
                        && toField != null && !toField.isEmpty()
                        && dashboardFromCol.matches("[a-hA-h]{1}")
                        && dashboardFromRow.matches("[1-8]")
                        && dashboardToCol.matches("[a-hA-h]{1}")
                        && dashboardToRow.matches("[1-8]")
            )
        {
            int fromRow = Integer.parseInt(dashboardFromRow) - 1;
            int fromCol = PIECE_MOVEMENT_CONVERSION.get(dashboardFromCol);
            int toRow = Integer.parseInt(dashboardToRow) - 1;
            int toCol = PIECE_MOVEMENT_CONVERSION.get(dashboardToCol);

            moveDashboardField(fromRow, fromCol, toRow, toCol, currPlayer);
        } else {
            throw new IllegalArgumentException("Entered data out of range");
        }
    }

    public void moveDashboardField(int row, int col, int row_new, int col_new, String currPlayer) throws IllegalArgumentException {
        int fromFieldId = getDashboardFieldId(row, col);
        int toFieldId = getDashboardFieldId(row_new, col_new);
        boolean isCorrectMove = false;

        //System.out.println("Content target field " + toFieldId);

        //Check pieces rules
        String currMove = (getMovementChangeVector(row, col, row_new, col_new));
        String currTrace = (getMovementTrace(row, col, row_new, col_new));

        //System.out.println("currMove:" + currMove);
        //System.out.println("currTrace:" + currTrace);

        //Check dashboard rules
        if (row == row_new && col == col_new) {
            System.out.println("RULE 0: IT IS NOT POSSIBLE TO MOVE PIECE INTO THE SAME FIELD");
        } else if (fromFieldId == 0) {
            System.out.println("RULE 1: THERE IS NO PIECE ON THE INDICATED FIELD!");
        } else if (fromFieldId == 32 || toFieldId == 32) {
            System.out.println("RULE 2: ONE OF THE INDICATED FIELD NOT EXISTS ON DASHBOARD!");
        } else if (toFieldId != 0 && pieces[fromFieldId].getColor() == pieces[toFieldId].getColor()) {
            System.out.println("RULE 3: IT IS NOT POSSIBLE TO MOVE PIECE INTO FIELD WITH THE SAME COLOUR!");
        } else if (pieces[fromFieldId].getColor() != currPlayer) {
            System.out.println("RULE 4: WAIT FOR " + currPlayer + " MOVE!");
        } else {

            if (
                isCorrectMovementVector(currMove, pieces[fromFieldId].getType()) &&
                isCorrectMovementTrace(currTrace, pieces[fromFieldId].getType()) &&
                isCorrectMovementStride(row, col, row_new, col_new, pieces[fromFieldId].getType())
                && isMovementCollision(row, col, row_new, col_new, currMove) == 0
            ) {
                setDashboardFieldId(row_new, col_new, fromFieldId);
                clearDashboardField(row, col);
                isCorrectMove = true;

            } else {
                System.out.println("RULE 5: IT IS NOT POSSIBLE TO MOVE PIECE IN THIS PLACE!");

                System.out.println("isMovementCollision " + isMovementCollision(row, col, row_new, col_new, currMove));
                System.out.println("isCorrectMovementVector " + isCorrectMovementVector(currMove, pieces[fromFieldId].getType()));
                System.out.println("isCorrectMovementTrace " + isCorrectMovementTrace(currTrace, pieces[fromFieldId].getType()));
                System.out.println("isCorrectMovementStride " + isCorrectMovementStride(row, col, row_new, col_new, pieces[fromFieldId].getType()));
            }
        }

        if (!isCorrectMove) {
            throw new IllegalArgumentException("INVALID MOVE");
        }
    }

    public void showDashboardFieldsId() {
        for (int row[] : dashboardFieldsContent) {
            for (int col : row) {
                System.out.print(col + "\t");
            }
            System.out.println();
        }
    }

    public int findKings() {
        int cntKings = 0;

        for (ChessPiece objPiece : pieces) {
            //System.out.println("objPiece " + objPiece);
            if ( objPiece.getType().equals("KING") ) {
                cntKings++;
            }
        }
        return cntKings;
    }

    public void showDashboardFields() {
        ChessUtils customizeElement = new ChessUtils();

        int userDashboardView[][] = customizeElement.reverseFirstArrayDimension(dashboardFieldsContent);
        int startSign = 0;
        int rowNum = 0;
        String signsFillingFields[] = {":", " "};

        for (int row[] : userDashboardView) {
            StringBuilder descPieceField = new StringBuilder();
            StringBuilder restField = new StringBuilder();

            for (int pieceIdOnField : row) {
                if (pieceIdOnField != 0) {
                    ChessPiece objectOnField = pieces[pieceIdOnField - 1];
                    descPieceField.append(customizeElement.strExtend(objectOnField.getName(), signsFillingFields[startSign],false));
                } else {
                    descPieceField.append(customizeElement.strExtend("", signsFillingFields[startSign],false));
                }
                restField.append(customizeElement.strExtend("", signsFillingFields[startSign],false));
                startSign = 1 - startSign;
            }

            int dashboardRowLabel = userDashboardView.length - rowNum;
            String tabSign = "  ";

            System.out.println(tabSign + descPieceField);
            System.out.println(tabSign + restField);
            System.out.println(dashboardRowLabel + " " + restField.toString());
            System.out.println(tabSign + restField + "\n" + tabSign + restField);
            startSign = 1 - startSign;
            rowNum++;
        }

        StringBuilder lastRow = new StringBuilder();
        for (String key : PIECE_MOVEMENT_CONVERSION.keySet()) {
            lastRow.append("       " + key + "   ");
        }
        System.out.println(lastRow);
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

        //System.out.println("Try move " + availableDirections + " by " + pieceType);

        for (int i = 0; i < availableDirections.length(); i++) {
            //System.out.println(availableDirections.substring(i, i + 1) + " " + movementVector.substring(i, i + 1));

            if (availableDirections.substring(i, i + 1).equals(movementVector.substring(i, i + 1))
                    && availableDirections.substring(i, i + 1).equals("1")) {
                return true;
            }
        }
        return false;
    }

    boolean isCorrectMovementTrace (String movementVector, String pieceType) {
        String availableDirections = PIECE_MOVEMENT_VECTOR.get(pieceType).substring(5,8);
        //System.out.println("Try trace " + availableDirections + " by " + pieceType);

        for (int i = 0; i < availableDirections.length(); i++) {

            //System.out.println(availableDirections.substring(i, i + 1) + " " + movementVector.substring(i, i + 1));

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

        //System.out.println("maxStrideInt " + maxStrideInt);

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
                        return getDashboardFieldId(i,col);
                    }
                case "011":
            }
        }

        return 0;
    }
}
