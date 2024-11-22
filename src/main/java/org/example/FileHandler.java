package org.example;

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
            String jsonStr = Files.readString(Path.of(filePath));
            entities = jsonParser(jsonStr);
            outputStatistic(entities);
        } else if(filePath.endsWith(".csv")){
            List<String> strLines = Files.readAllLines(Path.of(filePath));
            entities = csvParser(strLines);
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
    private List<EntityObject> csvParser(List<String> strLines) {
        List<EntityObject> entities = new ArrayList<>();
        for(String strLine : strLines){
            if(strLine.equals("group,type,number,weight")) continue;

            String[] fields = strLine.split(",");
            EntityObject entity = new EntityObject();
            entity.setGroup(fields[0]);
            entity.setType(fields[1]);
            entity.setNumber(Long.parseLong(fields[2]));
            entity.setWeight(Long.parseLong(fields[3]));
            entities.add(entity);
        }
        return entities;
    }

    private void outputStatistic(List<EntityObject> entities) {
        System.out.println("Дубликаты:");
        for(String entityStr : getDuplicate(entities)) System.out.println(entityStr);
        System.out.println();

        System.out.println("Сумарный вес по гуппам:" );
        for (Map.Entry<String, String> pair : getTotalWeight(entities).entrySet()) {
            String key = pair.getKey();
            String vol = pair.getValue();
            System.out.println("Гуппа: " + key +" Суммарный вес = "+ vol);
        }
        System.out.println();

        System.out.println("Максимальный вес объектов в файле:" + getMaxWeight(entities));
        System.out.println();
        System.out.println("Минимальный вес объектов в файле:" + getMinWeight(entities));
        System.out.println();
    }

    private List<String> getDuplicate(List<EntityObject> entities) {
        List<String> duplicates = new ArrayList<>();
        List<String> uniqueEntries = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();

        for (EntityObject entity : entities) {
            String entry = "Group = " + entity.getGroup() + " Type = " + entity.getType();

            int index = uniqueEntries.indexOf(entry);
            if (index != -1) {
                counts.set(index, counts.get(index) + 1);
            } else {
                uniqueEntries.add(entry);
                counts.add(1);
            }
        }
        for (int i = 0; i < uniqueEntries.size(); i++) {
            if (counts.get(i) > 1) {
                duplicates.add(uniqueEntries.get(i) + " (количество повторений дубликатов: " + counts.get(i) + ")");
            }
        }
        return duplicates;
    }
    private Map<String,String> getTotalWeight(List<EntityObject> entities){
        Map<String,String> listSumTotalWeight = new HashMap<>();
        for(EntityObject entity: entities){
            if(listSumTotalWeight.get(entity.getGroup()) == null){
                BigInteger sum = new BigInteger("0");
                for(EntityObject entity2: entities){
                    if(entity2.getGroup().equals(entity.getGroup())){
                        BigInteger entity2weight = new BigInteger(Long.toString(entity2.getWeight()));
                        sum = sum.add(entity2weight);
                    }
                }
                listSumTotalWeight.put(entity.getGroup(),sum.toString());
            }
        }
        return listSumTotalWeight;
    }
    private String getMaxWeight(List<EntityObject> entities){
        long max = 0;
        for(EntityObject entity: entities){
            if(max < entity.getWeight()){
                max = entity.getWeight();
            }
        }
        return Long.toString(max);
    }
    private String getMinWeight(List<EntityObject> entities){
        long min = 0L;
        for(EntityObject entity: entities){
            if(min > entity.getWeight() || min == 0){
                min = entity.getWeight();
            }
        }
        return Long.toString(min);
    }

}