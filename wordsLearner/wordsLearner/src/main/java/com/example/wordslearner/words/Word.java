package com.example.wordslearner.words;

import org.apache.http.impl.EnglishReasonPhraseCatalog;

/**
 * Created by Юрий on 14.01.14.
 */
public class Word {

    private String english;
    private Translation translation;

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public Translation getTranslation() {
        return translation;
    }

    public void setTranslation(Translation translation) {
        this.translation = translation;
    }
}
