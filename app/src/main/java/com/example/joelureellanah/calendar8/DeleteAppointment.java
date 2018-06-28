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

public class DeleteAppointment extends AppCompatActivity {

    DatabaseHelper myDb;
    Button delete, delete_single;
    EditText text;
    Integer deletedRows;

    String e2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_appointment);
        myDb = new DatabaseHelper(this);

        delete = (Button) findViewById(R.id.deleteAll);
        text = (EditText) findViewById(R.id.editText_date2);
        delete_single = (Button) findViewById(R.id.deleteSingle);
//get user's selected date
        Intent open = getIntent();
        e2 = open.getStringExtra("e2");
        text.setText(e2);
//delete all appointment
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
                while (res.moveToNext()) {

                    if (res.getString(4).contains(e2)) {
                        buffer.append("Id :" + res.getString(0) + "\n");
                        buffer.append("Title :" + res.getString(1) + "\n");
                        buffer.append("Time :" + res.getString(2) + "\n");
                        buffer.append("Details :" + res.getString(3) + "\n");
                        buffer.append("Date :" + res.getString(4) + "\n\n");
                        deletedRows = myDb.deleteData(res.getString(0));
                    }
                }
                if(deletedRows > 0)
                    Toast.makeText(DeleteAppointment.this,"Data Deleted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(DeleteAppointment.this,"Data not Deleted",Toast.LENGTH_LONG).show();


            }
        });

//open new activity where user choose which appointment to delete
        delete_single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open = new Intent(DeleteAppointment.this, DeleteSingle.class);

                open.putExtra("e4", e2);

                startActivity(open);
            }
        });
    }



//display message if something goes wrong during deleting data.
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
