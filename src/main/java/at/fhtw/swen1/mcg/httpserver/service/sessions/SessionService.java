package at.fhtw.swen1.mcg.httpserver.service.sessions;

import at.fhtw.swen1.mcg.dto.User;
import at.fhtw.swen1.mcg.httpserver.http.ContentType;
import at.fhtw.swen1.mcg.httpserver.http.HttpStatus;
import at.fhtw.swen1.mcg.httpserver.server.Request;
import at.fhtw.swen1.mcg.httpserver.server.Response;
import at.fhtw.swen1.mcg.httpserver.server.Service;
import at.fhtw.swen1.mcg.httpserver.service.users.UserData;
import at.fhtw.swen1.mcg.persistence.UserRepository;
import org.json.JSONObject;

public class SessionService implements Service {
    @Override
    public Response handleRequest(Request request){
        UserRepository userRepository = new UserRepository();

        JSONObject body = UserData.getUserData(request.getBody());
        String username = body.get("Username").toString();
        String password = body.get("Password").toString();

        User result = userRepository.loginUser(username, password);
        String response;

        if(result == null){
            response = "Login failed";
        }else {
            response = "Successfully logged in as " + username;
        }

        return new Response(HttpStatus.OK,
                ContentType.PLAIN_TEXT,
                response);
    }
}
