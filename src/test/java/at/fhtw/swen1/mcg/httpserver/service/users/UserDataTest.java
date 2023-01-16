package at.fhtw.swen1.mcg.httpserver.service.users;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDataTest {

    @Test
    void getUserData() {
        UserData userData = new UserData();
        JSONObject body = userData.getUserData("{\"Username\":\"kienboec\", \"Password\":\"daniel\"}");
        String test = body.getString("Username");

        assertEquals("kienboec", test);
    }
}