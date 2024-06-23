package com.example.projeto;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class ExpiryDateTextWatcher implements TextWatcher {
    private final EditText editText;
    private final TextView text;

    private boolean isUpdating;

    public ExpiryDateTextWatcher(EditText editText, TextView text) {
        this.editText = editText;
        this.text = text;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Nothing to do here
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (isUpdating) {
            isUpdating = false;
            return;
        }

        String str = s.toString().replaceAll("[^\\d]", "");
        String formatted = "";

        if (str.length() >= 2) {
            formatted = str.substring(0, 2) + "/";
            if (str.length() > 2) {
                formatted += str.substring(2, Math.min(str.length(), 4));
            }
        } else {
            formatted = str;
        }

        isUpdating = true;
        editText.setText(formatted);
        editText.setSelection(formatted.length());
        text.setText(formatted);
    }

    @Override
    public void afterTextChanged(Editable s) {
        // Nothing to do here
    }
}

