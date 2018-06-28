package com.example.joelureellanah.calendar8;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditAppointment extends AppCompatActivity {


    DatabaseHelper myDb;
    Button editAppointment;
    EditText Title, Time, Detail, Date;
    TextView tit, tim, det, dat;
    String d1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_appointment);

        editAppointment = (Button) findViewById(R.id.editBTN);
        Title = (EditText) findViewById(R.id.editText_title1);
        Time = (EditText) findViewById(R.id.editText_time1);
        Detail = (EditText) findViewById(R.id.editText_details1);
        Date = (EditText) findViewById(R.id.editText_DATE);
        myDb = new DatabaseHelper(this);

//get user's selected day
        Intent open = getIntent();
        d1 = open.getStringExtra("e6");



        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            // show message
            showMessage("Error","Nothing found");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {

            if (res.getString(0).contains(d1)) {

                Title.setText(res.getString(1));
                Time.setText(res.getString(2));
                Detail.setText(res.getString(3));
                Date.setText(res.getString(4));
                //buffer.append(res.getString(2) + " ");
                //buffer.append(res.getString(1) + "\n\n");

            }
        }

//update the database with new event change
        editAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.deleteData(d1);

                boolean isInserted = myDb.insertData(Title.getText().toString(),
                        Time.getText().toString(),
                        Detail.getText().toString(),
                        Date.getText().toString());
                if(isInserted == true)
                    Toast.makeText(EditAppointment.this,"Event Updated",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(EditAppointment.this,"Event not Updated",Toast.LENGTH_LONG).show();
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
