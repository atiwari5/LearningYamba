package com.example.yamba;

import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class StatusFragment extends Fragment implements OnClickListener {

	private static final String TAG = "StatusFragment";
	private EditText editStatus;
	private Button buttonTweet;
	private TextView textCount;
	private int defaultTextColor;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_status, container, false);
		
		editStatus = (EditText) view.findViewById(R.id.editStatus);
		buttonTweet = (Button) view.findViewById(R.id.buttonTweet);
		textCount = (TextView) view.findViewById(R.id.textCount);
		buttonTweet.setOnClickListener(this);
		
		defaultTextColor =
				textCount.getTextColors().getDefaultColor(); 
				editStatus.addTextChangedListener(new TextWatcher() {
					
					@Override
					public void afterTextChanged(Editable s) {
						int count = 140 - editStatus.length(); 
						textCount.setText(Integer.toString(count));
						textCount.setTextColor(Color.GREEN); 
						if (count < 10)
						textCount.setTextColor(Color.RED);
						else
						textCount.setTextColor(defaultTextColor);	
					}
					
					@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub
					
				}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {
						// TODO Auto-generated method stub
						
					}
				
				});
				return view;
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		String status = editStatus.getText().toString();
		Log.d(TAG, "onClicked with status: " + status);
		new PostTask().execute(status);	
	}
	
	private final class PostTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected String doInBackground(String... params) {
			YambaClient yambaCloud =
					new YambaClient("student", "password");
					try {
					yambaCloud.postStatus( params[0] );
					return "Successfully posted";
					} catch (YambaClientException e) {
					e.printStackTrace();
					return "Failed to post to yamba service";
					}
					}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Toast.makeText(StatusFragment.this.getActivity(), result,
					Toast.LENGTH_LONG).show();
		}

		
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
