package com.example.wordslearner.services;

import com.example.wordslearner.words.WordsCollection;

import java.util.Map;

/**
 * Created by Юрий on 09.01.14.
 */
public class WordsService {

    public static Map.Entry<String, String> getNewWord() {
            return WordsCollection.getNewWord();
    }

    public static Map.Entry<String, String> getCurrentWord(){
        return WordsCollection.getCurrentWord();
    }

    public static String getRandomValue(){
        return WordsCollection.getRandomValue();
    }
}
