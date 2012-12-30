package com.vivek.contacto;

import com.vivek.contacto.library.checkconnection;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class individual_list extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.individual);
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setTitle("Vivek Bhusal");

		if(!checkconnection.checkInternetConnection(this)){
			final Dialog dialog = new Dialog(individual_list.this);
			 dialog.setContentView(R.layout.nointernet);
			 dialog.setTitle("Alert!!!");

			 Button button = (Button) dialog.findViewById(R.id.dismiss);
			
			 button.setOnClickListener(new OnClickListener() {
			     public void onClick(View v) {
			    		    	 
			    	 dialog.dismiss();
			    	 finish();
			     }
			     });
			 dialog.show();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.individual_list_menu, menu);
	    return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch(item.getItemId())
		{
		case android.R.id.home:
			Intent intent = new Intent(individual_list.this, ContactoMain.class);
			startActivity(intent);
			return true;
		case R.id.deleteitme:
			Toast.makeText(getApplicationContext(), "item will be deleted", Toast.LENGTH_SHORT).show();
			return true;
		
		case R.id.edititme:
			Toast.makeText(getApplicationContext(), "item needs to be edited", Toast.LENGTH_SHORT).show();
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
			
			
		}
		
	}
	

}
