import wp.example.chess.ChessDashboard;
import wp.example.chess.testExceptions;

import java.util.Scanner;
//import wp.example.chess.ChessPiece;
import java.util.HashMap;

public class ChessEngine {
    public static void main(String[] args) {
        System.out.println("GAME STARTED! \n");
        ChessDashboard dashboard1 =  new ChessDashboard();

        //testExceptions test = new testExceptions();

        //System.out.println("Chess" +  dashboard1.getDashboardField(0,0));
        //Move w
//        dashboard1.moveDashboardField(1, 0, 3, 0);
//
//        //Move b
//        dashboard1.moveDashboardField(6, 1, 4, 1);
//        dashboard1.moveDashboardField(4, 1, 2, 1);
//        //Attack
//        dashboard1.moveDashboardField(0, 0, 2, 0);
//        dashboard1.moveDashboardField(2, 0, 2, 1);
        //dashboard1.moveDashboardField(0, 0, 4, 0);

        //dashboard1.showDashboardFieldsId();
        //dashboard1.showDashboardFields();

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

            //dashboard.moveDashboardField(A1,D5);
            String coordinates[] = playerMove.split(" ");

            try {
                if (dashboard1.movePiece(coordinates[0], coordinates[1], Players[movesToken]) == 0) {
                    movesToken = 1 - movesToken;
                    dashboard1.showDashboardFields();
                    movesCounter++;
                }
            } catch (Exception e) {
                System.out.println("Something went wrong while moving. Repeat the moving.");
                e.printStackTrace();
            }
        }
    }
}
