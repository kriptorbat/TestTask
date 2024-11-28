package org.example;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static org.example.StatisticOutPut.printFinalStatistics;

public class JsonStatistic {
    private static final int BATCH_SIZE = 1000;

    public static void jsonReader(String filePath){
        Map<String, Integer> duplicates = new HashMap<>();
        Map<String, BigInteger> totalWeight = new HashMap<>();
        BigInteger maxWeight = BigInteger.ZERO;
        BigInteger minWeight = BigInteger.valueOf(Long.MAX_VALUE);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonFactory jsonFactory = new JsonFactory();

        try (JsonParser jsonParser = jsonFactory.createParser(new File(filePath))) {
            while (jsonParser.nextToken() != null) {
                if (jsonParser.getCurrentToken().isStructStart()) {
                    for (int i = 0; i < BATCH_SIZE && jsonParser.nextToken() != null; i++) {
                        JsonNode jsonNode = objectMapper.readTree(jsonParser);
                        String group = jsonNode.get("group").asText();
                        String type = jsonNode.get("type").asText();
                        BigInteger weight = BigInteger.valueOf(jsonNode.get("weight").asLong());

                        String key = group + " : " + type;
                        duplicates.put(key, duplicates.getOrDefault(key, 0) + 1);

                        totalWeight.put(group, totalWeight.getOrDefault(group, BigInteger.ZERO).add(weight));

                        if (weight.compareTo(maxWeight) > 0) {
                            maxWeight = weight;
                        }
                        if (weight.compareTo(minWeight) < 0) {
                            minWeight = weight;
                        }
                    }
                }
            }
            printFinalStatistics(duplicates, totalWeight, maxWeight, minWeight);
        } catch (FileNotFoundException e) {
            System.err.println("Файл не был найден или не коректный путь до файла");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
