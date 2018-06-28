package com.example.joelureellanah.calendar8;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

public class MoveDate extends AppCompatActivity {

    CalendarView newCalendar;
    EditText show_1, show_2, show_3, show_4;
    Button move1;
    DatabaseHelper myDb;

    int a;
    int b;
    int c;
    int numberOfEvent;
    String v1;
    String TITLE1, TIME1, DETAIL1, DATE1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_date);
        myDb = new DatabaseHelper(this);

        newCalendar = (CalendarView) findViewById(R.id.calendar1);
        move1 = (Button) findViewById(R.id.moveDateBtn);
        show_1 = (EditText) findViewById(R.id.show123);
        show_2 = (EditText) findViewById(R.id.show4);
        show_3 = (EditText) findViewById(R.id.show5);
        show_4 = (EditText) findViewById(R.id.show6);
        Intent open = getIntent();
        v1 = open.getStringExtra("e8");



        newCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + (month + 1) + "/" + year, Toast.LENGTH_SHORT).show();
                a = dayOfMonth;
                b = (month + 1);
                c = year;

            }
        });






        move1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String date1 = Integer.toString(a) + "/" + Integer.toString(b) + "/" + Integer.toString(c);

                Cursor res = myDb.getAllData();
                if(res.getCount() == 0) {
                    // show message
                    showMessage("Error","Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {

                    if (res.getString(0).contains(v1)) {
                        show_1.setText(res.getString(1));
                        show_2.setText(res.getString(2));
                        show_3.setText(res.getString(3));
                        show_4.setText(date1);

                    }
                }

                // Show all data

                myDb.deleteData(v1);

                boolean isInserted = myDb.insertData(show_1.getText().toString(),
                        show_2.getText().toString(),
                        show_3.getText().toString(),
                        show_4.getText().toString());
                if(isInserted == true)
                    Toast.makeText(MoveDate.this,"Event Updated",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MoveDate.this,"Event not Updated",Toast.LENGTH_LONG).show();




            }
        });


    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
