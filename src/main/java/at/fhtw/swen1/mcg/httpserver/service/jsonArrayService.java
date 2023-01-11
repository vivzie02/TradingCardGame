package at.fhtw.swen1.mcg.httpserver.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class jsonArrayService {
    public static JSONArray getArrayData(String requestBody){
        JSONArray jsonObjects;
        try {
            jsonObjects = new JSONArray(requestBody);
        }catch (JSONException err){
            System.out.println(err);
            return null;
        }
        return jsonObjects;
    }
}
