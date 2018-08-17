package com.mvvm.architecture.template.utils;

import android.os.Build;
import android.text.Html;
import android.widget.TextView;

import java.util.List;

public class TextUtils {
    public static final String SEPARATE_TYPE_PARAGRAPH = "\n";
    public static final String SEPARATE_TYPE_PARAGRAPH_ASTERISK = "\n※  ";
    public static final String SEPARATE_TYPE_ASTERISK = "※  ";
    public static final String SEPARATE_TYPE_COMMA = ", ";
    public static final String SEPARATE_TYPE_COMMA_DEFAULT = ",";
    public static final String SEPARATE_TYPE_HYPHEN = " - ";
    public static final String SEPARATE_TYPE_DASH = "_";
    public static final String SEPARATE_TYPE_COLON = " : ";
    public static final String SEPARATE_TYPE_SPACE = " ";
    public static final String SEPARATE_TYPE_SLASH = "/ ";
    public static final String SEPARATE_TYPE_DOT = "\\.";

    public static void setTextHtml(TextView textView, String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY));
        } else {
            textView.setText(Html.fromHtml(text));
        }
    }

    public static String fromListToString(List<String> stringList) {
        return fromListToString(stringList, SEPARATE_TYPE_PARAGRAPH, null, false);
    }

    public static String fromListToString(List<String> stringList, String separateType,
                                          String separateTypeFirst, boolean appendFirst) {
        StringBuilder builder = new StringBuilder();
        if (!isEmptyList(stringList)) {
            for (String string : stringList) {
                if (!android.text.TextUtils.isEmpty(string)) {
                    if (appendFirst && stringList.indexOf(string) == 0 &&
                        !android.text.TextUtils.isEmpty(separateTypeFirst)) {
                        builder.append(separateTypeFirst);
                    }
                    builder.append(string).append(separateType);
                }
            }
        }
        return substring(builder.toString(), separateType);
    }

    public static String substring(String str, String separateType) {
        if (str != null && str.length() > 0) {
            int length = str.length();
            String tmp = str.substring(length - separateType.length(), length);
            if (android.text.TextUtils.equals(tmp, separateType)) {
                str = str.substring(0, length - separateType.length());
            }
        }
        return str;
    }

    public static boolean isEmptyText(String text) {
        if (android.text.TextUtils.isEmpty(text)) {
            return true;
        } else {
            return android.text.TextUtils.isEmpty(text.trim());
        }
    }

    public static boolean isEmptyList(List objectList) {
        return !(objectList != null && objectList.size() > 0);
    }
}
