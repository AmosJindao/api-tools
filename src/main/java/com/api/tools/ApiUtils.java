package com.api.tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: srliu
 * Date: 10/5/19
 */
public class ApiUtils {
    public static String getFieldValue(String jsonData, String filedPath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new ByteArrayInputStream(jsonData.getBytes(StandardCharsets.UTF_8)));

        JsonNode targetNode = rootNode.findValue(filedPath);

        return null;
    }

    public static List<String> getFieldValues(String jsonData, String fieldPath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new ByteArrayInputStream(jsonData.getBytes(StandardCharsets.UTF_8)));

        String[] fieldPaths = splitPath(fieldPath);

        JsonNode targetNode = null;
        int len = fieldPaths.length;
//        for (int i = 0; i < len; i++) {
        targetNode = rootNode.findValue(fieldPaths[0]);

      List<JsonNode> timeNodes = targetNode.findValues(fieldPaths[1]);

//        }

        List<String> values = new ArrayList<>(timeNodes.size());
        for(JsonNode jsonNode:timeNodes){
            values.add(jsonNode.asText());
        }

        return values;
    }

    private static String[] splitPath(String fieldPath) {
        return fieldPath.split("\\.");
    }
}
