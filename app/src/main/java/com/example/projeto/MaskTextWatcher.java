package com.example.projeto;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class MaskTextWatcher implements TextWatcher {
    private final EditText editText;
    private final TextView text;


    public MaskTextWatcher(EditText editText,TextView text) {
        this.editText = editText;
        this.text = text;
    }

    private boolean isUpdating;
    private String oldText = "";

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

        for (int i = 0; i < str.length() && i < 12; i++) {
            formatted += str.charAt(i);
            if ((i + 1) % 4 == 0 && (i + 1) < str.length()) {
                formatted += " ";
            }
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
