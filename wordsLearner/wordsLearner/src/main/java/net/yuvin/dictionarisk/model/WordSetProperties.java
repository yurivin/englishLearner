package net.yuvin.dictionarisk.model;

/**
 * Created by Юрий on 23.03.2014.
 */
public class WordSetProperties extends IdEntity {

    private static final String QUESTION= "?";

    private Long wordSetId;
    private String title;
    private String languageFrom;
    private String languageTo;
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

    public String getLanguageFrom() {
        if(languageFrom == null) return QUESTION;
        return languageFrom;
    }

    public void setLanguageFrom(String languageFrom) {
        this.languageFrom = languageFrom;
    }

    public String getLanguageTo() {
        if(languageFrom == null) return QUESTION;
        return languageTo;
    }

    public void setLanguageTo(String languageTo) {
        this.languageTo = languageTo;
    }

}
