package com.vivek.contacto;

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
import android.widget.TextView;

import com.vivek.contacto.library.checkconnection;

public class ContactoMain extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacto_main);
		if(checkconnection.checkInternetConnection(this))
		{
			TextView demo = (TextView)findViewById(R.id.listingcontact);
			demo.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent i = new Intent(ContactoMain.this, individual_list.class);
					startActivity(i);
					
				}
			});
		}else
		{
			final Dialog dialog = new Dialog(ContactoMain.this);
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
	    inflater.inflate(R.menu.contacto_main, menu);
	    return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{            
		case R.id.additem:
			Intent intent = new Intent(ContactoMain.this, addcontact.class);
			startActivity(intent);
			return true;
            
         default:
        	 return super.onOptionsItemSelected(item);
		}
		
	}
	
	

}
