package net.yuvin.dictionarisk.words;

import java.util.*;

/**
 * Created by Юрий on 09.01.14.
 */
class WordsProcessor {

    private static List<Map.Entry<String, String>> listOfWords = new ArrayList<Map.Entry<String, String>>();
    private static Map.Entry<String, String> word;
    private static Integer iteration = -1;

    protected static Map.Entry<String, String> getNewWord(){

        if(iteration >= listOfWords.size() - 1)
            iteration = -1;
        iteration += 1;
        return word = listOfWords.get(iteration);
    }

    protected static Map.Entry<String, String> getCurrentWord(){
        return word;
    }

    protected static String getRandomValue(){
        Random randomGenerator = new Random();
        return listOfWords.get(randomGenerator.nextInt(listOfWords.size() - 1)).getValue();
    }

    protected static void initializeWords(){

        Map<String, String> words = new HashMap<String, String>();
        words.put("teacher", "учитель");
        words.put("runner","бегун");
        words.put("possibility","возможность");
        words.put("soldier","солдат");
        words.put("task","задача");
        words.put("goal","цель");
        words.put("ship","корабль");
        words.put("example","пример");
        words.put("try","попытка");
        words.put("to get","получать");
        listOfWords.addAll(words.entrySet());
    }

    protected static void initializeWords(Map<String, String> words){
        listOfWords.clear();
        listOfWords.addAll(words.entrySet());
    }

    protected static List<Map.Entry<String, String>> getListOfWords() {
        return listOfWords;
    }
}
