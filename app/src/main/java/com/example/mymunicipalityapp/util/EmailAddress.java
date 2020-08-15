package com.example.mymunicipalityapp.util;

import android.text.TextUtils;
import android.util.Patterns;

public class EmailAddress {

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}
