package com.example.joelureellanah.calendar8;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;
import android.support.annotation.NonNull;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    CalendarView calendar;
    Button save, show, delete, move, search;

    int a;
    int b;
    int c;
    int f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        save = (Button) findViewById(R.id.saveBtn);
        show = (Button) findViewById(R.id.view_editBtn);
        delete = (Button) findViewById(R.id.deleteBtn);
        move = (Button) findViewById(R.id.moveBtn);
        search = (Button) findViewById(R.id.searchBtn);
        calendar = (CalendarView) findViewById(R.id.calendar);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + (month + 1) + "/" + year, Toast.LENGTH_SHORT).show();
                a = dayOfMonth;
                b = (month + 1);
                c = year;

            }
        });


//if the Create Appointment Button is pressed..
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent open = new Intent(MainActivity.this, CreateAppointment.class);


                String date1 = Integer.toString(a) + "/" + Integer.toString(b) + "/" + Integer.toString(c);
                open.putExtra("e1", date1);

                startActivity(open);
            }
        });

//If the View/Edit Button is pressed..
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent open = new Intent(MainActivity.this, ViewEdit.class);
                String date1 = Integer.toString(a) + "/" + Integer.toString(b) + "/" + Integer.toString(c);
                open.putExtra("e5", date1);
                startActivity(open);
            }
        });

//if the Delete Appointment button is pressed
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor res = myDb.getAllData();
                if(res.getCount() == 0) {
                    // show message
                    showMessage("Error","Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                String date1 = Integer.toString(a) + "/" + Integer.toString(b) + "/" + Integer.toString(c);

                while (res.moveToNext()) {
                    if (res.getString(4).contains(date1)) {

                        f++;
                    }

                    }

//Validation to not allow user to delete where there is no event.
                if (f == 0) {

                    Toast.makeText(getApplicationContext(), "No Event to Delete", Toast.LENGTH_SHORT).show();


                } else {

                    Intent open = new Intent(MainActivity.this, DeleteAppointment.class);

                    open.putExtra("e2", date1);
                    f = 0;
                    startActivity(open);
                }
            }
        });

//if the Move Button is pressed..
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open = new Intent(MainActivity.this, MoveAppointment.class);


                String date1 = Integer.toString(a) + "/" + Integer.toString(b) + "/" + Integer.toString(c);
                open.putExtra("e7", date1);

                startActivity(open);
            }
        });

//if the Search Button is pressed..
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open = new Intent (MainActivity.this, SearchAppointment.class);
                startActivity(open);
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
