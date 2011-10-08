/**
 * 
 */
package org.hfoss.posit.android.experimental.plugin.commodity;

import org.hfoss.posit.android.experimental.R;
import org.hfoss.posit.android.experimental.api.Find;
import org.hfoss.posit.android.experimental.api.activity.FindActivity;
import org.hfoss.posit.android.experimental.plugin.acdivoca.AcdiVocaFind;
import org.hfoss.posit.android.experimental.plugin.acdivoca.AcdiVocaMchnFindActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Spinner;


/**
 * FindActivity subclass for Outside In plugin.
 * 
 */
public class CommodityFindActivity extends FindActivity implements OnItemSelectedListener {

	private static final String TAG = "CommodityFindActivity";

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
		
		// Commodity spinner
		Spinner spinner = null;
		spinner = (Spinner)findViewById(R.id.commoditySpinner);
		String communeSection = (String) spinner.getSelectedItem();
//		result.put(AcdiVocaFind.COMMUNE_SECTION, communeSection);	
	
//		EditText eText = (EditText) findViewById(R.id.syringesInEditText2);
//		String value = eText.getText().toString();
//		find.setSyringesIn(Integer.parseInt(value));
//
//		eText = (EditText) findViewById(R.id.syringesOutEditText2);
//		value = eText.getText().toString();
//		find.setSyringesOut(Integer.parseInt(value));

		CheckBox checkBox = (CheckBox) findViewById(R.id.isNewCheckBox);
		find.setNew(checkBox.isChecked());

		return find;
	}

	@Override
	protected void displayContentInView(Find find) {
		CommodityFind oiFind = (CommodityFind)find;
		EditText et = (EditText)findViewById(R.id.guidEditText);
		et.setText(oiFind.getGuid());
		
		Spinner spinner = (Spinner)findViewById(R.id.commoditySpinner);
//		Spinner spinner2 = (Spinner)findViewById(R.id.spinnerCommuneSection);
//		AcdiVocaMchnFindActivity.setSpinner(spinner, contentValues, AcdiVocaFind.COMMUNE_SECTION);
		
//		et = (EditText)findViewById(R.id.syringesInEditText2);
//		et.setText(Integer.toString(oiFind.getSyringesIn()));
//		
//		et = (EditText)findViewById(R.id.syringesOutEditText2);
//		et.setText(Integer.toString(oiFind.getSyringesOut()));
		
		CheckBox cb = (CheckBox)findViewById(R.id.isNewCheckBox);
		cb.setChecked(oiFind.isNew());
		
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

}
