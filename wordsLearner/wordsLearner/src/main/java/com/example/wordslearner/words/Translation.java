package com.example.wordslearner.words;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Юрий on 14.01.14.
 */
public class Translation {


    private Collection<String> words = new HashSet<String>();
    private Languages language;

    public Languages getLanguage() {
        return language;
    }

    public Collection<String> getWords() {
        return words;
    }

    public void setWords(Collection<String> words) {
        this.words = words;
    }

    public void setLanguage(Languages language) {
        this.language = language;
    }
}
