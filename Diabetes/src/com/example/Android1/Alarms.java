package com.example.Android1;

import android.app.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import java.io.*;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Richard on 3/11/14.
 */
public class Alarms extends Activity {

  private ArrayList<PendingIntent>pendingIntents = new ArrayList<PendingIntent>();
  private ArrayList<ToggleButton>toggleButtons = new ArrayList<ToggleButton>();
  private ArrayList<EditText>editTexts = new ArrayList<EditText>();
  private ArrayList<EditText>editNotes = new ArrayList<EditText>();
  private ArrayList<CheckBox>checkBoxes = new ArrayList<CheckBox>();
  // All ToggleButtons
  private ToggleButton tog1,tog2,tog3,tog4,tog5,tog6,tog7,tog8,tog9,tog10;
        // All DateTime Texts
  private EditText ea1,ea2,ea3,ea4,ea5,ea6,ea7,ea8,ea9,ea10;
        // All Note Texts
  private EditText en1,en2,en3,en4,en5,en6,en7,en8,en9,en10;
        // All CheckBoxes
  private CheckBox cb1,cb2,cb3,cb4,cb5,cb6,cb7,cb8,cb9,cb10;
  // Whew, now that's over, time to set them...
  private Calendar c;
  private BroadcastReceiver br;
  private AlarmManager am;
  SimpleDateFormat sdf;
  Intent alarmIntent;

  @Override
  public boolean onCreateOptionsMenu(Menu menu){
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()){
      case R.id.action_main:
        Intent switchToMain = new Intent(this, MyActivity.class);
        startActivity(switchToMain);
        break;
      case R.id.action_sugar:
        Intent switchToSugar = new Intent(this, SugarLogger.class);
        startActivity(switchToSugar);
        break;
      case R.id.action_alarm:
        Intent switchToAlarms = new Intent();
        startActivity(switchToAlarms);
        break;
      case R.id.action_preferences:
        Intent switchtosettings = new Intent(this,Preferences.class);
        startActivity(switchtosettings);
        break;
      case R.id.action_map:
        Intent switchToMap = new Intent(this, Map.class);
        startActivity(switchToMap);
        break;
      default:break;
    }
    return true;
  }
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.alarms);
    initializeView();
    setFields();
  }

  @Override
  public void onPause(){
    super.onPause();
    // Saves the settings to csv file.
    String FILENAME = "Alarms.csv";
    String entry = "";
    for (int i= 0; i < 10; i++){
      String dateTime = String.valueOf(editTexts.get(i).getText());
      String isOn = String.valueOf(toggleButtons.get(i).isChecked());
      String isRecurring = String.valueOf(checkBoxes.get(i).isChecked());
      String notes = String.valueOf(editNotes.get(i).getText()+ " ");
      entry += dateTime + "," + isOn + "," + isRecurring + "," + notes + "\n";
    }
    try {
      FileOutputStream out = openFileOutput(FILENAME,Context.MODE_PRIVATE);
      out.write( entry.getBytes());
      out.close();
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  private void initializeView(){
    // Set EditTexts
    ea1 = (EditText)findViewById(R.id.txtAlarm1);ea2 = (EditText)findViewById(R.id.txtAlarm2);ea3 = (EditText)findViewById(R.id.txtAlarm3);ea4 = (EditText)findViewById(R.id.txtAlarm4);ea5 = (EditText)findViewById(R.id.txtAlarm5);ea6 = (EditText)findViewById(R.id.txtAlarm6);ea7 = (EditText)findViewById(R.id.txtAlarm7);ea8 = (EditText)findViewById(R.id.txtAlarm8);ea9 = (EditText)findViewById(R.id.txtAlarm9);ea10 = (EditText)findViewById(R.id.txtAlarm10);
    en1 = (EditText)findViewById(R.id.txtNotes1);en2 = (EditText)findViewById(R.id.txtNotes2);en3 = (EditText)findViewById(R.id.txtNotes3);en4 = (EditText)findViewById(R.id.txtNotes4);en5 = (EditText)findViewById(R.id.txtNotes5);en6 = (EditText)findViewById(R.id.txtNotes6);en7 = (EditText)findViewById(R.id.txtNotes7);en8 = (EditText)findViewById(R.id.txtNotes8);en9 = (EditText)findViewById(R.id.txtNotes9);en10 = (EditText)findViewById(R.id.txtNotes10);
    // Set ToggleButtons
    tog1 = (ToggleButton)findViewById(R.id.togAlarm1); tog2 = (ToggleButton)findViewById(R.id.togAlarm2);tog3 = (ToggleButton)findViewById(R.id.togAlarm3);tog4 = (ToggleButton)findViewById(R.id.togAlarm4);tog5 = (ToggleButton)findViewById(R.id.togAlarm5);tog6 = (ToggleButton)findViewById(R.id.togAlarm6);tog7 = (ToggleButton)findViewById(R.id.togAlarm7);tog8 = (ToggleButton)findViewById(R.id.togAlarm8);tog9 = (ToggleButton)findViewById(R.id.togAlarm9);tog10 = (ToggleButton)findViewById(R.id.togAlarm10);
    // Set Checkboxes
    cb1 = (CheckBox)findViewById(R.id.chkAlarm1);cb2 = (CheckBox)findViewById(R.id.chkAlarm2);cb3 = (CheckBox)findViewById(R.id.chkAlarm3);cb4 = (CheckBox)findViewById(R.id.chkAlarm4);cb5 = (CheckBox)findViewById(R.id.chkAlarm5);cb6 = (CheckBox)findViewById(R.id.chkAlarm6);cb7 = (CheckBox)findViewById(R.id.chkAlarm7);cb8 = (CheckBox)findViewById(R.id.chkAlarm8);cb9 = (CheckBox)findViewById(R.id.chkAlarm9);cb10 = (CheckBox)findViewById(R.id.chkAlarm10);
    // Set onClick listeners for toggleButtons
    tog1.setOnClickListener(togOCL);
    tog2.setOnClickListener(togOCL);
    tog3.setOnClickListener(togOCL);
    tog4.setOnClickListener(togOCL);
    tog5.setOnClickListener(togOCL);
    tog6.setOnClickListener(togOCL);
    tog7.setOnClickListener(togOCL);
    tog8.setOnClickListener(togOCL);
    tog9.setOnClickListener(togOCL);
    tog10.setOnClickListener(togOCL);
    // Set onClick listeners for EditTexts
    ea1.setOnClickListener(edtOCL);
    ea2.setOnClickListener(edtOCL);
    ea3.setOnClickListener(edtOCL);
    ea4.setOnClickListener(edtOCL);
    ea5.setOnClickListener(edtOCL);
    ea6.setOnClickListener(edtOCL);
    ea7.setOnClickListener(edtOCL);
    ea8.setOnClickListener(edtOCL);
    ea9.setOnClickListener(edtOCL);
    ea10.setOnClickListener(edtOCL);
    pushToArrays();

    // Set calendar and the text of the alarm editTexts
    c = Calendar.getInstance();
    sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm");
    for(EditText editText : editTexts) {
      if (String.valueOf(editText.getText()).equals("")) {
        editText.setText(sdf.format(c.getTime()));
      }
    }
    br = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        Toast.makeText(Alarms.this,"Alarm went off!", Toast.LENGTH_LONG).show();
        String notes = "";
        if(intent.hasExtra("notes")){
          notes = intent.getStringExtra("notes");
        }
        createNotifications(notes);
      }
    };
  }

  public void setFields(){
    String csvFile = "Alarms.csv";
    BufferedReader br = null;
    String line = "";
    String cvsSplitBy = ",";

    try {

      br = new BufferedReader(new FileReader(csvFile));
      int index = 0;
      while ((line = br.readLine()) != null) {

        // use comma as separator to populate fields.
        String[] settings = line.split(cvsSplitBy);
        editTexts.get(index).setText(settings[0]);
        toggleButtons.get(index).setChecked(Boolean.valueOf(settings[1]));
        checkBoxes.get(index).setChecked(Boolean.valueOf(settings[2]));
        editNotes.get(index).setText(settings[3]);
        index++;
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private void createNotifications(String notes){
    Intent intent = new Intent(this,Alarms.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(this,11,intent,0);
    Notification n = new Notification.Builder( this )
        .setContentTitle("Alarm:")
        .setContentText(notes)
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true).build();
    NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    notificationManager.notify(0,n);
  }

  // OnClickListener for ToggleButtons
  View.OnClickListener togOCL = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      ToggleButton toggleButton = (ToggleButton)view;
      int index = toggleButtons.indexOf(toggleButton);
      // Register the receiver and create intents for passing information
      String dateTime = String.valueOf(editTexts.get(index).getText());
      Calendar c = parseDateTime(dateTime);

      registerReceiver(br, new IntentFilter("com.example.Android1"));

      am = (AlarmManager)(Alarms.this.getSystemService(Context.ALARM_SERVICE));
      if (toggleButton.isChecked()){
        alarmIntent = new Intent("com.example.Android1");
        if (!String.valueOf(editNotes.get(index).getText()).equals("")){
          alarmIntent.putExtra("notes",String.valueOf(editNotes.get(index).getText()));
        }
        PendingIntent pi =  PendingIntent.getBroadcast(Alarms.this, index, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        pendingIntents.add(pi);
        if (checkBoxes.get(index).isChecked()){
          am.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
        }
        else{
          am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
        }
      }
      else{
        int piIndex = pendingIntents.indexOf(PendingIntent.getBroadcast(Alarms.this, index, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT))+ 1;
        pendingIntents.get(piIndex).cancel();
        pendingIntents.remove(piIndex);
      }
    }
  };

  // OnClickListener for EditTexts
  public View.OnClickListener edtOCL = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      final EditText editText = (EditText)view;
      final Calendar e = Calendar.getInstance();
      c = e;
      final TimePickerDialog t = new TimePickerDialog(Alarms.this,new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
          e.set(Calendar.HOUR, hour);
          e.set(Calendar.MINUTE, minute);
          setText(editText, e);
        }
      },c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),false);
      DatePickerDialog d = new DatePickerDialog(Alarms.this,new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
          e.set(year,month,day);
          t.show();
        }
      },c.get(Calendar.YEAR),c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
      d.show();
    }
  };
  public void setText(EditText editText, Calendar c){
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm");
    editText.setText(sdf.format(c.getTime()));
  }
  // Pushes the appropriate controls to their ArrayLists for easy indexing
  public void pushToArrays(){
    toggleButtons.add(tog1);
    toggleButtons.add(tog2);
    toggleButtons.add(tog3);
    toggleButtons.add(tog4);
    toggleButtons.add(tog5);
    toggleButtons.add(tog6);
    toggleButtons.add(tog7);
    toggleButtons.add(tog8);
    toggleButtons.add(tog9);
    toggleButtons.add(tog10);
    editTexts.add(ea1);
    editTexts.add(ea2);
    editTexts.add(ea3);
    editTexts.add(ea4);
    editTexts.add(ea5);
    editTexts.add(ea6);
    editTexts.add(ea7);
    editTexts.add(ea8);
    editTexts.add(ea9);
    editTexts.add(ea10);
    editNotes.add(en1);
    editNotes.add(en2);
    editNotes.add(en3);
    editNotes.add(en4);
    editNotes.add(en5);
    editNotes.add(en6);
    editNotes.add(en7);
    editNotes.add(en8);
    editNotes.add(en9);
    editNotes.add(en10);
    checkBoxes.add(cb1);
    checkBoxes.add(cb2);
    checkBoxes.add(cb3);
    checkBoxes.add(cb4);
    checkBoxes.add(cb5);
    checkBoxes.add(cb6);
    checkBoxes.add(cb7);
    checkBoxes.add(cb8);
    checkBoxes.add(cb9);
    checkBoxes.add(cb10);
  }

  public Calendar parseDateTime(String dateTime){
    Calendar c = Calendar.getInstance();
    c.setTime(sdf.parse(dateTime, new ParsePosition(0)));
    if (Calendar.getInstance().after(c)){
      c = Calendar.getInstance();
    }
    return c;
  }
}