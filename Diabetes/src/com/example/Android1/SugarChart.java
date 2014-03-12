package com.example.Android1;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Richard on 2/5/14.
 */
public class SugarChart extends Activity {

  ArrayList<Integer> bs = new ArrayList<Integer>();
  ArrayList<String> date = new ArrayList<String>();
  private GraphicalView mChart;
  private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
  private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
  private XYSeries mCurrentSeries;
  private XYSeriesRenderer mCurrentRenderer;

  @Override
  public boolean onCreateOptionsMenu(Menu menu){
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main, menu);
    return true;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    readLog();
    setContentView(R.layout.chart);
    initializeView();
  }

  private void initChart(){
    mCurrentSeries = new XYSeries( "BS");
    mDataset.addSeries(mCurrentSeries);
    mCurrentRenderer = new XYSeriesRenderer();
    mRenderer.addSeriesRenderer( mCurrentRenderer);

    mRenderer.setAxisTitleTextSize( 24 );
    mRenderer.setLabelsTextSize( 24 );
    mRenderer.setChartTitleTextSize( 48 );
  }

  private void initializeView(){
    Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
    Spinner spnBegin = (Spinner) findViewById(R.id.spnBegin);
    Spinner spnEnd = (Spinner) findViewById(R.id.spnEnd);

    // Populate the spinners with data
    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,date);
    spnBegin.setAdapter(spinnerArrayAdapter);
    spnEnd.setAdapter(spinnerArrayAdapter);

    btnSubmit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // Find & remove the current graph and all associated data
        LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
        if(mChart != null){
          layout.removeAllViews();
          mCurrentSeries.clear();
          initChart();
          refactorData();
          mChart = ChartFactory.getCubeLineChartView( SugarChart.this, mDataset, mRenderer, 0.3f);
          layout.addView(mChart);
        }
      }
    });


  }

  private void addData(){
    Integer x = 0;
    for(Integer sugar : bs) {
      mCurrentSeries.add(x += 1, sugar);
    }
  }

  private void refactorData(){
    Spinner spnBegin = (Spinner) findViewById(R.id.spnBegin);
    Spinner spnEnd = (Spinner) findViewById(R.id.spnEnd);
    int begin = date.indexOf(spnBegin.getSelectedItem());
    int ending = date.indexOf(spnEnd.getSelectedItem());
    int x = 0;
    while(begin <= ending){
      mCurrentSeries.add(x += 1, bs.get(begin) );
      begin++;
    }
  }


  @Override
  protected void onResume(){
    super.onResume();
    LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
    if(mChart == null){
      initChart();
      addData();
      mChart = ChartFactory.getCubeLineChartView( this, mDataset, mRenderer, 0.3f);
      layout.addView(mChart);
    }else{
      mChart.repaint();
    }
  }


  public void readLog(){
    String FILENAME = "sugar_log.csv";
    FileInputStream inputStream = null;
    String temp;
    String a[];

    try {
      inputStream = openFileInput( FILENAME );
      byte[] reader = new byte[inputStream.available()];
      while ( inputStream.read(reader) != -1){}

      Scanner s = new Scanner( new String(reader));
      s.useDelimiter("\\n");
      while(s.hasNext()){
        temp = s.next();
        a = temp.split(",");
        bs.add(Integer.parseInt(a[4]));
        date.add(a[0] + " " + a[1]);
      }
      s.close();
    }catch(Exception e){
      Log.e("Chart", e.getMessage());
    }finally {
      if(inputStream != null){
        try {
          inputStream.close();
        }catch(IOException e){
          Log.e("Chart", e.getMessage());
        }
      }
    }
  }
}