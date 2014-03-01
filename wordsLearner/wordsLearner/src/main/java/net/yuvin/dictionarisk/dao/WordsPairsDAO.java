package net.yuvin.dictionarisk.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import net.yuvin.dictionarisk.model.Word;

import java.util.*;

/**
 * Created by Юрий on 25.01.14.
 */
public class WordsPairsDAO {

    static Cursor cursor;
    static SQLiteDatabase db;

    protected static Map<String, String> getWordPairs(Context context, int wordSetId) {
        db = DbUtils.getReadableDb(context);
        String[] args = {String.valueOf(wordSetId)};
        cursor = db.rawQuery("SELECT foreignW, translation FROM WordPairs WHERE wordSetId = ?", args);
        int foreignWColIndex = cursor.getColumnIndex("foreignW");
        int translationColIndex = cursor.getColumnIndex("translation");
        Map<String, String> words = new HashMap<String, String>();
        if (cursor.moveToFirst()) {
            do {
                String foreignW = cursor.getString(foreignWColIndex);
                String translation = cursor.getString(translationColIndex);
                words.put(foreignW, translation);
            } while (cursor.moveToNext());
        }
        DbUtils.closeDb();
        return words;
    }

    protected static List<Word> getWords(Context context, int wordSetId) {
        db = DbUtils.getReadableDb(context);
        String[] args = {String.valueOf(wordSetId)};
        cursor = db.rawQuery("SELECT id, foreignW, translation FROM WordPairs WHERE wordSetId = ?", args);
        int idColIndex = cursor.getColumnIndex("id");
        int foreignWColIndex = cursor.getColumnIndex("foreignW");
        int translationColIndex = cursor.getColumnIndex("translation");
        List<Word> words = new ArrayList<Word>();
        Word word;
        if (cursor.moveToFirst()) {
            do {
                word = new Word();
                word.setId(cursor.getInt(idColIndex));
                word.setForeign(cursor.getString(foreignWColIndex));
                word.setTranslation(cursor.getString(translationColIndex));
                words.add(word);
            } while (cursor.moveToNext());
        }
        DbUtils.closeDb();
        return words;
    }

    protected static void insertWordPairs(Context context, Map<String, String> wordSet, long wordSetId) {
        db = DbUtils.getWritableDb(context);
        ContentValues cv = new ContentValues();
        for (Map.Entry<String, String> entry : wordSet.entrySet()) {
            cv.put("foreignW", entry.getKey());
            cv.put("translation", entry.getValue());
            cv.put("wordSetId", wordSetId);
            db.insert("WordPairs", null, cv);
        }
        DbUtils.closeDb();
    }

    protected static void deleteByWordsSetId(Context context, int id) {
        db = DbUtils.getWritableDb(context);
        String[] args = {String.valueOf(id)};
        db.delete("WordPairs", "wordSetId = ?", args);
        DbUtils.closeDb();
    }

    protected static void updateWord(Context context, String id, String foreign, String translation) {
        db = DbUtils.getWritableDb(context);
        ContentValues cv = new ContentValues();
        cv.put("foreignW", foreign);
        cv.put("translation", translation);
        db.update("WordPairs", cv, "id = ?", new String[]{id});
        DbUtils.closeDb();
    }

    protected static void delete(Context context, String wordId){
        db = DbUtils.getWritableDb(context);
        db.delete("WordPairs", "id = ?", new String[]{wordId});
        DbUtils.closeDb();
    }

}
