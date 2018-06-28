package com.example.joelureellanah.calendar8;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SearchAppointment extends AppCompatActivity {

    Button search;
    EditText userSearch;
    TextView appointmentSearch;
    DatabaseHelper myDb;

    int numberOfEvent;
    int numberOfSearch;
    String userInput3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_appointment);
        myDb = new DatabaseHelper(this);

        search = (Button) findViewById(R.id.searchBTN);
        userSearch = (EditText) findViewById(R.id.searchEditText);
        appointmentSearch = (TextView) findViewById(R.id.showSearch);


        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            // show message
            showMessage("Error","Nothing found");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {


                numberOfEvent ++;
                buffer.append(numberOfEvent + ". ");
                buffer.append(res.getString(2) + " ");
                buffer.append(res.getString(1) + " ");
                buffer.append(res.getString(4) + "\n\n");


        }

        // Show all data
        appointmentSearch.setText(buffer.toString());

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userInput3 = String.valueOf(userSearch.getText());



                Cursor res = myDb.getAllData();
                if(res.getCount() == 0) {
                    // show message
                    showMessage("Error","Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {

                    if (res.getString(1).contains(userInput3)) {
                        numberOfSearch++;
                        buffer.append(numberOfSearch + ". ");
                        buffer.append(res.getString(2) + " ");
                        buffer.append(res.getString(1) + " ");
                        buffer.append(res.getString(4) + "\n\n");
                    } else if (res.getString(3).contains(userInput3)) {
                        numberOfSearch++;
                        buffer.append(numberOfSearch + ". ");
                        buffer.append(res.getString(2) + " ");
                        buffer.append(res.getString(1) + " ");
                        buffer.append(res.getString(4) + "\n\n");
                    }
                }

                // Show all data
                appointmentSearch.setText(buffer.toString());

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
