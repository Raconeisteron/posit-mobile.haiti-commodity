/**
 * 
 */
package org.hfoss.posit.android.experimental.plugin.commodity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

import org.hfoss.posit.android.experimental.R;
import org.hfoss.posit.android.experimental.api.Find;
import org.hfoss.posit.android.experimental.api.LocaleManager;
import org.hfoss.posit.android.experimental.api.activity.FindActivity;
import org.hfoss.posit.android.experimental.plugin.acdivoca.AcdiVocaFind;
import org.hfoss.posit.android.experimental.plugin.acdivoca.AcdiVocaMchnFindActivity;
//import org.hfoss.posit.android.plugin.acdivoca.AcdiVocaDbHelper;
//import org.hfoss.posit.android.experimental.api.activity.SettingsActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


/**
 * FindActivity subclass for Commodity Tracker plugin.
 * 
 */
public class CommodityFindActivity extends FindActivity 
implements OnItemSelectedListener, OnDateChangedListener {

	private static final String TAG = "CommodityFindActivity";
	private String commodityspin[];
	private String marketspin[];
	private ArrayAdapter<String> mAdapter;
	private ArrayAdapter<String> cAdapter;
	Spinner cspinner; 
	Spinner mspinner; 
	private EditText eText;
	private String c;
	private String d;
	private int posc;
	private int posd;
	
	

	
//	Preferences

//	Private Context context = this.context;
//	this.context = context;
	
//	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//	String communesectionkey = 
//	prefs.getString(getString(R.string.cCommune_section_key),"");
//	Log.i(TAG, "onCreate()");
//	String CommuneSectionKey = 
//		this.getResources().getString(R.string.cCommune_section_key);
//	try {
//		String phone = prefs.getString(getString(R.string.cCommune_section_key),"");
//		if (phone.equals("")) {
//			mSpEditor = mSharedPrefs.edit();
//			mSpEditor.putString(getString(R.string.smsPhoneKey), 
//	getString(R.string.default_phone));
//			mSpEditor.commit();
//		}
//	prefs.getString("cCommune_section_key", "");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate()");
		super.onCreate(savedInstanceState);
		mspinner = (Spinner) findViewById(R.id.marketSpinner);
		marketspin = loadData("/commodity/marketlist.csv");
	    mAdapter = new ArrayAdapter<String>( 
	            this, android.R.layout.simple_spinner_item, marketspin); 
	    mspinner.setAdapter(mAdapter); 
	    cspinner = (Spinner) findViewById(R.id.commoditySpinner);
		commodityspin = loadData("/commodity/commoditylist.csv");
	    cAdapter = new ArrayAdapter<String>( 
	            this, android.R.layout.simple_spinner_item, commodityspin); 
	    cspinner.setAdapter(cAdapter); 
	    
	    
		mAdapter = 
			new ArrayAdapter<String>(
					this,
					android.R.layout.simple_spinner_item,
					marketspin );
//		mAdapter.sort(String.CASE_INSENSITIVE_ORDER);
		mAdapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);
		mspinner.setAdapter(mAdapter);
//		mspinner.setOnItemSelectedListener(
//				new AdapterView.OnItemSelectedListener() {
//					public void onItemSelected(
//							AdapterView<?> parent, 
//							View view, 
//							int position, 
//							long id) {
//						d = marketspin[position];
//						Log.i(TAG, "#######Market spin choice index = " + position);
//						Log.i(TAG, "#######Market spin choice = " + d);
////						find.setMarket(d);
//						//eText.setText(d);
//					}
//
//					public void onNothingSelected(AdapterView<?> parent) {
//					}
//				}
//		);
		
		cAdapter = 
			new ArrayAdapter<String>(
					this,
					android.R.layout.simple_spinner_item,
					commodityspin );
//		mAdapter.sort(String.CASE_INSENSITIVE_ORDER);
		cAdapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);
		cspinner.setAdapter(cAdapter);
//		cspinner.setOnItemSelectedListener(
//				new AdapterView.OnItemSelectedListener() {
//					public void onItemSelected(
//							AdapterView<?> parent, 
//							View view, 
//							int position, 
//							long id) {
//						c = commodityspin[position];
////						find.setCommodity(c);
//						//eText.setText(d);
//					}
//
//					public void onNothingSelected(AdapterView<?> parent) {
//					}
//				}
//		);
	    

	}
	
	
	/*
	 * 
	 * This is the OnResume item
	 * 
	 * 
	 */
//	@Override
//	protected void onResume() {
//		super.onResume();
//		
//		LocaleManager.setDefaultLocale(this);
//		
//		Bundle extras = getIntent().getExtras();
//
//		mAdapter = 
//			new ArrayAdapter<String>(
//					this,
//					android.R.layout.simple_spinner_item,
//					marketspin );
////		mAdapter.sort(String.CASE_INSENSITIVE_ORDER);
//		mAdapter.setDropDownViewResource(
//				android.R.layout.simple_spinner_dropdown_item);
//		mspinner.setAdapter(mAdapter);
//		mspinner.setOnItemSelectedListener(
//				new AdapterView.OnItemSelectedListener() {
//					public void onItemSelected(
//							AdapterView<?> parent, 
//							View view, 
//							int position, 
//							long id) {
//						d = marketspin[position];
//						Log.i(TAG, "#######Market spin choice index = " + position);
//						Log.i(TAG, "#######Market spin choice = " + d);
////						find.setMarket(d);
//						//eText.setText(d);
//					}
//
//					public void onNothingSelected(AdapterView<?> parent) {
//					}
//				}
//		);
//		
//		cAdapter = 
//			new ArrayAdapter<String>(
//					this,
//					android.R.layout.simple_spinner_item,
//					commodityspin );
////		mAdapter.sort(String.CASE_INSENSITIVE_ORDER);
//		cAdapter.setDropDownViewResource(
//				android.R.layout.simple_spinner_dropdown_item);
//		cspinner.setAdapter(cAdapter);
//		cspinner.setOnItemSelectedListener(
//				new AdapterView.OnItemSelectedListener() {
//					public void onItemSelected(
//							AdapterView<?> parent, 
//							View view, 
//							int position, 
//							long id) {
//						c = commodityspin[position];
////						find.setCommodity(c);
//						//eText.setText(d);
//					}
//
//					public void onNothingSelected(AdapterView<?> parent) {
//					}
//				}
//		);
		
//		if (ioFind.getMarket() != null){
//			setSpinner(cspinner, ioFind.getMarket());
//		}
//		
//	}

	@Override
	protected void initializeListeners() {
		super.initializeListeners();
		Button nextButton = ((Button) findViewById(R.id.nextButton));
		if (nextButton != null)
			nextButton.setOnClickListener(this);
		Calendar calendar = Calendar.getInstance();
		

		((DatePicker)findViewById(R.id.datePicker1)).init(
				calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH), 
				calendar.get(Calendar.DAY_OF_MONTH), this);
//		((Spinner)findViewById(R.id.marketSpinner)).setOnItemSelectedListener(this);		
//		((Spinner)findViewById(R.id.commoditySpinner)).setOnItemSelectedListener(this);
		((Spinner)findViewById(R.id.commoditySpinner)).setOnItemSelectedListener(
				new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(
							AdapterView<?> parent, 
							View view, 
							int position, 
							long id) {
						posc = position;
						c = commodityspin[position];
						Log.i(TAG, "#######Commodity spin choice index = " + position);
						Log.i(TAG, "#######Commodity spin choice = " + c);
//						find.setCommodity(c);
						//eText.setText(d);
					}

					public void onNothingSelected(AdapterView<?> parent) {
					}
				}
		);
		((Spinner)findViewById(R.id.marketSpinner)).setOnItemSelectedListener(
				new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(
							AdapterView<?> parent, 
							View view, 
							int position, 
							long id) {
						d = marketspin[position];
						Log.i(TAG, "#######Market spin choice index = " + position);
						Log.i(TAG, "#######Market spin choice = " + d);
//						find.setMarket(d);
						//eText.setText(d);
					}

					public void onNothingSelected(AdapterView<?> parent) {
					}
				}
		);
	}

	@Override
	protected Find retrieveContentFromView() {
		final CommodityFind find =  (CommodityFind)super.retrieveContentFromView();
		
//		TextView t = (TextView)findViewById(R.id.textView10);
//		t.setText(R.string.cCommuneSection);
		
		//Commune Section code
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String communesectionkey = 
			prefs.getString(getString(R.string.cCommune_section_key),"");
		Log.i(TAG, communesectionkey);
		
//		TextView tView = (TextView) findViewById(R.id.textView10);
//		tView.setText(prefs.getString(getString(R.string.cCommune_section_key),""));
		

		
//		Spinner spinner = (Spinner) findViewById(R.id.marketSpinner);
//		ArrayAdapter<String> mspinnerArrayAdapter = new ArrayAdapter<String>( 
//	            this, android.R.layout.simple_spinner_item, marketspin); 
//	    spinner.setAdapter(mspinnerArrayAdapter);  
	    
		
		find.setMarket(d);
		Log.i(TAG, "#######Market to DB = " + d);
		find.setCommodity(c);
		Log.i(TAG, "#######Commodity to DB = " + c);
		
//		Spinner spinner = (Spinner) findViewById(R.id.marketSpinner);
//		ArrayAdapter<String> mspinnerArrayAdapter = new ArrayAdapter<String>( 
//	            this, android.R.layout.simple_spinner_item, marketspin); 
//	    spinner.setAdapter(mspinnerArrayAdapter);  
	    
//		String marketchoice = (String) spinner.getSelectedItem();
//		Log.i(TAG, "#######Market spinner array = " + marketspin[0] + " " + marketspin[1]);
//		setUpSpinnerAdapter(marketspin);
	    
		
//			mAdapter = 
//				new ArrayAdapter<String>(
//						this,
//						android.R.layout.simple_spinner_item,
//						marketspin );
////			mAdapter.sort(String.CASE_INSENSITIVE_ORDER);
//			mAdapter.setDropDownViewResource(
//					android.R.layout.simple_spinner_dropdown_item);
//			spinner.setAdapter(mAdapter);
//			spinner.setOnItemSelectedListener(
//					new AdapterView.OnItemSelectedListener() {
//						public void onItemSelected(
//								AdapterView<?> parent, 
//								View view, 
//								int position, 
//								long id) {
//							String d = marketspin[position];
//							Log.i(TAG, "#######Market spin choice index = " + position);
//							Log.i(TAG, "#######Market spin choice = " + d);
//							find.setMarket(d);
//							//eText.setText(d);
//						}
//
//						public void onNothingSelected(AdapterView<?> parent) {
//						}
//					}
//			);
//			eText = ((EditText)findViewById(R.id.dossierEdit));
//			eText.addTextChangedListener(this);
//			eText.setText(""); 


		
//	    String marketchoice = "";
//		if (spinner != null) {		
//			marketchoice = (String) spinner.getSelectedItem();
//			Log.i(TAG, "#######Market choice = " + marketchoice);
//			find.setMarket(marketchoice);	
//		}

//		Need to make a find query for markets		
//		find.setMarket(marketchoice);	
		
		// Commodity spinner
		// String commodityspin[] = loadData("/commodity/commoditylist.csv");
			//loadData("/commodity/commoditylist.csv");
//		spinner = (Spinner) findViewById(R.id.commoditySpinner);
//	    ArrayAdapter<String> cspinnerArrayAdapter = new ArrayAdapter<String>( 
//	            this, android.R.layout.simple_spinner_item, commodityspin); 
////	    spinner.setAdapter(cspinnerArrayAdapter);
//	    
//		cAdapter = 
//			new ArrayAdapter<String>(
//					this,
//					android.R.layout.simple_spinner_item,
//					commodityspin );
////		mAdapter.sort(String.CASE_INSENSITIVE_ORDER);
//		cAdapter.setDropDownViewResource(
//				android.R.layout.simple_spinner_dropdown_item);
//		spinner.setAdapter(cAdapter);
//		spinner.setOnItemSelectedListener(
//				new AdapterView.OnItemSelectedListener() {
//					public void onItemSelected(
//							AdapterView<?> parent, 
//							View view, 
//							int position, 
//							long id) {
//						String c = commodityspin[position];
//						find.setCommodity(c);
//						//eText.setText(d);
//					}
//
//					public void onNothingSelected(AdapterView<?> parent) {
//					}
//				}
//		);
	    
//		
////		spinner = (Spinner)findViewById(R.id.commoditySpinner);
//		setUpSpinnerAdapter(commodityspin);
//	    String commoditychoice = "";
//		if (spinner != null) {		
//			commoditychoice = (String) spinner.getSelectedItem();
//			Log.i(TAG, "#######Comodity choice = " + commoditychoice);
//			find.setCommodity(commoditychoice);	
//		}
		
		
		//Commodity Prices
		
		EditText eText = (EditText) findViewById(R.id.editText1);
		String value = eText.getText().toString();
		find.setPrice1(Float.parseFloat(value));
		
		eText = (EditText) findViewById(R.id.editText3);
		value = eText.getText().toString();
		find.setPrice2(Float.parseFloat(value));
		
		eText = (EditText) findViewById(R.id.editText4);
		value = eText.getText().toString();
		find.setPrice3(Float.parseFloat(value));
		
		//Date. Be careful, and confirm how months should be handled with Gerry
		DatePicker picker = ((DatePicker)findViewById(R.id.datePicker1));
		value = picker.getYear() + "/" + picker.getMonth() + "/" + picker.getDayOfMonth();
		Log.i(TAG, "Date = " + value);
		find.setDate(value);
	
//		EditText eText = (EditText) findViewById(R.id.syringesInEditText2);
//		String value = eText.getText().toString();
//		find.setSyringesIn(Integer.parseInt(value));
//
//		eText = (EditText) findViewById(R.id.syringesOutEditText2);
//		value = eText.getText().toString();
//		find.setSyringesOut(Integer.parseInt(value));

//		CheckBox checkBox = (CheckBox) findViewById(R.id.isNewCheckBox);
//		find.setNew(checkBox.isChecked());

		return find;
	}

	@Override
	protected void displayContentInView(Find find) {
		
//There needs to eventually be a method to retrieve the guid - The global unique Id.		
		
		
		CommodityFind oiFind = (CommodityFind)find;
		EditText et; // = (EditText)findViewById(R.id.guidEditText);
//		et.setText(oiFind.getGuid());
		
		
		//This code handles the date item Watch it carefully, 
		// months is incremented unusually
		DatePicker dp = (DatePicker) findViewById(R.id.datePicker1);
		String date = oiFind.getDate();
		Log.i(TAG,"display DOB = " + date);
		int yr=0, mon=0, day=0;
		day = Integer.parseInt(date.substring(date.lastIndexOf("/")+1));
		yr = Integer.parseInt(date.substring(0,date.indexOf("/")));
		mon = Integer.parseInt(date.substring(date.indexOf("/")+1,date.lastIndexOf("/")));

		
// 		Note: The next line of code increments months so that they are 1-12 
//		instead of 0-11
		
//		mon += 1;
		String dateAdj = yr + "/" + mon + "/" + day;
		Log.i(TAG, dateAdj);
//		We need to do something with the dates. This creates a textview		
//		((TextView) findViewById(R.id.dob_label)).setText(getString(R.string.dob) 
//		+": " + dateAdj);
		try {
			if (date != null) {
				Log.i(TAG,"display DOB = " + date);
				dp.init(yr, mon, day, this);
			}
		} catch (IllegalArgumentException e) {
			Log.e(TAG, "Illegal Argument, probably month == 12 in " + date);
			e.printStackTrace();
		}
		

//		Spinner spinner = (Spinner) findViewById(R.id.marketSpinner);
//		ArrayAdapter<String> mspinnerArrayAdapter = new ArrayAdapter<String>( 
//	            this, android.R.layout.simple_spinner_item, marketspin); 
//	    spinner.setAdapter(mspinnerArrayAdapter);
//		Spinner mspinner = (Spinner) findViewById(R.id.marketSpinner);
//	    setSpinner(mspinner, oiFind.getMarket());		
		
//		Spinner spinner = (Spinner)findViewById(R.id.commoditySpinner);
//		String commodityspin[] = loadData("/commodity/commoditylist.csv");
//		Spinner spinner2 = (Spinner)findViewById(R.id.spinnerCommuneSection);
		
//		spinner = (Spinner) findViewById(R.id.commoditySpinner);
//	    ArrayAdapter<String> cspinnerArrayAdapter = new ArrayAdapter<String>( 
//	            this, android.R.layout.simple_spinner_item, commodityspin); 
//	    spinner.setAdapter(cspinnerArrayAdapter); 
//		setUpSpinnerAdapter(commodityspin);
		
	    
//		Spinner cspinner = (Spinner) findViewById(R.id.commoditySpinner);
		Log.i(TAG, "##############" + oiFind.getCommodity());
//		Log.i(TAG, "##############" + cspinner.getItemAtPosition(2));
//		Log.i(TAG, "##############" + oiFind.getCommodity().equals(cspinner.getItemAtPosition(2)));
//		setSpinner(cspinner, oiFind.getCommodity());
		
		

		
		et = (EditText)findViewById(R.id.editText1);
		et.setText(Float.toString((oiFind.getPrice1())));
		
		et = (EditText)findViewById(R.id.editText3);
		et.setText(Float.toString((oiFind.getPrice2())));
		
		et = (EditText)findViewById(R.id.editText4);
		et.setText(Float.toString((oiFind.getPrice3())));
		
//		
//		et = (EditText)findViewById(R.id.syringesOutEditText2);
//		et.setText(Integer.toString(oiFind.getSyringesOut()));
		
//		CheckBox cb = (CheckBox)findViewById(R.id.isNewCheckBox);
//		cb.setChecked(oiFind.isNew());
		
	}

	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.nextButton:
			if(saveFind()){
				Toast.makeText(this, getString(R.string.ctoast_saved), Toast.LENGTH_SHORT).show();
//				CommoditySmsManager.sendSMS("2036101410", d+","+
//						cspinner.getItemAtPosition(posc)+","+
//						((EditText)findViewById(R.id.editText1)).getText().toString()+","+
//						((EditText)findViewById(R.id.editText3)).getText().toString()+","+
//						((EditText)findViewById(R.id.editText4)).getText().toString());
				if ( posc < cspinner.getCount()-1)
					cspinner.setSelection(++posc);
				EditText et = (EditText)findViewById(R.id.editText1);
				et.setText(Float.toString(0));
				
				et = (EditText)findViewById(R.id.editText3);
				et.setText(Float.toString(0));
				
				et = (EditText)findViewById(R.id.editText4);
				et.setText(Float.toString(0));
			}
			else
				Toast.makeText(this, getString(R.string.ctoast_unsaved), Toast.LENGTH_SHORT).show();
			break;
		}
		
		//  If DatePicker was touched, mark the form as edited
//		if (id == R.id.datePicker1) {
//			isProbablyEdited = true;
//			mSaveButton.setEnabled(true);	
//		}
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	//spinner function	
//	public static void setSpinner(Spinner spinner, ContentValues contentValues, 
//	String attribute){
//		String selected = contentValues.getAsString(attribute);
//		int k = 0;
//		if(selected != null){
//			String item = (String) spinner.getItemAtPosition(k);
//			while (k < spinner.getCount()-1 && !selected.equals(item)) {
//				++k;
//				item = (String) spinner.getItemAtPosition(k);				
//			}
//			if (k < spinner.getCount())
//				spinner.setSelection(k);
//			else
//				spinner.setSelection(0);
//		}
//		else{
//			spinner.setSelection(0);
//		}
//	}
	
	/**
	 * Invoked when the DatePicker is touched.
	 */
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		Log.i(TAG, "onDateChanged");
//		isProbablyEdited = true;
//		mSaveButton.setEnabled(true);	
	}
	
	//spinner function without content values
	public static void setSpinner(Spinner spinner, String selected, String attribute){
//		String selected = contentValues.getAsString(attribute);
		int k = 0;
		if(selected != null){
			String item = (String) spinner.getItemAtPosition(k);
			while (k < spinner.getCount()-1 && !selected.equals(item)) {
				++k;
				item = (String) spinner.getItemAtPosition(k);				
			}
			if (k < spinner.getCount())
				spinner.setSelection(k);
			else
				spinner.setSelection(0);
		}
		else{
			spinner.setSelection(0);
		}
	}
	
	//spinner function using an array
	public static void setSpinner(Spinner spinner, String selected){
//		String selected = contentValues.getAsString(attribute);
		int k = 0;
		if(selected != null){
			String item = (String) spinner.getItemAtPosition(k);
			while (k < spinner.getCount()-1 && !selected.equals(item)) {
				++k;
				item = (String) spinner.getItemAtPosition(k);				
			}
			Log.i(TAG, "k is equal to " + k);
			Log.i(TAG, "spinner.getCount() is equal to " + spinner.getCount());			
			if (k < spinner.getCount()){	
				spinner.setSelection(k);
				Log.i(TAG, "selection is set");
			}
			else
				Log.i(TAG, "Problem if reached");
				spinner.setSelection(0);
		}
		else{
			Log.i(TAG, "Problem if reached");
			spinner.setSelection(0);
		}
	}
	
	/**
	 * Reads beneficiary data from a text file.  Currently the
	 * file name is hard coded as "beneficiaries.txt" and it is
	 * stored in the /assets folder.
	 * @return  Returns an array of Strings, each of which represents
	 * a Beneficiary record.
	 */
	private String[] loadData(String filepath) {
		String[] data = null;
		
		File file = null; 
		file = new File(Environment.getExternalStorageDirectory() 
					+ filepath);
			

		BufferedReader br = null;
		String line = null;
		int k = 0;
		
		try {
			//InputStream iStream = this.getAssets().open("beneficiaries.txt");
			FileInputStream iStream = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(iStream));
			data = new String[20000]; //Note: temp value
			line = br.readLine();
			//while (line != null && k < 1000)  {
//			if (beneficiaryType == AcdiVocaDbHelper.FINDS_TYPE_MCHN) {
//				
//				// Reading from Beneficiare.csv and filter by distrCtr
//				while (line != null)  {
//					//				Log.i(TAG, line);
//					if (line.length() > 0 && line.charAt(0) != '*')  {
//						if (line.contains(distrCtr)) {
//							data[k] = line;
//							k++;
//						}
//					}
//					line = br.readLine();
//				}
//			} else {
				
				// Reading from Livelihood.csv and no filter
				while (line != null)  {
					//				Log.i(TAG, line);
					if (line.length() > 0 && line.charAt(0) != '*')  {
//						if (!line.contains("No_dossier")) {
//						if (line.contains(distrCtr)) {	
//							Log.i(TAG, line);
							data[k] = line;
							k++;
						}
					line = br.readLine();

					}
//				}	
//		}
		} catch (IOException e) {
			Log.e(TAG, "IO Exception,  file =   " + file);
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		} catch (StringIndexOutOfBoundsException e) {
			Log.e(TAG, "Bad line?  " + line);
//			showDialog(STRING_EXCEPTION);
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
		String[] dossiers = new String[k];  // Create the actual size of array
		for (int i= 0; i < k; i++)
			dossiers[i] = data[i];
		return dossiers;
	}
	
	private void setUpSpinnerAdapter(final String[] data) {
		mAdapter = 
			new ArrayAdapter<String>(
					this,
					android.R.layout.simple_spinner_item,
					data );
//		mAdapter.sort(String.CASE_INSENSITIVE_ORDER);
		mAdapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);
		cspinner.setAdapter(mAdapter);
		cspinner.setOnItemSelectedListener(
				new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(
							AdapterView<?> parent, 
							View view, 
							int position, 
							long id) {
						String d = data[position];

						//eText.setText(d);
					}

					public void onNothingSelected(AdapterView<?> parent) {
					}
				}
		);
//		eText = ((EditText)findViewById(R.id.dossierEdit));
//		eText.addTextChangedListener(this);
//		eText.setText(""); 
	}
	
	public String makeSMStext(Find find) {
		CommodityFind oiFind = (CommodityFind)find;
		String smsString = oiFind.getMarket() + "," + oiFind.getCommodity() + "," + 
		oiFind.getPrice1()+ "," + oiFind.getPrice2() + "," + oiFind.getPrice3() 
		+ "," + oiFind.getDate();
		return smsString;
	}

	
}