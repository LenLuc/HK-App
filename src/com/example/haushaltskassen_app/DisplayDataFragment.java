package com.example.haushaltskassen_app;

import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class DisplayDataFragment extends ListFragment{
	
	public final static String EXTRA_DATE = "com.example.android-SQLite.DATE";
	ArrayAdapter<Ausgabe> adapter;

	
	
	  @Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	      
		  	MySQLiteHelper db = new MySQLiteHelper(getActivity());
		  	List<Ausgabe> ausgaben = db.getAllAusgaben();
		  	 adapter = new ArrayAdapter<Ausgabe>( inflater.getContext(),android.R.layout.simple_list_item_1, ausgaben);
		  	setListAdapter(adapter);

		  	return super.onCreateView(inflater, container, savedInstanceState);
	  }
	      

		@Override
		public void onListItemClick(ListView listView, View view, int position, long id) {
			AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
    		adb.setTitle("Ausgabe");
    		adb.setMessage("Ausgewählte Ausgabe:"+listView.getItemAtPosition(position));
    		final Ausgabe ausgabe = (Ausgabe) listView.getItemAtPosition(position);
    		adb.setNeutralButton("Abbrechen",null);
    		adb.setPositiveButton("Löschen", new DialogInterface.OnClickListener()
	    	{
	        	@Override
	        	public void onClick(DialogInterface dialog, int whichButton)
	        		{
	        		MySQLiteHelper db = new MySQLiteHelper(getActivity());
	        		db.deleteAusgabe(ausgabe);
	        		adapter.remove(ausgabe);
	        		adapter.notifyDataSetChanged();
	        		}
	    	});
    		 
    		adb.setNegativeButton("Bearbeiten", new DialogInterface.OnClickListener()
 	    	{
 	        	@Override
 	        	public void onClick(DialogInterface dialog, int whichButton)
 	        		{
 	        			//Call intent to open MainActivity
 	        		
 	        			final Intent intent = new Intent(getActivity(), BearbeitenActivity.class);
 	        			//put information of ausgabe in the intent
 	        			handelAusgabeForViews.setAusgabeInIntent(intent,ausgabe);
 	        			startActivity(intent);
 	         	   	}
 	    	});
    		adb.show();
	    
		}
		
}

	  


