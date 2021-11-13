package com.mycompany.mszczepienia.dataloader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.FileReader;
import java.io.IOException;

public class Generator {

    static JSONParser jsonParser = new JSONParser();

    public static JSONArray getUsersArray(String filename) {
        try {
            Resource resource = new ClassPathResource(filename);
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(resource.getFile()));
            return (JSONArray) jsonParser.parse(jsonObject.get("objects").toString());

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
