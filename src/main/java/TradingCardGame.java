import at.fhtw.swen1.mcg.dto.Battle;
import at.fhtw.swen1.mcg.dto.User;
import at.fhtw.swen1.mcg.httpServer.EchoGetHandler;
import at.fhtw.swen1.mcg.httpServer.RootHandler;
import at.fhtw.swen1.mcg.httpServer.echoHeader;
import at.fhtw.swen1.mcg.httpServer.echoPostHandler;
import at.fhtw.swen1.mcg.persistence.UserRepository;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.Scanner;

public class TradingCardGame {
    public static void main(String[] args) {

        int port = 9000;
        try{
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            System.out.println("server started at " + port);
            server.createContext("/", new RootHandler());
            server.createContext("/echoHeader", new echoHeader());
            server.createContext("/echoGet", new EchoGetHandler());
            server.createContext("/echoPost", new echoPostHandler());
            server.setExecutor(null);
            server.start();
        }
        catch (Exception ex){
            System.out.println(ex);
        }

        /*Scanner myObj = new Scanner(System.in);  // Create a Scanner object

        while(true){
            System.out.println("Choose option: Create new user(c), login(l)");
            String option = myObj.nextLine();  // Read user input
            if(option.equals("c")){
                UserRepository.createUser();
            }else if(option.equals("l")){
                break;
            }
            else{
                System.out.println("Invalid input");
            }
        }
        User player1 = null;
        while(player1 == null){
            player1 = UserRepository.loginUser();
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
                player1 = UserRepository.shop(player1);
            }else {
                System.out.println("invalid input");
            }
        }
        */
    }
}
