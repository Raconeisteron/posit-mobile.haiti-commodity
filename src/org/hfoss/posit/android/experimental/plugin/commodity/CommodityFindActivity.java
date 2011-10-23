/**
 * 
 */
package org.hfoss.posit.android.experimental.plugin.commodity;

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
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * FindActivity subclass for Outside In plugin.
 * 
 */
public class CommodityFindActivity extends FindActivity implements OnItemSelectedListener {

	private static final String TAG = "CommodityFindActivity";

	
//	Preferences

//	Private Context context = this.context;
//	this.context = context;
	
//	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//	String communesectionkey = prefs.getString(getString(R.string.cCommune_section_key),"");
//	Log.i(TAG, "onCreate()");
//	String CommuneSectionKey = 
//		this.getResources().getString(R.string.cCommune_section_key);
//	try {
//		String phone = prefs.getString(getString(R.string.cCommune_section_key),"");
//		if (phone.equals("")) {
//			mSpEditor = mSharedPrefs.edit();
//			mSpEditor.putString(getString(R.string.smsPhoneKey), getString(R.string.default_phone));
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
		
//		((Spinner)findViewById(R.id.commoditySpinner)).setOnItemSelectedListener(this);
	}

	@Override
	protected Find retrieveContentFromView() {
		CommodityFind find =  (CommodityFind)super.retrieveContentFromView();
		
//		TextView t = (TextView)findViewById(R.id.textView10);
//		t.setText(R.string.cCommuneSection);
		
		//Commune Section code
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String communesectionkey = prefs.getString(getString(R.string.cCommune_section_key),"");
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
		EditText et = (EditText)findViewById(R.id.guidEditText);
//		et.setText(oiFind.getGuid());
		
		Spinner spinner = (Spinner)findViewById(R.id.commoditySpinner);
//		Spinner spinner2 = (Spinner)findViewById(R.id.spinnerCommuneSection);
		setSpinner(spinner, oiFind.getCommodity(), CommodityFind.cCommodity);
		
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
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	//spinner function	
//	public static void setSpinner(Spinner spinner, ContentValues contentValues, String attribute){
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
