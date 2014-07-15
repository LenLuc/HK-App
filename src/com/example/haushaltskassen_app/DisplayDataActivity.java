package com.example.haushaltskassen_app;




import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;



@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class DisplayDataActivity extends MyBaseActivity {

	
  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
@Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      Intent intent = getIntent();
      //contains DisplayDataFragment with Ausgabentabelle
      setContentView(R.layout.activity_display_data);
      

      
    
      
      // Make sure we're running on Honeycomb or higher to use ActionBar APIs
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
          // Show the Up button in the action bar.
          getActionBar().setDisplayHomeAsUpEnabled(true);
      }
     

  }

}



