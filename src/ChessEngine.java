import wp.example.chess.ChessDashboard;
import java.util.Scanner;

public class ChessEngine {
    public static void main(String[] args) {
        System.out.println("GAME STARTED! \n");
        ChessDashboard dashboard1 =  new ChessDashboard();

        int movesCounter = 0;
        int movesToken = 0;
        String playerMove;
        String[] Players = dashboard1.getPlayerType();
        Scanner cmdMove = new Scanner(System.in);
        dashboard1.showDashboardFields();

        while (dashboard1.findKings() == 2 && movesCounter < 1000) {

            System.out.println(Players[movesToken] + "`S MOVE");
            playerMove = cmdMove.nextLine();
            System.out.println("try turn to " + playerMove + " It`s " + movesCounter + " move number");

            String coordinates[] = playerMove.split(" ");

            try {
                dashboard1.movePiece(coordinates[0], coordinates[1], Players[movesToken]);
                movesToken = 1 - movesToken;
                dashboard1.showDashboardFields();
                movesCounter++;
            }
            catch (ArithmeticException | ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
                System.out.println("Invalid data. Repeat the moving");
                e.printStackTrace();
            }
            catch (Exception e) {
                System.out.println("Something went wrong while moving. Repeat the moving.");
                e.printStackTrace();
            }
        }
    }
}
