package com.example.haushaltskassen_app;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


@SuppressLint("NewApi")
public class ConnectToServer extends MyBaseActivity {
	 	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.connect_to_server);
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy); 
	        
	 }
	
	public boolean isNetworkAvailable() {
	    ConnectivityManager cm = (ConnectivityManager) 
	      getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
	    // if no network is available networkInfo will be null
	    // otherwise check if we are connected
	    if (networkInfo != null && networkInfo.isConnected()) {
	        return true;
	    }
	    return false;
	} 
	/** Called when the user clicks the ok button */
	public void onclickDownload(View view) {
	boolean network =	isNetworkAvailable();
	Log.d("network","network"+network);
	if(network){
		
	String url = new String( " http://fathomless-fjord-2416.herokuapp.com/api/expenses");
	
	 downloadTable(url);
	 }
		else {
   			AlertDialog.Builder adb = new AlertDialog.Builder(ConnectToServer.this);
   			adb.setTitle("Keine Netzwerkverbindung vorhanden");
   			adb.setPositiveButton("OK",null);
   			adb.show();
		} 
	
	
	
	}

	
	 public static void downloadTable(String url)
	 {
		 Log.d("downlo","bla");
	     HttpClient httpclient = new DefaultHttpClient();
	     Log.d("httpclient","bla");
	     // Prepare a request object
	     HttpGet httpget = new HttpGet(url); 
	     Log.d("httpget","bla");
	     //httpget.setHeader("Authorization","Token token=\"b3f0c4fc17a759b04016f1ce546059d9\"");
	   //  Log.d("setheader","bla");
	     // Execute the request
	     HttpResponse response;
	     try {
	    	 Log.d("try response","n");
	         response = httpclient.execute(httpget);
	         // Examine the response status
	         Log.d("Praeda","response"+response.getStatusLine().toString());

	         // Get hold of the response entity
	       HttpEntity entity = response.getEntity();
	         // If the response does not enclose an entity, there is no need
	         // to worry about connection release

	         if (entity != null) {
	        	 
	             // A Simple JSON Response Read, do get Ausgaben
	             InputStream instream = entity.getContent();
	             List<Ausgabe> result= readJsonStream(instream);
	            Log.d("result?",result.toString());
	             
	             instream.close();
	         }


	     } catch (Exception e) { Log.e("network", "exception with get request", e);}
	 }


	 
	   
	     
	     
	 		public static List<Ausgabe> readJsonStream(InputStream in) throws IOException {
	         JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
	         try {
	           return readAusgabenArray(reader);}
	          finally {
	           reader.close();
	         }
	       }

	       public static List<Ausgabe> readAusgabenArray(JsonReader reader) throws IOException {
	         List<Ausgabe> allAusgaben = new LinkedList<Ausgabe>();
	         
	         reader.beginArray();
	         while (reader.hasNext()) {
	           allAusgaben.add(readAusgabe(reader));
	         }
	         reader.endArray();
	         return allAusgaben;
	       }

	       public static Ausgabe readAusgabe(JsonReader reader) throws IOException {
	         int id = -1;
	         int user = -1;
	         String betrag = " ";
	         int common = -1;
	         
	         Ausgabe ausgabe= new Ausgabe();
	         
	         reader.beginObject();
	         while (reader.hasNext()) {
	           String name = reader.nextName();
	           if (name.equals("id")) {
	             id = reader.nextInt();
	             
	           } else if (name.equals("user")) {
	             String userString= reader.nextString();
	             
	              if(userString.equals("Piet")){user=1;} else {user=2;/*lucia*/}
	           } else if (name.equals("cost") ) {
	             double doubleBetrag = reader.nextDouble();
	             betrag=  Double.toString(doubleBetrag);
	             
	           } else if (name.equals("common")) {
	              common = reader.nextInt();
	            
	           } else {
	             reader.skipValue();
	           }
	         }
	         reader.endObject();
	         ausgabe.setBetrag(betrag);
	         ausgabe.setId(id);
	         ausgabe.setPerson(user);
	         ausgabe.setGodera(common);
	         return ausgabe;
	       }



	       /** Called when the user clicks the 2. ok button */
	   	public void onclickUpload(View view) {   
	   		boolean network =	isNetworkAvailable();
	   		
	   		if(network){
	   			MySQLiteHelper db = new MySQLiteHelper(ConnectToServer.this);
	   			List<Ausgabe> ausgaben = db.getAllAusgaben();
	   			if(ausgaben != null && ausgaben.size() > 0){
	   				try{ makeRequest("http://fathomless-fjord-2416.herokuapp.com/expenses.json", ausgaben); deleteTable();}
	   				catch(Exception e){Log.e("exection makeReq", "exception make post Request",e);};
	   			}
	   			
	   			else{
			
	   				
	   				Log.d("tabelle leer","leer");
	   				
	   				AlertDialog.Builder adb2 = new AlertDialog.Builder(ConnectToServer.this);
	   				adb2.setTitle("Ausgabentabelle ist leer.");
	   				adb2.setPositiveButton("OK",null);
	   				adb2.show();
	   				
	   			} 
			
	   		}
	   		
	   		else {
	   			AlertDialog.Builder adb = new AlertDialog.Builder(ConnectToServer.this);
	   			adb.setTitle("Keine Netzwerkverbindung vorhanden");
	   			adb.setPositiveButton("OK",null);
	   			adb.show();
			} 
	   		
	   	}   

	   	public static void makeRequest(String path, List<Ausgabe> ausgaben) throws Exception 
	   	{
	   	    //instantiates httpclient to make request
	   	    DefaultHttpClient httpclient = new DefaultHttpClient();

	   	    //url with the post data
	   	    HttpPost httpost = new HttpPost(path);
	   	    
	   	    //convert parameters into JSON object
	   	    JSONArray holder = getJsonObjectFromList(ausgaben);
	   	 for (int i=0; i<holder.length();i++) {
	   	    JSONObject holder1= (JSONObject) holder.get(i);
	   	    Log.d("jsonarray",holder1.toString());
	   	    //passes the results to a string builder/entity
	   	    StringEntity se = new StringEntity(holder1.toString());
	   	 
	   	    //sets the post request as the resulting string
	   	    httpost.setEntity(se);
	   	 
	   	 
	   	    //sets a request header so the page receving the request
	   	    //will know what to do with it
	   	    httpost.setHeader("Accept", "application/json");
	   	    httpost.setHeader("Authorization","Token token=\"b3f0c4fc17a759b04016f1ce546059d9\"");
	   	    httpost.setHeader("Content-type", "application/json");
	   
	   	    //Handles what is returned from the page 
	   	   // ResponseHandler responseHandler = new BasicResponseHandler();
	   	 
	   	 HttpResponse response =  httpclient.execute(httpost);
	   	 
	   	//EntityUtils.consume(response.getEntity());
	   	response.getEntity().consumeContent();}
	   	 
	   	 return;
	   	}

	   	public static JSONArray getJsonObjectFromList(List<Ausgabe> ausgaben) throws JSONException{
	   		
	   		
	   		JSONArray array = new JSONArray();
	   		if (ausgaben != null && ausgaben.size() > 0) {
	   	        
	   	        for (Ausgabe selectedAusgaben : ausgaben) {
	   	            JSONObject json = new JSONObject();
	   	            int godera=selectedAusgaben.getGodera();
	   	            if(godera== 2){godera=0;} 
	   	            int person = selectedAusgaben.getPerson();
	   	            String personForServer="";
	   	            if(person==1){ personForServer="Piet";} else { personForServer="Lucia";}
	   	            json.put("cost", ""+selectedAusgaben.getBetrag());
	   	            json.put("common", ""+godera);
	   	            json.put("user", ""+personForServer);
	   	            json.put("date", selectedAusgaben.getDate());
	   	            array.put(json);
	   	        }
	   	        
	   	    } 
	   		return array;
	   	}
	   	
	   	public void deleteTable(){

	        			MySQLiteHelper db = new MySQLiteHelper(ConnectToServer.this);
	        			List<Ausgabe> ausgaben = db.getAllAusgaben();
	        			for(int ii = 0; ii <= ausgaben.size()-1;ii++)
	        			{
	        				db.deleteAusgabe(ausgaben.get(ii));
	        			}
	        			Context context = getApplicationContext();
	        			Toast toast = Toast.makeText(context, "Tabelle wurde gelöscht und auf den Server geladen.", Toast.LENGTH_SHORT);
	        			toast.show();
	        	}
	   	
	   	
}
