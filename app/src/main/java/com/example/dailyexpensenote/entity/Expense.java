package com.example.dailyexpensenote.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Expense implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String expenseType;
    private String expenseDate;
    private String expenseTime;
    private String expenseAmount;
    private String document;

    public Expense(String expenseType, String expenseDate, String expenseTime, String expenseAmount, String document) {
        this.expenseType = expenseType;
        this.expenseDate = expenseDate;
        this.expenseTime = expenseTime;
        this.expenseAmount = expenseAmount;
        this.document = document;
    }

    @Ignore
    public Expense(int id, String expenseType, String expenseDate, String expenseTime, String expenseAmount, String document) {
        this.id = id;
        this.expenseType = expenseType;
        this.expenseDate = expenseDate;
        this.expenseTime = expenseTime;
        this.expenseAmount = expenseAmount;
        this.document = document;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getExpenseTime() {
        return expenseTime;
    }

    public void setExpenseTime(String expenseTime) {
        this.expenseTime = expenseTime;
    }

    public String getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
