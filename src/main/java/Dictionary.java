package main.java;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;

/**
 * A Scrabble Dictionary.
 * Used to verify that words inserted into the Board are valid Scrabble words
 * valid Scrabble words are stored in a HashSet
 */
public class Dictionary {
    private final HashSet<String> words;

    /**
     * Class Constructor
     * reads valid Scrabble words from a text file and stores them into a HashSet
     */
    public Dictionary() {
        words = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./dictionary.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File Not Found");
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /**
     * @return the valid Scrabble words
     */
    public HashSet<String> getDictionary() { return words; }

    /**
     * check if word is a valid Scrabble word
     * @param word is the word that is to be checked for validity
     * @return true iff word is a valid Scrabble word
     */
    public boolean isValid(String word) {
        return words.contains(word.toUpperCase(Locale.ROOT));
    }
}
