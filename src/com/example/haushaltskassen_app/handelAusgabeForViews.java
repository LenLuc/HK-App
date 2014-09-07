package com.example.haushaltskassen_app;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class handelAusgabeForViews {

	public final static String EXTRA_NAME= "com.example.android-SQLite.NAME";
	public final static String EXTRA_METHOD= "com.example.android-SQLite.METHOD";
	
public static Ausgabe getAusgabeFromIntent(Intent intent) {Ausgabe ausgabe= new Ausgabe();


ausgabe.setBetrag(intent.getStringExtra(MainActivity.EXTRA_BETRAG));
ausgabe.setDate(intent.getStringExtra(DisplayDataFragment.EXTRA_DATE));
ausgabe.setPerson(intent.getIntExtra(handelAusgabeForViews.EXTRA_NAME,0));
ausgabe.setGodera(intent.getIntExtra(handelAusgabeForViews.EXTRA_METHOD,0));

return ausgabe;}

public static Intent setAusgabeInIntent(Intent intent, Ausgabe ausgabe) {
	
		intent.putExtra(MainActivity.EXTRA_BETRAG, ausgabe.getBetrag());
		intent.putExtra(DisplayDataFragment.EXTRA_DATE, ausgabe.getDate());
		intent.putExtra(handelAusgabeForViews.EXTRA_NAME, ausgabe.getPerson());
		intent.putExtra(handelAusgabeForViews.EXTRA_METHOD,ausgabe.getGodera());
		
return intent;}



public static boolean isPersonPiet(Ausgabe ausgabe){ if(ausgabe.getPerson()==1){return true;}else{return false;}}
public static boolean isPersonLucia(Ausgabe ausgabe){return (ausgabe.getPerson()==2);}
public static boolean isGemeinsam(Ausgabe ausgabe){return (ausgabe.getGodera()==1);}
public static boolean isAusgelegt(Ausgabe ausgabe){return (ausgabe.getGodera()==2);}

}

