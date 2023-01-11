import at.fhtw.swen1.mcg.httpserver.server.Server;
import at.fhtw.swen1.mcg.httpserver.service.pcakages.packageService;
import at.fhtw.swen1.mcg.httpserver.service.sessions.sessionService;
import at.fhtw.swen1.mcg.httpserver.utils.Router;
import at.fhtw.swen1.mcg.httpserver.service.users.userService;

import java.io.IOException;

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
        router.addService("/sessions", new sessionService());
        router.addService("/packages", new packageService());

        return router;
    }
}
