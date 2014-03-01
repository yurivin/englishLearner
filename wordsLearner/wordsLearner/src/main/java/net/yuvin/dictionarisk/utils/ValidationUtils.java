package net.yuvin.dictionarisk.utils;

import android.widget.EditText;

import java.util.Collection;

/**
 * Created by Yuriy on 14.02.14.
 */
public class ValidationUtils {

    public static boolean checkCollectionEmptyness(Collection collection) {
        if (collection == null || collection.size() < 1)
            return true;
        else
            return false;
    }

    public static boolean checkCollectionEmptyness(Object[] collection) {
        if (collection == null || collection.length < 1)
            return true;
        else
            return false;
    }

    public static boolean checkUserTextEmptiness(EditText editText){
        if(editText.getText() == null || editText.getText().toString().isEmpty()){
            return true;
        }
        return false;
    }
}
