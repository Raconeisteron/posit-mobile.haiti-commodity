/**
 * 
 */
package org.hfoss.posit.android.experimental.plugin.commodity;

import java.util.Calendar;

import org.hfoss.posit.android.experimental.R;
import org.hfoss.posit.android.experimental.api.Find;
import org.hfoss.posit.android.experimental.api.activity.FindActivity;
import org.hfoss.posit.android.experimental.plugin.acdivoca.AcdiVocaFind;
import org.hfoss.posit.android.experimental.plugin.acdivoca.AcdiVocaMchnFindActivity;
//import org.hfoss.posit.android.experimental.api.activity.SettingsActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * FindActivity subclass for Outside In plugin.
 * 
 */
public class CommodityFindActivity extends FindActivity implements OnItemSelectedListener, 
OnDateChangedListener {

	private static final String TAG = "CommodityFindActivity";

	
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


	}

	@Override
	protected void initializeListeners() {
		super.initializeListeners();
		Calendar calendar = Calendar.getInstance();

		((DatePicker)findViewById(R.id.datePicker1)).init(
				calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH), 
				calendar.get(Calendar.DAY_OF_MONTH), this);
		
//		((Spinner)findViewById(R.id.commoditySpinner)).setOnItemSelectedListener(this);
	}

	@Override
	protected Find retrieveContentFromView() {
		CommodityFind find =  (CommodityFind)super.retrieveContentFromView();
		
//		TextView t = (TextView)findViewById(R.id.textView10);
//		t.setText(R.string.cCommuneSection);
		
		//Commune Section code
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String communesectionkey = 
			prefs.getString(getString(R.string.cCommune_section_key),"");
		Log.i(TAG, communesectionkey);
		
		
		// Commodity spinner
		Spinner spinner = null;
		spinner = (Spinner)findViewById(R.id.commoditySpinner);
		String commoditychoice = (String) spinner.getSelectedItem();
		find.setCommodity(commoditychoice);	
		
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
		

		
		
		Spinner spinner = (Spinner)findViewById(R.id.commoditySpinner);
//		Spinner spinner2 = (Spinner)findViewById(R.id.spinnerCommuneSection);
		setSpinner(spinner, oiFind.getCommodity(), CommodityFind.C_COMMODITY);
		
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
//	
}
