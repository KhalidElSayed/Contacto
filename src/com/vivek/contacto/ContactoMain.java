package com.vivek.contacto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.vivek.contacto.library.UserFunctions;
import com.vivek.contacto.library.checkconnection;

public class ContactoMain extends Activity {
	HashMap<String, String> allname;
	List<HashMap<String, String>> List_fill;
	ListView listview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacto_main);
		if(checkconnection.checkInternetConnection(this))
		{
			allname = new HashMap<String, String>();
			List_fill = new ArrayList<HashMap<String, String>>();
			
			listview =(ListView) findViewById(R.id.list);
			
			AsyncConnection getdata = new AsyncConnection();
			getdata.execute();
						
		}else
		{
			final Dialog dialog = new Dialog(ContactoMain.this);
			dialog.setContentView(R.layout.nointernet);
			dialog.setTitle("Alert!!!");

			 Button button = (Button) dialog.findViewById(R.id.dismiss);
			
			 button.setOnClickListener(new OnClickListener() {
			     public void onClick(View v) 
			     {
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
			finish();
			return true;
            
         default:
        	 return super.onOptionsItemSelected(item);
		}
		
	}
	
	private class AsyncConnection extends AsyncTask<Void, Void, Void>{
		JSONArray JSary;
		@Override
		protected Void doInBackground(Void... arg0) {
			UserFunctions user = new UserFunctions();
			JSONObject JSob = user.alllist();
			try {
				JSary = JSob.getJSONArray("data");
				for(int i=0; i<JSary.length(); i++){
					JSONObject newob = JSary.getJSONObject(i);
					String fname = newob.getString("c_fname");
					String lname = newob.getString("c_lname");
									
					allname.put("fname", fname);
					allname.put("lname", lname);
						
					List_fill.add(allname);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
//			Toast.makeText(getApplicationContext(),rest, Toast.LENGTH_SHORT).show();
			ListAdapter adapter = new SimpleAdapter(ContactoMain.this, List_fill, R.layout.listing,new String[]{"fname","lname"},new int[]{R.id.fnamelist, R.id.lnamelist});
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long id) {
					// TODO Auto-generated method stub
					String fname,lname,homeno,mobileno,officeno,idl;
					try {
						Intent in = new Intent(ContactoMain.this, individual_list.class);
						JSONObject tempobj = JSary.getJSONObject(position);
						
						idl = tempobj.getString("id");
						in.putExtra("id", idl);
						
						if(tempobj.getString("c_fname")!=null){
							fname = tempobj.getString("c_fname");
							in.putExtra("fname", fname);
						}
						if(tempobj.getString("c_lname")!=null){
							lname = tempobj.getString("c_lname");
							in.putExtra("lname", lname);
						}
						if(tempobj.getString("n_home")!=null){
							homeno = tempobj.getString("n_home");
							in.putExtra("nhome", homeno);
						}
						if(tempobj.getString("n_mobile")!=null){
							mobileno= tempobj.getString("n_mobile");
							in.putExtra("n_mobile", mobileno);
						}
						if(tempobj.getString("n_office")!=null){
							officeno = tempobj.getString("n_office");
							in.putExtra("n_office", officeno);
						}
						
						startActivity(in);
						finish();
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
			
			});
		}
		
		
	}
	
	

}
