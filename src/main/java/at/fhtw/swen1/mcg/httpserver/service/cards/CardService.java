package at.fhtw.swen1.mcg.httpserver.service.cards;

import at.fhtw.swen1.mcg.dto.Card;
import at.fhtw.swen1.mcg.dto.User;
import at.fhtw.swen1.mcg.httpserver.http.ContentType;
import at.fhtw.swen1.mcg.httpserver.http.HttpStatus;
import at.fhtw.swen1.mcg.httpserver.server.Request;
import at.fhtw.swen1.mcg.httpserver.server.Response;
import at.fhtw.swen1.mcg.httpserver.server.Service;
import at.fhtw.swen1.mcg.httpserver.service.AuthorizationService;
import at.fhtw.swen1.mcg.httpserver.service.users.UserData;
import at.fhtw.swen1.mcg.persistence.CardRepository;
import at.fhtw.swen1.mcg.persistence.UserRepository;
import org.json.JSONObject;

import java.util.List;

public class CardService implements Service {
    @Override
    public Response handleRequest(Request request){
        if(!(request.getMethod().toString().equals("GET"))){
            return new Response(HttpStatus.BAD_REQUEST,
                    ContentType.PLAIN_TEXT,
                    "can't POST to cards");
        }

        User player = AuthorizationService.authorizeUser(request);
        List<Card> usersCards = player.getCardsInStack();
        String response = "";
        for (Card card : usersCards
             ) {
            response = response + card.getName() + " | " + card.getDamage() + "\n";
        }

        return new Response(HttpStatus.OK,
                ContentType.PLAIN_TEXT,
                response);
    }
}
