package org.example;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.printf("Введите путь к json или csv файлу(exit чтобы выйти из программы):");
            String input = scanner.nextLine();
            if(input != null){
                if(input.equals("exit")) return;
                else {
                    FileHandler fileHandler = new FileHandler();
                    fileHandler.fileRecognizer(input);
                }
            }
        }
    }
}