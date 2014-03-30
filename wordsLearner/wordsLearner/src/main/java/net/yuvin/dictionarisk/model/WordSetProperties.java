package net.yuvin.dictionarisk.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Юрий on 23.03.2014.
 */
public class WordSetProperties extends IdEntity {

    public static final String QUESTION = "?";

    private Long wordSetId;
    private String languageFrom;
    private String languageTo;

    public WordSetProperties(){ }

    public Long getWordSetId() {
        return wordSetId;
    }

    public void setWordSetId(Long wordSetId) {
        this.wordSetId = wordSetId;
    }

    public String getLanguageFrom() {
        if (languageFrom == null) return QUESTION;
        return languageFrom;
    }

    public void setLanguageFrom(String languageFrom) {
        this.languageFrom = languageFrom;
    }

    public String getLanguageTo() {
        if (languageTo == null) return QUESTION;
        return languageTo;
    }

    public void setLanguageTo(String languageTo) {
        this.languageTo = languageTo;
    }
}
