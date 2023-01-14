package at.fhtw.swen1.mcg.httpserver.service.users;

import org.json.JSONException;
import org.json.JSONObject;

public class UserData {
    public static JSONObject getUserData(String requestBody){
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(requestBody);
        }catch (JSONException err){
            System.out.println(err);
            return null;
        }
        return jsonObject;
    }
}
