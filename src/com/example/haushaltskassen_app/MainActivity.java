package com.example.haushaltskassen_app;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;


import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
 
public class MainActivity extends Activity {
	public final static String EXTRA_BETRAG = "com.example.android-SQLite.BETRAG";
	public final static String EXTRA_PIET = "com.example.android-SQLite.PIET";
	public final static String EXTRA_LUCIA = "com.example.android-SQLite.LUCIA";
	public final static String EXTRA_GEMEINSAM = "com.example.android-SQLite.GEMEINSAM";
	public final static String EXTRA_AUSGELEGT = "com.example.android-SQLite.AUSGELEGT";
  
	

    @Override
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Intent intent = getIntent();
        if(intent.getExtras()!=null){
        	//Then the activity is called by DisplayDataActivity and the
        	//content of the ausgabe that should be modivied has to be displayed in the textfields
            String betrag = intent.getStringExtra(MainActivity.EXTRA_BETRAG);
            boolean piet = intent.getBooleanExtra(MainActivity.EXTRA_PIET,false);
            boolean lucia = intent.getBooleanExtra(MainActivity.EXTRA_LUCIA, false);
            boolean gemeinsam = intent.getBooleanExtra(MainActivity.EXTRA_GEMEINSAM, false);
            boolean ausgelegt = intent.getBooleanExtra(MainActivity.EXTRA_AUSGELEGT, false);
            EditText editText = (EditText) findViewById(R.id.editBetragAusgabe);
    	    CheckBox checkboxPiet = (CheckBox) findViewById(R.id.checkBoxPiet);
    	    CheckBox checkboxLucia = (CheckBox) findViewById(R.id.checkBoxLucia);
    	    CheckBox checkboxGemeinsam = (CheckBox) findViewById(R.id.checkBoxGemeinsam);
    	    CheckBox checkboxAusgelegt = (CheckBox) findViewById(R.id.checkBoxAusgelegt);
    	    editText.setText(betrag);
    	    checkboxPiet.setChecked(piet);
    	    checkboxLucia.setChecked(lucia);
    	    checkboxGemeinsam.setChecked(gemeinsam);
    	    checkboxAusgelegt.setChecked(ausgelegt);
    	 
        }
        
      /*  MySQLiteHelper db = new MySQLiteHelper(this);*/
 
        /**
         * CRUD Operations
         * */
        // add Ausgaben
     /*   db.addAusgabe(new Ausgabe(1,true, "10.50"));  
      * 
        db.addAusgabe(new Ausgabe(2,false,"0.20"));      
        db.addAusgabe(new Ausgabe(1,false,"5.20"));
 
        // get all ausgaben
        List<Ausgabe> list = db.getAllAusgaben();
 
        // delete one ausgabe
        db.deleteAusgabe(list.get(0));
 
        // get all ausgabe
        db.getAllAusgaben();*/
 
    }
    /** Called when the user clicks the Fertig button */
    public void saveData(View view){
    		final Intent intent = new Intent(this, DisplayDataActivity.class);
    	    EditText editText = (EditText) findViewById(R.id.editBetragAusgabe);
    	    CheckBox checkboxPiet = (CheckBox) findViewById(R.id.checkBoxPiet);
    	    CheckBox checkboxLucia = (CheckBox) findViewById(R.id.checkBoxLucia);
    	    CheckBox checkboxGemeinsam = (CheckBox) findViewById(R.id.checkBoxGemeinsam);
    	    CheckBox checkboxAusgelegt = (CheckBox) findViewById(R.id.checkBoxAusgelegt);
    	    
    	    String betrag = editText.getText().toString();
    	    boolean piet = checkboxPiet.isChecked();
    	    boolean lucia = checkboxLucia.isChecked();
    	    boolean gemeinsam = checkboxGemeinsam.isChecked();
    	    boolean ausgelegt = checkboxAusgelegt.isChecked();
    	   
    	    //Check if entries make sense
            if ((betrag.length() == 0) || (piet & lucia) || ((piet== false) & (lucia==false))|| (gemeinsam & ausgelegt) || ((gemeinsam==false) & (ausgelegt==false)))
        	{  //Do nothing
            	AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
    			adb.setTitle("Unvollständige Eingabe");
    			//adb.setMessage("Direkt zur Ausgabentabelle?");
    			adb.setNegativeButton("OK",null);
    			/*adb.setPositiveButton("Ja", new DialogInterface.OnClickListener()
		    	{
		        	@Override
		        	public void onClick(DialogInterface dialog, int whichButton)
		        		{
		        		startActivity(intent);
		        		}
		    	});*/
    			adb.show();
        	}
            else{
    	    intent.putExtra(EXTRA_BETRAG, betrag);
    	    intent.putExtra(EXTRA_PIET, piet);
    	    intent.putExtra(EXTRA_LUCIA, lucia);
    	    intent.putExtra(EXTRA_GEMEINSAM, gemeinsam);
    	    intent.putExtra(EXTRA_AUSGELEGT, ausgelegt);
    	    startActivity(intent);}
    	}
    public boolean onCreateOptionsMenu(Menu menu) {
    	// Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
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
        	
    		AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
			adb.setTitle("Gesamte Tabelle löschen?");
			adb.setPositiveButton("Nein",null);
			adb.setNegativeButton("Ja",new DialogInterface.OnClickListener()
	    	{
	        	@Override
	        	public void onClick(DialogInterface dialog, int whichButton)
	        		{
	        			MySQLiteHelper db = new MySQLiteHelper(MainActivity.this);
	        			List<Ausgabe> ausgaben = db.getAllAusgaben();
	        			for(int ii = 0; ii <= ausgaben.size()-1;ii++)
	        			{
	        				db.deleteAusgabe(ausgaben.get(ii));
	        			}
	        			Context context = getApplicationContext();
	        			Toast toast = Toast.makeText(context, "Gesamte Tabelle gelöscht!", Toast.LENGTH_SHORT);
	        			toast.show();
	        			
	        		}
	        	});
        	adb.show();
        return true;
            
        case R.id.goto_table:
        	Log.d("intent","goto datadisplay");
        	 Intent intent = new Intent(this, DisplayDataActivity.class);
             startActivity(intent);
             return true;
             
            
        case R.id.bestimme_abrechnung:
        	
	        			MySQLiteHelper db = new MySQLiteHelper(MainActivity.this);
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
	        			
	        			
	        		

	        			AlertDialog.Builder adb2 = new AlertDialog.Builder(MainActivity.this);
	        			adb2.setTitle(pietZahltString);
	        			adb2.setMessage(" Tabelle löschen?");
	        			adb2.setPositiveButton("Nein",null);
	        			adb2.setNegativeButton("Ja",new DialogInterface.OnClickListener()
	        	    	{
	        	        	@Override
	        	        	public void onClick(DialogInterface dialog, int whichButton)
	        	        		{
	        	        			MySQLiteHelper db = new MySQLiteHelper(MainActivity.this);
	        	        			List<Ausgabe> ausgaben = db.getAllAusgaben();
	        	        			for(int ii = 0; ii <= ausgaben.size()-1;ii++)
	        	        			{
	        	        				db.deleteAusgabe(ausgaben.get(ii));
	        	        			}
	        	        			Context context = getApplicationContext();
	        	        			Toast toast = Toast.makeText(context, "Gesamte Tabelle gelöscht!", Toast.LENGTH_SHORT);
	        	        			toast.show();
	        	        		}
	        	        	});
	        			adb2.show();
            return true;
        
        }
        return super.onOptionsItemSelected(item);
    }
}


