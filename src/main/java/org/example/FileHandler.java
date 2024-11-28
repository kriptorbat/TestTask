package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static java.nio.file.Files.newBufferedReader;

public class FileHandler {
    public void fileRecognizer(String filePath) throws IOException {
        List<String> typeAndGroup = new ArrayList<>();
        if(filePath.endsWith(".json")){
            BufferedReader reader = newBufferedReader(Path.of(filePath));
            String line;
            String[] group = new String[2];
            String[] type = new String[2];
            while ((line = reader.readLine()) != null) {
                System.out.print(line);
                System.out.print("\"group\"");
                if (line.startsWith("\"group\"")) {
                    group = line.split(":");
                    group[1] = group[1].replaceAll(",", "");
                }
                if (line.startsWith("\"type\"")){
                    type = line.split(":");
                    type[1] = type[1].replaceAll(",", "");
                    typeAndGroup.add(group[1] + " - " + type[1]);
                }
            }
        }


        for(String str: typeAndGroup){
            System.out.println(str);

        }

    }

//    public void fileRecognizer(String filePath) throws IOException {
//        List<EntityObject> entities = new ArrayList<>();
//        GenerateStatistic generateStatistic = new GenerateStatistic();
//
//        Map<String,String> totalWeight = new HashMap<>();
//
//        try {
//            if (filePath.endsWith(".json")) {
//                BufferedReader reader = Files.newBufferedReader(Path.of(filePath));
//                String line;
//                StringBuilder jsonStr = new StringBuilder();
//                int ent = 0;
//                while ((line = reader.readLine()) != null) {
//                    if(line.equals("}")) ent++;
//                    jsonStr.append(line);
//                    if(ent == 10000){
//                        entities = jsonParser(jsonStr.toString());
//                        //Map<String,String> totalWeight_ = generateStatistic.getTotalWeight(entities);
//                        entities =
//
//                    }
//                }
//                entities = jsonParser(jsonStr.toString());
//                outputStatistic(entities);
//
//
//            } else if (filePath.endsWith(".csv")) {
//
//                BufferedReader reader = Files.newBufferedReader(Path.of(filePath));
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    if (line.equals("group,type,number,weight")) continue;
//                    entities.add(csvParser(line));
//                }
//                outputStatistic(entities);
//            } else {
//                System.out.println("Файл по пути " + filePath + " с нужным расширением не был найден");
//            }
//        } catch (OutOfMemoryError e) {
//            System.err.println("Файл слишком большой, недостаточно памяти");
//        }
//    }

//    private List<EntityObject> jsonParser(String jsonStr) {
//        List<EntityObject> entities = new ArrayList<>();
//
//        jsonStr = jsonStr.replaceAll("\\[", "").replaceAll("]", "");
//        String[] entityFields = jsonStr.split("},\\{");
//
//
////        for (String field : entityFields) {
////            field = field.replaceAll("\\{", "").replaceAll("}", "");
////
////            String[] fields = field.split(",");
////            EntityObject entity = new EntityObject();
////            entity.setGroup(fields[0].split(":")[1]);
////            entity.setType(fields[1].split(":")[1]);
////            entity.setNumber(Long.parseLong(fields[2].split(":")[1]));
////            entity.setWeight(Long.parseLong(fields[3].split(":")[1]));
////            entities.add(entity);
////        }
//        return entities;
//    }
//
//    private EntityObject csvParser(String line) {
//        String[] fields = line.split(",");
//        EntityObject entity = new EntityObject();
//        entity.setGroup(fields[0]);
//        entity.setType(fields[1]);
//        entity.setNumber(Long.parseLong(fields[2]));
//        entity.setWeight(Long.parseLong(fields[3]));
//        return entity;
//    }
//
//    private void outputStatistic(List<EntityObject> entities) {
//        try {
//            System.out.println("Дубликаты:");
//
//            for (String entityStr : getDuplicates(entities)) System.out.println(entityStr);
//
//            System.out.println();
//
//            System.out.println("Сумарный вес по гуппам:");
//            for (Map.Entry<String, String> pair : getTotalWeight(entities).entrySet()) {
//                System.out.println("Гуппа: " + pair.getKey() + " Суммарный вес = " + pair.getValue());
//            }
//            System.out.println();
//
//            System.out.println("Максимальный вес объектов в файле:" + getMaxWeight(entities));
//            System.out.println();
//            System.out.println("Минимальный вес объектов в файле:" + getMinWeight(entities));
//            System.out.println();
//        } catch (OutOfMemoryError e) {
//            System.err.println("Файл слишком большой, недостаточно памяти");
//        }
//    }
//
//
}