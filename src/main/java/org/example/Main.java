package org.example;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final int BATCH_SIZE = 1000;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите путь к json:");
        String filePath = scanner.nextLine(); // Укажите путь к вашему файлу

        Map<String, Integer> duplicates = new HashMap<>();
        Map<String, BigInteger> totalWeight = new HashMap<>();
        BigInteger maxWeight = BigInteger.ZERO;
        BigInteger minWeight = BigInteger.valueOf(Long.MAX_VALUE);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonFactory jsonFactory = new JsonFactory();

        try (JsonParser jsonParser = jsonFactory.createParser(new File(filePath))) {
            int count = 0;

            // Начинаем чтение JSON
            while (jsonParser.nextToken() != null) {
                // Проверяем, является ли текущий токен началом объекта
                if (jsonParser.getCurrentToken().isStructStart()) {
                    for (int i = 0; i < BATCH_SIZE && jsonParser.nextToken() != null; i++) {
                        JsonNode jsonNode = objectMapper.readTree(jsonParser);
                        String group = jsonNode.get("group").asText();
                        String type = jsonNode.get("type").asText();
                        BigInteger weight = BigInteger.valueOf(jsonNode.get("weight").asLong());

                        // Обработка дубликатов
                        String key = group + ":" + type;
                        duplicates.put(key, duplicates.getOrDefault(key, 0) + 1);

                        // Суммарный вес
                        totalWeight.put(group, totalWeight.getOrDefault(group, BigInteger.ZERO).add(weight));

                        // Максимальный и минимальный вес
                        if (weight.compareTo(maxWeight) > 0) {
                            maxWeight = weight;
                        }
                        if (weight.compareTo(minWeight) < 0) {
                            minWeight = weight;
                        }

                        count++;
                    }
                }
            }

            // Вывод окончательной статистики
            printFinalStatistics(duplicates, totalWeight, maxWeight, minWeight);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printFinalStatistics(Map<String, Integer> duplicates,
                                             Map<String, BigInteger> totalWeight,
                                             BigInteger maxWeight,
                                             BigInteger minWeight) {
        System.out.println("Окончательная статистика:");

        System.out.println("\nДубликаты:");
        for (Map.Entry<String, Integer> entry : duplicates.entrySet()) {
            if(entry.getValue() > 1){
                System.out.println("Группа и тип: " + entry.getKey() + ", Количество: " + entry.getValue());
            }
        }

        System.out.println("\nСуммарный вес по группам:");
        for (Map.Entry<String, BigInteger> entry : totalWeight.entrySet()) {
            System.out.println("Группа: " + entry.getKey() + ", Суммарный вес: " + entry.getValue());
        }

        System.out.println("\nМаксимальный вес: " + maxWeight);
        System.out.println("Минимальный вес: " + minWeight);
    }
}