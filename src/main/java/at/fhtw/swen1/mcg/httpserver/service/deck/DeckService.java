package at.fhtw.swen1.mcg.httpserver.service.deck;

import at.fhtw.swen1.mcg.dto.Card;
import at.fhtw.swen1.mcg.dto.User;
import at.fhtw.swen1.mcg.httpserver.http.ContentType;
import at.fhtw.swen1.mcg.httpserver.http.HttpStatus;
import at.fhtw.swen1.mcg.httpserver.server.Request;
import at.fhtw.swen1.mcg.httpserver.server.Response;
import at.fhtw.swen1.mcg.httpserver.server.Service;
import at.fhtw.swen1.mcg.httpserver.service.AuthorizationService;
import at.fhtw.swen1.mcg.persistence.DeckRepository;

import java.util.List;

public class DeckService implements Service {
    @Override
    public Response handleRequest(Request request){
        if(!(request.getMethod().toString().equals("GET"))){
            return new Response(HttpStatus.BAD_REQUEST,
                    ContentType.PLAIN_TEXT,
                    "can't POST to deck");
        }

        User player = AuthorizationService.authorizeUser(request);

        List<Card> deck = DeckRepository.getUsersDeck(player);
        String response = "";

        if(deck == null){
            response = "There are no decks available for this user";
        }
        else{
            for (Card card: deck
                 ) {
                response = response + card.getName() + " | " + card.getDamage() + "\n";
            }
        }


        return new Response(HttpStatus.OK,
                ContentType.PLAIN_TEXT,
                response);
    }
}
