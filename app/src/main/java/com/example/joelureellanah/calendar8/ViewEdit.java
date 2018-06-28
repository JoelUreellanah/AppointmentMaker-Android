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

public class ViewEdit extends AppCompatActivity {

    Button editbtn;
    EditText uInput;
    TextView textShow;
    DatabaseHelper myDb;
    String a1;
    int numberOfEvent;
    int userNumber1;
    String transfer;
    int f;

    List<String> x = new ArrayList<>();
    List<Integer> y = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit);

        editbtn = (Button) findViewById(R.id.editBtn1);
        uInput = (EditText) findViewById(R.id.userTxt);
        textShow = (TextView) findViewById(R.id.editText_show1);
        myDb = new DatabaseHelper(this);


        Intent open = getIntent();
        a1 = open.getStringExtra("e5");


        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            // show message
            showMessage("Error","Nothing found");
            return;
        }

        StringBuffer buffer = new StringBuffer();
            while (res.moveToNext()) {

            if (res.getString(4).contains(a1)) {
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
            textShow.setText(buffer.toString());
        } else {
            textShow.setText("No Event Found");
            editbtn.setEnabled(false);
            uInput.setEnabled(false);
        }



        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNumber1 = Integer.valueOf(String.valueOf(uInput.getText()));

                for(int i = 0; i < x.size(); i++) {
                    if (userNumber1 == y.get(i)) {
                        transfer = x.get(i);
                        Intent open = new Intent(ViewEdit.this, EditAppointment.class);
                        open.putExtra("e6", transfer);
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
