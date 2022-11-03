import javax.xml.crypto.Data;
import java.sql.*;
import java.util.Scanner;

public class TradingCardGame {
    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object

        while(true){
            System.out.println("Choose option: Create new user(c), login(l)");
            String option = myObj.nextLine();  // Read user input
            if(option.equals("c")){
                DatabaseAccess.createUser();
            }else if(option.equals("l")){
                break;
            }
            else{
                System.out.println("Invalid input");
            }
        }
        User player1 = null;
        while(player1 == null){
            player1 = DatabaseAccess.loginUser();
        }
        Battle rounds = new Battle();

        while(true){
            System.out.println("Select option: Battle(b), shop(s), deck selection(d) or quit(q)");
            String option = myObj.nextLine();  // Read user input
            if(option.equals("q")){
                break;
            } else if (option.equals("b")) {
                rounds.startBattle(player1);
            } else if (option.equals("d")) {
                player1.deckSelect();
            } else if (option.equals("s")) {
                player1 = DatabaseAccess.shop(player1);
            }else {
                System.out.println("invalid input");
            }
        }
    }
}
