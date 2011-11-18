package org.hfoss.posit.android.experimental.functionplugins;

/*
* File: TutorialActivity.java
* 
* Copyright (C) 2009 The Humanitarian FOSS Project (http://www.hfoss.org)
* 
* This file is part of POSIT, Portable Open Search and Identification Tool.
*
* POSIT is free software; you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License (LGPL) as published 
* by the Free Software Foundation; either version 3.0 of the License, or (at
* your option) any later version.
* 
* This program is distributed in the hope that it will be useful, 
* but WITHOUT ANY WARRANTY; without even the implied warranty of 
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* General Public License for more details.
* 
* You should have received a copy of the GNU LGPL along with this program; 
* if not visit http://www.gnu.org/licenses/lgpl.html.
* 
*/


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


//Note: the following imports often do not apply and have been heavily edited
//import org.hfoss.posit.android.Log;
import org.hfoss.posit.android.experimental.R;
import org.hfoss.posit.android.experimental.api.AppControlManager;
import org.hfoss.posit.android.experimental.api.Find;
import org.hfoss.posit.android.experimental.functionplugins.CommodityFilePickerActivity;
import org.hfoss.posit.android.experimental.api.database.DbManager;
//import org.hfoss.posit.android.experimental.api.FilePickerActivity;
import org.hfoss.posit.android.experimental.plugin.FindActivityProvider;
import org.hfoss.posit.android.experimental.plugin.FindPluginManager;
import org.hfoss.posit.android.experimental.plugin.commodity.CommodityDbManager;
import org.hfoss.posit.android.experimental.plugin.commodity.CommodityFind;
import org.hfoss.posit.android.experimental.plugin.commodity.CommodityListFindsActivity;
import org.hfoss.posit.android.experimental.plugin.commodity.CommodityMessage;
import org.hfoss.posit.android.experimental.plugin.commodity.CommoditySearchFilterActivity;
import org.hfoss.posit.android.experimental.plugin.commodity.CommoditySmsManager;
import org.hfoss.posit.android.experimental.functionplugins.LoginActivity;
import org.hfoss.posit.android.experimental.api.activity.SettingsActivity;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
* This class designed to make the user more comfortable with
* posit the first time posit is opened. The user navigates through the tutorial
* using previous, next, skip, and finish buttons.
*/

interface SmsCallBack {
	public void smsMgrCallBack(String s);
}

public class CommodityAdminActivity extends OrmLiteBaseActivity<DbManager>
implements SmsCallBack {

	public static String TAG = "AdminActivity";
	
	public static final int MAX_BENEFICIARIES = 20000;  // Max readable
	
	public static final String DEFAULT_DIRECTORY = "commodity";
	public static final String DEFAULT_MCHN_DIRECTORY = "commodity/commoditylists";
	public static final String DEFAULT_AGRI_DIRECTORY = "commodity/markets";
	public static final String DEFAULT_LOG_DIRECTORY = "commodity/super";
	public static final String SMS_LOG_FILE = "smslog.txt";
	public static final String DEFAULT_COMMODITYLIST_FILE = "commoditylist.csv";
	public static final String DEFAULT_MARKET_FILE = "marketlist.csv";
	public static final String COMMA= ",";
	public static final int DONE = 0;
	public static final int ERROR = -1;

	public static final int IO_EXCEPTION = 1;
	public static final int STRING_EXCEPTION = 2;
	public static final int SEND_DIST_REP = 3;
	public static final int SMS_REPORT = 4;
	public static final int SUMMARY_OF_IMPORT = 5;
	public static final int ZERO_BENEFICIARIES_READ = 6;
	public static final int INVALID_PHONE_NUMBER = 7;
	public static final int DISTRIBUTION_SUMMARY = 8;

// Figure out if I need a Commodity DbManager	
	private CommodityDbManager dbManager;
	
	private ArrayAdapter<String> adapter;
	private String mBeneficiaries[] = null;
	private ProgressDialog mProgressDialog;
	private String mDistrCtr;
	
	private Context mContext;
	private String mSmsReport;
	private String mImportDataReport;
	private String mDistributionSummaryReport;
	private String[] mSummaryItems;

	private ArrayList<CommodityMessage> mCommodityMsgs;

	/**
	 * Callback method used by SmsManager to report how
	 * many messages were sent. 
	 * @param smsReport the report from SmsManager
	 */
	public void smsMgrCallBack(String smsReport) {
		mSmsReport = smsReport;
		showDialog(SMS_REPORT);
	}
	
// Need to determine whether I need to create a DbManager	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate()");
		super.onCreate(savedInstanceState);
		dbManager = (CommodityDbManager)dbManager;
	}

	@Override
	protected void onResume() {
		Log.i(TAG, "onResume()");
		super.onResume();
		int count = 0;
////		AcdiVocaLocaleManager.setDefaultLocale(this);  // Locale Manager should be in API
		setContentView(R.layout.acdivoca_admin);
		List<? extends Find> finds = this.getHelper().getAllFinds();
//		Toast.makeText(this, "Saving Finds to Log File", Toast.LENGTH_LONG).show();
		count = logFinds(finds);
//		if (count  >= 0) {
//			finish();
//			Toast.makeText(
//					this, count + 
//					" Finds saved to SD Card: " + DEFAULT_MCHN_DIRECTORY + "/"
//							+ DEFAULT_COMMODITYLIST_FILE, Toast.LENGTH_LONG).show();
//		} else {
//			finish();
//			Toast.makeText(
//					this,
//					"Error while writing to file: " + DEFAULT_LOG_DIRECTORY + "/"
//							+ DEFAULT_COMMODITYLIST_FILE, Toast.LENGTH_LONG).show();
//		}
	}	
	
	@Override
	protected void onPause() {
		Log.i(TAG, "onPause()");
		super.onPause();
	}

	/**
	 * Creates the menu options.
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.acdi_voca_admin_menu, menu);
		mContext = this;
		return true;
	}
	
	
	
	
	/**
	 * Appends Finds (as strings) to a text file on the SD card.
	 * 
	 * @param finds, a list of Find records	 * 
	 * @return True if Finds were written successfully, False otherwise.
	 */
	protected int logFinds(List<? extends Find> finds) {
		int count = 0;
		try {
			File dir = new File(Environment.getExternalStorageDirectory()
					+ "/" + DEFAULT_MCHN_DIRECTORY);
			if (!dir.exists()) {
				if (dir.mkdir()) {
					Log.i(TAG, "Created directory " + dir);
				}
			}
			if (dir.canWrite()) {
				Log.i(TAG, dir + " is writeable");
			}
            File file = new File(Environment.getExternalStorageDirectory()
                    + "/" + DEFAULT_MCHN_DIRECTORY 
                    + "/"
                    + DEFAULT_COMMODITYLIST_FILE);
            if (!file.exists()) {
            	if (file.createNewFile()) 
            		Log.i(TAG, "Created file " + file);
            }
            
			PrintWriter writer = new PrintWriter(new BufferedWriter(
					new FileWriter(file, true)));
			
			// NOTE:  For now we use the Find's isAdhoc field (which is unused) for
			// recording whether the Find is logged.

			Iterator<? extends Find> it = finds.iterator();
			while (it.hasNext()) {
				Find find = it.next();
				Log.i(TAG, "Find = " + find);
				
//				if (find.getIs_adhoc() != IS_LOGGED) {
//					find.setIs_adhoc(IS_LOGGED);
////				if (((OutsideInFind)find).getIsLogged() == false) {
////					((OutsideInFind) find).setIsLogged(true);
//					getHelper().update(find);
//					writer.println(new Date() + ": " + find);
//					Log.i(TAG, "Wrote to file: " + find);
//					++count;
//				}
			}
			writer.flush();
			writer.close();
			return count;
		} catch (IOException e) {
			Log.e(TAG, "IO Exception writing to Log " + e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}
	

	
	/**
	 * The Admin menu needs to be carefully prepared to control the management
	 * of distribution events through the various stages:  select distribution point,
	 * import beneficiary data, start, stop, send distribution report.  
	 * Here's where the various values are set:
	 * 
	 * 1) Select distribution point -- selected in settings.
	 * 2) Import beneficiary file -- set in SettingsActivity
	 * 3) Start -- set in onMenuItemSelected
	 * 4) Stop -- set in onMenuItemSelected
	 * 5) Send report -- set in SendSms
	 */
//	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		
		menu.clear();
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.commodity_admin_menu, menu);
		
		MenuItem loadAgriItem = menu.findItem(R.id.cload_market_data);
		MenuItem loadMchnItem = menu.findItem(R.id.cload_commodity_data);
//		MenuItem listFindsItem = menu.findItem(R.id.admin_list_beneficiaries);
//		MenuItem startStopDistr = menu.findItem(R.id.start_stop_distribution);
		MenuItem sendDistrReport = menu.findItem(R.id.csend_commodity_report);
		
		// Is the app in Distribution mode?
//		Log.i(TAG, "Distribution Stage = " + AppControlManager.displayDistributionStage(this));
	
		// What type of user is logged in?
		Log.i(TAG, "UserType = " + AppControlManager.getUserType());
		
		loadAgriItem.setVisible(true);
		loadAgriItem.setEnabled(true);
		loadMchnItem.setVisible(true);
		loadMchnItem.setEnabled(true);
		sendDistrReport.setVisible(true);
		sendDistrReport.setEnabled(true);
		
		
		//Note: Need to re-implement Super user
		
		// Only SUPER user sees the ListFinds menu
//		if (!AppControlManager.isSuperUser()) {  
//			listFindsItem.setVisible(false);
//		} else {
//			listFindsItem.setVisible(true);
//		}
		
		// Only SUPER and AGRON users can import livelihood data
//		if ( !AppControlManager.isDuringDistributionEvent() 
//				&& (AppControlManager.isSuperUser() || AppControlManager.isAgronUser())) {
//			loadAgriItem.setVisible(true);
//			loadAgriItem.setEnabled(true);
//		} else {
//			loadAgriItem.setVisible(false);
//			loadAgriItem.setEnabled(false);
//		}
		
		// AGRON users don't see distribution event menus
//		if (AppControlManager.isAgronUser()) {
//			loadMchnItem.setVisible(false);
//			startStopDistr.setVisible(false);
//			sendDistrReport.setVisible(false);
//		}

//		Log.i(TAG, "onPrepareMenuOptions, " 
//				+ " distribution stage = " + AppControlManager.displayDistributionStage(this));
		
		// For each menu item set its visibility based on Distribution event stage
//		MenuItem item = null;
//		for (int k = 0; k < menu.size(); k++) {
//			item = menu.getItem(k);
//			switch (item.getItemId()) {
//			case R.id.cload_commodity_data:
//				if (AppControlManager.isImportDataStage()) 
//					item.setEnabled(true);
//				else 
//					item.setEnabled(false);
//				break;
//			case R.id.start_stop_distribution:
//				SubMenu sub = item.getSubMenu();
//				if (AppControlManager.isStartDistributionStage()) {
//					item.setEnabled(true);
//					sub.getItem(0).setEnabled(true);
//					sub.getItem(1).setEnabled(false);
//				} else if (AppControlManager.isStopDistributionStage()) {
//					item.setEnabled(true);
//					sub.getItem(0).setEnabled(false);
//					sub.getItem(1).setEnabled(true);		
//				} else {
//					item.setEnabled(false);
//				}
//				break;
//			case R.id.csend_commodity_report:
//				if (AppControlManager.isSendDistributionReportStage()) 
//					item.setEnabled(true);
//				else 
//					item.setEnabled(false);
//				break;
//			}
//		}
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Manages the selection of menu items.
	 * 
	 * @see android.app.Activity#onMenuItemSelected(int, android.view.MenuItem)
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Log.i(TAG, "Menu Item = " + item.getTitle());
		Intent intent = new Intent();

		switch (item.getItemId()) {
		case R.id.settings_menu_item:
			startActivity(new Intent(this, SettingsActivity.class));
			break;
		case R.id.admin_list_beneficiaries:
			startActivity(new Intent(this, CommodityListFindsActivity.class));
			break;
		case R.id.cload_commodity_data:
			intent.setClass(this, CommodityFilePickerActivity.class);
			
			this.startActivityForResult(intent, CommodityFilePickerActivity.ACTION_CHOOSER);
			break;
		case R.id.cload_market_data:
			intent.setClass(this, CommodityFilePickerActivity.class);
			
			this.startActivityForResult(intent, CommodityFilePickerActivity.ACTION_CHOOSER);
			break;
			
		//	Class<Activity> loginActivity = FindActivityProvider.getLoginActivityClass();
		//	if (loginActivity != null) {
//				intent.setClass(this, loginActivity);
//				intent.putExtra(AcdiVocaUser.USER_TYPE_STRING, AcdiVocaUser.UserType.ADMIN.ordinal());
//				intent.putExtra(AcdiVocaFind.TYPE, AcdiVocaFind.TYPE_MCHN);
//				this.startActivityForResult(intent, LoginActivity.ACTION_LOGIN);
				
//				Toast.makeText(this, getString(R.string.toast_admin_login_required), Toast.LENGTH_LONG).show();	
//			}
//			break;
//		case R.id.cload_market_data:
//			loginActivity = FindActivityProvider.getLoginActivityClass();
//			if (loginActivity != null) {
//				intent.setClass(this, loginActivity);
////				intent.putExtra(AcdiVocaUser.USER_TYPE_STRING, AcdiVocaUser.UserType.AGRON.ordinal());
////				intent.putExtra(AcdiVocaFind.TYPE, AcdiVocaFind.TYPE_AGRI);
//				this.startActivityForResult(intent, LoginActivity.ACTION_LOGIN);
//				
//				Toast.makeText(this, getString(R.string.toast_admin_login_required), Toast.LENGTH_LONG).show();	
//			}
//			break;
//		case R.id.start_distribution:
//			Log.i(TAG, "Start distribution event");
//			item.setEnabled(false);
//			AppControlManager.moveToNextDistributionStage(this);
//			break;
//		case R.id.stop_distribution:
//			Log.i(TAG, "Start distribution event");
//			item.setEnabled(false);
//			AppControlManager.moveToNextDistributionStage(this);
//			displayDistributionSummary();
//			break;
		case R.id.csend_commodity_report:
//			sendDistributionReport();
			break;
		}
		return true;
	}
	
// Commodity Tracker doesn't have distribution events	
	
//	/**
//	 * Provides a summary of the distribution event and displays it
//	 * as an Alert Dialog. 
//	 */
//	private void displayDistributionSummary() {
//		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
//		String distrKey = this.getResources().getString(R.string.distribution_point_key);
//		String distributionCtr = sharedPrefs.getString(distrKey, "");
//		Log.i(TAG, distrKey +"="+ distributionCtr);
//		
//		Dao<CommodityFind, Integer> dao = null;
//		try {
//			dao = this.dbManager.getCommodityFindDao();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
////		int nAbsentees = AcdiVocaFind.queryNDistributionAbsentees(dao, distributionCtr);
////		int nWomen = AcdiVocaFind.queryNDistributionWomenProcessed(dao, distributionCtr);
////		int nChildren = AcdiVocaFind.queryNDistributionChildrenProcessed(dao, distributionCtr);
////
////		mSummaryItems = new String[3];
////		mDistributionSummaryReport = getString(R.string.distribution_summary);
////		mSummaryItems[0] = getString(R.string.nabsentees) + " " + nAbsentees;
////		mSummaryItems[1] = getString(R.string.nmothers) + " "  + nWomen;
////		mSummaryItems[2] = getString(R.string.nchildren) + " "  + nChildren;
////		showDialog(DISTRIBUTION_SUMMARY);
//	}
	
	/**
	 * Sends both update messages and bulk messages.
	 */
	private void sendDistributionReport() {
		
		CommoditySmsManager mgr = CommoditySmsManager.getInstance(this);
		if (!mgr.isPhoneNumberSet(this)) {
			showDialog(INVALID_PHONE_NUMBER);
			return;
		}

		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		String distrKey = this.getResources().getString(R.string.distribution_point_key);
		String distributionCtr = sharedPrefs.getString(distrKey, "");
		Log.i(TAG, distrKey +"="+ distributionCtr);

		// First create update reports -- i.e., those beneficiaries who were present and had changes.
//		AcdiVocaDbHelper db = new AcdiVocaDbHelper(this);
		
		Dao<CommodityFind, Integer> dao = null;
		
		
		// Try/catch combination caused an error, commented out
		
		
//		try {
			dao = this.dbManager.getCommodityFindDao();
			mCommodityMsgs = CommodityFind.constructMessages(dao, CommoditySearchFilterActivity.RESULT_SELECT_UPDATE, distributionCtr);
			//Don't need bulk update messages, commented out for now
//			mCommodityMsgs.addAll(CommodityFind.constructBulkUpdateMessages(dao, distributionCtr));
//		} 

//		catch (SQLException e1) {
//			e1.printStackTrace();
//		}
		

		// Now create bulk messages for absentees
//		db = new AcdiVocaDbHelper(this);
//		mAcdiVocaMsgs.addAll(db.createBulkUpdateMessages(distributionCtr));
		
		
		Log.i(TAG, "nMsgs to send " + mCommodityMsgs.size());
		// Prompt the user
		showDialog(SEND_DIST_REP);
	}
	
//Need FilePickerActivity stuff	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i(TAG,"onActivityResult = " + resultCode);
		int beneficiaryType = 0;

		switch (requestCode) {
		
		case CommodityFilePickerActivity.ACTION_CHOOSER:
			if (resultCode == CommodityFilePickerActivity.RESULT_OK) {
				String filename = data.getStringExtra(Intent.ACTION_CHOOSER);

				// Are we loading beneficiary update data, all of type MCHN or AGRI data
//				beneficiaryType = data.getIntExtra(CommodityFind.TYPE, -1);
				
				Log.i(TAG, "File picker file = " + filename + " Beneficiary type = " + beneficiaryType);

				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

// No types in Commodity Tracker But do need to use distribution point code for markets and commune sections				

				
//				if (beneficiaryType == CommodityFind.TYPE_MCHN) {
//
//					// Get this phone's Distribution Center
//					SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//					mDistrCtr = prefs.getString(this.getResources().getString(R.string.distribution_point_key), null);
//
//
//						if (mDistrCtr == null) {	
//							Log.i(TAG, "Aborting loadBeneficiaryData, No distribution post selected");
//							Toast.makeText(this, getString(R.string.toast_distribution_post), Toast.LENGTH_SHORT);
//							return;
//
//						}
//				}
//				if (beneficiaryType == CommodityFind.TYPE_AGRI) {
//
//					// Get this phone's commune section
//					mDistrCtr = prefs.getString(this.getResources().getString(R.string.commune_section_key), null);
//
//
//						if (mDistrCtr == null) {	
//							Log.i(TAG, "Aborting loadBeneficiaryData for Livelihood data, No commune section selected");
//	//						Toast.makeText(this, getString(R.string.toast_distribution_post), Toast.LENGTH_SHORT);
//							return;
//						}
//					}
				
				mProgressDialog = ProgressDialog.show(this, getString(R.string.loading_data),
						getString(R.string.please_wait), true, true);
				
				ImportDataThread thread = new ImportDataThread(this, filename, beneficiaryType, new ImportThreadHandler());
				thread.start();				
			}
			break;

//Need a File Picker Activity			
		case LoginActivity.ACTION_LOGIN:
			if (resultCode == RESULT_OK) {
				
				Intent intent = new Intent();
				beneficiaryType = data.getIntExtra(CommodityFind.TYPE, -1);
				Log.i(TAG, "Logged in, beneficiary type = " + beneficiaryType);
				intent.putExtra(CommodityFind.TYPE, beneficiaryType);
				intent.setClass(this, CommodityFilePickerActivity.class);
				
				this.startActivityForResult(intent, CommodityFilePickerActivity.ACTION_CHOOSER);
			
				break;
			} else {
				Toast.makeText(this, getString(R.string.toast_incorrect), Toast.LENGTH_LONG).show();
				finish();
			} 
		
		default:
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	
	/**
	 * Reads data from a text file into the Db.
	 */
	private int importBeneficiaryDataToDb(String filename, int beneficiaryType) {		
		ContentValues values = new ContentValues();
		
		
		
	
		// Read all the file names on the SD Card
//		File directory = new File(Environment.getExternalStorageDirectory() + "/" + DEFAULT_DIRECTORY);
//		File file[] = directory.listFiles();
//		
//		// List files on sdcard
	    File file[] = Environment.getExternalStorageDirectory().listFiles(); 
	    for (int i = 0; i < file.length; i++)
	    	Log.i(TAG, file[i].getAbsolutePath());  
		
		Log.i(TAG, "Reading beneficiaries from " + filename);
		mBeneficiaries = loadBeneficiaryData(filename, mDistrCtr, beneficiaryType);
		
		if (mBeneficiaries.length == 0) {
			mImportDataReport = getString(R.string.beneficiaries_imported2) + " " + mBeneficiaries.length;
			return ZERO_BENEFICIARIES_READ;
		} else if (mBeneficiaries.length == 1) {
			if (Integer.parseInt(mBeneficiaries[0]) == IO_EXCEPTION)
				return IO_EXCEPTION;
			else if (Integer.parseInt(mBeneficiaries[0]) == STRING_EXCEPTION)
				return STRING_EXCEPTION;
		}
		CommodityDbManager dbManager = (CommodityDbManager)this.dbManager;
		try {
			int rows = CommodityFind.clearTable(this.dbManager.getCommodityFindDao());
			Log.i(TAG, "Deleted rows in beneficiary table = " + rows);
			rows = CommodityMessage.clearTable(dbManager.getCommodityMessageDao());
			Log.i(TAG, "Deleted rows in message table = " + rows);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		long nImports = 0;
		Log.i(TAG, "Beneficiary type to be loaded = " + beneficiaryType);
		
// I need this code. Should be split into Markets and Commodities instead.		
//		try {
//		if (beneficiaryType == CommodityFind.TYPE_MCHN) {
			nImports = CommodityFind.addUpdateCommodities(this.dbManager.getCommodityFindDao(),  mBeneficiaries);
//		} else  {
//			nImports = CommodityFind.addAgriBeneficiaries(this.dbManager.getCommodityFindDao(), mBeneficiaries);
//		}
//		}
		
//		catch (SQLException e) {
//			e.printStackTrace();
//		}

		mImportDataReport = getString(R.string.beneficiaries_imported2) + " " + nImports;
		Log.i(TAG, "Inserted to database " + nImports + " Beneficiaries");	
		
		// Move to the next stage of the distribution event process
//		if(beneficiaryType == CommodityFind.TYPE_MCHN) {
//		AppControlManager.moveToNextDistributionStage(this);
//		}
		return DONE;
	}
	
	/**
	 * Reads beneficiary data from a text file.  Currently the
	 * file name is hard coded as "beneficiaries.txt" and it is
	 * stored in the /assets folder.
	 * @return  Returns an array of Strings, each of which represents
	 * a Beneficiary record.
	 */
	private String[] loadBeneficiaryData(String filename, String distrCtr, int beneficiaryType) {
		String[] data = null;
		
		File file = null;
		
// Finds don't have types in this project		
		
//		if (beneficiaryType == CommodityFind.TYPE_MCHN)
			file = new File(Environment.getExternalStorageDirectory() 
				+ "/" + DEFAULT_MCHN_DIRECTORY + "/" 
				+ filename);
//		else 
//			file = new File(Environment.getExternalStorageDirectory() 
//					+ "/" + DEFAULT_AGRI_DIRECTORY + "/" 
//					+ filename);
			

		BufferedReader br = null;
		String line = null;
		int k = 0;
		
		try {
			//InputStream iStream = this.getAssets().open("beneficiaries.txt");
			FileInputStream iStream = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(iStream));
			data = new String[MAX_BENEFICIARIES];
			line = br.readLine();
			//while (line != null && k < 1000)  {
			
// Commodities don't have types
			
//			if (beneficiaryType == CommodityFind.TYPE_MCHN) {
				
				// Reading from Beneficiare.csv and filter by distrCtr
				while (line != null)  {
					//				Log.i(TAG, line);
					if (line.length() > 0 && line.charAt(0) != '*')  {
						if (line.contains(distrCtr)) {
							data[k] = line;
							k++;
						}
					}
					line = br.readLine();
				}
//			} 
//		else {
//				
//				// Reading from Livelihood.csv and no filter
//				while (line != null)  {
//					//				Log.i(TAG, line);
//					if (line.length() > 0 && line.charAt(0) != '*')  {
////						if (!line.contains("No_dossier")) {
//						if (line.contains(distrCtr)) {						
//							data[k] = line;
//							k++;
//						}
//					}
//					line = br.readLine();
//				}	
//			}
		} catch (IOException e) {
			Log.e(TAG, "IO Exception,  file =   " + file);
			e.printStackTrace();
			String[] error = {"" + IO_EXCEPTION};
			return error;
		} catch (StringIndexOutOfBoundsException e) {
			Log.e(TAG, "Bad line?  " + line);
//			showDialog(STRING_EXCEPTION);
			e.printStackTrace();
			String[] error = {"" + STRING_EXCEPTION};
			return error;
		}
		String[] dossiers = new String[k];  // Create the actual size of array
		for (int i= 0; i < k; i++)
			dossiers[i] = data[i];
		return dossiers;
	}
	
	/**
	 * Creates a dialog to handle various alerts and announcements to the user.
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		Log.i(TAG, "onCreateDialog, id= " + id);
		switch (id) {
		case ZERO_BENEFICIARIES_READ:
			return new AlertDialog.Builder(this).setIcon(
					R.drawable.alert_dialog_icon).setTitle(mImportDataReport)
					.setPositiveButton(R.string.alert_dialog_ok,
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// User clicked OK so do some stuff
							finish();
						}
					}).create();		
		case DISTRIBUTION_SUMMARY:
			return new AlertDialog.Builder(this).setIcon(
					R.drawable.alert_dialog_icon).setTitle(mDistributionSummaryReport)
					.setItems(mSummaryItems, null)
					.setPositiveButton(R.string.alert_dialog_ok,
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// User clicked OK so do some stuff
							finish();
						}
					}).create();
		case SUMMARY_OF_IMPORT:
			return new AlertDialog.Builder(this).setIcon(
					R.drawable.alert_dialog_icon).setTitle(mImportDataReport)
					.setPositiveButton(R.string.alert_dialog_ok,
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// User clicked OK so do some stuff
							finish();
						}
					}).create();
		case SMS_REPORT:
			return new AlertDialog.Builder(this).setIcon(
					R.drawable.alert_dialog_icon).setTitle(mSmsReport)
					.setPositiveButton(R.string.alert_dialog_ok,
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// User clicked OK so do some stuff
							finish();
						}
					}).create();
		case IO_EXCEPTION:
			return new AlertDialog.Builder(this).setIcon(
					R.drawable.alert_dialog_icon).setTitle(getString(R.string.io_exc) + DEFAULT_COMMODITYLIST_FILE)
					.setPositiveButton(R.string.alert_dialog_ok,
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// User clicked OK so do some stuff
							finish();
						}
					}).create();
		case STRING_EXCEPTION:
			return new AlertDialog.Builder(this).setIcon(
					R.drawable.alert_dialog_icon).setTitle(getString(R.string.string_exc))
					.setPositiveButton(R.string.alert_dialog_ok,
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// User clicked OK so do some stuff
							finish();
						}
					}).create();
		case SEND_DIST_REP:
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
			final String phoneNumber = sp.getString(mContext.getString(R.string.smsPhoneKey),"");
			Log.i(TAG, "phonenumber = " + phoneNumber); 
			return new AlertDialog.Builder(this).setIcon(
					R.drawable.about2).setTitle(
//							"#: " + phoneNumber
//							+ "\n" + mAcdiVocaMsgs.size() 
//							+ " " + getString(R.string.send_dist_rep))
							mCommodityMsgs.size() + " " + getString(R.string.send_dist_rep2)
							+ " #: " + phoneNumber)
							.setPositiveButton(R.string.alert_dialog_ok,
									new DialogInterface.OnClickListener() {								
								public void onClick(DialogInterface dialog,
										int which) {
									CommoditySmsManager mgr = CommoditySmsManager.getInstance((Activity) mContext);
									mgr.sendMessages(mContext, mCommodityMsgs);
// There shouldn't be distribution stages									
//									AppControlManager.moveToNextDistributionStage(mContext);
									mSmsReport = "#: " + phoneNumber + "\n" + mCommodityMsgs.size();
									showDialog(SMS_REPORT);
									//finish();
								}
							}).setNegativeButton(R.string.alert_dialog_cancel,
									new DialogInterface.OnClickListener() {										
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
								}
							}).create();
		default:
			return null;
		}
	}
	
	
	/**
	 * Called just before the dialog is shown. Need to change title
	 * to reflect the current phone number. 
	 */
	@Override
	protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
		super.onPrepareDialog(id, dialog, args);
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
		String phoneNumber = sp.getString(mContext.getString(R.string.smsPhoneKey),"");
		Button needsabutton;
		AlertDialog d = (AlertDialog) dialog;
		Log.i(TAG, "phonenumber = " + phoneNumber); 
		switch (id) {
		case SEND_DIST_REP:
			Log.i(TAG, "onPrepareDialog id= " + id);
//			dialog.setTitle("#: " + phoneNumber
//					+ "\n" +  mAcdiVocaMsgs.size() 
//					+ " " + mContext.getString(R.string.send_dist_rep));
			d.setTitle(mCommodityMsgs.size() + " " + getString(R.string.send_dist_rep2)
			+ " #: " + phoneNumber);
			
			needsabutton = d.getButton(DialogInterface.BUTTON_POSITIVE);
			needsabutton.setText(R.string.alert_dialog_ok);
			needsabutton.invalidate();
			
			needsabutton = d.getButton(DialogInterface.BUTTON_NEGATIVE);
			needsabutton.setText(R.string.alert_dialog_cancel);
			needsabutton.invalidate();
			
			break;
			

		case SMS_REPORT:
			d.setTitle( mCommodityMsgs.size() + " " + getString(R.string.being_sent_to) +
			" # : " + phoneNumber);
			
			needsabutton = d.getButton(DialogInterface.BUTTON_POSITIVE);
			needsabutton.setText(R.string.alert_dialog_ok);
			needsabutton.invalidate();

			break;

		}
	}
	

	/**
	 * Class for handling data import.
	 */
	class ImportThreadHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == DONE) {
				mProgressDialog.dismiss();
				showDialog(SUMMARY_OF_IMPORT);
			} else if (msg.what == ZERO_BENEFICIARIES_READ) {
				mProgressDialog.dismiss();
				showDialog(ZERO_BENEFICIARIES_READ);
			}
			else if (msg.what == IO_EXCEPTION) {
				mProgressDialog.dismiss();
				showDialog(IO_EXCEPTION);
			}
			else if (msg.what == STRING_EXCEPTION) {
				mProgressDialog.dismiss();
				showDialog(STRING_EXCEPTION);
			}
		}
	}
	
	/**
	 * Thread to handle import of data from external file. 
	 *
	 */
	class ImportDataThread extends Thread {
		private Context mContext;
		private Handler mHandler;
		private String mFilename;
		private int mBeneficiaryType;
		
		public ImportDataThread(Context context, String filename, int beneficiaryType, Handler handler) {
			mHandler = handler;
			mContext = context;
			mFilename = filename;
			mBeneficiaryType = beneficiaryType;
		}
	
		@Override
		public void run() {
			int result = 0;
			
			result = importBeneficiaryDataToDb(mFilename, mBeneficiaryType);
			if (result == DONE)
				mHandler.sendEmptyMessage(CommodityAdminActivity.DONE);
			else if (result == IO_EXCEPTION)
				mHandler.sendEmptyMessage(CommodityAdminActivity.IO_EXCEPTION);
			else if (result == STRING_EXCEPTION)
				mHandler.sendEmptyMessage(CommodityAdminActivity.STRING_EXCEPTION);
			else if (result == ZERO_BENEFICIARIES_READ) 
				mHandler.sendEmptyMessage(CommodityAdminActivity.ZERO_BENEFICIARIES_READ);

		}
	}
}