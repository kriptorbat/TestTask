package org.example;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateStatistic {
    public List<String> getDuplicates(List<EntityObject> entities) {
        Map<String, Integer> counts = new HashMap<>();
        for (EntityObject entity : entities) {
            String entry = "Group = " + entity.getGroup() + " Type = " + entity.getType();
            counts.put(entry, counts.getOrDefault(entry, 0) + 1);
        }

        List<String> duplicates = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > 1) {
                duplicates.add(entry.getKey() + " (количество повторений дубликатов: " + entry.getValue() + ")");
            }
        }
        return duplicates;
    }

    public Map<String, String> getTotalWeight(List<EntityObject> entities) {
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

    public String getMaxWeight(List<EntityObject> entities) {
        long max = 0;
        for (EntityObject entity : entities) {
            if (max < entity.getWeight()) {
                max = entity.getWeight();
            }
        }
        return Long.toString(max);
    }

    public String getMinWeight(List<EntityObject> entities) {
        long min = 0;
        for (EntityObject entity : entities) {
            if (min > entity.getWeight() || min == 0) {
                min = entity.getWeight();
            }
        }
        return Long.toString(min);
    }
}
