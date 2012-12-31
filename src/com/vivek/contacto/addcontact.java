package com.vivek.contacto;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vivek.contacto.library.UserFunctions;
import com.vivek.contacto.library.checkconnection;

public class addcontact extends Activity {

	EditText fname, lname, mobile, home, office;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcontact);
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setTitle("Add Contact");
		
		if(checkconnection.checkInternetConnection(this)){
			Button done = (Button) findViewById(R.id.done);
			
			done.setOnClickListener(new View.OnClickListener() {
			
				@Override
				public void onClick(View arg0) {
					fname = (EditText) findViewById(R.id.AD_fname);
					lname = (EditText) findViewById(R.id.AD_lname);
					mobile = (EditText) findViewById(R.id.AD_Mobileno);
					home = (EditText) findViewById(R.id.AD_Homeno);
					office = (EditText) findViewById(R.id.AD_Officeno);
					
					if(fname.getText().toString().length()<=0){
						Toast.makeText(getApplicationContext(), "First Name Required", Toast.LENGTH_SHORT).show();
					}else{
						AsycConnection addC = new AsycConnection();
						addC.execute();
					}
		
				}
			});
		}else{
			final Dialog dialog = new Dialog(addcontact.this);
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
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
		case android.R.id.home:
			Intent intent = new Intent(this, ContactoMain.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return true;
       
        default:
        	 return super.onOptionsItemSelected(item);
		}
	}
	
	private class AsycConnection extends AsyncTask<Void, Void, Void>{
		String result;
		@Override
		protected Void doInBackground(Void... params) {
			UserFunctions user = new UserFunctions();
			JSONObject JSob = user.addcontact(fname.getText().toString(), lname.getText().toString(), mobile.getText().toString(), home.getText().toString(), office.getText().toString());
			try {
				if(JSob.getString("res")!=null){
					result = JSob.getString("res");
				}else{
					result = "error";
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void res) {
			// TODO Auto-generated method stub
			Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(addcontact.this, ContactoMain.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
		}
		
		
	}

}
