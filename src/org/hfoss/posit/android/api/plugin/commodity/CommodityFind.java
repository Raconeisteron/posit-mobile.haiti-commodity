package org.hfoss.posit.android.api.plugin.commodity;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hfoss.posit.android.api.Find;
import org.hfoss.posit.android.api.database.DbManager;
import org.hfoss.posit.android.plugin.acdivoca.AcdiVocaFind;
import org.hfoss.posit.android.plugin.acdivoca.AttributeManager;
import org.hfoss.posit.android.api.plugin.commodity.CommodityFind;
import org.hfoss.posit.android.api.plugin.commodity.CommodityMessage;
import org.hfoss.posit.android.api.plugin.commodity.CommodityAttributeManager;
import org.hfoss.posit.android.api.plugin.commodity.CommoditySearchFilterActivity;

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
	
//	public static final String TYPE = AttributeManager.FINDS_TYPE;    
	public static final int TYPE_MCHN = 0;
	public static final int TYPE_AGRI = 1;
	public static final int TYPE_BOTH = 2;
	public static final String[] FIND_TYPE_STRINGS = {"MCHN", "AGRI", "BOTH"};  // For display purpose

	public static final String STATUS = AttributeManager.FINDS_STATUS;
	public static final int STATUS_NEW = 0;      // New registration, no Dossier ID
	public static final int STATUS_UPDATE = 1;   // Update, imported from TBS, with Dossier ID
	public static final int STATUS_DONTCARE = -1;  
	public static final String[] FIND_STATUS_STRINGS = {"New", "Update"};  // For display purpose

//	public static final String MESSAGE_ID = AttributeManager.FINDS_MESSAGE_ID;
//	public static final String MESSAGE_STATUS = AttributeManager.FINDS_MESSAGE_STATUS;

	public static final String FIRSTNAME = AttributeManager.FINDS_FIRSTNAME;
	public static final String LASTNAME = AttributeManager.FINDS_LASTNAME;

	public static final String ADDRESS = AttributeManager.FINDS_ADDRESS;
	public static final String DOB = AttributeManager.FINDS_DOB;
	public static final String SEX = AttributeManager.FINDS_SEX;
	public static final String AGE = "age";

	public static final String BENEFICIARY_CATEGORY = AttributeManager.FINDS_BENEFICIARY_CATEGORY;
	public static final String HOUSEHOLD_SIZE = AttributeManager.FINDS_HOUSEHOLD_SIZE;

	public static final String DISTRIBUTION_POST = AttributeManager.FINDS_DISTRIBUTION_POST;
	public static final String Q_MOTHER_LEADER = AttributeManager.FINDS_Q_MOTHER_LEADER; // "mother_leader";
	public static final String Q_VISIT_MOTHER_LEADER = AttributeManager.FINDS_Q_VISIT_MOTHER_LEADER; // "visit_mother_leader";
	public static final String Q_PARTICIPATING_AGRI = AttributeManager.FINDS_Q_PARTICIPATING_AGRI; // "pariticipating_agri";
	public static final String Q_RELATIVE_AGRI = AttributeManager.FINDS_Q_RELATIVE_AGRI; // "pariticipating_agri";
	public static final String Q_PARTICIPATING_BENE = AttributeManager.FINDS_Q_PARTICIPATING_BENE; // "pariticipating_agri";
	public static final String Q_RELATIVE_BENE = AttributeManager.FINDS_Q_RELATIVE_BENE; // "pariticipating_agri";
	
	public static final String NAME_AGRI_PARTICIPANT = AttributeManager.FINDS_NAME_AGRI_PARTICIPANT; // "name_agri_paricipant";

	public static final String ZERO = "0";
	public static final String ONE = "1";

	// For the agriculture registration form
	public static final String LAND_AMOUNT = AttributeManager.FINDS_LAND_AMOUNT; // "amount_of_land";	
	public static final String IS_FARMER = AttributeManager.FINDS_IS_FARMER; //  "is_farmer";
	public static final String IS_MUSO = AttributeManager.FINDS_IS_MUSO;  // "is_MUSO";
	public static final String IS_RANCHER = AttributeManager.FINDS_IS_RANCHER;  //  "is_rancher";
	public static final String IS_STOREOWN = AttributeManager.FINDS_IS_STOREOWN; //  "is_store_owner";
	public static final String IS_FISHER = AttributeManager.FINDS_IS_FISHER;  // "is_fisher";
	public static final String IS_OTHER = AttributeManager.FINDS_IS_OTHER;  // "is_other";
	public static final String IS_ARTISAN = AttributeManager.FINDS_IS_ARTISAN; // "is_artisan";
	
	public static final String HAVE_VEGE = AttributeManager.FINDS_HAVE_VEGE; //  "have_vege";
	public static final String HAVE_CEREAL = AttributeManager.FINDS_HAVE_CEREAL;  //  "have_cereal";
	public static final String HAVE_TUBER = AttributeManager.FINDS_HAVE_TUBER;  // "have_tuber";
	public static final String HAVE_TREE = AttributeManager.FINDS_HAVE_TREE; // "have_tree";
	public static final String HAVE_GRAFTING = AttributeManager.FINDS_HAVE_GRAFTING; // "have_grafting";
	public static final String HAVE_HOUE = AttributeManager.FINDS_HAVE_HOUE;  //  "have_houe";
	public static final String HAVE_PIOCHE = AttributeManager.FINDS_HAVE_PIOCHE;  // "have_pioche";
	public static final String HAVE_BROUETTE = AttributeManager.FINDS_HAVE_BROUETTE; // "have_brouette";
	public static final String HAVE_MACHETTE = AttributeManager.FINDS_HAVE_MACHETTE; //  "have_machette";
	public static final String HAVE_SERPETTE = AttributeManager.FINDS_HAVE_SERPETTE;  // "have_serpette";
	public static final String HAVE_PELLE = AttributeManager.FINDS_HAVE_PELLE;  // "have_pelle";
	public static final String HAVE_BARREAMINES = AttributeManager.FINDS_HAVE_BARREAMINES; // "have_barreamines";
	public static final String RELATIVE_1 = AttributeManager.FINDS_RELATIVE_1;  // "relative_1";
	public static final String RELATIVE_2 = AttributeManager.FINDS_RELATIVE_2;  // "relative_2";
	public static final String HAVE_COFFEE = AttributeManager.FINDS_HAVE_COFFEE; //  "have_vege";

	public static final String PARTNER_FAO = AttributeManager.FINDS_PARTNER_FAO;// "partner_fao";
	public static final String PARTNER_SAVE = AttributeManager.FINDS_PARTNER_SAVE;// "partner_save";
	public static final String PARTNER_CROSE = AttributeManager.FINDS_PARTNER_CROSE;// "partner_crose";
	public static final String PARTNER_PLAN = AttributeManager.FINDS_PARTNER_PLAN;// "partner_plan";
	public static final String PARTNER_MARDNR = AttributeManager.FINDS_PARTNER_MARDNR;// "partner_mardnr";
	public static final String PARTNER_OTHER = AttributeManager.FINDS_PARTNER_OTHER;// "partner_other";
	
	public static final String MALNOURISHED = AttributeManager.FINDS_MALNOURISHED;  // "MALNOURISHED";
	public static final String PREVENTION = AttributeManager.FINDS_PREVENTION;     // "PREVENTION";
	public static final String EXPECTING = AttributeManager.FINDS_EXPECTING;   // "EXPECTING";
	public static final String NURSING = AttributeManager.FINDS_NURSING;      // "NURSING";
	
    public static final String MALE = AttributeManager.FINDS_MALE;          // "MALE";
    public static final String FEMALE = AttributeManager.FINDS_FEMALE;        // "FEMALE";
    public static final String YES = AttributeManager.FINDS_YES;           // "YES";
    public static final String NO = AttributeManager.FINDS_NO;            // "NO";
    public static final String TRUE = AttributeManager.FINDS_TRUE;       // "TRUE";
    public static final String FALSE = AttributeManager.FINDS_FALSE;      // "FALSE";
    public static final String COMMUNE_SECTION = AttributeManager.LONG_COMMUNE_SECTION;
    
	public static final String Q_PRESENT = AttributeManager.FINDS_Q_PRESENT;   // "Present";
	public static final String Q_TRANSFER = AttributeManager.FINDS_Q_TRANSFER;   //"Transfer";
	public static final String Q_MODIFICATION = AttributeManager.FINDS_Q_MODIFICATIONS;  // "Modifications";
	public static final String MONTHS_REMAINING = "MonthsRemaining";
	public static final String Q_CHANGE = AttributeManager.FINDS_Q_CHANGE;   //"ChangeInStatus";   // Added to incorporated changes to beneficiary type
	public static final String CHANGE_TYPE = AttributeManager.FINDS_CHANGE_TYPE;   //"ChangeType";


	// Fields for reading the beneficiaries.txt file. The numbers correspond to
	// the columns.  These might need to be changed.
	// Here's the file header line (line 1)
	//	*No dossier,Nom,Prenom,Section Communale,Localite beneficiaire,Date entree,Date naissance,Sexe,Categorie,Poste distribution,
	//	068MP-FAT, Balthazar,Denisana,Mapou,Saint Michel,2010/08/03,1947/12/31, F,Enfant Prevention,Dispensaire Mapou,
	private static final int FIELD_DOSSIER = 0;
	private static final int FIELD_LASTNAME = 1;
	private static final int FIELD_FIRSTNAME = 2;
	private static final int FIELD_SECTION = 3;
	private static final int FIELD_LOCALITY = 4;
	private static final int FIELD_ENTRY_DATE = 5;
	private static final int FIELD_BIRTH_DATE = 6;
	private static final int FIELD_SEX = 7;
	private static final int FIELD_CATEGORY = 8;
	private static final int FIELD_DISTRIBUTION_POST = 9;
	private static final String COMMA= ",";
	
	private static final int AGRI_FIELD_DOSSIER = 0;
	private static final int AGRI_FIELD_LASTNAME = 1;
	private static final int AGRI_FIELD_FIRSTNAME = 2;
	private static final int AGRI_FIELD_COMMUNE = 3;
	private static final int AGRI_FIELD_SECTION = 4;
	private static final int AGRI_FIELD_LOCALITY = 5;
	private static final int AGRI_FIELD_ENTRY_DATE = 6;
	private static final int AGRI_FIELD_BIRTH_DATE = 7;
	private static final int AGRI_FIELD_SEX = 8;
	private static final int AGRI_FIELD_CATEGORY = 9;
	private static final int AGRI_FIELD_NUM_PERSONS  = 10;
	
//	public static final String TEST_FIND = "id=0, address=null, category=null,"
//		+ "changeType=null, communeSection=null, sex=null,"
//		+ "distributionPost=null, dob=null, dossier=null, firstName=Ralph,"
//		+ "relativeTwo=null, relativeOne=null, nameAgriParticipant=null,"
//		+ "lastName=Morelli, householdSize=null, hasHoe=false,"
//		+ "hasMachette=false, hasPelle=false, hasPick=false,"
//		+ "hasSerpette=false, hasTree=false, hasTuber=false, hasVege=false,"
//		+ "hasGrafting=false, id=0, isArtisan=false, isChanged=false,"
//		+ "isDistributionPresent=false, isFarmer=false, isFisherman=false,"
//		+ "isMotherLeader=false, isMuso=false, isOther=false,"
//		+ "isParticipatingAgri=false, isParticipatingMchn=false,"
//		+ "isPartnerCROSE=false, isPartnerFAO=false, isPartnerMARDNR=false,"
//		+ "isPartnerOther=false, isPartnerPLAN=false, isPartnerSAVE=false,"
//		+ "isRancher=false, isStoreOwner=false, landAmount=0,"
//		+ "hasCoffee=false, messageId=0, messageStatus=0, hasCereal=false,"
//		+ "projectId=0, hasBrouette=false, hasBarreamines=false,"
//		+ "distributionMonthsRemaining=0, status=0, type=0,"
//		+ "visitedByMotherLeader=false";
//	
//	// id is generated by the database and set on the object automagically
//	@DatabaseField(columnName = DOSSIER) String dossier;
//	@DatabaseField(columnName = TYPE) int type;
//	@DatabaseField(columnName = STATUS) int status;
////	@DatabaseField(columnName = MESSAGE_ID) int message_id;
////	@DatabaseField(columnName = MESSAGE_STATUS) int message_status;
//	@DatabaseField(columnName = FIRSTNAME) String firstname;
//	@DatabaseField(columnName = LASTNAME) String lastname;
//	@DatabaseField(columnName = ADDRESS) String address;
//	@DatabaseField(columnName = DOB) String dob;
//	@DatabaseField(columnName = SEX) String sex;
//	@DatabaseField(columnName = HOUSEHOLD_SIZE) String household_size;
//	@DatabaseField(columnName = BENEFICIARY_CATEGORY) String beneficiary_category;
//	@DatabaseField(columnName = DISTRIBUTION_POST) String distribution_post;
//	@DatabaseField(columnName = Q_MOTHER_LEADER) boolean mother_leader;
//	@DatabaseField(columnName = Q_VISIT_MOTHER_LEADER) boolean visit_mother_leader;
//	@DatabaseField(columnName = Q_PARTICIPATING_AGRI) boolean participating_agri;
//	@DatabaseField(columnName = Q_PARTICIPATING_BENE) boolean participating_bene;
//	@DatabaseField(columnName = IS_FARMER) boolean is_farmer;
//	@DatabaseField(columnName = IS_MUSO) boolean is_MUSO;
//	@DatabaseField(columnName = IS_RANCHER) boolean is_rancher;
//	@DatabaseField(columnName = IS_STOREOWN) boolean is_store_owner;
//	@DatabaseField(columnName = IS_FISHER) boolean is_fisher;
//	@DatabaseField(columnName = IS_ARTISAN) boolean is_artisan;
//	@DatabaseField(columnName = IS_OTHER) boolean is_other;
//	@DatabaseField(columnName = LAND_AMOUNT) int amount_of_land;
//	@DatabaseField(columnName = HAVE_VEGE) boolean have_vege;
//	@DatabaseField(columnName = HAVE_TUBER) boolean have_tuber;
//	@DatabaseField(columnName = HAVE_CEREAL) boolean have_cereal;
//	@DatabaseField(columnName = HAVE_TREE) boolean have_tree;
//	@DatabaseField(columnName = HAVE_GRAFTING) boolean have_grafting;
//	@DatabaseField(columnName = HAVE_COFFEE) boolean have_coffee;
//	@DatabaseField(columnName = PARTNER_FAO) boolean partner_fao;
//	@DatabaseField(columnName = PARTNER_SAVE) boolean partner_save;
//	@DatabaseField(columnName = PARTNER_CROSE) boolean partner_crose;
//	@DatabaseField(columnName = PARTNER_PLAN) boolean partner_plan;
//	@DatabaseField(columnName = PARTNER_MARDNR) boolean partner_mardnr;
//	@DatabaseField(columnName = PARTNER_OTHER) boolean partner_other;
//	@DatabaseField(columnName = COMMUNE_SECTION) String communeSection;
//	@DatabaseField(columnName = HAVE_HOUE) boolean have_hoe;
//	@DatabaseField(columnName = HAVE_PIOCHE) boolean have_pick;
//	@DatabaseField(columnName = HAVE_BROUETTE) boolean have_wheelbarrow;
//	@DatabaseField(columnName = HAVE_MACHETTE) boolean have_machete;
//	@DatabaseField(columnName = HAVE_SERPETTE) boolean have_pruning_knife;
//	@DatabaseField(columnName = HAVE_PELLE) boolean have_shovel;
//	@DatabaseField(columnName = HAVE_BARREAMINES) boolean have_crowbar;
//	@DatabaseField(columnName = RELATIVE_1) String relative_1;
//	@DatabaseField(columnName = RELATIVE_2) String relative_2;
//	@DatabaseField(columnName = Q_CHANGE) boolean ChangeInStatus;
//	@DatabaseField(columnName = CHANGE_TYPE) String ChangeType;
//	
//	@DatabaseField(columnName = Q_PRESENT) boolean Present;
//	@DatabaseField(columnName = MONTHS_REMAINING) int MonthsRemaining;
//	@DatabaseField(columnName = NAME_AGRI_PARTICIPANT) String name_agri_participant;	
	

	
	
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
	
	
//	/**
//	 * Translates attribute name from Haitian to English.  Beneficiaries.txt 
//	 * 	data file represents categories in Haitian.  
//	 * @param date
//	 * @return
//	 */
//	private static String translateCategoryData(String category) {
//		if (category.equals(AttributeManager.FINDS_MALNOURISHED_HA))
//			return AttributeManager.FINDS_MALNOURISHED;
//		else if (category.equals(AttributeManager.FINDS_EXPECTING_HA))
//			return AttributeManager.FINDS_EXPECTING;
//		else if (category.equals(AttributeManager.FINDS_NURSING_HA))
//			return AttributeManager.FINDS_NURSING;		
//		else if (category.equals(AttributeManager.FINDS_PREVENTION_HA))
//			return AttributeManager.FINDS_PREVENTION;	
//		else return category;
//	}
//	
//	
//	
//	/**
//	 * Beneficiaries.txt represents sex as 'M' or 'F'.  We represent them as
//	 * 'FEMALE' or 'MALE'
//	 * @param date
//	 * @return
//	 */
//	private static String translateSexData(String sex) {
//		if (sex.equals(AttributeManager.ABBREV_FEMALE))
//			return AttributeManager.FINDS_FEMALE;
//		else if (sex.equals(AttributeManager.ABBREV_MALE))
//			return AttributeManager.FINDS_MALE;
//		else return sex;
//	}
//	
//	
//	// End of code requiring refactoring
//	
//	
//	
//	
	
	
//	public static final String SYRINGES_IN = "syringes_in";
//	public static final String SYRINGES_OUT = "syringes_out";
//	public static final String IS_NEW = "is_new";
//
//	@DatabaseField(columnName = SYRINGES_IN)
//	protected int syringesIn;
//	@DatabaseField(columnName = SYRINGES_OUT)
//	protected int syringesOut;
//	@DatabaseField(columnName = IS_NEW)
//	protected boolean isNew;
	
	//This code adds the database fields
	public static final String C_COMMODITY = "commodity";
	public static final String C_PRICE_1 = "price1";
	public static final String C_PRICE_2 = "price2";
	public static final String C_PRICE_3 = "price3";
//	public static final String C_UNITS = "units";
	public static final String C_DATE = "date";
	public static final String C_MARKET = "market";
	
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
	 * Inserts an array of beneficiaries input from AcdiVoca data file.
	 * NOTE:  The Android date picker stores months as 0..11, so
	 *  we have to adjust dates.
	 * @param beneficiaries
	 * @return
	 */
//	public static int addAgriBeneficiaries(Dao<CommodityFind, Integer> dao, String[] beneficiaries) {
//		Log.i(TAG, "Adding " + beneficiaries.length + " AGRI beneficiaries");
//		String fields[] = null;
//		int count = 0;
////		int result = 0;
//		
//		CommodityFind avFind = null;
//		for (int k = 0; k < beneficiaries.length; k++) {
//			avFind = new CommodityFind();
//
//			fields = beneficiaries[k].split(AttributeManager.PAIRS_SEPARATOR);
////			avFind.type = TYPE_AGRI;
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
	
	public static int addUpdateCommodities(Dao<CommodityFind, Integer> dao, String[] beneficiaries){
		return 0;
	}
	
	/**
	 * Inserts an array of beneficiaries input from AcdiVoca data file.
	 * NOTE:  The Android date picker stores months as 0..11, so
	 *  we have to adjust dates.
	 * @param beneficiaries
	 * @return
	 */
//	public static int addUpdateBeneficiaries(Dao<CommodityFind, Integer> dao, String[] beneficiaries) {
//		Log.i(TAG, "Adding " + beneficiaries.length + " MCHN beneficiaries");
//		String fields[] = null;
//		int count = 0;
////		int result = 0;
//
//		CommodityFind avFind = null;
//		for (int k = 0; k < beneficiaries.length; k++) {
//			avFind = new CommodityFind();
//			
//			fields = beneficiaries[k].split(COMMA);
////			avFind.type =  CommodityFind.TYPE_MCHN;
//			avFind.status = CommodityFind.STATUS_UPDATE;
////			avFind.dossier = fields[FIELD_DOSSIER];
////			avFind.lastname = fields[FIELD_LASTNAME];
////			avFind.firstname =  fields[FIELD_FIRSTNAME];
////			avFind.address = fields[FIELD_LOCALITY];
//			String adjustedDate = translateDateForDatePicker(fields[FIELD_BIRTH_DATE]);
////			avFind.dob = adjustedDate;
//			String adjustedSex = translateSexData(fields[FIELD_SEX]);
////			avFind.sex = adjustedSex;
//			String adjustedCategory = translateCategoryData(fields[FIELD_CATEGORY]);
////			avFind.beneficiary_category = adjustedCategory;
////			avFind.distribution_post = fields[FIELD_DISTRIBUTION_POST];
//
//			count += createFind(dao, avFind);
////			
////			try {
////				result = dao.create(avFind);
////				if (result == 1) 
////					++count;
////				else 
////					Log.e(TAG, "Error creating beneficiary entry " + avFind.toString());
////			} catch (SQLException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
//		}
//		Log.i(TAG, "Inserted to Db " + count + " Beneficiaries");
//		return count;
//	}

	
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
	
//	public int getSyringesIn() {
//		return syringesIn;
//	}
//
//	public void setSyringesIn(int syringesIn) {
//		this.syringesIn = syringesIn;
//	}
//
//	public int getSyringesOut() {
//		return syringesOut;
//	}
//
//	public void setSyringesOut(int syringesOut) {
//		this.syringesOut = syringesOut;
//	}
//
//	public boolean isNew() {
//		return isNew;
//	}
//
//	public void setNew(boolean isNew) {
//		this.isNew = isNew;
//	}
	
	//Commodity Tracker database setters and getters
	
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
