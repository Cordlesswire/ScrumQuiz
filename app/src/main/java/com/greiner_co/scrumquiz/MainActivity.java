package com.greiner_co.scrumquiz;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
    private CheckBox cb1;
    private CheckBox cb2;
    private CheckBox cb3;
    private CheckBox cb4;
    private CheckBox cb5;
    private CheckBox cb6;
    private CheckBox cb7;
    private CheckBox cb8;
    private CheckBox cb9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.greiner_co.scrumquiz.R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.scrum_quiz_title));
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);

        initializeVariables();
    }

    /**
     * Private method to help us initialize our variables
     */
    private void initializeVariables() {
        correctAnswers = new boolean[6];
        TextView seekBar1TextView = (TextView) findViewById(R.id.seek_bar_1_text_view);
        seekBar1TextView.setText("0");
        SeekBar seekBar1 = (SeekBar) findViewById(R.id.seek_bar_1);
        setSeekBarListener(seekBar1, seekBar1TextView);
        EditText editTextView1 = (EditText) findViewById(R.id.question_2_edit_text);
        enableKeyboardHidingOnEditText(editTextView1);
        setEditText1Listener(editTextView1);
        RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.question_1_radio_group);
        setRadioGroup1Listener(radioGroup1);
        RadioGroup radioGroup2 = (RadioGroup) findViewById(R.id.question_5_radio_group);
        setRadioGroup2Listener(radioGroup2);
        cb1 = (CheckBox) findViewById(R.id.question_3_checkbox_1);
        cb2 = (CheckBox) findViewById(R.id.question_3_checkbox_2);
        cb3 = (CheckBox) findViewById(R.id.question_3_checkbox_3);
        cb4 = (CheckBox) findViewById(R.id.question_3_checkbox_4);
        cb5 = (CheckBox) findViewById(R.id.question_3_checkbox_5);
        cb6 = (CheckBox) findViewById(R.id.question_3_checkbox_6);
        cb7 = (CheckBox) findViewById(R.id.question_6_checkbox_1);
        cb8 = (CheckBox) findViewById(R.id.question_6_checkbox_2);
        cb9 = (CheckBox) findViewById(R.id.question_6_checkbox_3);
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
        boolean cb1Value, cb2Value, cb3Value, cb4Value, cb5Value, cb6Value, cb7Value, cb8Value, cb9Value;

        // Check answers for question 3
        cb1Value = cb1.isChecked();

        cb2Value = cb2.isChecked();
        cb3Value = cb3.isChecked();
        cb4Value = cb4.isChecked();
        cb5Value = cb5.isChecked();
        cb6Value = cb6.isChecked();

        // Only answers 2 + 3 + 5 are right
        correctAnswers[2] = (cb2Value && cb3Value && cb5Value) && !(cb1Value || cb4Value || cb6Value);

        // Check answers for question 6
        cb7Value = cb7.isChecked();
        cb8Value = cb8.isChecked();
        cb9Value = cb9.isChecked();

        // only answer 9 is right
        correctAnswers[5] = cb9Value && !(cb7Value || cb8Value);
    }
}
