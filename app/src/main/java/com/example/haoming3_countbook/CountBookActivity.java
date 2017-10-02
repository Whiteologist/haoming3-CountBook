/*
 * CountBook - CountBookActivity
 *
 * Version 1.0
 *
 * October 1, 2017
 *
 * Copyright (c) 2017 Jimmy Liang, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms of conditions of the Code of Student Behavior at University of Alberta.
 */

package com.example.haoming3_countbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class CountBookActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private ArrayList<CountBook> counters = new ArrayList<>();
    private CountBook counter;
    private int index;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counter);

        loadFromFile();

        Intent intent = getIntent();
        index = intent.getIntExtra("index", 0);
        counter = counters.get(index);

        final EditText Name = (EditText) findViewById(R.id.name);
        final EditText Initial = (EditText) findViewById(R.id.initialValue);
        final EditText Current = (EditText) findViewById(R.id.currentValue);
        final EditText Comment = (EditText) findViewById(R.id.comment);
        final Button increment = (Button) findViewById(R.id.increment);
        final Button decrement = (Button) findViewById(R.id.decrement);
        final Button reset = (Button) findViewById(R.id.reset);
        final Button save = (Button) findViewById(R.id.save);
        Button delete = (Button) findViewById(R.id.delete);
        increment.setEnabled(false);
        decrement.setEnabled(false);
        reset.setEnabled(false);
        save.setEnabled(false);

        /**
         * Enables save button if the name field is filled in
         * Disables save button if the name field is empty
         */
        Name.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                checkInputs();
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            public void afterTextChanged(Editable s) { checkInputs(); }

            private void checkInputs() {
                if (Name.getText().toString().matches("")) { save.setEnabled(false); }
                else { save.setEnabled(true); }
            }
        });

        /**
         * Enables reset and save button if the initial value field is filled in
         * Disables reset and save button if the initial value field is empty
         */
        Initial.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                checkInputs();
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            public void afterTextChanged(Editable s) { checkInputs(); }

            private void checkInputs() {
                if (Initial.getText().toString().matches("")) {
                    reset.setEnabled(false);
                    save.setEnabled(false);
                } else {
                    reset.setEnabled(true);
                    save.setEnabled(true);
                }
            }
        });

        /**
         * Enables increment, decrement, reset and save button if the current value field is filled in
         * Disables increment, decrement, reset and save button if the current value field is empty
         */
        Current.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                checkInputs();
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            public void afterTextChanged(Editable s) { checkInputs(); }

            private void checkInputs() {
                if (Current.getText().toString().matches("")) {
                    increment.setEnabled(false);
                    decrement.setEnabled(false);
                    reset.setEnabled(false);
                    save.setEnabled(false);
                } else {
                    increment.setEnabled(true);
                    decrement.setEnabled(true);
                    reset.setEnabled(true);
                    save.setEnabled(true);
                    counter.setDate();
                }
            }
        });

        /**
         * Increment the current counter by 1
         * Update the current date
         */
        increment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int count = Integer.parseInt(Current.getText().toString());
                int number = count + 1;
                Current.setText(String.valueOf(number));
                counter.setDate();
            }
        });

        /**
         * Decrement the current counter by 1
         * Update the current date
         */
        decrement.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int count = Integer.parseInt(Current.getText().toString());
                if (count > 0) {
                    int number = count - 1;
                    Current.setText(String.valueOf(number));
                    counter.setDate();
                }
            }
        });

        /**
         * Reset the current value into initial value
         * Update the current date
         */
        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int count = Integer.parseInt(Initial.getText().toString());
                Current.setText(String.valueOf(count));
                counter.setDate();
            }
        });

        /**
         * Save the current values into CountBook
         */
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(Activity.RESULT_OK);
                String name = Name.getText().toString();
                int value = Integer.parseInt(Initial.getText().toString());
                int current = Integer.parseInt(Current.getText().toString());
                String comment = Comment.getText().toString();

                counter.setName(name);
                counter.setValue(value);
                counter.setCount(current);
                counter.setComment(comment);
                counters.set(index, counter);
                saveInFile();
                finish();
            }
        });

        /**
         * Delete the current counter
         */
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(Activity.RESULT_OK);
                counters.remove(index);
                saveInFile();
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();

        counter = counters.get(index);

        EditText Name = (EditText) findViewById(R.id.name);
        Name.setText(counter.getName());

        EditText initialValue = (EditText) findViewById(R.id.initialValue);
        initialValue.setText(String.valueOf(counter.getValue()));

        EditText currentValue = (EditText) findViewById(R.id.currentValue);
        currentValue.setText(String.valueOf(counter.getCount()));

        EditText Comment = (EditText) findViewById(R.id.comment);
        Comment.setText(counter.getComment());

    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<CountBook>>() {}.getType();
            counters = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            counters = new ArrayList<>();
        }
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(counters, writer);
            writer.flush();

            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}