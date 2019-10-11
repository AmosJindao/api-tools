package com.api.tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Author: srliu
 * Date: 10/5/19
 */
public class ApiUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String getFieldValue(String jsonData, String filedPath) throws IOException {
        List<String> values = getFieldValues(jsonData,filedPath);

        if(values != null && !values.isEmpty()){
            return values.get(0);
        }

        return null;
    }

    /**
     *
     * @param jsonData
     * @param fieldPath
     * @return
     * @throws IOException
     */
    public static List<String> getFieldValues(String jsonData, String fieldPath) throws IOException {
        String[] fieldPaths = splitPath(fieldPath);

        return getFieldValues(jsonData, fieldPaths);
    }

    public static List<String> getFieldValues(String jsonData, String[] fieldPaths) throws IOException {
        JsonNode rootNode = objectMapper.readTree(new ByteArrayInputStream(jsonData.getBytes(StandardCharsets.UTF_8)));

        if(rootNode == null){
            return Collections.emptyList();
        }

        List<JsonNode> lastLayerNodes = null;
        List<JsonNode> results = Arrays.asList(rootNode);;

        int len = fieldPaths.length;
        for (int i = 0; i < len; i++) {
            lastLayerNodes = results;

            results = new LinkedList<>();
            if(lastLayerNodes != null && !lastLayerNodes.isEmpty()){
                for(JsonNode node:lastLayerNodes){
                    List<JsonNode> tmpNodes = node.findValues(fieldPaths[i]);
                    if(tmpNodes!= null && !tmpNodes.isEmpty()){
                        results.addAll(tmpNodes);
                    }
                }
            }

            if(results.isEmpty()){
                break;
            }
        }

        List<String> values = new ArrayList<>(results.size());
        for (JsonNode jsonNode : results) {
            values.add(jsonNode.asText());
        }

        return values;
    }

    private static String[] splitPath(String fieldPath) {
        return fieldPath.split("\\.");
    }
}
