package main.java;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class Dictionary {
    private HashSet<String> dictionary;

    public Dictionary() {
        dictionary = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./dictionary.txt"))) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                dictionary.add(line);
                i++;
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File Not Found");
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public HashSet<String> getDictionary() { return dictionary; }

    public boolean inDictionary(String word) {
        return dictionary.contains(word);
    }
}
