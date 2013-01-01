package com.vivek.contacto;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vivek.contacto.library.UserFunctions;
import com.vivek.contacto.library.checkconnection;

public class individual_list extends Activity {

	String fname,lname,mobile,home,office,id;
	TextView hn, mn, on;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.individual);
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setTitle(" ");
		hn= (TextView)findViewById(R.id.INDIVIDUAL_homenum);
		mn= (TextView)findViewById(R.id.INDIVIDUAL_mobnum);
		on= (TextView)findViewById(R.id.INDIVIDUAL_officenum);

		if(checkconnection.checkInternetConnection(this)){
			Intent intent = getIntent();
			if(intent!=null){
				if(intent.getStringExtra("fname")!=null){
					fname = intent.getStringExtra("fname");
					if(intent.getStringExtra("lname")!=null){
						lname = intent.getStringExtra("lname");
						actionbar.setTitle(fname+" "+lname);
					}else{
						actionbar.setTitle(fname);
					}
				}
				if(intent.getStringExtra("nhome")!=null){
					home = intent.getStringExtra("nhome");
				}
				if(intent.getStringExtra("n_mobile")!=null){
					mobile = intent.getStringExtra("n_mobile");
				}
				if(intent.getStringExtra("n_office")!=null){
					office = intent.getStringExtra("n_office");
				}
				id=intent.getStringExtra("id");
				hn.setText(home);
				mn.setText(mobile);
				on.setText(office);
			
				
			}
			
		}else{
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
			finish();
			return true;
		case R.id.deleteitme:
			AsyncConnection connection = new AsyncConnection();
			connection.execute();
			return true;
		
		case R.id.edititme:
			Intent edit_intent = new Intent(individual_list.this, addcontact.class);
			edit_intent.putExtra("fname", fname);
			edit_intent.putExtra("lname", lname);
			edit_intent.putExtra("mobile", mobile);
			edit_intent.putExtra("home", home);
			edit_intent.putExtra("office", office);
			edit_intent.putExtra("id", id);
			startActivity(edit_intent);
			
			Toast.makeText(getApplicationContext(), "item needs to be edited", Toast.LENGTH_SHORT).show();
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
			
			
		}
		
	}
	
	private class AsyncConnection extends AsyncTask<Void, Void, Void>{
		String res;
		@Override
		protected Void doInBackground(Void... arg0) {
			UserFunctions user = new UserFunctions();
			JSONObject jsob = user.deletecontact(id);
			try {
				res = jsob.getString("res");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			Toast.makeText(getApplicationContext(),res+" Deleting", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(individual_list.this, ContactoMain.class);
			startActivity(intent);
			
		}
		
	}

}
