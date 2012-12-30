
package com.vivek.contacto.library;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class UserFunctions {
	
	private JSONParser jsonParser;
	
	private static String URL = "http://katibajyo.com/api/contacto.php";

	private static String add_tag = "add";
	private static String delete_tag = "delete";
	private static String view_tag = "view";
	private static String list_tag = "list";
	
	
	// constructor
	public UserFunctions(){
		jsonParser = new JSONParser();
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
