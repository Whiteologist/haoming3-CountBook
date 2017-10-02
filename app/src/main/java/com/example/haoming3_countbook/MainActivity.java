/*
 * CountBook - MainActivity
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private ListView counterList;

    private ArrayList<CountBook> counter = new ArrayList<CountBook>();
    private ArrayAdapter<CountBook> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        counterList = (ListView) findViewById(R.id.counterList);
        Button add = (Button) findViewById(R.id.add);

        /**
         * View and edit each counter upon clicking
         */
        counterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
                Intent intent = new Intent(MainActivity.this, CountBookActivity.class);
                intent.putExtra("index",index);
                startActivity(intent);
                CountBook item = (CountBook) parent.getItemAtPosition(index);
            }
        });

        /**
         * Add a new counter
         */
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
        adapter = new ArrayAdapter<CountBook>(this,android.R.layout.simple_list_item_1, counter);
        counterList.setAdapter(adapter);
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
