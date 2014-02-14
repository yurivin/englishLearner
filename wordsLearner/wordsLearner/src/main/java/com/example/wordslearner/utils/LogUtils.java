package com.example.wordslearner.utils;

import android.util.Log;
import com.example.wordslearner.utils.ValidationUtils;

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
