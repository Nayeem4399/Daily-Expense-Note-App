package com.example.dailyexpensenote.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dailyexpensenote.dao.ExpenseDao;
import com.example.dailyexpensenote.entity.Expense;

@Database(entities = {Expense.class},version = 1)
public abstract class ExpenseDatabase extends RoomDatabase {

    public static ExpenseDatabase db;

    public abstract ExpenseDao getExpenseDao();


    public static ExpenseDatabase getObject(Context context){

        if(db==null){
            db= Room.databaseBuilder(context,ExpenseDatabase.class,"User")
                    .allowMainThreadQueries()
                    .build();

            return db;
        }

        else
            return db;
    }
}
