package at.fhtw.swen1.mcg.httpserver.service.users;

import at.fhtw.swen1.mcg.dto.User;
import at.fhtw.swen1.mcg.httpserver.http.ContentType;
import at.fhtw.swen1.mcg.httpserver.http.HttpStatus;
import at.fhtw.swen1.mcg.httpserver.server.Request;
import at.fhtw.swen1.mcg.httpserver.server.Response;
import at.fhtw.swen1.mcg.httpserver.server.Service;
import at.fhtw.swen1.mcg.httpserver.service.AuthorizationService;
import at.fhtw.swen1.mcg.persistence.UserDataRepository;
import at.fhtw.swen1.mcg.persistence.UserRepository;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserService implements Service {
    @Override
    public Response handleRequest(Request request){
        JSONObject body;
        List<String> userData = new ArrayList<>();
        String response = "";

        if(request.getPathParts().size() > 1){
            User player = AuthorizationService.authorizeUser(request);
            if(!request.getPathParts().get(1).equals(player.getUsername())){
                return new Response(HttpStatus.OK,
                        ContentType.PLAIN_TEXT,
                        "Wrong authentication token");
            }
            if(request.getMethod().toString().equals("GET")){
                userData = UserDataRepository.getUserData(player);
                for (String data: userData
                     ) {
                    response = response + data + " ";
                }

                return new Response(HttpStatus.OK,
                        ContentType.PLAIN_TEXT,
                        response);
            } else if (request.getMethod().toString().equals("PUT")) {
                body = UserData.getUserData(request.getBody());
                response = UserDataRepository.editUserData(player, body);
                return new Response(HttpStatus.OK,
                        ContentType.PLAIN_TEXT,
                        response);
            }

            return new Response(HttpStatus.OK,
                    ContentType.PLAIN_TEXT,
                    "bad path");
        }

        body = UserData.getUserData(request.getBody());

        String username = body.get("Username").toString();
        String password = body.get("Password").toString();

        int result = UserRepository.createUser(username, password);

        if(result == 0){
            response = "User created successfully";
        } else if (result == 1) {
            response = "User already exists";
        }else {
            response = "Error when creating user";
        }

        return new Response(HttpStatus.OK,
                ContentType.PLAIN_TEXT,
                response);
    }
}
