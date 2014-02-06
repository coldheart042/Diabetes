package com.example.Android1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import java.util.ArrayList;

/**
 * Created by Richard on 2/5/14.
 */
public class SugarChart extends Activity {
  Paint paint;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.chart);
//    requestWindowFeature(Window.FEATURE_NO_TITLE);
    initializeView();
  }


  public void initializeView(){
    paint = new Paint();
    paint.setColor(Color.BLACK);
    paint.setStrokeWidth( 1 );

    setContentView( new Panel(this) );

  }
  class Panel extends View {
    public Panel(Context context){
      super(context);
    }

    @Override
    public void onDraw( Canvas canvas){
      ArrayList<Integer> bsList = new ArrayList<Integer>();
      // The setup:
      int originX = 10;
      int originY = (canvas.getHeight()-150);
      canvas.drawColor(Color.WHITE);
      canvas.drawLine(originX , originY, originX, 50, paint);
      canvas.drawLine(originX , originY, (canvas.getWidth()-10), originY, paint);

      // The take-down:

      for(Integer weight : bsList){

      }
    }
  }
}
