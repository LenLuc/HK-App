package com.example.haushaltskassen_app;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
public class BearbeitenActivity extends Activity{
	Ausgabe ausgabe;

	

    @Override
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bearbeiten_layout);
     
       Intent intent = getIntent();
      
   
        if(intent.getExtras()!=null){
        	/*get Ausgabe*/
        	Ausgabe ausgabe = handelAusgabeForViews.getAusgabeFromIntent(intent);
        	
        	
        	/*get textfields and checkboxes*/
            EditText editText = (EditText) findViewById(R.id.editBetragAusgabeBearbeiten);
            EditText editDate = (EditText) findViewById(R.id.editDate);
            CheckBox checkboxPiet = (CheckBox) findViewById(R.id.checkBoxPietBearbeiten);
            CheckBox checkboxLucia = (CheckBox) findViewById(R.id.checkBoxLuciaBearbeiten);
            CheckBox checkboxGemeinsam = (CheckBox) findViewById(R.id.checkBoxGemeinsamBearbeiten);
            CheckBox checkboxAusgelegt = (CheckBox) findViewById(R.id.checkBoxAusgelegtBearbeiten);
            
            /*set values*/
            editText.setText(ausgabe.getBetrag());
            editDate.setText(ausgabe.getDate());

            checkboxPiet.setChecked(handelAusgabeForViews.isPersonPiet(ausgabe));
            checkboxLucia.setChecked(handelAusgabeForViews.isPersonLucia(ausgabe));
            checkboxGemeinsam.setChecked(handelAusgabeForViews.isGemeinsam(ausgabe));
            checkboxAusgelegt.setChecked(handelAusgabeForViews.isAusgelegt(ausgabe));
    	 
        }
        
     
    }
    /**Called when the user clicks the Abbrechen button**/
    public void abbrechenBearbeiten(View view){
     	AlertDialog.Builder adbAbbrechen = new AlertDialog.Builder(BearbeitenActivity.this);
			adbAbbrechen.setTitle("Ausgabe bearbeiten abbrechen?");
			adbAbbrechen.setNegativeButton("Nein",null);
			adbAbbrechen.setPositiveButton("Ja",new DialogInterface.OnClickListener(){
				@Override
 	        	public void onClick(DialogInterface dialog, int whichButton)
 	        		{final Intent intentAbbrechen = new Intent(BearbeitenActivity.this, DisplayDataActivity.class);
 	        		startActivity(intentAbbrechen);}
			});
			adbAbbrechen.show();
    }
    
    /** Called when the user clicks the Fertig button */
    public void saveData(View view){
    	//Log.d("saveData",ausgabe.toString());
    		//get data from user entry
    	    EditText editText = (EditText) findViewById(R.id.editBetragAusgabeBearbeiten);
    	    EditText editDate = (EditText) findViewById(R.id.editDate);
    	    CheckBox checkboxPiet = (CheckBox) findViewById(R.id.checkBoxPietBearbeiten);
    	    CheckBox checkboxLucia = (CheckBox) findViewById(R.id.checkBoxLuciaBearbeiten);
    	    CheckBox checkboxGemeinsam = (CheckBox) findViewById(R.id.checkBoxGemeinsamBearbeiten);
    	    CheckBox checkboxAusgelegt = (CheckBox) findViewById(R.id.checkBoxAusgelegtBearbeiten);
    	    
    	    String betrag = editText.getText().toString();
    	    String date = editDate.getText().toString();
    	    boolean piet = checkboxPiet.isChecked();
    	    boolean lucia = checkboxLucia.isChecked();
    	    boolean gemeinsam = checkboxGemeinsam.isChecked();
    	    boolean ausgelegt = checkboxAusgelegt.isChecked();
    	    

    	    //Check if entries make sense
            if ((betrag.length() == 0) || (piet & lucia) || ((piet== false) & (lucia==false))|| (gemeinsam & ausgelegt) || ((gemeinsam==false) & (ausgelegt==false)))
        	{  //Show error
            	AlertDialog.Builder adb = new AlertDialog.Builder(BearbeitenActivity.this);
    			adb.setTitle("Unvollständige Eingabe.");
    			adb.setNegativeButton("OK",null);
    			/*adb.setPositiveButton("Ja", new  DialogInterface.OnClickListener(){
    				//go to Ausgabetabelle
    				@Override
		        	public void onClick(DialogInterface dialog, int whichButton)
		        		{
    				final Intent intent = new Intent(BearbeitenActivity.this, DisplayDataActivity.class);
    				startActivity(intent);
		        		}
    			});*/
    			adb.show();
        	}
            else{
            		//save user entry
            		MySQLiteHelper dbtemp = new MySQLiteHelper(this);
            		int person;
            		int godera;
            		if(piet){ person = 1;} else{person=2;}
            		if(gemeinsam){ godera =1;}else{godera=2;}
            		Ausgabe ausgabe =new Ausgabe(person,godera,betrag);
            		ausgabe.setDate(date);
            		dbtemp.addAusgabe(ausgabe);
            		//ask what now
                	AlertDialog.Builder adb = new AlertDialog.Builder(BearbeitenActivity.this);
        			adb.setTitle("Änderung wurde gespeichert!");
        			/*adb.setNegativeButton("Nein",new DialogInterface.OnClickListener() {
						//Clear MainActivity to enter new ausgabe
						@Override
						public void onClick(DialogInterface dialog, int whichButtom) {
							
	        				final Intent intent = new Intent(BearbeitenActivity.this, BearbeitenActivity.class);
	        				startActivity(intent);
						}
					});*/
        			adb.setPositiveButton("OK", new  DialogInterface.OnClickListener(){
        				//go to Ausgabetabelle
        				@Override
    		        	public void onClick(DialogInterface dialog, int whichButton)
    		        		{
        				final Intent intent = new Intent(BearbeitenActivity.this, DisplayDataActivity.class);
        				startActivity(intent);
    		        		}
        			}
            		);
            		adb.show();
            	}
    	}
    

}
