package at.fhtw.swen1.mcg.httpserver.service.stats;

import at.fhtw.swen1.mcg.dto.User;
import at.fhtw.swen1.mcg.httpserver.http.ContentType;
import at.fhtw.swen1.mcg.httpserver.http.HttpStatus;
import at.fhtw.swen1.mcg.httpserver.server.Request;
import at.fhtw.swen1.mcg.httpserver.server.Response;
import at.fhtw.swen1.mcg.httpserver.server.Service;
import at.fhtw.swen1.mcg.httpserver.service.AuthorizationService;
import at.fhtw.swen1.mcg.persistence.UserRepository;

public class StatService implements Service {
    @Override
    public Response handleRequest(Request request){
        if(!request.getPathParts().get(1).equals("packages")){
            return new Response(HttpStatus.OK,
                    ContentType.PLAIN_TEXT,
                    "bad path");
        }

        User player = AuthorizationService.authorizeUser(request);

        int result = UserRepository.shop(player);
        String response;

        if(result == 0){
            response = "Package bought";
        }else {
            response = "something went wrong";
        }

        return new Response(HttpStatus.OK,
                ContentType.PLAIN_TEXT,
                response);
    }
}
