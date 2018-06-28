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

import java.util.ArrayList;
import java.util.List;

public class MoveAppointment extends AppCompatActivity {

    Button Move;
    EditText uInput2;
    TextView showAppointments;
    String currentDate;
    DatabaseHelper myDb;
    int numberOfEvent;
    int userNumber2;
    String transfer1;
    int f;

    List<String> x = new ArrayList<>();
    List<Integer> y = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_appointment);
        myDb = new DatabaseHelper(this);

        Move = (Button) findViewById(R.id.moveBTN);
        showAppointments = (TextView) findViewById(R.id.editText_show2);
        uInput2 = (EditText) findViewById(R.id.userTxt2);



        Intent open = getIntent();
        currentDate = open.getStringExtra("e7");

        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            // show message
            showMessage("Error","Nothing found");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {

            if (res.getString(4).contains(currentDate)) {
                numberOfEvent ++;
                buffer.append(numberOfEvent + ". ");
                buffer.append(res.getString(2) + " ");
                buffer.append(res.getString(1) + "\n\n");
                x.add(res.getString(0));
                y.add(numberOfEvent);
                f++;
            }
        }

        // Show all data
        if (f > 0) {
            showAppointments.setText(buffer.toString());
        } else {
            //if no event found
            showAppointments.setText("No Event to Move Found");
            Move.setEnabled(false);
            uInput2.setEnabled(false);
        }


        Move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 userNumber2 = Integer.valueOf(String.valueOf(uInput2.getText()));

                for(int i = 0; i < x.size(); i++) {
                    if (userNumber2 == y.get(i)) {
                        transfer1 = x.get(i);
                        Intent open = new Intent(MoveAppointment.this, MoveDate.class);
                        open.putExtra("e8", transfer1);
                        startActivity(open);
                    }
                }

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
