package com.example.haushaltskassen_app;

import java.util.List;

//import com.example.myfirstapp.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import android.view.View;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class DisplayDataActivity extends MyBaseActivity {

 
  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
@Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      Log.d("contentview","contentview1");
      setContentView(R.layout.activity_display_data);
      Log.d("contentview","contentview2");
     int name=0; /* piet=1 lucia=2*/
     int godera=0; /*gemeinsam= 1, ausgelegt = 2*/
    
    // if (getFragmentManager().findFragmentById(android.R.id.content) == null) {
     

    // }
      //Get Intent
      Intent intent = getIntent();
      if(intent.getExtras()!=null){
    	  String betrag = intent.getStringExtra(MainActivity.EXTRA_BETRAG);
      
    	  boolean piet = intent.getBooleanExtra(MainActivity.EXTRA_PIET,false);
    	  boolean lucia = intent.getBooleanExtra(MainActivity.EXTRA_LUCIA, false);
	      boolean gemeinsam = intent.getBooleanExtra(MainActivity.EXTRA_GEMEINSAM, false);
	      boolean ausgelegt = intent.getBooleanExtra(MainActivity.EXTRA_AUSGELEGT, false);
     
      	//Initialize data base
      	MySQLiteHelper dbtemp = new MySQLiteHelper(this);
      	if (piet){ name = 1;}
      	if (lucia){name = 2;}
      	if (gemeinsam){ godera = 1;}
      	if (ausgelegt){ godera = 2;}
      	if ((betrag.length() == 0) || (piet & lucia) || ((piet== false) & (lucia==false))|| (gemeinsam & ausgelegt) || ((gemeinsam==false) & (ausgelegt==false)))
      		{  //Do nothing
      		}
      
      	else{ //Add ausgabe to data base
      		Ausgabe ausgabe =new Ausgabe(name, godera, betrag);
      		dbtemp.addAusgabe(ausgabe);
      		
      		}
      }
      
    
      
      // Make sure we're running on Honeycomb or higher to use ActionBar APIs
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
          // Show the Up button in the action bar.
          getActionBar().setDisplayHomeAsUpEnabled(true);
      }
     

  }
 /* public boolean onCreateOptionsMenu(Menu menu) {
  	// Inflate the menu items for use in the action bar
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.display_data_activity_menu, menu);
      return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      
      case R.id.einstellungen:
    	  final Intent intentEinstellung = new Intent(this, HKPreferences.class); 
    	  startActivity(intentEinstellung);
    	  return true;
	
      case R.id.daten_hochladen:
    	  final Intent intentConnect = new Intent(this, ConnectToServer.class); 
    	  startActivity(intentConnect);
    	  return true;
    	  
      case R.id.delete_table:
      	
  		AlertDialog.Builder adb = new AlertDialog.Builder(DisplayDataActivity.this);
			adb.setTitle("Gesamte Tabelle löschen?");
			adb.setPositiveButton("Nein",null);
			adb.setNegativeButton("Ja",new DialogInterface.OnClickListener()
	    	{
	        	@Override
	        	public void onClick(DialogInterface dialog, int whichButton)
	        		{
	        			MySQLiteHelper db = new MySQLiteHelper(DisplayDataActivity.this);
	        			List<Ausgabe> ausgaben = db.getAllAusgaben();
	        			for(int ii = 0; ii <= ausgaben.size()-1;ii++)
	        			{
	        				db.deleteAusgabe(ausgaben.get(ii));
	        			}
	        			Context context = getApplicationContext();
	        			Toast toast = Toast.makeText(context, "Gesamte Tabelle gelöscht!", Toast.LENGTH_SHORT);
	        			toast.show();
	        			
	        			Intent intent1 = new Intent(DisplayDataActivity.this,MainActivity.class);
	        			startActivity(intent1);
	        		}
	        	});
      	adb.show();
          return true;
          

      case R.id.goto_neueAusgabe:
     	 	Intent intentAusgabe = new Intent(this, MainActivity.class);
          startActivity(intentAusgabe);
          return true;
          
      case R.id.bestimme_abrechnung:
      	
			MySQLiteHelper db = new MySQLiteHelper(DisplayDataActivity.this);
			List<Ausgabe> ausgaben = db.getAllAusgaben();
			Ausgabe ausgabetemp = new Ausgabe();
			float gemeinsamPiet=0;
			float gemeinsamLucia=0;
			float ausgelegtPiet=0;
			float ausgelegtLucia=0;
			for(int ii = 0; ii <= ausgaben.size()-1;ii++)
			{
				ausgabetemp=ausgaben.get(ii);
				int godera = ausgabetemp.getGodera();// 1 gemeinsam 2 ausgelegt
				int loderp = ausgabetemp.getPerson();//1 Piet, 2 Lucia
				float betrag=Float.valueOf(ausgabetemp.getBetrag());
				
				if(loderp==1){
					if(godera==1){
					gemeinsamPiet=gemeinsamPiet +betrag;
					}
					else{
						ausgelegtPiet =ausgelegtPiet+betrag;
					}
				}
				else{if(godera==1){
					gemeinsamLucia=gemeinsamLucia +betrag;
					}
					else{
						ausgelegtLucia =ausgelegtLucia+betrag;
					}
					
				}
			}
			SharedPreferences sharedpreferences = getSharedPreferences(HKPreferences.EXTRA_MyPREFERENCES, Context.MODE_PRIVATE); 
			String einkommenLucia = sharedpreferences.getString(HKPreferences.EXTRA_EINKOMMEN1, "");
			String einkommenPiet = sharedpreferences.getString(HKPreferences.EXTRA_EINKOMMEN2, "");
			
			float floatEinkommen1 = Float.valueOf(einkommenLucia);
			float floatEinkommen2 = Float.valueOf(einkommenPiet);
			float anteilPiet = floatEinkommen2/(floatEinkommen1+ floatEinkommen2);
			float gemeinsamGesamt=(gemeinsamPiet+gemeinsamLucia);
			float pietZahlt= (gemeinsamGesamt*anteilPiet)-gemeinsamPiet-ausgelegtPiet+ausgelegtLucia;
			
			String pietZahltString="Fehler in Abrechnung";
			
			if(pietZahlt<0){
				 pietZahltString="Piet bekommt von Lucia \n"+Float.toString(-pietZahlt)+" Euro";
			}
			else{
				 pietZahltString="Lucia bekommt von Piet \n"+Float.toString(pietZahlt)+" Euro";
			}
			
			
		

			AlertDialog.Builder adb2 = new AlertDialog.Builder(DisplayDataActivity.this);
			adb2.setTitle(pietZahltString);
			adb2.setMessage(" Tabelle löschen?");
			adb2.setPositiveButton("Nein",null);
			adb2.setNegativeButton("Ja",new DialogInterface.OnClickListener()
	    	{
	        	@Override
	        	public void onClick(DialogInterface dialog, int whichButton)
	        		{
	        			MySQLiteHelper db = new MySQLiteHelper(DisplayDataActivity.this);
	        			List<Ausgabe> ausgaben = db.getAllAusgaben();
	        			for(int ii = 0; ii <= ausgaben.size()-1;ii++)
	        			{
	        				db.deleteAusgabe(ausgaben.get(ii));
	        			}
	        			
	        			Context context = getApplicationContext();
	        			Toast toast = Toast.makeText(context, "Gesamte Tabelle gelöscht!", Toast.LENGTH_SHORT);
	        			toast.show();
	        			
	        			Intent intent2 = new Intent(DisplayDataActivity.this,MainActivity.class);
	        			startActivity(intent2);
	        		}
	        	});
			adb2.show();
return true;
      }
      
      return super.onOptionsItemSelected(item);
  }
  @Override
  public void onDestroy() {
      super.onDestroy();  // Always call the superclass
      
      // Stop method tracing that the activity started during onCreate()
      android.os.Debug.stopMethodTracing();
  }*/
}



