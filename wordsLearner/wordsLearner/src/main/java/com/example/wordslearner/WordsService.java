package com.example.wordslearner;

import java.util.Map;
import java.util.Random;

/**
 * Created by Юрий on 09.01.14.
 */
public class WordsService {

    public static Map.Entry<String, String> setNewWord() {
        Random randomGenerator = new Random();
        if (randomGenerator.nextInt() % 2 == 0)
            return WordsCollection.setNewWord();
        else {
            Map.Entry<String, String> word = WordsCollection.setNewWord();
            word.setValue(WordsCollection.getRandomValue());
            return word;
        }
    }
}
