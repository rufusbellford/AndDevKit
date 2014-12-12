package com.rc.devkit.utilities;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class Keyboard 
{
    public static void showKeyboard(final EditText editText)
    {
        final InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        editText.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                imm.showSoftInput(editText, 0);
            }
        }, 100);
    }

    public static void hideKeyboard(final EditText editText)
    {
        final InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
