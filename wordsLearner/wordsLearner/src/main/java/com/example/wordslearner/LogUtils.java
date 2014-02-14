package com.example.wordslearner;

import android.util.Log;

/**
 * Created by Юрий on 25.01.14.
 */
public class LogUtils {

    public static void debugForCycleLog(String tag, String[] array) {
        if (!ValidationUtils.checkCollectionEmptyness(array)) {
            for (String item : array) {
                Log.d(tag, item);
            }
        }
    }
}
