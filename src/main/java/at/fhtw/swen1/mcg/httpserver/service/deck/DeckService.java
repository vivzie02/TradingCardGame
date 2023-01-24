package at.fhtw.swen1.mcg.httpserver.service.deck;

import at.fhtw.swen1.mcg.dto.Card;
import at.fhtw.swen1.mcg.dto.User;
import at.fhtw.swen1.mcg.httpserver.http.ContentType;
import at.fhtw.swen1.mcg.httpserver.http.HttpStatus;
import at.fhtw.swen1.mcg.httpserver.server.Request;
import at.fhtw.swen1.mcg.httpserver.server.Response;
import at.fhtw.swen1.mcg.httpserver.server.Service;
import at.fhtw.swen1.mcg.httpserver.service.AuthorizationService;
import at.fhtw.swen1.mcg.httpserver.service.JsonArrayService;
import at.fhtw.swen1.mcg.persistence.DeckRepository;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.lang.model.type.DeclaredType;
import java.util.ArrayList;
import java.util.List;

public class DeckService implements Service {
    @Override
    public Response handleRequest(Request request){
        DeckRepository deckRepository = new DeckRepository();

        if(request.getMethod().toString().equals("GET")){
            User player = AuthorizationService.authorizeUser(request);

            List<Card> deck = deckRepository.getUsersDeck(player);

            String response = "";

            if(deck == null){
                return new Response(HttpStatus.OK,
                        ContentType.PLAIN_TEXT,
                        "There are no decks available for this user");
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
        } else if (request.getMethod().toString().equals("PUT")) {
            User player = AuthorizationService.authorizeUser(request);

            JSONArray body = new JSONArray(request.getBody());

            List<String> cardids = new ArrayList<>();

            for(int i = 0; i < 4; i++){
                String cardInfo = body.getString(i);
                cardids.add(cardInfo);
            }

            deckRepository.createDeck(cardids, player);

            return new Response(HttpStatus.OK,
                    ContentType.PLAIN_TEXT,
                    "Deck created");
        }

        return new Response(HttpStatus.BAD_REQUEST,
                ContentType.PLAIN_TEXT,
                "Bad request");


    }
}
