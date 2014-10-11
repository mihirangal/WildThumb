package com.main.wildthumb;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
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
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.example.wildthumb.R;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment {

	public HomeFragment() {
	}

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
    "<zipcode></zipcode>"+
    "<userid></userid>"+
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_home,
				container, false);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);

		if (postLoginData())
			parse_comments();

		
		ArrayList<String> your_array_list = new ArrayList<String>();
		if (this.postLoginData()) {

			this.parse_comments();
			Log.d("sgsgsdgs", Matching.CreateArrayList(curr_response).toString());
			your_array_list.addAll(Matching.CreateArrayList(curr_response));
			lv1 = (ListView) rootView.findViewById(R.id.listViewc);
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
					getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,
					your_array_list);
			Log.d("Sdgsdg",your_array_list.toString());
			if(lv1!=null)
			{
			lv1.setAdapter(arrayAdapter);
			}
		}

		return rootView;
	}

	public boolean postLoginData() {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();

		/* login.php returns true if username and password is equal to saranga */
		HttpPost httppost = new HttpPost(
				"http://pikaclass.web.engr.illinois.edu/logs.php");

		try {
			// Add user name and password

			String username = "mangal";
			String password = "mihir";

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("username", username));
			nameValuePairs.add(new BasicNameValuePair("password", password));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			Log.w("SENCIDE", "Execute HTTP Post Request");
			HttpResponse response = httpclient.execute(httppost);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				String responseString = out.toString();
				Log.d("res", responseString);
				if (responseString.contains("Successful") == true) {
					return true;
				}
				// ..more logic
			} else {
				// Closes the connection.
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
