package com.example.wordslearner.dao;

import android.content.Context;
import com.example.wordslearner.model.Word;

import java.util.List;
import java.util.Map;

/**
 * Created by Юрий on 26.01.14.
 */
public class DbService {

    public static void deleteWordSet(Context context, String wordsSetTitle){
        int wordsSetId = WordSetsDAO.getIdByTitle(context, wordsSetTitle);
        WordsPairsDAO.deleteByWordsSetId(context, wordsSetId);
        WordSetsDAO.deleteById(context, wordsSetId);
    }

    public static Map<String, String> getWordPairs(Context context, String wordsSetTitle){
        return WordsPairsDAO.getWordPairs(context, WordSetsDAO.getIdByTitle(context, wordsSetTitle));
    }

    public static List<Word> getWords(Context context, int wordsSetId){
        return WordsPairsDAO.getWords(context, wordsSetId);
    }

    public static void insertWordPairs(Context context, Map<String, String> wordSet, long wordSetId){
        WordsPairsDAO.insertWordPairs(context, wordSet, wordSetId);
    }

    public static String[] getAllWordSetNames(Context context){
        return WordSetsDAO.getAllWordSetNames(context);
    }

    public static Integer getWordsSetId(Context context, String title){
       return WordSetsDAO.getIdByTitle(context, title);
    }

    public static long insertNewWordSet(Context context, String title) {
       return WordSetsDAO.insertNewWordSet(context, title);
    }

    public static void updateWord(Context context, String id, String foreign, String translation){
        WordsPairsDAO.updateWord(context, id, foreign, translation);
    }

    public static void renameWordsSet(Context context, int id, String newTitle){
        WordSetsDAO.renameSet(context, id, newTitle);
    }

    public static void deleteWord(Context context, String wordId){
        WordsPairsDAO.delete(context, wordId);
    }
}
