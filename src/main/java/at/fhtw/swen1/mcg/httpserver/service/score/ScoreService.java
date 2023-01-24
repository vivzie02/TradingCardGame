package at.fhtw.swen1.mcg.httpserver.service.score;

import at.fhtw.swen1.mcg.dto.User;
import at.fhtw.swen1.mcg.httpserver.http.ContentType;
import at.fhtw.swen1.mcg.httpserver.http.HttpStatus;
import at.fhtw.swen1.mcg.httpserver.server.Request;
import at.fhtw.swen1.mcg.httpserver.server.Response;
import at.fhtw.swen1.mcg.httpserver.server.Service;
import at.fhtw.swen1.mcg.httpserver.service.AuthorizationService;
import at.fhtw.swen1.mcg.httpserver.service.users.UserData;
import at.fhtw.swen1.mcg.persistence.UserRepository;
import org.json.JSONObject;

public class ScoreService implements Service {
    @Override
    public Response handleRequest(Request request){
        UserRepository userRepository = new UserRepository();
        User player = AuthorizationService.authorizeUser(request);

        String response = userRepository.getScoreBoard();

        return new Response(HttpStatus.OK,
                ContentType.PLAIN_TEXT,
                response);
    }
}
