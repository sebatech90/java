import wp.example.chess.ChessDashboard;
import java.util.Scanner;
//import wp.example.chess.ChessPiece;
import java.util.HashMap;

public class ChessEngine {
    public static void main(String[] args) {
        System.out.println("Hello! \n");
        ChessDashboard dashboard1 =  new ChessDashboard();

        //System.out.println("Chess" +  dashboard1.getDashboardField(0,0));
        //Move w
        dashboard1.moveDashboardField(1, 0, 3, 0);

        //Move b
        dashboard1.moveDashboardField(6, 1, 4, 1);
        dashboard1.moveDashboardField(4, 1, 2, 1);
        //Attack
        dashboard1.moveDashboardField(0, 0, 2, 0);
        dashboard1.moveDashboardField(2, 0, 2, 1);
        //dashboard1.moveDashboardField(0, 0, 4, 0);

        //dashboard1.showDashboardFieldsId();
        dashboard1.showDashboardFields();

        int mainCounter = 0;

        while (dashboard1.findKings() == 2) {

            String turn;

            Scanner cmdMove = new Scanner(System.in);

            turn = cmdMove.nextLine();

            System.out.println("turn " + mainCounter);

            mainCounter++;
        }
    }
}
