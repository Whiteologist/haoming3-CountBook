/*
 * CountBook - AddActivity
 *
 * Version 1.0
 *
 * October 1, 2017
 *
 * Copyright (c) 2017 Jimmy Liang, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms of conditions of the Code of Student Behavior at University of Alberta.
 */

package com.example.haoming3_countbook;

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

public class AddActivity extends AppCompatActivity {

    public static final String FILENAME = "file.sav";
    private ArrayList<CountBook> counter = new ArrayList<CountBook>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create);

        Intent intent = getIntent();
        loadFromFile();

        final EditText Name = (EditText) findViewById(R.id.name);
        final EditText Initial = (EditText) findViewById(R.id.initialValue);
        final EditText Comment = (EditText) findViewById(R.id.comment);
        final Button add = (Button) findViewById(R.id.add);
        add.setEnabled(false);

        /**
         * Enables add button if the name field is filled in
         * Disables add button if the name field is empty
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
                if (Name.getText().toString().matches("") || Initial.getText().toString().matches("")) {
                    add.setEnabled(false);
                }else{
                    add.setEnabled(true);
                }
            }
        });

        /**
         * Enables add button if the initial value field is filled in
         * Disables add button if the initial value field is empty
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
                if (Name.getText().toString().matches("") || Initial.getText().toString().matches("")) {
                    add.setEnabled(false);
                }else{
                    add.setEnabled(true);
                }
            }
        });

        /**
         * Save the profile into CountBook array
         */
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                String name = Name.getText().toString();
                int initial = Integer.parseInt(Initial.getText().toString());
                String comment = Comment.getText().toString();

                counter.add(new CountBook(name, initial, comment));
                saveInFile();
                finish();
            }
        });
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<CountBook>>() {}.getType();
            counter = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            counter = new ArrayList<CountBook>();
        }
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(counter, writer);
            writer.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}
