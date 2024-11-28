package org.example;

import java.math.BigInteger;
import java.util.Map;

public class StatisticOutPut {
    public static void printFinalStatistics(Map<String, Integer> duplicates,
                                             Map<String, BigInteger> totalWeight,
                                             BigInteger maxWeight,
                                             BigInteger minWeight) {
        System.out.println("Статистика:");

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
