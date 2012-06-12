package org.hfoss.positcommodity.android.api.plugin.commodity;

import java.util.ArrayList;
import java.util.List;

import org.hfoss.positcommodity.android.R;
import org.hfoss.positcommodity.android.api.Find;
import org.hfoss.positcommodity.android.api.activity.ListFindsActivity;
import org.hfoss.positcommodity.android.api.activity.MapFindsActivity;
import org.hfoss.positcommodity.android.api.activity.SettingsActivity;
import org.hfoss.positcommodity.android.api.database.DbHelper;
import org.hfoss.positcommodity.android.api.database.DbManager;
import org.hfoss.positcommodity.android.api.plugin.FindPluginManager;
import org.hfoss.positcommodity.android.api.plugin.FunctionPlugin;
import org.hfoss.positcommodity.android.functionplugin.commoditysms.CommoditySMSSyncActivity;
import org.hfoss.positcommodity.android.functionplugin.commoditysms.SelectFind;
import org.hfoss.positcommodity.android.functionplugin.commoditysms.SelectFindListAdapter;
import org.hfoss.positcommodity.android.functionplugin.log.LogFindsActivity;
import org.hfoss.positcommodity.android.sync.SyncActivity;
import org.hfoss.positcommodity.android.sync.SyncCommoditySMS;
import org.hfoss.positcommodity.android.functionplugin.fileviewer.FileViewActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemSelectedListener;
//import android.widget.CheckBox;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CommodityListFindsActivity extends ListFindsActivity /*implements OnItemSelectedListener*/ {

	private static final String TAG = "CommodityListFindsActivity";
	private final CommodityListFindsActivity mActivity = this;
	private static Boolean flag = false; // for checking and unchecking checkboxes
	private static Boolean menuFlag = false; // for toggling menu item
	private final Handler mHandler = new Handler();
	private SyncCommoditySMS mSyncService = null;
	private static final int CONFIRM_PHONE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
	}

	//	@Override
	//	public void onListItemClick(ListView parent, View view, int position, long id) {
	//		Log.i(TAG, "Does the listerner see anything?");
	//		Intent intent = new Intent(parent.getContext(),
	//				FindPluginManager.mFindPlugin.getmFindActivityClass());
	//		TextView tv = (TextView) view.findViewById(R.id.id);
	//		int ormId = Integer.parseInt((String) tv.getText());
	//		intent.putExtra(Find.ORM_ID, ormId);
	//		intent.setAction(Intent.ACTION_EDIT);
	//		Log.i(TAG, "Listeners set");
	//		startActivity(intent);
	//	}
	/**
	 * RoModel method to get the RowModel Object out of the adapter
	 * @param position is the position of the object on the view
	 * @return the RowModel object
	 */
	private RowModel getModel(int position) {
		return ((CheckAdapter)getListAdapter()).items.get(position);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		flag = false;
	}


	/**
	 * Sets up a custom list adapter specific to Commodity finds.
	 */
	@Override
	protected ListAdapter setUpAdapter() {

		List<? extends Find> list = this.getHelper().getAllFinds();
		//	Log.i(TAG, "&&&&&&&&&"+list.get(0).toString()+ "SIZEEEEEE :"+ list.size());
		int resId = getResources().getIdentifier(FindPluginManager.mFindPlugin.mListFindLayout,
				"layout", getPackageName());
		if (list != null){
			List<RowModel> rList = new ArrayList<RowModel>();
			DbManager dbHelper = DbHelper.getDbManager(mActivity);
			CommodityFind oiFind;
			int x = 0;
			for(int i = 0; i<list.size(); i++){
				Find f = list.get(i);
				String guid = f.getGuid();				
				oiFind = (CommodityFind)dbHelper.getFindByGuid(guid);
				if (oiFind.smsStatus != 1){
					RowModel rm = new RowModel(list.get(i));
					rList.add(x, rm);
					x++;
				}
			}

			CheckAdapter adapter = new CheckAdapter(this, resId, rList);
			//Log.i(TAG, "********"+adapter.items.get(0).rfind.toString());
			return adapter;
		}
		else{
			List<RowModel> rList = new ArrayList<RowModel>();
			CheckAdapter adapter = new CheckAdapter(this, resId, rList);
			return adapter;
		}
	}


	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Log.i(TAG, "onMenuitemSelected()");
		switch (item.getItemId()) {
		case R.id.delete_finds_menu_item:
			Log.i(TAG, "Delete all finds menu item"); 
			showDialog(CONFIRM_DELETE_DIALOG);
			break;
		case R.id.select_all_items:
			if(menuFlag == false){
				Log.i(TAG, "Select all items");
				CheckAdapter adapter =  (CheckAdapter)getListAdapter();
				flag = true;
				adapter.notifyDataSetChanged();
				menuFlag = !menuFlag;
				break;
			}	
			else{	
				Log.i(TAG, "Select None items");
				CheckAdapter adapter = (CheckAdapter)getListAdapter();
				flag = false;
				adapter.notifyDataSetChanged();
				menuFlag = !menuFlag;
				break;
			}
		case R.id.send_selected:	
			//sendSelected(findViewById(R.layout.commodity_list_row));
			showDialog(CONFIRM_PHONE);
			break;
		}
		return true;
	}


	/**
	 * Send out the selected items
	 * @param view
	 */
	protected void sendSelected(View view) {
		List<RowModel> rmod = ((CheckAdapter)getListAdapter()).items;
		List<String> rgid = new ArrayList<String>();
		int x = 0;
		for (int i=0; i< rmod.size(); i++){
			if (rmod.get(i).isChecked == true){
				rgid.add(rmod.get(i).rfind.getGuid());
				x++;
			}		
		}
		String[] guids = new String[x];
		for(int i=0; i<rgid.size(); i++){
			guids[i] = rgid.get(i);
		}
		Log.i(TAG, "GETTING THE RIGHT SENDING STUFFS : "+ guids.length);
		if (x > 0) {
			Toast.makeText(this, R.string.bt_synching, Toast.LENGTH_SHORT).show();
			String smsPref = PreferenceManager.getDefaultSharedPreferences(this).getString(this.getString(R.string.smsPhoneKey), "");
			Log.i(TAG, "What we have : "+ smsPref);
			mSyncService = new SyncCommoditySMS(this, mHandler);
			mSyncService.sendFinds(smsPref, (String[])guids);
			Intent intent = new Intent(this, LogFindsActivity.class);
			startActivity(intent);
			Toast.makeText(this, R.string.bt_synching_complete, Toast.LENGTH_SHORT).show();
			finish();
		}
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case CONFIRM_DELETE_DIALOG:
			return new AlertDialog.Builder(this).setIcon(R.drawable.alert_dialog_icon)
					.setTitle(R.string.confirm_delete)
					.setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							// User clicked OK so do some stuff
							if (deleteAllFind()) {
								finish();
							}
						}
					}).setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							// User clicked cancel so do nothing
						}
					}).create();
		case CONFIRM_PHONE:
			String smsPref = PreferenceManager.getDefaultSharedPreferences(this).getString(this.getString(R.string.smsPhoneKey), "");
			String title = "Phone: " + smsPref;
			return new AlertDialog.Builder(this).setTitle(title)
					.setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							sendSelected(findViewById(R.layout.commodity_list_row));
						}
					}).setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
						
						}
					}).create();
		default:
			return null;
		}
	}
	
	

	/**
	 * Creates the menus for this activity.
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menuFlag = false;
		Log.i(TAG, "OnCreateOptionsMenu");
		MenuInflater inflater = getMenuInflater();
		if (mListMenuPlugins.size() > 0) {
			for (FunctionPlugin plugin: mListMenuPlugins) {
				String check = plugin.getmMenuTitle();
				MenuItem item = menu.add(plugin.getmMenuTitle());
				int id = getResources().getIdentifier(
						plugin.getmMenuIcon(), "drawable", "org.hfoss.positcommodity.android");
				item.setIcon(id);

			}
		}
		inflater.inflate(R.menu.list_finds_menu, menu);
		return true;
	}

	/**
	 * Handles activity when the menu panel is opened.
	 * @see android.app.Activity#onMenuOpened(android.view.Menu)
	 */
	public boolean onMenuOpened(int featureId, Menu menu){
		Log.i(TAG, "onMenuOpend : "+menuFlag.toString());
		if (menuFlag == true){
			//menu.getItem(3).setTitle("Select None");
			menu.getItem(3).setTitle(R.string.select_none);
		}
		else{
			//menu.getItem(3).setTitle("Select All");
			menu.getItem(3).setTitle(R.string.select_all);
		}
		return true;
	}

	/**
	 * Adapter for displaying finds, extends FindsListAdapter to 
	 * take care of displaying the extra fields in CommodityFind. 
	 */
	class CheckAdapter extends FindsListAdapter {
		Activity context;
		protected List<RowModel> items;

		CheckAdapter(Activity context, int id, List list) {
			super(context, id, list);
			//			Log.i(TAG, "BUILT THE CHECK ADAPTER"+list.get(0).toString()+ " SIZE: "+ list.size());
			this.context=context;
			this.items = list;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View row=convertView;
			ViewWrapper wrapper;
			CheckBox cb;
			RowModel rmfind = items.get(position);
			if (row==null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = vi.inflate(R.layout.commodity_list_row, null);
				wrapper=new ViewWrapper(row);
				row.setTag(wrapper);
				cb=wrapper.getCheckBox();
				CompoundButton.OnCheckedChangeListener l=new CompoundButton.OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						Integer myPosition=(Integer)buttonView.getTag();
						RowModel model= getModel(myPosition);
						model.isChecked=isChecked;
					}
				};
				cb.setOnCheckedChangeListener(l);
			}
			else {
				wrapper=(ViewWrapper)row.getTag();
				cb=wrapper.getCheckBox();
			}
			// display textView items on screen
			CommodityFind find = (CommodityFind)rmfind.rfind;
			if (find != null) {
				TextView tv = (TextView) row.findViewById(R.id.latitude);
				tv.setText(String.valueOf(find.getLatitude()));
				tv = (TextView) row.findViewById(R.id.longitude);
				tv.setText(String.valueOf(find.getLongitude()));
				tv = (TextView) row.findViewById(R.id.commodity_name);
				tv.setText(String.valueOf(find.getCommodity()));
				tv = (TextView) row.findViewById(R.id.market_name);
				tv.setText(String.valueOf(find.getMarket()));
				tv = (TextView) row.findViewById(R.id.longitude);
				tv = (TextView) row.findViewById(R.id.id);
				tv.setText(Integer.toString(find.getId()));
				tv = (TextView) row.findViewById(R.id.price1);
				tv.setText(Float.toString(find.getPrice1())+",   ");
				tv = (TextView) row.findViewById(R.id.price2);
				tv.setText(Float.toString(find.getPrice2())+",   ");
				tv = (TextView) row.findViewById(R.id.price3);
				tv.setText(Float.toString(find.getPrice3()));
			}
			cb.setTag(new Integer(position));
			cb.setChecked(flag);
			return(row);
		}
	}
	/*
	 * RowModel Object created to associate the CheckBoxes and Finds
	 */
	class RowModel {
		Find rfind;
		boolean isChecked=false;

		RowModel(Find cfind) {
			this.rfind = cfind;
		}



		public String toString() {
			return rfind.toString();
		}
	}
	/*
	 * ViewWrapper class for the checkBoxes
	 */
	class ViewWrapper {
		View base;
		CheckBox cb=null;

		ViewWrapper(View base) {
			this.base=base;
		}

		CheckBox getCheckBox() {
			if (cb==null) {
				cb=(CheckBox)base.findViewById(R.id.checkBox1);
			}

			return(cb);
		}
	}
}
