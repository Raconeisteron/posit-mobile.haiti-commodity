package org.hfoss.posit.android.functionplugin.fileviewer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.hfoss.posit.android.R;
import org.hfoss.posit.android.api.plugin.commodity.CommodityFind;

// @see http://www.dreamincode.net/forums/topic/190013-creating-simple-file-chooser/
public class CommodityFilePickerActivity extends ListActivity {

	public static final String TAG = "CommodityFilePicker";
	public static final int ACTION_CHOOSER = 1;
	public static final int RESULT_OK = 1;
	public static final int RESULT_ERROR = 0;
	public static final String HOME_DIRECTORY = "/sdcard/commodity";
	public static final String HOME_DIRECTORY_MCHN = "/sdcard/commodity/commoditylists";
	public static final String HOME_DIRECTORY_AGRI = "/sdcard/acdivoca/markets";

	private File currentDir;
	private FileArrayAdapter adapter;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate()"); //Code added for error checking
		Intent intent = this.getIntent();
		 Bundle extras = intent.getExtras();
			if (extras == null) {
				return;
			}
		Log.i(TAG, "Problem with extras?"); //Code added for error checking
		String home = extras.getString("home");
		currentDir = new File(home);
		
//added default code:	
		currentDir = new File(HOME_DIRECTORY_MCHN);
		
		File files[] = currentDir.listFiles();
		List<String> datafiles = new ArrayList<String>();
		Log.i(TAG, currentDir.getName()); //Code added for error checking
		try {
			for (File ff: files) {
				if (ff.isFile()) {
					datafiles.add(ff.getName());
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "IO Exception");
			e.printStackTrace();
			Intent returnIntent = new Intent();
			setResult(RESULT_ERROR, returnIntent);
			finish();
		}
		
		if (datafiles.size() == 0) 
			setContentView(R.layout.commodity_list_files);

        adapter = new FileArrayAdapter(this, R.layout.commodity_list_files, datafiles );
        this.setListAdapter(adapter);
	}
	
	
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String filename = adapter.getItem(position);
	    Intent returnIntent = new Intent();
	    returnIntent.putExtra(Intent.ACTION_CHOOSER, filename);

		setResult(RESULT_OK, returnIntent);
		finish();
    }

	class FileArrayAdapter extends ArrayAdapter<String>{

		private Context c;
		private int id;
		private List<String>items;

		public FileArrayAdapter(Context context, int textViewResourceId,
				List<String> filenames) {
			super(context, textViewResourceId, filenames);
			c = context;
			id = textViewResourceId;
			items = filenames;
		}
		public String getItem(int i){
			return items.get(i);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.commodity_list_files_row, null);
			}
			final String filename = items.get(position);
			if (filename != null) {
				TextView t1 = (TextView) v.findViewById(R.id.filename);
				t1.setText(filename);
			}
			return v;
		}

	}



}
