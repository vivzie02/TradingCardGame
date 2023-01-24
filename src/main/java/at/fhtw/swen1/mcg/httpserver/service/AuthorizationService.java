package at.fhtw.swen1.mcg.httpserver.service;

import at.fhtw.swen1.mcg.dto.User;
import at.fhtw.swen1.mcg.httpserver.server.Request;
import at.fhtw.swen1.mcg.persistence.UserRepository;

public class AuthorizationService {
    public static User authorizeUser(Request request){
        UserRepository userRepository = new UserRepository();
        String userToken = request.getHeaderMap().getHeader("Authorization");
        return userRepository.loginWithToken(userToken);
    }
}
