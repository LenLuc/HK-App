package com.example.haushaltskassen_app;



import android.os.Bundle;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;

 
public class MainActivity extends MyBaseActivity {
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
        
     
    }
    /** Called when the user clicks the Fertig button */
    public void saveData(View view){
    		//get data from user entry
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
        	{  //Show error
            	AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
    			adb.setTitle("Unvollständige Eingabe. Zur Ausgabetabelle?");
    			adb.setNegativeButton("Nein",null);
    			adb.setPositiveButton("Ja", new  DialogInterface.OnClickListener(){
    				//go to Ausgabetabelle
    				@Override
		        	public void onClick(DialogInterface dialog, int whichButton)
		        		{
    				final Intent intent = new Intent(MainActivity.this, DisplayDataActivity.class);
    				startActivity(intent);
		        		}
    			});
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
            		dbtemp.addAusgabe(ausgabe);
            		//ask what now
                	AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
        			adb.setTitle("Weiter zur Ausgabentabelle?");
        			adb.setNegativeButton("Nein",new DialogInterface.OnClickListener() {
						//Clear MainActivity to enter new ausgabe
						@Override
						public void onClick(DialogInterface dialog, int whichButtom) {
							
	        				final Intent intent = new Intent(MainActivity.this, MainActivity.class);
	        				startActivity(intent);
						}
					});
        			adb.setPositiveButton("Ja", new  DialogInterface.OnClickListener(){
        				//go to Ausgabetabelle
        				@Override
    		        	public void onClick(DialogInterface dialog, int whichButton)
    		        		{
        				final Intent intent = new Intent(MainActivity.this, DisplayDataActivity.class);
        				startActivity(intent);
    		        		}
        			}
            		);
            		adb.show();
            	}
    	}
   
}


