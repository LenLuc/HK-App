package com.example.haushaltskassen_app;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MyBaseActivity extends Activity{
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
	        	
	    		AlertDialog.Builder adb = new AlertDialog.Builder(this);
	    		
				adb.setTitle("Gesamte Tabelle löschen?");
				adb.setPositiveButton("Nein",null);Log.d("alert","alert2");
				adb.setNegativeButton("Ja",new DialogInterface.OnClickListener()
		    	{
		        	@Override
		        	public void onClick(DialogInterface dialog, int whichButton)
		        		{Log.d("alert","alert1");
		        			MySQLiteHelper db = new MySQLiteHelper(getApplicationContext());
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
	        	
		        			MySQLiteHelper db = new MySQLiteHelper(getApplicationContext());
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
		        			
		        			
		        		

		        			AlertDialog.Builder adb2 = new AlertDialog.Builder(this);
		        			adb2.setTitle(pietZahltString);
		        			adb2.setMessage(" Tabelle löschen?");
		        			adb2.setPositiveButton("Nein",null);
		        			adb2.setNegativeButton("Ja",new DialogInterface.OnClickListener()
		        	    	{
		        	        	@Override
		        	        	public void onClick(DialogInterface dialog, int whichButton)
		        	        		{
		        	        			MySQLiteHelper db = new MySQLiteHelper(getApplicationContext());
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
