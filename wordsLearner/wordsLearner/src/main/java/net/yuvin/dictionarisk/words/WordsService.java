package net.yuvin.dictionarisk.words;

import java.util.Map;

/**
 * Created by Юрий on 09.01.14.
 */
public class WordsService {

    public static Map.Entry<String, String> getNewWord() {
            return WordsProcessor.getNewWord();
    }

    public static Map.Entry<String, String> getCurrentWord(){
        return WordsProcessor.getCurrentWord();
    }

    public static String getRandomValue(){
        return WordsProcessor.getRandomValue();
    }

    public static boolean isInitialised(){
        return WordsProcessor.getListOfWords().size() > 0;
    }

    public static void initializeWords(){
        WordsProcessor.initializeWords();
    }

    public static void initializeWords(Map<String, String> words) {
        WordsProcessor.initializeWords(words);
    }
}
