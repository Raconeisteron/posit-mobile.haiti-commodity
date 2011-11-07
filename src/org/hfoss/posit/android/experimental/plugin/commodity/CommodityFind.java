package org.hfoss.posit.android.experimental.plugin.commodity;

import java.sql.Date;
import java.sql.SQLException;

import org.hfoss.posit.android.experimental.api.Find;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class CommodityFind extends Find {

	public static final String SYRINGES_IN = "syringes_in";
	public static final String SYRINGES_OUT = "syringes_out";
	public static final String IS_NEW = "is_new";

	@DatabaseField(columnName = SYRINGES_IN)
	protected int syringesIn;
	@DatabaseField(columnName = SYRINGES_OUT)
	protected int syringesOut;
	@DatabaseField(columnName = IS_NEW)
	protected boolean isNew;
	
	//This code adds the database fields
	public static final String C_COMMODITY = "commodity";
	public static final String C_PRICE_1 = "price1";
	public static final String C_PRICE_2 = "price2";
	public static final String C_PRICE_3 = "price3";
	public static final String C_UNITS = "units";
	public static final String C_DATE = "date";
	
	@DatabaseField(columnName = C_COMMODITY)
	protected String commodity;
	@DatabaseField(columnName = C_PRICE_1)   
	protected float price1;
	@DatabaseField(columnName = C_PRICE_2)   
	protected float price2;
	@DatabaseField(columnName = C_PRICE_3)   
	protected float price3;
	@DatabaseField(columnName = C_UNITS)   //Unit of measurement
	protected String units;
	@DatabaseField(columnName = C_DATE) 
	protected String date;

	public CommodityFind() {
		// Necessary by ormlite
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
	
	public int getSyringesIn() {
		return syringesIn;
	}

	public void setSyringesIn(int syringesIn) {
		this.syringesIn = syringesIn;
	}

	public int getSyringesOut() {
		return syringesOut;
	}

	public void setSyringesOut(int syringesOut) {
		this.syringesOut = syringesOut;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	
	//Commodity Tracker database setters and getters
	
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

	public void setUnits(String units) {
		this.units = units;
	}
	
	public String getUnits(){
		return units;
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	public String getDate(){
		return date;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(ORM_ID).append("=").append(id).append(",");
		sb.append(GUID).append("=").append(guid).append(",");
		sb.append(NAME).append("=").append(name).append(",");
		sb.append(LATITUDE).append("=").append(latitude).append(",");
		sb.append(LONGITUDE).append("=").append(longitude).append(",");
		if (time != null)
			sb.append(TIME).append("=").append(time.toString()).append(",");
		else
			sb.append(TIME).append("=").append("").append(",");
		if (modify_time != null)
			sb.append(MODIFY_TIME).append("=").append(modify_time.toString())
					.append(",");
		else
			sb.append(MODIFY_TIME).append("=").append("").append(",");
		sb.append(REVISION).append("=").append(revision).append(",");
		sb.append(IS_ADHOC).append("=").append(is_adhoc).append(",");
		sb.append(ACTION).append("=").append(action).append(",");
		sb.append(DELETED).append("=").append(deleted).append(",");
//		sb.append(SYRINGES_IN).append("=").append(syringesIn).append(",");
//		sb.append(SYRINGES_OUT).append("=").append(syringesOut).append(",");
		return sb.toString();
	}

}
