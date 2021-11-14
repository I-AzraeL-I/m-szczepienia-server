package com.mycompany.mszczepienia.dataloader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Generator {

    static JSONParser jsonParser = new JSONParser();

    public static JSONArray getUsersArray(String filename) {
        try {
            Resource resource = new ClassPathResource(filename);
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new BufferedReader(new InputStreamReader(resource.getInputStream())));
            return (JSONArray) jsonParser.parse(jsonObject.get("objects").toString());

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
