package com.example.wordslearner;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Юрий on 09.01.14.
 */
public class WordsService {

    public static Map.Entry<String, String> getNewWord() {
            return WordsCollection.getNewWord();
    }
}
