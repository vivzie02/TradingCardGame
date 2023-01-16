import at.fhtw.swen1.mcg.httpserver.server.Server;
import at.fhtw.swen1.mcg.httpserver.service.cards.CardService;
import at.fhtw.swen1.mcg.httpserver.service.deck.DeckService;
import at.fhtw.swen1.mcg.httpserver.service.packages.PackageService;
import at.fhtw.swen1.mcg.httpserver.service.sessions.SessionService;
import at.fhtw.swen1.mcg.httpserver.service.stats.StatService;
import at.fhtw.swen1.mcg.httpserver.service.transactions.TransactionService;
import at.fhtw.swen1.mcg.httpserver.utils.Router;
import at.fhtw.swen1.mcg.httpserver.service.users.UserService;

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
        router.addService("/users", new UserService());
        router.addService("/sessions", new SessionService());
        router.addService("/packages", new PackageService());
        router.addService("/transactions", new TransactionService());
        router.addService("/cards", new CardService());
        router.addService("/deck", new DeckService());
        router.addService("/stats", new StatService());

        return router;
    }
}
