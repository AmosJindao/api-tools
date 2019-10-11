package com.api.tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Stream;

/**
 * Author: srliu
 * Date: 10/5/19
 */
public class ApiUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     *
     * @param jsonData
     * @param fieldPath the path to the target field, and split by ., /, \.
     * @return
     * @throws IOException
     */
    public static String getFieldValue(String jsonData, String fieldPath) throws IOException {
        List<String> values = getFieldValues(jsonData, fieldPath);

        if (values != null && !values.isEmpty()) {
            return values.get(0);
        }

        return null;
    }

    /**
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

        List<JsonNode> results = getFieldJsonNodes(rootNode, fieldPaths);

        List<String> values = new ArrayList<>(results.size());
        for (JsonNode jsonNode : results) {
            values.add(jsonNode.asText());
        }

        return values;
    }

    public static List<JsonNode> getFieldJsonNodes(JsonNode rootNode, String[] fieldPaths) {
        if (rootNode == null || fieldPaths == null || fieldPaths.length == 0) {
            return Collections.emptyList();
        }

        List<JsonNode> lastLayerNodes = null;
        List<JsonNode> results = Arrays.asList(rootNode);

        int len = fieldPaths.length;
        for (int i = 0; i < len; i++) {
            lastLayerNodes = results;

            results = new LinkedList<>();
            if (lastLayerNodes != null && !lastLayerNodes.isEmpty()) {
                for (JsonNode node : lastLayerNodes) {
                    List<JsonNode> tmpNodes = node.findValues(fieldPaths[i]);
                    if (tmpNodes != null && !tmpNodes.isEmpty()) {
                        results.addAll(tmpNodes);
                    }
                }
            }

            if (results.isEmpty()) {
                break;
            }
        }

        return results;
    }

    public static String[] splitPath(String fieldPath) {
        return fieldPath.split("[\\.\\\\/]");
    }

    public static void main(String[] args) {
        Stream.of(splitPath("/a1/a2/a3/a5")).forEach(o-> System.out.println(o));
        Stream.of(splitPath("\\a1\\a2\\a3\\a5")).forEach(o-> System.out.println(o));
        Stream.of(splitPath("a1.a2.a3.a5")).forEach(o-> System.out.println(o));
    }

}
