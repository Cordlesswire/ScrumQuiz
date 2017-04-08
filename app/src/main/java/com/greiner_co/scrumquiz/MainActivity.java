package com.greiner_co.scrumquiz;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private boolean[] correctAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.greiner_co.scrumquiz.R.layout.activity_main);

        initializeVariables();
    }

    /**
     * Private method to help us initialize our variables
     */
    private void initializeVariables() {
        correctAnswers = new boolean[6];
        TextView seekBar1TextView = (TextView) findViewById(com.greiner_co.scrumquiz.R.id.seek_bar_1_text_view);
        seekBar1TextView.setText("0");
        SeekBar seekBar1 = (SeekBar) findViewById(com.greiner_co.scrumquiz.R.id.seek_bar_1);
        setSeekBarListener(seekBar1, seekBar1TextView);
        EditText editTextView1 = (EditText) findViewById(com.greiner_co.scrumquiz.R.id.question_2_edit_text);
        enableKeyboardHidingOnEditText(editTextView1);
        setEditText1Listener(editTextView1);
        final RadioGroup radioGroup1 = (RadioGroup) findViewById(com.greiner_co.scrumquiz.R.id.question_1_radio_group);
        setRadioGroup1Listener(radioGroup1);
        final RadioGroup radioGroup2 = (RadioGroup) findViewById(com.greiner_co.scrumquiz.R.id.question_5_radio_group);
        setRadioGroup2Listener(radioGroup2);
    }

    /**
     * Set the seekbar listener to react on value changes
     *
     * @param seekBar  the seekbar object
     * @param textView the corresponding textView to show the value of the seekbar
     */
    private void setSeekBarListener(final SeekBar seekBar, final TextView textView) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, final int progress, final boolean fromUser) {
                textView.setText(String.valueOf(progress));
                correctAnswers[3] = progress == 5;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d("MainActivity", "onStartTrackingTouch received");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("MainActivity", "onStopTrackingTouch received");
            }
        });
    }

    /**
     * Set the listener to the editText view to react on value changes
     *
     * @param et the editText view
     */
    private void setEditText1Listener(final EditText et) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = et.getText().toString().toLowerCase();

                correctAnswers[1] = text.equals("done");
            }
        });
    }

    /**
     * Set radiogroup1 Listener to react on changes of the value
     *
     * @param rg RadioGroup id to set the listener to
     */
    private void setRadioGroup1Listener(final RadioGroup rg) {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                View rb = rg.findViewById(checkedId);
                int index = rg.indexOfChild(rb);

                switch (index) {
                    case 0:
                        correctAnswers[0] = false;
                        break;
                    case 1:
                        correctAnswers[0] = true;
                        break;
                }
            }
        });
    }

    /**
     * Set radiogroup2 Listener to react on changes of the value
     *
     * @param rg RadioGroup id to set the listener to
     */
    private void setRadioGroup2Listener(final RadioGroup rg) {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                View rb = rg.findViewById(checkedId);
                int index = rg.indexOfChild(rb);

                switch (index) {
                    case 0:
                        correctAnswers[4] = false;
                        break;
                    case 1:
                        correctAnswers[4] = true;
                        break;
                    case 2:
                        correctAnswers[4] = false;
                }
            }
        });
    }

    /**
     * Part 1 of 2 Code samples to hide the keyboard as soon as clicked outside of a TextEdit
     *
     * @param editText EditText view to set the keyboard hiding to
     * @see <a href="http://stackoverflow.com/a/19828165/1469260">http://stackoverflow.com/a/19828165/1469260</a>
     */
    private void enableKeyboardHidingOnEditText(EditText editText) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    /**
     * Part 2 of 2 Code samples to hide the keyboard as soon as clicked outside of a TextEdit
     *
     * @param view EditText view to hide the keyboard
     * @see <a href="http://stackoverflow.com/a/19828165/1469260">http://stackoverflow.com/a/19828165/1469260</a>
     */
    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @SuppressWarnings("UnusedParameters")
    public void resultButtonClicked(View view) {
        int rightAnswers = 0;
        int wrongAnswers = 0;

        checkRadioButtons();

        for (boolean answer : correctAnswers) {
            if (answer) {
                rightAnswers++;
            } else {
                wrongAnswers++;
            }
        }

        if (rightAnswers == 6) {
            Toast.makeText(this, "All questions correct answered! Great job!", Toast.LENGTH_LONG).show();
        } else {
            String result = wrongAnswers + " out of 6 are wrong. Please try again.";
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Verify the state of each contained checkbox to set the correct answers
     */
    private void checkRadioButtons() {
        boolean cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8, cb9;

        // Check answers for question 3
        CheckBox cb = (CheckBox) findViewById(com.greiner_co.scrumquiz.R.id.question_3_checkbox_1);
        cb1 = cb.isChecked();
        cb = (CheckBox) findViewById(com.greiner_co.scrumquiz.R.id.question_3_checkbox_2);
        cb2 = cb.isChecked();
        cb = (CheckBox) findViewById(com.greiner_co.scrumquiz.R.id.question_3_checkbox_3);
        cb3 = cb.isChecked();
        cb = (CheckBox) findViewById(com.greiner_co.scrumquiz.R.id.question_3_checkbox_4);
        cb4 = cb.isChecked();
        cb = (CheckBox) findViewById(com.greiner_co.scrumquiz.R.id.question_3_checkbox_5);
        cb5 = cb.isChecked();
        cb = (CheckBox) findViewById(com.greiner_co.scrumquiz.R.id.question_3_checkbox_6);
        cb6 = cb.isChecked();

        // Only answers 2 + 3 + 5 are right
        correctAnswers[2] = (cb2 && cb3 && cb5) && !(cb1 || cb4 || cb6);

        // Check answers for question 6
        cb = (CheckBox) findViewById(com.greiner_co.scrumquiz.R.id.question_6_checkbox_1);
        cb7 = cb.isChecked();
        cb = (CheckBox) findViewById(com.greiner_co.scrumquiz.R.id.question_6_checkbox_2);
        cb8 = cb.isChecked();
        cb = (CheckBox) findViewById(com.greiner_co.scrumquiz.R.id.question_6_checkbox_3);
        cb9 = cb.isChecked();

        // only answer 9 is right
        correctAnswers[5] = cb9 && !(cb7 || cb8);
    }
}
