package com.example.wordslearner.words;

/**
 * Created by Юрий on 14.01.14.
 */
public class WordInfo {

    Word word;
    /**
     * Number of right answers for this word
     */
    int balance;

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
