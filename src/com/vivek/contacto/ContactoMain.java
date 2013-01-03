package com.vivek.contacto;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.vivek.contacto.library.UserFunctions;
import com.vivek.contacto.library.checkconnection;

public class ContactoMain extends ListActivity {
	
	ArrayList<HashMap<String, String>> List_fill;
	LinearLayout progresslayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacto_main);
		
		if(checkconnection.checkInternetConnection(this))
		{
			List_fill = new ArrayList<HashMap<String, String>>();
			progresslayout = (LinearLayout)findViewById(R.id.progesslayout);
			
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
			
			return true;
            
         default:
        	 return super.onOptionsItemSelected(item);
		}
		
	}
	
	private class AsyncConnection extends AsyncTask<Void, Void, Void>{
		JSONArray JSary;
		String fname,lname;
		@Override
		protected void onPreExecute() {
			progresslayout.setVisibility(View.VISIBLE);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			UserFunctions user = new UserFunctions();
			JSONObject JSob = user.alllist();
			try {
				JSary = JSob.getJSONArray("data");
				List_fill.clear();
				for(int i=0; i<JSary.length(); i++){
					JSONObject newob = JSary.getJSONObject(i);
					try{
						fname= newob.getString("c_fname");
						lname = newob.getString("c_lname");
					}catch(JSONException e){
						e.printStackTrace();
					}
					HashMap<String, String> c_details = new HashMap<String, String>();
					c_details.put("fname", fname);
					c_details.put("lname", lname);
						
					List_fill.add(c_details);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			progresslayout.setVisibility(View.GONE);
			ListAdapter adapter = new SimpleAdapter(ContactoMain.this, List_fill, R.layout.listing,new String[]{"fname","lname"},new int[]{R.id.fnamelist, R.id.lnamelist});
			setListAdapter(adapter);
			ListView listview = getListView();
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
