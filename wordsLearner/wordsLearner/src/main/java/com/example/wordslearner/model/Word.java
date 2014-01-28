package com.example.wordslearner.model;

/**
 * Created by Юрий on 28.01.14.
 */
public class Word extends IdEntity {

    private String foreign;

    private String translation;

    public String getForeign() {
        return foreign;
    }

    public void setForeign(String foreign) {
        this.foreign = foreign;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
