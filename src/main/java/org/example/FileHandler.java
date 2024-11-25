package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileHandler {
    public void fileRecognizer(String filePath) throws IOException {
        List<EntityObject> entities = new ArrayList<EntityObject>();
        if (filePath.endsWith(".json")) {
            BufferedReader reader = Files.newBufferedReader(Path.of(filePath));
            String line;
            StringBuilder jsonStr = new StringBuilder();
            //String jsonStr = Files.readString(Path.of(filePath));
            while ((line = reader.readLine()) != null) {
                jsonStr.append(line);
            }
            entities = jsonParser(jsonStr.toString());
            outputStatistic(entities);
        } else if (filePath.endsWith(".csv")) {
            BufferedReader reader = Files.newBufferedReader(Path.of(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("group,type,number,weight")) continue;
                entities.add(csvParser(line));
            }
            outputStatistic(entities);
        } else {
            System.out.println("Файл по пути " + filePath + " с нужным расширением не был найден");
        }
    }

    private List<EntityObject> jsonParser(String jsonStr) {
        List<EntityObject> entities = new ArrayList<>();

        jsonStr = jsonStr.replaceAll("\\[", "").replaceAll("]", "");
        String[] entityFields = jsonStr.split("},\\{");

        for (String field : entityFields) {
            field = field.replaceAll("\\{", "").replaceAll("}", "");

            String[] fields = field.split(",");
            EntityObject entity = new EntityObject();
            entity.setGroup(fields[0].split(":")[1]);
            entity.setType(fields[1].split(":")[1]);
            entity.setNumber(Long.parseLong(fields[2].split(":")[1]));
            entity.setWeight(Long.parseLong(fields[3].split(":")[1]));
            entities.add(entity);
        }
        return entities;
    }

    private EntityObject csvParser(String line) {
        String[] fields = line.split(",");
        EntityObject entity = new EntityObject();
        entity.setGroup(fields[0]);
        entity.setType(fields[1]);
        entity.setNumber(Long.parseLong(fields[2]));
        entity.setWeight(Long.parseLong(fields[3]));
        return entity;
    }

    private void outputStatistic(List<EntityObject> entities) {
        System.out.println("Дубликаты:");
        for (String entityStr : getDuplicate(entities)) System.out.println(entityStr);
        System.out.println();

        System.out.println("Сумарный вес по гуппам:");
        for (Map.Entry<String, String> pair : getTotalWeight(entities).entrySet()) {
            String key = pair.getKey();
            String vol = pair.getValue();
            System.out.println("Гуппа: " + key + " Суммарный вес = " + vol);
        }
        System.out.println();

        System.out.println("Максимальный вес объектов в файле:" + getMaxWeight(entities));
        System.out.println();
        System.out.println("Минимальный вес объектов в файле:" + getMinWeight(entities));
        System.out.println();
    }

    private List<String> getDuplicate(List<EntityObject> entities) {
        Map<String,Integer> counts = new HashMap<>();
        List<String> duplicates = new ArrayList<>();
        for (EntityObject entity : entities) {
            String entry = "Group = " + entity.getGroup() + " Type = " + entity.getType();
            counts.put(entry, counts.getOrDefault(entry, 0) + 1);
        }

        for (Map.Entry<String,Integer> entry : counts.entrySet()) {
            if (entry.getValue() > 1) {
                duplicates.add(entry.getKey() + " (количество повторений дубликатов: " + entry.getValue() + ")");
            }
        }
        return duplicates;
    }

    private Map<String, String> getTotalWeight(List<EntityObject> entities) {
        Map<String, String> listSumTotalWeight = new HashMap<>();
        for (EntityObject entity : entities) {
            if (listSumTotalWeight.get(entity.getGroup()) == null) {
                BigInteger sum = new BigInteger("0");
                for (EntityObject entity2 : entities) {
                    if (entity2.getGroup().equals(entity.getGroup())) {
                        BigInteger entity2weight = new BigInteger(Long.toString(entity2.getWeight()));
                        sum = sum.add(entity2weight);
                    }
                }
                listSumTotalWeight.put(entity.getGroup(), sum.toString());
            }
        }
        return listSumTotalWeight;
    }

    private String getMaxWeight(List<EntityObject> entities) {
        long max = 0;
        for (EntityObject entity : entities) {
            if (max < entity.getWeight()) {
                max = entity.getWeight();
            }
        }
        return Long.toString(max);
    }

    private String getMinWeight(List<EntityObject> entities) {
        long min = 0;
        for (EntityObject entity : entities) {
            if (min > entity.getWeight() || min == 0) {
                min = entity.getWeight();
            }
        }
        return Long.toString(min);
    }
}