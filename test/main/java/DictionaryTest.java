package main.java;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DictionaryTest {

    @Test
    public void testInDictionary() {
        Dictionary dictionary = new Dictionary();
        assertEquals(dictionary.isValid("FOOD"), true);
    }

    @Test
    public void testNotInDictionary() {
        Dictionary dictionary = new Dictionary();
        assertEquals(dictionary.isValid("ASJDIF"), false);
    }

}