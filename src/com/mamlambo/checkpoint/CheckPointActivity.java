package com.mamlambo.checkpoint;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class CheckPointActivity extends Activity {

	public static final String DEBUG_TAG = "CheckPointActivity";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		new CheckForFingerEnrollmentTask().execute();
	}

	private class CheckForFingerEnrollmentTask extends
			AsyncTask<Void, Integer, Integer> {
		
		@Override
		protected Integer doInBackground(Void... unused) {

			int iMap = com.authentec.tsm.TSM.LAP(CheckPointActivity.this)
					.GetMap().exec();

			return iMap;
		}

		protected void onPostExecute(final Integer result) {

			Button mVerifyButton = (Button) findViewById(R.id.button1);
			mVerifyButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {

					if (result == 0) {
						Intent secActivity = new Intent(
								CheckPointActivity.this, SecureActivity.class);
						Bundle bundle = new Bundle();
						bundle.putBoolean("fingersEnrolled", false);
						secActivity.putExtras(bundle);
						startActivity(secActivity);
						finish();
					} else if (result == (-1)) {
						// timed out
						Toast.makeText(getApplicationContext(),
								"Verification Request Timed Out",
								Toast.LENGTH_SHORT).show();
					} else {
						// fingerprint reader has fingerprints
						new PerformUserVerificationTask().execute();
						
						// note: res is bitmap of fingers enrolled, b0-b9 from left pinky to right pinky
					}

				}
			});
		}
	}

	private class PerformUserVerificationTask extends
			AsyncTask<Void, Integer, Integer> {

		@Override
		protected Integer doInBackground(Void... unused) {

			final String sScreen = "lap-verify";

			/* perform the verification attempt */
			int iResult = com.authentec.tsm.TSM.LAP(CheckPointActivity.this)
					.verify().viaGfxScreen(sScreen) /* this is very important! */
					.exec();

			return iResult;

		}

		protected void onPostExecute(Integer result) {

			if (result == com.authentec.tsm.AM_STATUS.eAM_STATUS_OK) {

				Intent secActivity = new Intent(CheckPointActivity.this,
						SecureActivity.class);
				Bundle bundle = new Bundle();
				bundle.putBoolean("fingersEnrolled", true);
				secActivity.putExtras(bundle);
				startActivity(secActivity);
				finish();
			}

			else {
				String strIdentity = getResources().getString(
						R.string.identityBad);
				Toast.makeText(getApplicationContext(), strIdentity,
						Toast.LENGTH_SHORT).show();
			}
		}
	}

}
