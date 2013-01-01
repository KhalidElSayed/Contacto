
package com.vivek.contacto.library;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.util.Log;

public class UserFunctions {
	
	private JSONParser jsonParser;
	
	private static String URL = "http://katibajyo.com/api/contacto.php";

	private static String add_tag = "add";
	private static String delete_tag = "delete";
	private static String view_tag = "view";
	private static String list_tag = "list";
	private static String edit_tag = "edit";
	
	
	// constructor
	public UserFunctions(){
		jsonParser = new JSONParser();
	}
	
	public void justry(){
		InputStream is;
		String url = URL;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("action", add_tag));
		params.add(new BasicNameValuePair("username", "testing"));
		params.add(new BasicNameValuePair("c_fname", "fname"));
		params.add(new BasicNameValuePair("c_lname", "lname"));
		params.add(new BasicNameValuePair("n_mobile", "mobile"));
		params.add(new BasicNameValuePair("n_home", "home"));
		params.add(new BasicNameValuePair("n_office", "office"));
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
        String paramString = URLEncodedUtils.format(params, "utf-8");
        url += "?" + paramString;
        HttpGet httpGet = new HttpGet(url);
		
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
			BufferedReader br= new BufferedReader(new InputStreamReader(is));
 
			StringBuilder sb = new StringBuilder();
 
			String line;
	    	while ((line = br.readLine()) != null) {
	    		sb.append(line);
	    	} 
	    	Log.i("jsondata", sb.toString());
//	    	System.out.println(sb.toString());
 
	    	br.close();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public JSONObject addcontact(String fname, String lname, String mobile, String home, String office ){
	
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("action", add_tag));
		params.add(new BasicNameValuePair("username", "testing"));
		params.add(new BasicNameValuePair("c_fname", fname));
		params.add(new BasicNameValuePair("c_lname", lname));
		params.add(new BasicNameValuePair("n_mobile", mobile));
		params.add(new BasicNameValuePair("n_home", home));
		params.add(new BasicNameValuePair("n_office", office));
		JSONObject json = jsonParser.getJSONFromUrl(URL, params);
		
		return json;
	}
	public JSONObject editcontact(String id, String fname, String lname, String mobile, String home, String office ){
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("action", edit_tag));
		params.add(new BasicNameValuePair("id", id));
		params.add(new BasicNameValuePair("username", "testing"));
		params.add(new BasicNameValuePair("c_fname", fname));
		params.add(new BasicNameValuePair("c_lname", lname));
		params.add(new BasicNameValuePair("n_mobile", mobile));
		params.add(new BasicNameValuePair("n_home", home));
		params.add(new BasicNameValuePair("n_office", office));
		JSONObject json = jsonParser.getJSONFromUrl(URL, params);
		
		return json;
	}
	
	public JSONObject deletecontact(String id ){
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("action", delete_tag));
		params.add(new BasicNameValuePair("id", id));
		JSONObject json = jsonParser.getJSONFromUrl(URL, params);
		return json;
	}
	
	public JSONObject individualcontact(String id ){
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("action", view_tag));
		params.add(new BasicNameValuePair("id", id));
		JSONObject json = jsonParser.getJSONFromUrl(URL, params);
		return json;
	}
	public JSONObject alllist(){
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("action", list_tag));
		JSONObject json = jsonParser.getJSONFromUrl(URL, params);
		return json;
	}

	
}
