import wp.example.chess.ChessDashboard;
import java.util.Scanner;

public class ChessEngine {
    private static int continueGameCondition = 2;
    private static int maxPlayerTurnsInGame = 1000;
    int triedMovesCounter;
    int playerTurnToken;
    String currPlayerMove;
    String[] Players;

    public ChessEngine () {
        this.triedMovesCounter = 0;
        this.playerTurnToken = 0;
    }

    public void startGame () {
        System.out.println("\n\n" + "CHESS GAME STARTED! \n" + "ENTER MOVE - FOR EXAMPLE A1 A3 \n");

        ChessDashboard dashboard =  new ChessDashboard();
        dashboard.showDashboardFields();
        this.Players = dashboard.getPlayerType();
        Scanner cmdMove = new Scanner(System.in);

        while (dashboard.findKings() == continueGameCondition && triedMovesCounter < maxPlayerTurnsInGame) {

            System.out.println(Players[playerTurnToken] + "`S MOVE \n");
            currPlayerMove = cmdMove.nextLine();

            try {
                String coordinates[] = currPlayerMove.split(" ");

                System.out.println("TRY TURN FROM " + coordinates[0] + " TO " + coordinates[1]
                        + "\n" + "THERE`S " + (triedMovesCounter)++ + " MOVE NUMBER");

                dashboard.movePiece(coordinates[0], coordinates[1], Players[playerTurnToken]);
                playerTurnToken = 1 - playerTurnToken;
                dashboard.showDashboardFields();
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

    public static void main(String[] args) {
        ChessEngine gameSession = new ChessEngine();
        gameSession.startGame();
    }
}
