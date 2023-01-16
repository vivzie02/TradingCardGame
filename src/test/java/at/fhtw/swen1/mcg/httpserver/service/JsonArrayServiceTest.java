package at.fhtw.swen1.mcg.httpserver.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonArrayServiceTest {

    @Test
    void getArrayData() {
        String body = "[{\"Id\":\"845f0dc7-37d0-426e-994e-43fc3ac83c08\", \"Name\":\"WaterGoblin\", \"Damage\": 10.0}, {\"Id\":\"99f8f8dc-e25e-4a95-aa2c-782823f36e2a\", \"Name\":\"Dragon\", \"Damage\": 50.0}, {\"Id\":\"e85e3976-7c86-4d06-9a80-641c2019a79f\", \"Name\":\"WaterSpell\", \"Damage\": 20.0}, {\"Id\":\"1cb6ab86-bdb2-47e5-b6e4-68c5ab389334\", \"Name\":\"Ork\", \"Damage\": 45.0}, {\"Id\":\"dfdd758f-649c-40f9-ba3a-8657f4b3439f\", \"Name\":\"FireSpell\",    \"Damage\": 25.0}]";

        JsonArrayService jsonArrayService = new JsonArrayService();
        JSONArray jsonArray = jsonArrayService.getArrayData(body);

        JSONObject test = jsonArray.getJSONObject(0);

        assertEquals("845f0dc7-37d0-426e-994e-43fc3ac83c08", test.get("Id"));
    }
}