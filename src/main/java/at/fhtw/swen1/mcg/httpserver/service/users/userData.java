package at.fhtw.swen1.mcg.httpserver.service.users;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class userData {
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
