package at.fhtw.swen1.mcg.service.users;

import at.fhtw.swen1.mcg.dto.Battle;
import at.fhtw.swen1.mcg.httpserver.http.ContentType;
import at.fhtw.swen1.mcg.httpserver.http.HttpStatus;
import at.fhtw.swen1.mcg.httpserver.server.Request;
import at.fhtw.swen1.mcg.httpserver.server.Response;
import at.fhtw.swen1.mcg.httpserver.server.Service;

public class userService implements Service {
    @Override
    public Response handleRequest(Request request){
       return new Response(HttpStatus.OK,
                ContentType.PLAIN_TEXT,
                "Echo-" + request.getPathParts().get(1));
    }
}
