package at.fhtw.swen1.mcg.httpserver.service.battle;

import at.fhtw.swen1.mcg.dto.Battle;
import at.fhtw.swen1.mcg.dto.Card;
import at.fhtw.swen1.mcg.dto.User;
import at.fhtw.swen1.mcg.httpserver.http.ContentType;
import at.fhtw.swen1.mcg.httpserver.http.HttpStatus;
import at.fhtw.swen1.mcg.httpserver.server.Request;
import at.fhtw.swen1.mcg.httpserver.server.Response;
import at.fhtw.swen1.mcg.httpserver.server.Service;
import at.fhtw.swen1.mcg.httpserver.service.AuthorizationService;
import at.fhtw.swen1.mcg.httpserver.service.users.UserData;
import at.fhtw.swen1.mcg.persistence.DeckRepository;
import at.fhtw.swen1.mcg.persistence.UserRepository;
import org.json.JSONObject;

import java.util.List;

public class BattleService implements Service {
    @Override
    public Response handleRequest(Request request){
        JSONObject body = UserData.getUserData(request.getBody());

        if(!(request.getMethod().toString().equals("POST"))){
            return new Response(HttpStatus.BAD_REQUEST,
                    ContentType.PLAIN_TEXT,
                    "bad Request");
        }

        User player1 = AuthorizationService.authorizeUser(request);
        User player2 = UserRepository.loginWithToken(body.get("SecondPlayerToken").toString());

        List<Card> decks = DeckRepository.getUsersDeck(player1);
        player1.setDeck(decks);

        decks = DeckRepository.getUsersDeck(player2);
        player2.setDeck(decks);

        Battle battle = new Battle();
        battle.startBattle(player1, player2);


        String response = "test";


        return new Response(HttpStatus.OK,
                ContentType.PLAIN_TEXT,
                response);
    }
}
