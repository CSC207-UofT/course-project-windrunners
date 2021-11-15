package main.java;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DictionaryTest {

    @Test
    public void testInDictionary() {
        Dictionary dictionary = new Dictionary();
        assertTrue(dictionary.isValid("FOOD"));
    }

    @Test
    public void testNotInDictionary() {
        Dictionary dictionary = new Dictionary();
        assertFalse(dictionary.isValid("ASJDIF"));
    }

}