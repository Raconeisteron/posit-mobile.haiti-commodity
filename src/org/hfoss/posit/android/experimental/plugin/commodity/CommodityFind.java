package org.hfoss.posit.android.experimental.plugin.commodity;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hfoss.posit.android.experimental.api.Find;
import org.hfoss.posit.android.experimental.api.database.DbManager;
import org.hfoss.posit.android.experimental.plugin.acdivoca.AcdiVocaFind;
import org.hfoss.posit.android.experimental.plugin.acdivoca.AttributeManager;
import org.hfoss.posit.android.experimental.plugin.commodity.CommodityFind;
import org.hfoss.posit.android.experimental.plugin.commodity.CommodityMessage;
import org.hfoss.posit.android.experimental.plugin.commodity.CommodityAttributeManager;
import org.hfoss.posit.android.experimental.plugin.commodity.CommoditySearchFilterActivity;

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
	
	public static final String MESSAGE_ID = CommodityAttributeManager.FINDS_MESSAGE_ID;
	public static final String MESSAGE_STATUS = CommodityAttributeManager.FINDS_MESSAGE_STATUS;

	@DatabaseField(columnName = MESSAGE_ID) int message_id;
	@DatabaseField(columnName = MESSAGE_STATUS) int message_status;
	public static final String TYPE = CommodityAttributeManager.FINDS_TYPE;  
	
	public CommodityFind() {
		// Necessary by ormlite
	}
	
	/**
	 * Returns an array of AcdiVocaMessages for new or updated beneficiaries. 
	 * Fetches the beneficiary records from the Db and converts the column names
	 * and their respective values to abbreviated attribute-value pairs.
	 * @param filter
	 * @param order_by
	 * @return
	 */
	public static ArrayList<CommodityMessage> constructMessages(Dao<CommodityFind, Integer> dao, int filter, String distrCtr) {
		Log.i(TAG, "Creating messages for beneficiaries");

		List<CommodityFind> list = CommodityFind.fetchAllByMessageStatus(dao, filter, distrCtr);

		ArrayList<CommodityMessage> CommodityMsgs = new ArrayList<CommodityMessage>();
		if (list != null) {
			Log.i(TAG,"created MessagesForBeneficiaries " +  " count=" + list.size() + " filter= " + filter);
		
		// Construct the messages and return as a ArrayList
			Iterator<CommodityFind> it = list.iterator();

			while (it.hasNext()) {
				CommodityFind avFind = it.next();   // Process the next beneficiary
				CommodityMessage avMessage = avFind.toSmsMessage();
				CommodityMsgs.add(avMessage);
			}
		}
		return CommodityMsgs;		
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
	
	
// Turning this into something else, to use with Markets and Commodity names.	
//	/**
//	 * Inserts an array of beneficiaries input from AcdiVoca data file.
//	 * NOTE:  The Android date picker stores months as 0..11, so
//	 *  we have to adjust dates.
//	 * @param beneficiaries
//	 * @return
//	 */
//	public static int addAgriBeneficiaries(Dao<AcdiVocaFind, Integer> dao, String[] beneficiaries) {
//		Log.i(TAG, "Adding " + beneficiaries.length + " AGRI beneficiaries");
//		String fields[] = null;
//		int count = 0;
////		int result = 0;
//		
//		AcdiVocaFind avFind = null;
//		for (int k = 0; k < beneficiaries.length; k++) {
//			avFind = new AcdiVocaFind();
//
//			fields = beneficiaries[k].split(AttributeManager.PAIRS_SEPARATOR);
//			avFind.type = TYPE_AGRI;
//			avFind.status = STATUS_UPDATE;
//			avFind.dossier = fields[AGRI_FIELD_DOSSIER];
//			avFind.lastname = fields[AGRI_FIELD_LASTNAME];
//			avFind.firstname =  fields[AGRI_FIELD_FIRSTNAME];
//			avFind.address = fields[AGRI_FIELD_LOCALITY];
//			String adjustedDate = translateDateForDatePicker(fields[AGRI_FIELD_BIRTH_DATE]);
//			avFind.dob = adjustedDate;
//			String adjustedSex = translateSexData(fields[AGRI_FIELD_SEX]);
//			avFind.sex = adjustedSex;
//			String adjustedCategory = translateCategoryData(fields[AGRI_FIELD_CATEGORY]);
//			avFind.beneficiary_category = adjustedCategory;
//			avFind.household_size = fields[AGRI_FIELD_NUM_PERSONS];
//			
//			count += createFind(dao, avFind);
//
////			try {
////				result = dao.create(avFind);
////				if (result == 1) 
////					++count;
////				else 
////					Log.e(TAG, "Error creating beneficiary entry " + avFind.toString());
////			} catch (SQLException e) {
////				e.printStackTrace();
////			}
//		}
//		Log.i(TAG, "Inserted to Db " + count + " Beneficiaries");
//		return count;
//	}
	
	
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
	 * Retrieves selected beneficiaries from table. Retrieves those beneficiaries for whom 
	 * SMS messages will be sent.  For
	 * NEW beneficiaries all beneficiaries whose messages are UNSENT are returned.
	 * For UPDATE beneficiaries only those for whom  there's a change in STATUS
	 * are returned.
	 * @param filter
	 * @param order_by
	 * @return
	 */
	
	//Note: code that caused conflicts was commented out
	
	public static List<CommodityFind> fetchAllByMessageStatus(Dao<CommodityFind, Integer> dao,
			int filter, String distrCtr) {
	Log.i(TAG, "Fetching beneficiaries by filter = " + filter);
	List<CommodityFind> list = null;
	try {
		QueryBuilder<CommodityFind, Integer> queryBuilder = dao.queryBuilder();
		
		Where<CommodityFind, Integer> where = queryBuilder.where();
		if (filter == CommoditySearchFilterActivity.RESULT_SELECT_NEW) {
//			where.eq(CommodityFind.STATUS, CommodityFind.STATUS_NEW);
			where.and();
			where.eq( CommodityFind.MESSAGE_STATUS, CommodityMessage.MESSAGE_STATUS_UNSENT);
		} else if (filter == CommoditySearchFilterActivity.RESULT_SELECT_UPDATE) {
//			where.eq(CommodityFind.STATUS, CommodityFind.STATUS_UPDATE);
			where.and();
			where.eq(CommodityFind.MESSAGE_STATUS, CommodityMessage.MESSAGE_STATUS_UNSENT);
			where.and();
//			where.eq(CommodityFind.DISTRIBUTION_POST, distrCtr);
			where.and();
//			where.eq(CommodityFind.Q_CHANGE, true);
		}
		PreparedQuery<CommodityFind> preparedQuery = queryBuilder.prepare();
		list = dao.query(preparedQuery);
	} catch (SQLException e) {
		Log.e(TAG, "SQL Exception " + e.getMessage());
		e.printStackTrace();
	}
	return list;
}
	
	
	/**
	 * Updates Beneficiary table for processed message.
	 * @param beneficiary_id, the row_id
	 * @param msg_id, the message's row_id
	 * @param msgStatus, one of PENDING, SENT, etc.
	 * @return
	 */
	public static boolean updateMessageStatus(Dao<CommodityFind, Integer> dao, int beneficiary_id, int msg_id, int msgStatus) {
		Log.i(TAG, "Updating beneficiary = " + beneficiary_id + " for message " + msg_id + " status=" + msgStatus);

		CommodityFind avFind = null;
		int rows = 0;
		boolean result = false;
		try {
			avFind = dao.queryForId(beneficiary_id);  // Retrieve the beneficiary
			if (avFind != null) {
				avFind.message_status = msgStatus;
				avFind.message_id = msg_id;
				rows = dao.update(avFind);
				result = rows == 1;
			} else {
				Log.e(TAG, "Unable to retrieve beneficiary id = " + beneficiary_id ); 
			}
		} catch (SQLException e) {
			Log.e(TAG, "SQL Exception " + e.getMessage());
			e.printStackTrace();
		}
		if (result) 
			Log.d(TAG, "Updated beneficiary id = " + beneficiary_id + " for message " + msg_id + " status=" + msgStatus); 
		else
			Log.e(TAG, "Unable to update beneficiary id = " + beneficiary_id + " to message status = " + msgStatus);
		return result;
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
	
	/**
	 * Returns a representation of this Find in a highly compressed
	 * Sms (Text) message.
	 * @return
	 */
	public CommodityMessage toSmsMessage() {
		int msg_id = CommodityMessage.UNKNOWN_ID;
		int beneficiary_id = id;   //AcdiVocaDbHelper.UNKNOWN_ID;
//		int beneficiary_status = -1;
//		int message_status = -1;
		String statusStr = "";
		
		String rawMessage = toString();
		String smsMessage = "";
//		if (status == STATUS_UPDATE) 
//			smsMessage = toSmsUpdateMessage();
//		else if (status == STATUS_NEW && type == TYPE_MCHN)
//			smsMessage = toSmsNewMchnMessage();
//		else if (status == STATUS_NEW && type == TYPE_AGRI)
//			smsMessage = toSmsNewAgriMessage();

		// Create a header (length and status) to message
		String msgHeader = "MsgId:" + msg_id + ", Len:" + smsMessage.length() +  ", " + statusStr 
		+ " Bid = " + beneficiary_id;

		return new CommodityMessage(msg_id, 
				beneficiary_id, 
				CommodityMessage.MESSAGE_STATUS_UNSENT,
				rawMessage, smsMessage, msgHeader,!CommodityMessage.EXISTING);
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
