package net.yuvin.dictionarisk.model;

import net.yuvin.dictionarisk.enums.Languages;

/**
 * Created by Юрий on 23.03.2014.
 */
public class WordSetProperties extends IdEntity {

    private static final String QUESTION= "?";

    private Long wordSetId;
    private String title;
    private Languages languageFrom;
    private Languages languageTo;
    private boolean custom;

    public boolean isCustom() {
        return custom;
    }

    public void setCustom(boolean custom) {
        this.custom = custom;
    }

    public Long getWordSetId() {
        return wordSetId;
    }

    public void setWordSetId(Long wordSetId) {
        this.wordSetId = wordSetId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Languages getLanguageFrom() {
        if(languageFrom == null) return Languages.QUESTION;
        return languageFrom;
    }

    public void setLanguageFrom(Languages languageFrom) {
        this.languageFrom = languageFrom;
    }

    public Languages getLanguageTo() {
        if(languageFrom == null) return Languages.QUESTION;
        return languageTo;
    }

    public void setLanguageTo(Languages languageTo) {
        this.languageTo = languageTo;
    }

}
