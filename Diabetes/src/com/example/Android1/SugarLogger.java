package com.example.Android1;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Richard on 2/5/14.
 */
public class SugarLogger extends Activity {

  private EditText txtDate;
  private EditText txtTime;
  private RadioButton rdoBefore;
  private RadioButton rdoAfter;
  private EditText txtNotes;
  private EditText txtBS;
  private Button btnBack;
  private Button btnAdd;
  final Calendar c = Calendar.getInstance();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.sugar);

    initializeView();
  }

  private void initializeView(){
    txtDate = (EditText) findViewById(R.id.txtDate);
    txtTime = (EditText) findViewById(R.id.txtTime);
    rdoBefore = (RadioButton) findViewById(R.id.rdoBefore);
    rdoAfter = (RadioButton) findViewById(R.id.rdoAfter);
    txtNotes = (EditText) findViewById(R.id.txtNotes);
    txtBS = (EditText) findViewById(R.id.txtBS);
    btnBack = (Button) findViewById(R.id.btnBack);
    btnAdd = (Button) findViewById(R.id.btnAdd);
    setCurrentDateOnView();

    btnBack.setOnClickListener(new View.OnClickListener(){
      public void onClick(View v){
        Intent switchActivity = new Intent(v.getContext() ,MyActivity.class);
        startActivity(switchActivity);
      }
    });



    btnAdd.setOnClickListener(new View.OnClickListener(){
      public void onClick(View v){
        String date = String.valueOf(txtDate.getText());
        String time = String.valueOf(txtTime.getText());
        String beforeAfter = setMeal();
        String notes = String.valueOf(txtNotes.getText());
        String bs = String.valueOf(txtBS.getText());

        String FILENAME = "sugar_log.csv";
        String entry = date + "," + time + "," + beforeAfter + "," + notes + "," + bs + "\n";
        try {
          FileOutputStream out = openFileOutput(FILENAME, Context.MODE_APPEND);
          out.write( entry.getBytes());
          out.close();
          Toast.makeText(SugarLogger.this,getString(R.string.success), Toast.LENGTH_SHORT).show();
        } catch (Exception e){
          e.printStackTrace();
          Toast.makeText(SugarLogger.this,getString(R.string.failure), Toast.LENGTH_SHORT).show();
        }
      }

    });
  }

  public void switchToChart(View view){
    Intent switchActivity = new Intent(view.getContext() , SugarChart.class);
    startActivity(switchActivity);
  }

  public void setCurrentDateOnView(){

    String dateFormat = "MM-dd-yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat( dateFormat, Locale.US);
    String timeFormat = "hh:mm a";
    SimpleDateFormat stf = new SimpleDateFormat( timeFormat, Locale.US);
    txtDate.setText(sdf.format(c.getTime()));
    txtTime.setText(stf.format(c.getTime()));
  }

  public String setMeal(){
    String r = "B";
    if (rdoBefore.isChecked()){
      r = "B";
    }
    if (rdoAfter.isChecked()){
      r = "A";
    }
    return r;
  }

}
