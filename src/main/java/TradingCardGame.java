import at.fhtw.swen1.mcg.dto.Battle;
import at.fhtw.swen1.mcg.dto.User;
import at.fhtw.swen1.mcg.httpserver.server.Server;
import at.fhtw.swen1.mcg.httpserver.utils.Router;
import at.fhtw.swen1.mcg.persistence.UserRepository;
import at.fhtw.swen1.mcg.service.users.userService;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class TradingCardGame {
    public static void main(String[] args) {
        Server server = new Server(10001, configureRouter());
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Router configureRouter()
    {
        Router router = new Router();
        router.addService("/users", new userService());

        return router;
    }
}
