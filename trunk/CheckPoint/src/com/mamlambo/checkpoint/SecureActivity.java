package com.mamlambo.checkpoint;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SecureActivity extends Activity {

	protected boolean mbFingersEnrolled;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secure);

		boolean bFingersEnrolled = false;
		Bundle extras = getIntent().getExtras(); 
		if (extras != null) { 
			bFingersEnrolled = extras.getBoolean("fingersEnrolled"); 
		} 

		TextView txt = (TextView) findViewById(R.id.secureResultTxt);

		if (bFingersEnrolled == true) {
			txt.setText(R.string.identityOk);
		} else {
			txt.setText(R.string.identityAssumed);
		}
	}

}
