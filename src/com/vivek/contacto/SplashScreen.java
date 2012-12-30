package com.vivek.contacto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		
		Thread timer = new Thread(){
			public void run(){
				try{
			
					sleep(2000);
					
				}catch(InterruptedException e){
					e.printStackTrace();
				}finally{
					Intent mainpage = new Intent(SplashScreen.this, ContactoMain.class);
					startActivity(mainpage);
				}
			}
		};
		timer.start();
		
	}


}
