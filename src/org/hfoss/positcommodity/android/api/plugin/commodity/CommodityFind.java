package org.hfoss.positcommodity.android.api.plugin.commodity;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hfoss.positcommodity.android.api.Find;
import org.hfoss.positcommodity.android.api.database.DbManager;
import org.hfoss.positcommodity.android.api.plugin.commodity.CommodityFind;
import org.hfoss.positcommodity.android.api.plugin.commodity.CommodityMessage;
import org.hfoss.positcommodity.android.api.plugin.commodity.CommoditySearchFilterActivity;
import org.hfoss.positcommodity.android.plugin.acdivoca.AcdiVocaFind;
import org.hfoss.positcommodity.android.plugin.acdivoca.AttributeManager;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;
@DatabaseTable(tableName = DbManager.FIND_TABLE_NAME)
public class CommodityFind extends Find {

	//The following will be code that requires refactoring
	
	private static final String TAG = "AcdiVocaFind";
	
	public static final String DOSSIER = AttributeManager.FINDS_DOSSIER;
	public static final String PROJECT_ID = "project_id";
	public static final String NAME = "name";
	


	
	
	private static int createFind(Dao<CommodityFind, Integer> dao, CommodityFind avFind) {
		int rows = 0;
		try {
			rows = dao.create(avFind);
			if (rows == 1) 
				Log.i(TAG, "Created beneficiary entry " + avFind.toString());
			else {
				Log.e(TAG, "Db Error creating beneficiary entry " + avFind.toString());
				rows = 0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rows;
	}
	
	

	
	//This code adds the database fields
	public static final String C_TAG = "tag";
	public static final String C_COMMODITY = "commodity";
	public static final String C_PRICE_1 = "price1";
	public static final String C_PRICE_2 = "price2";
	public static final String C_PRICE_3 = "price3";
	public static final String C_DATE = "date";
	public static final String C_MARKET = "market";
	public static final String C_SMSSTATUS = "smsstatus";
	public static final String C_Notes = "notes";
	
	@DatabaseField(columnName = C_TAG)
	protected int tag;
	@DatabaseField(columnName = C_COMMODITY)
	protected String commodity;
	@DatabaseField(columnName = C_PRICE_1)   
	protected float price1;
	@DatabaseField(columnName = C_PRICE_2)   
	protected float price2;
	@DatabaseField(columnName = C_PRICE_3)   
	protected float price3;
//	@DatabaseField(columnName = C_UNITS)   //Unit of measurement
	protected String units;
	@DatabaseField(columnName = C_DATE) 
	protected String date;
	@DatabaseField(columnName = C_MARKET)
	protected String market;
	@DatabaseField(columnName = C_SMSSTATUS)
	protected int smsStatus;
	@DatabaseField(columnName = C_Notes)
	protected String note;
	

	
	public CommodityFind() {
		// Necessary by ormlite
	}
	

	

	
	public static int addUpdateCommodities(Dao<CommodityFind, Integer> dao, String[] beneficiaries){
		return 0;
	}
	


	
	/**
	 * Creates the table for this class.
	 * 
	 * @param connectionSource
	 */
	public static void createTable(ConnectionSource connectionSource) {
		Log.i(TAG, "Creating CommodityFind table");
		try {
			TableUtils.createTable(connectionSource, CommodityFind.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	
	//Commodity Tracker database setters and getters
	public int getTag(){
		return tag;
	}
	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}	
	
	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	public float getPrice1() {
		return price1;
	}
	
	public void setPrice1(float price1) {
		this.price1 = price1;
	}
	
	public float getPrice2() {
		return price2;
	}
	
	public void setPrice2(float price2) {
		this.price2 = price2;
	}
	
	public float getPrice3() {
		return price3;
	}
	
	public void setPrice3(float price3) {
		this.price3 = price3;
	}

	public void setTag(int newTag){
		this.tag = newTag;
	}
//	public void setUnits(String units) {
//		this.units = units;
//	}
//	
//	public String getUnits(){
//		return units;
//	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	public String getDate(){
		return date;
	}
	
	public int getSMSStatus() {
		return smsStatus;
	}

	public void setSMSStatus(int status) {
		this.smsStatus = status;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String userNote) {
		this.note = userNote;
	}
	

	
	
	/**
	 * Deletes all rows from the Beneficiary Table.
	 * @return
	 */
	public static int clearTable(Dao<CommodityFind, Integer> dao) {
		Log.i(TAG, "Clearing Finds Table");
		int count = 0;
		try {
			DeleteBuilder<CommodityFind, Integer> deleteBuilder =  dao.deleteBuilder();
			// Delete all rows -- no where clause
			count = dao.delete(deleteBuilder.prepare());
		} catch (SQLException e) {
			Log.e(TAG, "SQL Exception " + e.getMessage());
			e.printStackTrace();
		}
		return count;
	}
	
	
	/**
	 * The Android date picker stores dates as 0..11. Weird.
	 * So we have to adjust dates input from data file.
	 * @param date
	 * @return
	 */
	@SuppressWarnings("finally")
	private static String translateDateForDatePicker(String date) {
		try {
			String[] yrmonday = date.split("/");
			date =  yrmonday[0] + "/" + (Integer.parseInt(yrmonday[1]) - 1) + "/" + yrmonday[2];
		} catch (Exception e) {
			Log.i(TAG, "Bad date = " + date + " " + e.getMessage());
			e.printStackTrace();
		} finally {
			return date;
		}
	}
	
	
	/**
	 * Retrieves a Find object by its Id
	 * @return
	 */
	public static CommodityFind fetchById (Dao<CommodityFind, Integer> dao, int id) {		
		Log.i(TAG, "Fetching message for id = " + id);
		CommodityFind avFind = null;
		try {
			avFind = dao.queryForId(id);
		} catch (SQLException e) {
			Log.e(TAG, "SQL Exception " + e.getMessage());
			e.printStackTrace();
		}
		return avFind;
	}
	


	
	/**
	 * Updates beneficiary table for bulk dossier numbers -- i.e. n1&n2&...&nM.
	 * Bulk messages are sent to record absentees at AcdiVoca distribution events.
	 * @param acdiVocaMsg
	 * @return
	 */
	
	//Needs to be implemented
	public static int updateMessageStatusForBulkMsg(Dao<CommodityFind, Integer> dao, CommodityMessage commodityMsg, int msgId, int msgStatus) {
//		String msg = commodityMsg.getSmsMessage();
//		Log.i(TAG, "Updating for bulk message = " + msg);
//		boolean result = false;
//		int rows = 0;
		int count = 0;
//		String dossiers[] = msg.split(AttributeManager.LIST_SEPARATOR);
//	
//		for (int k = 0; k < dossiers.length; k++) {			
//			try {
//				AcdiVocaFind avFind = CommodityFind.fetchByAttributeValue(dao, AcdiVocaFind.DOSSIER,  dossiers[k]);
//				if (avFind != null) {
//					avFind.message_status = msgStatus;
//					avFind.message_id = msgId;
//					rows = dao.update(avFind);
//					result = rows == 1;
//					if (result) {
//						Log.d(TAG, "Updated beneficiary id = " + avFind.getId() + " to message status = " + msgStatus);
//						++count;
//					}
//					else
//						Log.e(TAG, "Unable to update beneficiary id = " + avFind.getId() + " to message status = " + msgStatus);
//				}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		return count;
	}
	
	public String toLog(){
		float average = (float) ((price1+price2+price3)/3.0);		
		DecimalFormat dec = new DecimalFormat("########.##");
		StringBuilder sb = new StringBuilder();
		sb.append(tag).append(";");
		sb.append(date).append(";");
		sb.append(market).append(";");
		sb.append(commodity).append(";");
		sb.append(price1).append(";");
		sb.append(price2).append(";");
		sb.append(price3).append(";");
		sb.append(dec.format(average)).append(";");
		sb.append(note);
		return sb.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
//		sb.append(ORM_ID).append("=").append(id).append(",");
		sb.append(GUID).append("=").append(guid).append(",");
		sb.append(C_COMMODITY).append("=").append(commodity).append(",");
		sb.append(C_DATE).append("=").append(date).append(",");
		sb.append(C_MARKET).append("=").append(market).append(",");
		sb.append(C_PRICE_1).append("=").append(price1).append(",");
		sb.append(C_PRICE_2).append("=").append(price2).append(",");
		sb.append(C_PRICE_3).append("=").append(price3).append(",");
		sb.append(C_Notes).append("=").append(note).append(",");
//		sb.append(NAME).append("=").append(name).append(",");
//		sb.append(LATITUDE).append("=").append(latitude).append(",");
//		sb.append(LONGITUDE).append("=").append(longitude).append(",");
//		if (time != null)
//			sb.append(TIME).append("=").append(time.toString()).append(",");
//		else
//			sb.append(TIME).append("=").append("").append(",");
//		if (modify_time != null)
//			sb.append(MODIFY_TIME).append("=").append(modify_time.toString())
//					.append(",");
//		else
//			sb.append(MODIFY_TIME).append("=").append("").append(",");
//		sb.append(REVISION).append("=").append(revision).append(",");
//		sb.append(IS_ADHOC).append("=").append(is_adhoc).append(",");
//		sb.append(ACTION).append("=").append(action).append(",");
//		sb.append(DELETED).append("=").append(deleted).append(",");
		
		return sb.toString();
	}

}
