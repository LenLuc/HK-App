package com.example.haushaltskassen_app;


import android.os.Bundle;




import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.widget.TextView;


public class HKPreferences extends MyBaseActivity {

   TextView einkommenLucia;
   TextView einkommenPiet;

   public static final String EXTRA_MyPREFERENCES = "com.example.android-SQLite.MyPrefs" ;
   public static final String EXTRA_EINKOMMEN1 = "com.example.android-SQLite.einkommenLucia"; 
   public static final String EXTRA_EINKOMMEN2 = "com.example.android-SQLite.einkommenPiet"; 


   SharedPreferences sharedpreferences;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.preferences);

      einkommenLucia = (TextView) findViewById(R.id.editEinkommenLucia);
      einkommenPiet = (TextView) findViewById(R.id.editEinkommenPiet);


      sharedpreferences = getSharedPreferences(EXTRA_MyPREFERENCES, Context.MODE_PRIVATE);
     
      if (sharedpreferences.contains(EXTRA_EINKOMMEN1))
      {
         einkommenLucia.setText(sharedpreferences.getString(EXTRA_EINKOMMEN1, ""));

      }
      if (sharedpreferences.contains(EXTRA_EINKOMMEN2))
      {
         einkommenPiet.setText(sharedpreferences.getString(EXTRA_EINKOMMEN2, ""));

      }
     

   }
/** called when the user clicks fertig*/
   public void run(View view){
	   
      String e1  = einkommenLucia.getText().toString();
      String e2  = einkommenPiet.getText().toString();
     
      Editor editor = sharedpreferences.edit();
      editor.putString(EXTRA_EINKOMMEN1, e1);
      editor.putString(EXTRA_EINKOMMEN2, e2);


      editor.commit(); 
		
      	final Intent intent = new Intent(this, MainActivity.class);
		AlertDialog.Builder adb = new AlertDialog.Builder(HKPreferences.this);
		adb.setTitle("Einkommen gespeichert.");
		adb.setMessage("Zurück zur Startseite?");
		adb.setPositiveButton("Nein",null);
		adb.setNegativeButton("Ja",new DialogInterface.OnClickListener()
    	{
        	@Override
        	public void onClick(DialogInterface dialog, int whichButton)
        		{
        		
        		startActivity(intent);
        		}
    	});
		adb.show();
    

   }
  // @Override
  // public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
  //    getMenuInflater().inflate(R.menu.main, menu);
   //   return true;
   //}

}
