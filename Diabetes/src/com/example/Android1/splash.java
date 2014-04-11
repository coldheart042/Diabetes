package com.example.Android1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by Richard on 4/11/14.
 */
public class splash extends Activity {

  // Timeout (ten seconds)
  private static int SPLASH_TIMEOUT = 10000;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.splash);
    initialize();
  }

  private void initialize() {
    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        Intent i = new Intent(splash.this, MyActivity.class);
        startActivity(i);
      }
    };
    new Handler().postDelayed(runnable, SPLASH_TIMEOUT);
  }
}