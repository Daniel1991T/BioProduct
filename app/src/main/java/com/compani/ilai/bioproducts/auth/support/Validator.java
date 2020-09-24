package com.compani.ilai.bioproducts.auth.support;

import android.util.Log;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static final String EMAIL_REGEX = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+";

    public static Validator instance;

    public static Validator getInstance() {
        if (instance == null) {
            return new Validator();
        }
        return instance;
    }

    public boolean isEmailValid(TextInputLayout inputLayoutEmail) {
        String email = inputLayoutEmail.getEditText().getText().toString().trim();
        if (email.isEmpty()) {
            inputLayoutEmail.setError("Enter an email");
            return false;
        }

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            inputLayoutEmail.setError("Email invalid");
            return false;
        } else {
            inputLayoutEmail.setError(null);
            return true;
        }
    }

    public boolean isPasswordValid(TextInputLayout inputLayoutPassword) {
        String password = inputLayoutPassword.getEditText().getText().toString().trim();
        Log.d("password", "isPasswordValid: " + password);
        if (password.isEmpty()) {
            inputLayoutPassword.setError("Enter a password");
            return false;
        }
        Pattern upperCasePattern = Pattern.compile("[A-Z ]");
        Pattern lowerCasePattern = Pattern.compile("[a-z ]");
        Pattern digitCasePattern = Pattern.compile("[0-9]");

        if (password.length() < 8) {
            inputLayoutPassword.setError("Password length must have al least 8 character!");
            return false;
        } else {
            inputLayoutPassword.setError(null);
        }

        if (!upperCasePattern.matcher(password).find()) {
            inputLayoutPassword.setError("Password must have at least one uppercase character!");
            return false;
        } else {
            inputLayoutPassword.setError(null);
        }

        if (!lowerCasePattern.matcher(password).find()) {
            inputLayoutPassword.setError("Password must have at least one lowercase character!");
            return false;
        } else {
            inputLayoutPassword.setError(null);
        }

        if (!digitCasePattern.matcher(password).find()) {
            inputLayoutPassword.setError("Password must have at least one digit character!");
            return false;
        } else {
            inputLayoutPassword.setError(null);
        }

        return true;
    }

    public boolean isSamePassword(TextInputLayout inputLayoutPassword, TextInputLayout inputLayoutRePassword) {
        String password = inputLayoutPassword.getEditText().getText().toString();
        String retryPassword = inputLayoutRePassword.getEditText().getText().toString();

        if (!password.equals(retryPassword)) {
            inputLayoutPassword.setError("Passwords do not match");
            inputLayoutRePassword.setError("Passwords do not match");
            return false;
        } else {
            inputLayoutPassword.setError(null);
            inputLayoutRePassword.setError(null);
        }
        return true;
    }

    public boolean isFieldEmpty(TextInputLayout inputLayout) {
        String field = inputLayout.getEditText().getText().toString();
        if (field.isEmpty()) {
            inputLayout.setError("Field empty");
            return true;
        }
        inputLayout.setError(null);
        return false;
    }
}
