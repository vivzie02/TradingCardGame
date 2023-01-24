package at.fhtw.swen1.mcg.httpserver.service.packages;

import at.fhtw.swen1.mcg.dto.Card;
import at.fhtw.swen1.mcg.dto.MagicCard;
import at.fhtw.swen1.mcg.dto.MonsterCard;
import at.fhtw.swen1.mcg.httpserver.http.ContentType;
import at.fhtw.swen1.mcg.httpserver.http.HttpStatus;
import at.fhtw.swen1.mcg.httpserver.server.Request;
import at.fhtw.swen1.mcg.httpserver.server.Response;
import at.fhtw.swen1.mcg.httpserver.server.Service;
import at.fhtw.swen1.mcg.httpserver.service.JsonArrayService;
import at.fhtw.swen1.mcg.persistence.CardRepository;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PackageService implements Service {
    @Override
    public Response handleRequest(Request request) {
        CardRepository cardRepository = new CardRepository();
        JSONArray body = JsonArrayService.getArrayData(request.getBody());

        List<Card> cardsInPackage = new ArrayList<>();
        Card newCard;
        String elementType;

        for(int i = 0; i < 5; i++)
        {
            JSONObject cardInfo = body.getJSONObject(i);
            if(cardInfo.get("Name").toString().contains("Water")){
                elementType = "Water";
            } else if (cardInfo.get("Name").toString().contains("Fire")) {
                elementType = "Fire";
            }else {
                elementType = "Normal";
            }

            if(cardInfo.get("Name").toString().contains("Spell")){
                newCard = new MagicCard(elementType, Float.valueOf(cardInfo.get("Damage").toString()), cardInfo.get("Id").toString());
            }
            else {
                newCard = new MonsterCard(cardInfo.get("Name").toString().replace(elementType, ""), elementType, Float.valueOf(cardInfo.get("Damage").toString()), cardInfo.get("Id").toString());
            }
            cardsInPackage.add(newCard);
        }

        int result = cardRepository.createPackage(cardsInPackage);
        String response;

        if(result == 0){
            response = "Package created successfully";
        }else {
            response = "Package creation failed";
        }

        return new Response(HttpStatus.OK,
                ContentType.PLAIN_TEXT,
                response);
    }
}
