package com.example.joelureellanah.calendar8;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAppointment extends AppCompatActivity {

    DatabaseHelper myDb;
    Button create, synonymsBtn;
    EditText title, time, detail, date, synonyms;
    String e1;
    String sTitle, sTime, sDetail;
    int w;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment);
        myDb = new DatabaseHelper(this);

        title = (EditText) findViewById(R.id.editText_title);
        time = (EditText) findViewById(R.id.editText_time);
        detail = (EditText) findViewById(R.id.editText_details);
        date = (EditText) findViewById(R.id.editText_date);
        synonyms = (EditText) findViewById(R.id.synonyms);


        create = (Button) findViewById(R.id.createBtn);
        synonymsBtn = (Button) findViewById(R.id.thesaurus);

        //get the selected date from the main activity
        Intent open = getIntent();
        e1 = open.getStringExtra("e1");
        date.setText(e1);

        AddData();


    }


    // Add new appointment
    public  void AddData() {
        create.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();




                        sTitle = title.getText().toString();
                        sTime = time.getText().toString();
                        sDetail = detail.getText().toString();

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {

                            if (res.getString(1).matches(sTitle) && res.getString(4).contains(e1)) {
//Dispaly Message when a same Title of event is already been created.
                                Toast.makeText(CreateAppointment.this, "Title already inserted. Please insert a different one.", Toast.LENGTH_LONG).show();
                                w++;
                                break;
                            }
                        }

                        // if the user does not input one of the three textbox display a toast.
                        if (sTitle.matches("") || sTime.matches("") || sDetail.matches("")) {
                            Toast.makeText(CreateAppointment.this,"Please fill all the requirements.",Toast.LENGTH_LONG).show();
                        } else if (w > 0) {
                            Toast.makeText(CreateAppointment.this, "Title already inserted. Please insert a different one.", Toast.LENGTH_SHORT).show();
                        } else {
                            // save appointment in the database
                            boolean isInserted = myDb.insertData(title.getText().toString(),
                                    time.getText().toString(),
                                    detail.getText().toString(),
                                    date.getText().toString());
                            if (isInserted == true)
                                Toast.makeText(CreateAppointment.this, "Data Inserted", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(CreateAppointment.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }
}
