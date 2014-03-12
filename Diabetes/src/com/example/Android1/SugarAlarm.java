package com.example.Android1;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Richard on 3/11/14.
 */
public class SugarAlarm extends Activity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.alarms);
    initializeApp();
  }

  private void initializeApp() {

  }
}
