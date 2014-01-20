package com.example.wordslearner;

import com.example.wordslearner.words.WordInfo;

import java.util.Collection;
import java.util.HashSet;

/**
 * Class which holds info about current learning session
 * Created by Юрий on 14.01.14.
 */
public class Session {
    /**
     * Number of right answers to exclude word from learning;
     */
    private int exclusionBorder;
    private Collection<WordInfo> wordInfos = new HashSet<WordInfo>();
    /**
     * All right answers minus all answers with mistakes
     */
    private int rating;

    public Session(int eclusionBorder){

    }

    public Collection<WordInfo> getWordInfos() {
        return wordInfos;
    }

    public void setWordInfos(Collection<WordInfo> wordInfos) {
        this.wordInfos = wordInfos;
    }

    public int getExclusionBorder() {
        return exclusionBorder;
    }

    public void setExclusionBorder(int exclusionBorder) {
        this.exclusionBorder = exclusionBorder;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
