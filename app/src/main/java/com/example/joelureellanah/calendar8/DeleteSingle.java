package com.example.joelureellanah.calendar8;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DeleteSingle extends AppCompatActivity {

    DatabaseHelper myDb;
    Button delete;
    EditText input;
    TextView bigtext;
    Integer deletedRows;
    String storeArray;


    String e3;
    int numberOfEvent;
    int c;
    int userNumber;
    List<String> x = new ArrayList<>();
    List<Integer> y = new ArrayList<>();
    String[] idArray;
    List<String> w = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_single);
        myDb = new DatabaseHelper(this);

        delete = (Button) findViewById(R.id.deleteSingleBtn);
        bigtext = (TextView) findViewById(R.id.editText_show);
        input = (EditText) findViewById(R.id.deleteTxt);


        Intent open = getIntent();
        e3 = open.getStringExtra("e4");
        //bigtext.setText(e3);

        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            // show message
            showMessage("Error","Nothing found");
            return;
        }

        StringBuffer buffer = new StringBuffer();

        while (res.moveToNext()) {

            if (res.getString(4).contains(e3)) {
                numberOfEvent ++;
                buffer.append(numberOfEvent + ". ");
                buffer.append(res.getString(2) + " ");
                buffer.append(res.getString(1) + "\n\n");
                x.add(res.getString(0));
                y.add(numberOfEvent);
                w.add(res.getString(1));

                //idArray[c] = res.getString(0);
                //c ++;
            }

        }

        // Show all data
        //showMessage("Data",buffer.toString());
        bigtext.setText(buffer.toString());



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //userNumber = Integer.valueOf(String.valueOf(input.getText()));

                        //for(int i = 0; i < x.size(); i++) {
                            //if (userNumber == y.get(i)) {
                                //deletedRows = myDb.deleteData(x.get(i));
                            //}
                        //}


                //if(deletedRows > 0)
                    //Toast.makeText(DeleteSingle.this,"Data Deleted",Toast.LENGTH_LONG).show();
                //else
                    //Toast.makeText(DeleteSingle.this,"Data not Deleted",Toast.LENGTH_LONG).show();

                showMessagetoDelete();

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
//show message to verify is user really want to delete event
    public void showMessagetoDelete() {

        userNumber = Integer.valueOf(String.valueOf(input.getText()));
        c = (userNumber - 1);
        storeArray = w.get(c);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Would you like to delete event: " + storeArray + " ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do this


                            userNumber = Integer.valueOf(String.valueOf(input.getText()));

                            for (int i = 0; i < x.size(); i++) {
                                if (userNumber == y.get(i)) {
                                    deletedRows = myDb.deleteData(x.get(i));
                                }
                            }


                            if (deletedRows > 0)
                                Toast.makeText(DeleteSingle.this, "Data Deleted", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(DeleteSingle.this, "Data not Deleted", Toast.LENGTH_LONG).show();


                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

        }


}
