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
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (true){
            System.out.print("Введите путь к json или к csv файлу:");

            String input = scanner.nextLine();
            if(input.endsWith(".csv")) CsvStatistics.csvReader(input);
            else if(input.endsWith(".json")) JsonStatistic.jsonReader(input);
            else if(input.equals("exit")) return;
            else System.out.println("Файл с таким разрешением не был найден");
        }
    }
}