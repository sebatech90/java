import wp.example.chess.ChessDashboard;
import java.util.Scanner;

public class ChessEngine {
    public static void main(String[] args) {

        ChessDashboard dashboard1 =  new ChessDashboard();

        int triedMovesCounter = 0;
        int movesToken = 0;
        String playerMove;
        String[] Players = dashboard1.getPlayerType();
        Scanner cmdMove = new Scanner(System.in);
        dashboard1.showDashboardFields();

        System.out.println("\n\n" + "CHESS GAME STARTED! \n" + "ENTER MOVE - FOR EXAMPLE A1 A3 \n");

        while (dashboard1.findKings() == 2 && triedMovesCounter < 1000) {

            System.out.println(Players[movesToken] + "`S MOVE \n");
            playerMove = cmdMove.nextLine();

            try {
                String coordinates[] = playerMove.split(" ");

                System.out.println("TRY TURN FROM " + coordinates[0] + " TO " + coordinates[1]
                + "\n" + "THERE`S " + (triedMovesCounter)++ + " MOVE NUMBER");

                dashboard1.movePiece(coordinates[0], coordinates[1], Players[movesToken]);
                movesToken = 1 - movesToken;
                dashboard1.showDashboardFields();
            }
            catch (ArithmeticException | ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
                System.out.println("INVALID DATA. REPEAT THE MOVING");
                //e.printStackTrace();
            }
            catch (Exception e) {
                System.out.println("SOMETHING WENT WRONG WHILE MOVING. REPEAT THE MOVING.");
                //e.printStackTrace();
            }
        }
    }
}
