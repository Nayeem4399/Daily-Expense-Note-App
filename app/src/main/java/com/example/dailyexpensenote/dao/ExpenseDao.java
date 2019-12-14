package com.example.dailyexpensenote.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dailyexpensenote.entity.Expense;

import java.util.List;

@Dao
public interface ExpenseDao {

    @Insert
    long insertNewExpense(Expense expense);


    @Delete
    int deleteExpense(Expense expense);


    @Update
    int updateExpese(Expense expense);



    @Query("Select*from Expense")
    List<Expense> getAllExpense();

/*
    @Query("Select*from Expense Where (expenseType=\"Dinner\")")
    List<Expense>getAllTypeExpense();*/
}
