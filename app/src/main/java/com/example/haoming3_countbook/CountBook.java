/*
 * CountBook - CountBook object
 *
 * Version 1.0
 *
 * October 1, 2017
 *
 * Copyright (c) 2017 Jimmy Liang, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms of conditions of the Code of Student Behavior at University of Alberta.
 */

package com.example.haoming3_countbook;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CountBook {
    private String name;
    private int value;
    private int count;
    private String comment;
    private Date date;

    public CountBook(String name, int count, String comment){
        this.name = name;
        this.value = count;
        this.count = count;
        this.comment = comment;
        this.date = new Date();
    }

    public String getName(){ return name; }

    public void setName(String name){ this.name = name; }

    public int getValue() { return value; }

    public void setValue(int value) { this.value = value; }

    public int getCount(){
        return count;
    }

    public void setCount(int count){ this.count = count; }

    public String getComment(){ return comment; }

    public void setComment(String comment){ this.comment = comment; }

    public void setDate() { this.date = new Date(); }

    /**
     * Display the Counter with values on the array in MainActivity
     * @return the Counter
     */
    @Override
    public String toString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return name + " | Value: " + Integer.toString(count) + " | " + getComment() + "\n" + df.format(date);
    }
}
