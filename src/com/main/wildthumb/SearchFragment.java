package com.main.wildthumb;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.example.wildthumb.R;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchFragment extends Fragment {
	
	public SearchFragment(){}
	
	String username = "mangal";
	String dataToCollect = null;
	Integer classno;
	TextView mytext;
	private ListView lv1 = null;
	private ListView lv2 = null;
	private ListView lv3 = null;
	private String s1[] = { "a", "b", "c", "d", "e", "f" };
	private String s2[] = { "r", "s", "t", "u", "v", "w", "x" };
	private String s3[] = { "r", "s", "f", "u", "c", "k", "x" };
	private String[] parsed;
	private String curr_response;
	EditText myText;
	String query;
	Button post;
	public void parse_comments() {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();

		/* login.php returns true if username and password is equal to saranga */
		HttpPost httppost = new HttpPost(
				"http://ec2-54-179-211-42.ap-southeast-1.compute.amazonaws.com/Service1/Service1.asmx?op=Search");
		String xml =

"<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"+
  "<soap:Body>"+
    "<Search xmlns=\"http://tempuri.org/\">"+
    "<keyword>"+query+"</keyword>"+
    "<zipcode></zipcode>"+
    "<season></season>"+
    "</Search>"+
  "</soap:Body>"+
"</soap:Envelope>";
	    StringEntity entity;
	    String response_string = null;

		try {
			// Add user name and password

//			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//			nameValuePairs.add(new BasicNameValuePair("keyword", "plant"));
//			nameValuePairs.add(new BasicNameValuePair("zipcode", ""));
//			nameValuePairs.add(new BasicNameValuePair("season", ""));
//			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			 entity = new StringEntity(xml, HTTP.UTF_8);
		        httppost.setHeader("Content-Type","text/xml;charset=UTF-8");
		        httppost.setEntity(entity);

			// Execute HTTP Post Request
			Log.w("SENCIDE", "Execute HTTP Post Request");
			HttpResponse response = httpclient.execute(httppost);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				String responseString = out.toString();
				curr_response= responseString;
				
				Log.d("rests", responseString);
				if (responseString.contains("liked")) {
					Context context = getActivity().getApplicationContext();
					CharSequence text = "already liked ";
					int duration = Toast.LENGTH_SHORT;

					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				} else {
					Context context = getActivity().getApplicationContext();
					CharSequence text = "you liked " + classno;
					int duration = Toast.LENGTH_SHORT;

					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}
				

				// ..more logic
			} else {
				// Closes the connection.
				response.getEntity().getContent().close();
				Log.d("shit",statusLine.getReasonPhrase());
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void post(View v)
	{
		 query= myText.getText().toString();
			ArrayList<String> your_array_list = new ArrayList<String>();
			 
			String s = "rose";
			String t = "daisy";
			your_array_list.add(s);
			your_array_list.add(t);
			this.parse_comments();

			
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
					getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,
					your_array_list);
			Log.d("Sdgsdg",your_array_list.toString());
			if(lv1!=null)
			{
			lv1.setAdapter(arrayAdapter);
			}
		
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_find_people,
				container, false);

		lv1 = (ListView) rootView.findViewById(R.id.listViewc);
		myText= (EditText)rootView.findViewById(R.id.textViewer1);
		post = (Button)rootView.findViewById(R.id.button1);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
			

		
		mytext = (TextView)rootView.findViewById(R.id.textView1 );
	    post.setOnClickListener(new View.OnClickListener()
	    {
	          @Override
	          public void onClick(View InputFragmentView)
	          {
	       
	     		 query= myText.getText().toString();
	 			ArrayList<String> your_array_list = new ArrayList<String>();
	 			 
	 	
	 			parse_comments();

	 			
	 			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
	 					getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,
	 					your_array_list);
	 			Log.d("Sdgsdg",your_array_list.toString());
	 			your_array_list.addAll(Matching.CreateArrayList(curr_response));
	 			
	 			if(lv1!=null)
	 			{
	 			lv1.setAdapter(arrayAdapter);
	 			}
	 		
	          }
	     });

		

		return rootView;
	}



}


