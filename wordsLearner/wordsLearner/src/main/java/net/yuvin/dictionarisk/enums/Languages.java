package net.yuvin.dictionarisk.enums;

import java.security.spec.RSAOtherPrimeInfo;

/**
 * Created by Юрий on 23.03.2014.
 */
public enum Languages {

    OTHER("other", "other"),
    QUESTION("question", "?");



    String ISO1;
    String title;
    Languages(String ISO1, String title){
        this.ISO1 = ISO1;
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public String getISO1(){
        return ISO1;
    }
}
