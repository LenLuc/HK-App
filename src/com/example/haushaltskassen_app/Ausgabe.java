package com.example.haushaltskassen_app;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import android.util.Log;



public class Ausgabe {
	Calendar calendar =new GregorianCalendar(TimeZone.getDefault()); 
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	
	
		private int id;
		private int person;
	    private int godera;//gemeinsam oder ausgelegt
	    private String betrag; //ContentValues can't handle BigDecimal. Convert from string for calculations.
	    private String date;
	    //private String kommentar;
	    
	
	    
	    public Ausgabe(){}
	 
	    public Ausgabe(int person, int godera, String betrag) {
	        super();
	        this.person = person;
	        this.godera = godera;
	        this.betrag = betrag;
	       this.date =dateFormat.format(calendar.getTime());
	        
	       
	    }
	 
	    //getters & setters
	 
	    @Override
	    public String toString() {
	     String name="ka";
	    	String method="ka";
	        if(person==1) { name="Piet";}
	        else{ name = "Lucia";}
	        if(godera==1) { method="ja";}
	        else{ method = "nein";}
	        return  betrag +" Euro hat "+name+" am "+date+" ausgegeben. Gemeinsame Kosten: "+method;
	        	    }
	 
	  /* public String[] ausgabeArray() {
	    	String[] array = {Integer.toString(person),Integer.toString(godera),betrag} ;
	    return array;
	    }*/
	    
	    public int getId() {return id;}
	     public int getPerson() { return person;}
	     public int getGodera() { return godera;}
	     public String getBetrag() { return  betrag;}
	     public String getDate() {return date;}
	     
	     public void setId(int id2){id=id2;}
	     public void setPerson (int person2){person=person2;}
	     public void setGodera (int godera2){godera=godera2;}
	     public void setBetrag (String betrag2){betrag=betrag2;}
	     public void setDate(String date2) {date=date2;}
}
