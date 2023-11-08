package com.example.smartalarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;

public class Calender1 extends AppCompatActivity {
    private MySQLiteDBHandler dbHandler;
    private EditText editText;
    private CalendarView calendarView;
    private SQLiteDatabase sqLiteDatabase;

    private String selectedDate;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender1);
        editText = findViewById(R.id.editTextText);
        calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {

                selectedDate = Integer.toString(year) + Integer.toString(month) + Integer.toString(day);
                ReadDatabase(view);


            }
        });

        try {
            dbHandler = new MySQLiteDBHandler(this, "CalenderDatabase.db", null, 1);
            sqLiteDatabase = dbHandler.getWritableDatabase();
            sqLiteDatabase.execSQL("CREATE TABLE EventCalender(Date TEXT,Event TEXT)");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertDatabase(View view) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Date", selectedDate);
        contentValues.put("Event", editText.getText().toString());
        sqLiteDatabase.insert("EventCalender", null, contentValues);

    }


    public void ReadDatabase(View view) {
        String query = "Select Event from EventCalendar where Date = " + selectedDate;
        try {
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            cursor.moveToFirst();
            editText.setText(cursor.getString(0));
        } catch (Exception e) {
            e.printStackTrace();
            editText.setText("");
        }


    }
}