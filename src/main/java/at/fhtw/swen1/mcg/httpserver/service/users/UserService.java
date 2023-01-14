package at.fhtw.swen1.mcg.httpserver.service.users;

import at.fhtw.swen1.mcg.httpserver.http.ContentType;
import at.fhtw.swen1.mcg.httpserver.http.HttpStatus;
import at.fhtw.swen1.mcg.httpserver.server.Request;
import at.fhtw.swen1.mcg.httpserver.server.Response;
import at.fhtw.swen1.mcg.httpserver.server.Service;
import at.fhtw.swen1.mcg.persistence.UserRepository;
import org.json.JSONObject;

public class UserService implements Service {
    @Override
    public Response handleRequest(Request request){
        JSONObject body = UserData.getUserData(request.getBody());
        String username = body.get("Username").toString();
        String password = body.get("Password").toString();

        int result = UserRepository.createUser(username, password);
        String response;

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
