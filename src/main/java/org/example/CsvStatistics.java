package org.example;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static org.example.StatisticOutPut.printFinalStatistics;

public class CsvStatistics {
    private static final int BATCH_SIZE = 10000;

    public static void csvReader(String filePath) {
        Map<String, Integer> duplicates = new HashMap<>();
        Map<String, BigInteger> totalWeight = new HashMap<>();
        BigInteger maxWeight = BigInteger.ZERO;
        BigInteger minWeight = BigInteger.valueOf(Long.MAX_VALUE);

        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            csvReader.readNext();

            while ((nextLine = csvReader.readNext()) != null) {
                String group = nextLine[0];
                String type = nextLine[1];
                BigInteger weight = new BigInteger(nextLine[3]);

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
            printFinalStatistics(duplicates, totalWeight, maxWeight, minWeight);

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
}
