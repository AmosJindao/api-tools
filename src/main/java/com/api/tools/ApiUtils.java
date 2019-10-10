package com.api.tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Author: srliu
 * Date: 10/5/19
 */
public class ApiUtils {
    public String getValue(String jsonData, String filedPath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new ByteArrayInputStream(jsonData.getBytes(StandardCharsets.UTF_8)));


        return null;
    }
}
