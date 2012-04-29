///*
// * File: AttributeManager.java
// * 
// * Copyright (C) 2011 The Humanitarian FOSS Project (http://hfoss.org).
// * 
// * This file is part of POSIT-Haiti Server.
// *
// * POSIT-Haiti Server is free software; you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by 
// * the Free Software Foundation; either version 3.0 of the License, or (at
// * your option) any later version.
// * 
// * This program is distributed in the hope that it will be useful, 
// * but WITHOUT ANY WARRANTY; without even the implied warranty of 
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// * General Public License for more details.
// * 
// * You should have received a copy of the GNU General Public License
// * along with this program; if not visit http://www.gnu.org/licenses/gpl.html.
// *
// */
//
//package org.hfoss.posit.android.api.plugin.commodity;   // Mobile side package
////package haiti.server.datamodel;                  // Server side package
//
//import java.util.HashMap;
//import java.util.Iterator;
//
//import android.util.Log;
//
///**
// * This class manages all attributes and abbreviations
// * on both the client (mobile) and server side.  
// * 
// * On the mobile side, it defines the mappings between
// * Db column names, such as 'firstname' and he abbreviation
// * used for that attribute in the SMS messasge ('f').  
// * 
// * On the server side it defines the reverse mapping, from
// * 'f' to 'firstname'.   
// * 
// */
//public class CommodityAttributeManager {
//	public static final String TAG = "AttributeManager";
//	
//	private static CommodityAttributeManager mInstance = null; 
//	private static HashMap<String,String> mappings;
//
//	public enum BeneficiaryType {
//		UNKNOWN(-1), MCHN(0), AGRI(1), BOTH(2);
//
//		private int code;
//
//		private BeneficiaryType(int code) {
//			this.code = code;
//		}
//
//		public int getCode() {
//			return code;
//		}
//	}
//
//	public enum Sex {
//		U, M, F
//	};
//
//	public enum YnQuestion {
//		U, Y, N
//	};
//
//	public enum BeneficiaryCategory {
//		UNKNOWN(-1), AGRI(0), EXPECTING(1), NURSING(2), PREVENTION(3), MALNOURISHED(
//				4);
//
//		private int code;
//
//		private BeneficiaryCategory(int code) {
//			this.code = code;
//		}
//
//		public int getCode() {
//			return code;
//		}
//	}
//
//	public enum Abbreviated {
//		TRUE, FALSE
//	};
//
//	public enum MessageStatus {
//		UNKNOWN(-1), NEW(0), UPDATED(1), PENDING(2), PROCESSED(3), ALL(4);
//		private int code;
//
//		private MessageStatus(int code) {
//			this.code = code;
//		}
//
//		public int getCode() {
//			return code;
//		}
//	};
//
//	public enum MessageType {
//		UNKNOWN(-1), REGISTRATION(0), UPDATE(1), ATTENDANCE(2), ALL(3);
//
//		private int code;
//
//		private MessageType(int code) {
//			this.code = code;
//		}
//
//		public int getCode() {
//			return code;
//		}
//	}	
//	
//	public static final String ATTR_VAL_SEPARATOR = "=";
//	public static final String PAIRS_SEPARATOR = ",";
//	public static final String LIST_SEPARATOR = "&"; // Don't use '/' and don't use '|'
//	public static final String NUMBER_SLASH_SIZE_SEPARATOR = ":";
//	public static final String NOT_FOUND = " NOT FOUND";
//	public static final String WHERE = " where ";
//	public static final String SELECT_FROM = "select * from ";
//	public static final String DATE_SEPARATOR = ":";
//	public static final String LINE_ENDER = ";";
//	public static final String CONJUNCTION = " and ";
//	public static final String DOES_NOT_EXIST = " does not exist!";
//	public static final String MATCH_NOT_FOUND = "MATCH NOT FOUND!";
//	public static final String UPDATE = "UPDATE ";
//	public static final String SET = " SET ";
//	public static final String SINGLE_QUOTE = "'";
//	public static final String OPEN_PAREN = "(";
//	public static final String CLOSE_PAREN = ")";
//	public static final String DB_HOST = "org.sqlite.JDBC";
//	public static final String DB_NAME = "jdbc:sqlite:";
//	public static final String INSERT = "INSERT INTO ";
//	public static final String VALUES = "VALUES ";
//	public static final String USER_DIRECTORY = "user.dir";
//	public static final String DATABASE_PATHNAME = "/db/";
//	public static final int ACK_MESSAGES_AT = 5;
//	public static final String MSG_NUMBER_SEPARATOR = ":";
//	
//	public static final String NEW_USER = "Insert into user (username, password, role) values('";
//	public static final int NEW_USER_ROLE = 1;
//
//	public static final String OUTER_DELIM = PAIRS_SEPARATOR;
//	public static final String INNER_DELIM = ATTR_VAL_SEPARATOR;
//	
//	public static final String URL_OUTER_DELIM = "%2C";
//	public static final String URL_INNER_DELIM = "%3D";
//	public static final String URL_PLUS = "%2B";
//	public static final String PLUS = "+";
//	public static final String FORM_BENEFICIARY = null;
//	
//	
//	// Server side widget, button, and label names used
//	//  in the DataEntry forms.
//
//	// This array of abbreviations is used to encode
//	//  multiple fields of Y/N data -- such as 'is the 
//	//  beneficiary a farmer' -- into a single integer.
//	//  For example, the binary integer 'is=9' or, in binary,
//	//  'is=1001' would represent 'fa and ra" or 'farmer and rancher'.
//	//  Methods are available to perform the encoding.
//
//	
//	
//	// More Y/N questions for the agri form
//	
//	public static final String FORM_VEGETABLES = "Vegetables";
//	public static final String FORM_CEREAL = "Cereal";
//	public static final String FORM_TUBERS = "Tubers";
//	public static final String FORM_TREE = "Tree";
//	public static final String FORM_LIVRE = "Livre";
//	public static final String FORM_MARMITES = "Marmites";
//	public static final String FORM_POTE = "Pote";
//	public static final String FORM_KG = "Kg";
//	public static final String FORM_PLANTULES = "Plantules";
//	public static final String FORM_BOUTURES = "Boutures";
//	public static final String FORM_HOE = "Hoe";
//	public static final String FORM_PICKAXE = "Pickaxe";
//	public static final String FORM_WHEELBARROW = "Wheelbarrow";
//	public static final String FORM_MACHETTE = "Machette";
//	public static final String FORM_PRUNING_KNIFE = "PruningKnife";
//	public static final String FORM_SHOVEL = "Shovel";
//	public static final String FORM_CROWBAR = "Crowbar";
//	
//	public static final String FINDS_HAVE_BARREAMINES = "have_crowbar";
//	public static final String FINDS_HAVE_BROUETTE = "have_wheelbarrow";
//	public static final String FINDS_HAVE_CEREAL = "have_cereal";
//	public static final String FINDS_HAVE_HOUE = "have_hoe";
//	public static final String FINDS_HAVE_MACHETTE = "have_machete";
//	public static final String FINDS_HAVE_PELLE = "have_shovel";
//	public static final String FINDS_HAVE_PIOCHE = "have_pick";
//	public static final String FINDS_HAVE_SERPETTE = "have_pruning_knife";
//	public static final String FINDS_HAVE_TREE = "have_tree";
//	public static final String FINDS_HAVE_GRAFTING = "have_grafting";
//	public static final String FINDS_HAVE_TUBER = "have_tuber";
//	public static final String FINDS_HAVE_VEGE = "have_vege";
//	public static final String FINDS_HAVE_COFFEE = "have_coffee";
//	
//	public static final String ABBREV_HAVE_BARREMINES = "ba";
//	public static final String ABBREV_HAVE_BROUTTE = "br";
//	public static final String ABBREV_HAVE_CEREAL = "ce";
//	public static final String ABBREV_HAVE_HOE = "ho";
//	public static final String ABBREV_HAVE_MACHETE = "ma";
//	public static final String ABBREV_HAVE_PELLE = "pe";
//	public static final String ABBREV_HAVE_PIOCHE = "pi";
//	public static final String ABBREV_HAVE_SERPETTE = "se";
//	public static final String ABBREV_HAVE_TREE = "tr";
//	public static final String ABBREV_HAVE_GRAFTING = "gr";
//	public static final String ABBREV_HAVE_VEG = "ve";
//	public static final String ABBREV_HAVE_TUBER = "tu";
//	public static final String ABBREV_HAVE_COFFEE = "co";
//	
//	// This pair of constants is used to encode/decode Y/N
//	// questions regarding plant, seeds, and tools.
//	public static final String ABBREV_HASA = "hs";
//	public static final String[] hasAFields = {ABBREV_HAVE_BARREMINES, ABBREV_HAVE_BROUTTE,
//		ABBREV_HAVE_CEREAL, ABBREV_HAVE_HOE, ABBREV_HAVE_MACHETE, ABBREV_HAVE_PELLE, 
//		ABBREV_HAVE_PIOCHE, ABBREV_HAVE_SERPETTE, ABBREV_HAVE_TREE, ABBREV_HAVE_VEG, 
//		ABBREV_HAVE_TUBER, ABBREV_HAVE_COFFEE, ABBREV_HAVE_GRAFTING};
//
//	// -------------- DATA VALUES
//	// These correspond to data values represented as Enums
//	// public static final String FORM_MALE="MALE";
//	// public static final String FORM_FEMALE="FEMALE";
//	public static final String FORM_INFANT_MAL = "InfantMal";
//	public static final String FORM_INFANT_PREVENTION = "InfantPrevention";
//	public static final String FORM_MOTHER_EXPECTING = "MotherExpecting";
//	public static final String FORM_MOTHER_NURSING = "MotherNursing";
//	
//	public static final String BUTTON_INFANT_MAL="Enfant mal nourri";
//	public static final String BUTTON_INFANT_PREVENTION="Enfant en prevention";
//	public static final String BUTTON_MOTHER_EXPECTING="Femme enceinte";
//	public static final String BUTTON_MOTHER_NURSING="Femme allaitante";
//	
//	
//	public static final String FINDS_MALNOURISHED = "MALNOURISHED";
//	public static final String FINDS_EXPECTING = "EXPECTING";
//	public static final String FINDS_NURSING = "NURSING";
//	public static final String FINDS_PREVENTION = "PREVENTION";
//	
////	public static final String FINDS_MALNOURISHED_HA = "Enfant Mal"; //Note: different spelling
//	public static final String FINDS_MALNOURISHED_HA = "Enfant Mal Nouri";
//	public static final String FINDS_EXPECTING_HA = "Femme Enceinte";
//	public static final String FINDS_NURSING_HA = "Femme Allaitante";
//	public static final String FINDS_PREVENTION_HA = "Enfant Prevention";
//	
//	public static final String ABBREV_MALNOURISHED = "M";
//	public static final String ABBREV_EXPECTING = "E";
//	public static final String ABBREV_NURSING = "N";
//	public static final String ABBREV_PREVENTION = "P";
//
//	public static final String FORM_FEMALE="Female";
//	public static final String FORM_MALE="Male";
//	public static final String FORM_NO = "No";	
//	public static final String FORM_YES = "Yes";
//	
//	public static final String FINDS_FEMALE = "FEMALE";
//	public static final String FINDS_MALE = "MALE";
//	public static final String FINDS_NO = "NO"; 
//	public static final String FINDS_YES = "YES";
//	public static final String FINDS_TRUE = "TRUE";
//	public static final String FINDS_FALSE = "FALSE";
//	
//	public static final String BUTTON_FEMALE="FEMALE";
//	public static final String BUTTON_MALE="MALE";
//	public static final String BUTTON_NO="No";
//	public static final String BUTTON_YES="Yes";
//	
//	public static final String ABBREV_FEMALE= "F";
//	public static final String ABBREV_MALE= "M";
//	public static final String ABBREV_NO= "N";	
//	public static final String ABBREV_YES= "Y";
//
//	
//	public static final String ABBREV_TRUE = "T";
//	public static final String ABBREV_FALSE = "F";
//
//
//	
//	public static final String FINDS_Q_CHANGE = "ChangeInStatus";   // Added to incorporated changes to beneficiary type
//	public static final String FINDS_CHANGE_TYPE = "ChangeType";
//	public static final String FINDS_Q_PRESENT = "Present";
//	public static final String FINDS_Q_TRANSFER = "Transfer";
//	public static final String FINDS_Q_MODIFICATIONS = "Modifications";
//	
//	public static final String FINDS_Q_TRANSFER_NEW_CATEGORY = "Transfer to new category";
//	public static final String FINDS_Q_TRANSFER_LACTATE = "Transfer from pregnant to lactating";
//	public static final String FINDS_Q_TRANSFER_PREVENTION = "Transfer from lactating to prevention";
//	public static final String FINDS_Q_TRANSFER_LOCATION = "Transfer due to location change";
//	public static final String FINDS_Q_TRANSFER_ABORTION = "Transfer due to abortion";
//	public static final String FINDS_Q_CHANGED_BENEFICIARY_DATA = "Changed beneficiary data"; //Added
//	public static final String FINDS_Q_DECEASED= "Deceased";
//	public static final String FINDS_Q_FRAUD = "Fraud";
//	public static final String FINDS_Q_COMPLETED_PROGRAM = "Completed program";
//	public static final String FINDS_Q_OTHER = "Other"; //Added
//	
//	//NEEDS TO TRANSLATE
////	public static final String FINDS_Q_TRANSFER_NEW_CATEGORY_HA = "Transfere nan yon lòt kategori";
////	public static final String FINDS_Q_TRANSFER_LACTATE_HA = "Transfert de: Enceinte à Allaitante";
////	public static final String FINDS_Q_TRANSFER_PREVENTION_HA = "Transfert de: Allaitante à Prevention";
////	public static final String FINDS_Q_TRANSFER_LOCATION_HA = "Transfert du au changement de lieu";
////	public static final String FINDS_Q_TRANSFER_ABORTION_HA = "Transfert du a cause de l’avortement";
////	public static final String FINDS_Q_DECEASED_HA = "Decede";
////	public static final String FINDS_Q_FRAUD_HA = "Fraud";
////	public static final String FINDS_Q_COMPLETED_PROGRAM_HA = "Cycle complet";
//
//	
//	public static final String FINDS_RELATIVE_AGRI = "relative_having_agrAid";
//	public static final String FINDS_RELATIVE_BENE = "relative_having_beneAid";
//	// ---------------- LONG COLUMNS NAMES FROM PHONE"S DB------
//	// Mobile side column names used in the phone's Db. Mostly 
//	//  used by DbHelper to construct the SMS messages. A raw
//	//  message pulled from the phone's DB would take the form:
//	//  'firstname=joe,lastname=smith,...,sex=M'
//	//  Using the data defined here, it would be encoded as
//	// 'f=joe,l=smith,...,g=M'
//	public static final String FINDS_BENE_DOSSIER = "Mchn";
//	public static final String FINDS_AGRI_DOSSIER = "Agri";
//	public static final String FINDS_BOTH_DOSSIER = "Both";
//	
//
////  These don't seem to be necessary any more, but keep as commented out
////	public static final String ABBREV_AGRICULTURE_1 = "a1";
////	public static final String ABBREV_AGRICULTURE_2 = "a2";
////	public static final String ABBREV_GIVE_NAME = "gn";
////	public static final String ABBREV_YES = "y";
////	public static final String ABBREV_NO = "n";
////	public static final String ABBREV_MALE = "M"; //"m";
////	public static final String ABBREV_FEMALE = "F"; //"f";
////	public static final String ABBREV_INFANT_CATEGORY = "ic";
////	public static final String ABBREV_INFANT_MAL = "ia";
////	public static final String ABBREV_INFANT_PREVENTION = "P"; //"ip";
////	public static final String ABBREV_MOTHER_CATEGORY = "mc";
////	public static final String ABBREV_MOTHER_EXPECTING = "E"; //"me";
////	public static final String ABBREV_MOTHER_NURSING = "N"; //"mn";
////	public static final String ABBREV_DATA = "d";
////	public static final String ABBREV_GENERAL_INFORMATION = "gi";
////	public static final String ABBREV_MCHN_INFORMATION = "mchn";
////	public static final String ABBREV_CONTROLS = "ctrl";
//
//	
//	// ------------- LONG NAMES FOR SERVER SIDE FORMS
//	// Don't know whether all of these are necessary?
//	// TODO:  Clean up this list
////	public static final String LONG_FIRST = "firstName";
////	public static final String LONG_LAST = "lastName";
//	public static final String LONG_MESSAGE_NUMBER = "messageNumber"; //added
////	public static final String LONG_COMMUNE = "commune";
//	public static final String LONG_COMMUNE_SECTION = "communeSection";
////	public static final String LONG_ADDRESS = "address";
////	public static final String LONG_AGE = "age";
////	public static final String LONG_SEX = "sex";
////	public static final String LONG_BENEFICIARY = "beneficiary";
//	public static final String LONG_NUMBER_IN_HOME = "NumberInHome";
////	public static final String LONG_HEALTH_CENTER = "HealthCenter"; //re-added
////	public static final String LONG_DISTRIBUTION_POST = "DistributionPost";
////	public static final String LONG_RELATIVE_1 = "Relative1"; //added
////	public static final String LONG_RELATIVE_2 = "Relative2"; //added
////	public static final String LONG_NAME_CHILD = "nameChild";
////	public static final String LONG_NAME_WOMAN = "nameWoman";
////	public static final String LONG_HUSBAND = "husband";
////	public static final String LONG_FATHER = "father";
////	public static final String LONG_MOTHER_LEADER = "motherLeader";
////	public static final String LONG_VISIT_MOTHER = "visitMotherLeader";
////	public static final String LONG_AGRICULTURE_1 = "agriculture1";
////	public static final String LONG_AGRICULTURE_2 = "agriculture2";
////	public static final String LONG_GIVE_NAME = "giveName";
////	public static final String LONG_YES = "yes";
////	public static final String LONG_NO = "no";
////	public static final String LONG_MALE = "male";
////	public static final String LONG_FEMALE = "female";
////	public static final String LONG_INFANT_CATEGORY = "InfantCategory";
//	public static final String LONG_INFANT_MAL = "InfantMal";
//	public static final String LONG_INFANT_PREVENTION = "InfantPrevention";
////	public static final String LONG_MOTHER_CATEGORY = "MotherCategory";
//	public static final String LONG_MOTHER_EXPECTING = "MotherExpecting";
//	public static final String LONG_MOTHER_NURSING = "MotherNursing";
////	public static final String LONG_DATA = "data";
////	public static final String LONG_GENERAL_INFORMATION = "generalInformation";
////	public static final String LONG_MCHN_INFORMATION = "mchnInformation";
////	public static final String LONG_CONTROLS = "controls";
//	public static final String LONG_MESSAGE_STATUS = "messageStatus"; //added
//	public static final String LONG_STATUS = "status";
//	public static final String LONG_ID = "id";
//	public static final String LONG_AV = "AV";
//	public static final String LONG_TYPE = "type";
//	public static final String LONG_MESSAGE_TYPE = "messageType";  //added
////	public static final String LONG_BENEFICIARY_TYPE = "beneficiaryType"; //added
//	
////	public static final String LONG_DOSSIER = "dossier";
//	
//	
//	
//// Distribution points
//
//		
//	
////	public static final String CENTRE_PLATON_CEDRE = "Centre Platon Cedre";
////	public static final String POINT_FIXE_KA_TOUSEN = "Point Fixe Ka Tousen";
////	public static final String ANSE_A_PITRES = "Anse a Pitres";
////	public static final String DISPENSAIRE_BANANE = "Dispensaire Banane";
////	public static final String PT_FIXE_CALUMETTE = "Pt fixe Calumette";
////	public static final String CENTRE_BELLE_ANCE = "Centre Belle-Ance";
////	public static final String DISPENSAIRE_MAPOU = "Dispensaire Mapou";
////	public static final String PT_FIXE_BAIE_D_ORANGE = "Pt fixe Baie d_orange";
////	public static final String DISPENSAIRE_MARBRIOLE = "Dispensaire marbriole";
////	public static final String PT_FIXE_CORAIL_LAMOTHE = "Pt fixe Corail Lamothe";
////	public static final String PT_FIXE_PICHON = "Pt fixe Pichon";
////	public static final String PT_FIXE_BEL_AIR = "Pt Fixe Bel-air";
////	public static final String LABICHE = "Labiche";
////	public static final String CENTRE_ST_JOSPEH = "Centre St Joseph";
////	public static final String DISPENSAIRE_STE_ROSE_DE_LIMA = "Dispensaire Ste rose de lima";
////	public static final String DISPENSAIRE_BOUCAN_BELIER = "Dispensaire Boucan Belier";
////	public static final String DISPENSAIRE_RICOT = "Dispensaire Ricot";
////	public static final String PT_FIXE_MACIEUX = "Pt Fixe Macieux";
////	public static final String POINT_FIXE_DE_MAYETTE = "Point Fixe de Mayette";
////	public static final String POINT_FIXE_DE_AMAZONE = "Point Fixe de Amazone";
////	public static final String DISPENSAIRE_GRAND_GOSIER = "Dispensaire Grand Gosier";
////	public static final String DISPENSAIRE_BODARIE = "Dispensaire Bodarie";
////	public static final String PT_FIXE_BOULAY = "Pt fixe Boulay";
////	public static final String CENTRE_SACRE_COUER = "Centre Sacre Coeur";
////	public static final String DISPENSAIRE_DE_SAVANE_ZOMBI = "Dispensaire de Savane Zombi";
////	public static final String DISPENSAIRE_DE_BLACK_MAR_MIRANDE = "Dispensaire de Bleck/ Mar Mirande";
////	
//	
//	public static final String DISTRIBUTION_POINT_1 = "Centre Platon Cedre";
//	public static final String DISTRIBUTION_POINT_2 = "Point Fixe Ka Tousen";
//	public static final String DISTRIBUTION_POINT_3 = "Anse a Pitres";
//	public static final String DISTRIBUTION_POINT_4 = "Dispensaire Banane";
//	public static final String DISTRIBUTION_POINT_5 = "Pt fixe Calumette";
//	public static final String DISTRIBUTION_POINT_6 = "Centre Belle-Ance";
//	public static final String DISTRIBUTION_POINT_7 = "Dispensaire Mapou";
//	public static final String DISTRIBUTION_POINT_8 = "Pt fixe Baie d_orange";
//	public static final String DISTRIBUTION_POINT_9 = "Dispensaire marbriole";
//	public static final String DISTRIBUTION_POINT_10 = "Pt fixe Corail Lamothe";
//	public static final String DISTRIBUTION_POINT_11 = "Pt fixe Pichon";
//	public static final String DISTRIBUTION_POINT_12 = "Pt Fixe Bel-air";
//	public static final String DISTRIBUTION_POINT_13 = "Labiche";
//	public static final String DISTRIBUTION_POINT_14 = "Centre St Joseph";
//	public static final String DISTRIBUTION_POINT_15 = "Dispensaire Ste rose de lima";
//	public static final String DISTRIBUTION_POINT_16 = "Dispensaire Boucan Belier";
//	public static final String DISTRIBUTION_POINT_17 = "Dispensaire Ricot";
//	public static final String DISTRIBUTION_POINT_18 = "Pt Fixe Macieux";
//	public static final String DISTRIBUTION_POINT_19 = "Point Fixe de Mayette";
//	public static final String DISTRIBUTION_POINT_20 = "Point Fixe de Amazone";
//	public static final String DISTRIBUTION_POINT_21 = "Dispensaire Grand Gosier";
//	public static final String DISTRIBUTION_POINT_22 = "Dispensaire Bodarie";
//	public static final String DISTRIBUTION_POINT_23 = "Pt fixe Boulay";
//	public static final String DISTRIBUTION_POINT_24 = "Centre Sacre Coeur";
//	public static final String DISTRIBUTION_POINT_25 = "Dispensaire de Savane Zombi";
//	public static final String DISTRIBUTION_POINT_26 = "Dispensaire de Bleck/ Mar Mirande";
//	
//	
//	public static final String ABBREV_D1 = "HEAP-014";
//	public static final String ABBREV_D2 = "HEAP-017";
//	public static final String ABBREV_D3 = "HEAP-020";
//	public static final String ABBREV_D4 = "HEAP-021";
//	public static final String ABBREV_D5 = "HEBA-016";
//	public static final String ABBREV_D6 = "HEBA-017";
//	public static final String ABBREV_D7 = "HEBA006";
//	public static final String ABBREV_D8 = "HEBA007";
//	public static final String ABBREV_D9 = "HEBA008";	
//	public static final String ABBREV_D10 = "HEBA009";
//	public static final String ABBREV_D11 = "HEBA010";	
//	public static final String ABBREV_D12 = "HEBA015";	
//	public static final String ABBREV_D13 = "HECF-005";	
//	public static final String ABBREV_D14 = "HECF001";	
//	public static final String ABBREV_D15 = "HECF002";	
//	public static final String ABBREV_D16 = "HECF003";	
//	public static final String ABBREV_D17 = "HECF004";	
//	public static final String ABBREV_D18 = "HECF023";	
//	public static final String ABBREV_D19 = "HECF024";	
//	public static final String ABBREV_D20 = "HECF025";
//	public static final String ABBREV_D21 = "HEGG-018";
//	public static final String ABBREV_D22 = "HEGG-019";
//	public static final String ABBREV_D23 = "HEGG-020";
//	public static final String ABBREV_D24 = "HETH-024";
//	public static final String ABBREV_D25 = "HETH026";
//	public static final String ABBREV_D26 = "HETH027";
//	
//	
//	
//	
//	/**
//	 * Private constructor means it can't be instantiated.
//	 * @param activity
//	 */
//	private CommodityAttributeManager(){
//	}
//	
//	public static CommodityAttributeManager getInstance(){
//		mInstance = new CommodityAttributeManager();
//		mInstance.init();
//		//assert(mInstance != null);
//		return mInstance;
//	}
//	
//	/**
//	 * Default constructor, inserts all the attributes into a HashMap
//	 */
////	public AttributeManager() {
//	public static void init() {
//		mappings = new HashMap<String, String>();
////		mappings.put(ABBREV_ATTRIBUTE, LONG_ATTRIBUTE);
////		mappings.put(ABBREV_FIRST, LONG_FIRST);
////		mappings.put(ABBREV_LAST, LONG_LAST);
////		mappings.put(ABBREV_COMMUNE, LONG_COMMUNE);
//
////		mappings.put(ABBREV_LOCALITY, LONG_ADDRESS);
////		mappings.put(ABBREV_DOB, LONG_AGE);
////		mappings.put(ABBREV_SEX, LONG_SEX);
////		
////		mappings.put(ABBREV_CATEGORY, LONG_BENEFICIARY);
////		mappings.put(ABBREV_NUMBER_IN_HOME, LONG_NUMBER_IN_HOME);
////		mappings.put(ABBREV_HEALTH_CENTER, LONG_HEALTH_CENTER); //Re-added
////		mappings.put(ABBREV_DISTRIBUTION_POST, LONG_DISTRIBUTION_POST);
////		mappings.put(ABBREV_NAME_CHILD, LONG_NAME_CHILD);
////		mappings.put(ABBREV_NAME_WOMAN, LONG_NAME_WOMAN);
////		mappings.put(ABBREV_HUSBAND, LONG_HUSBAND);
////		mappings.put(ABBREV_FATHER, LONG_FATHER);
////		mappings.put(ABBREV_RELATIVE_1, LONG_RELATIVE_1); //added
////		mappings.put(ABBREV_RELATIVE_2, LONG_RELATIVE_2); //added
//
//		
//
//		
//		// ----------- DATA MAPPINGS -------------------------
//
//		
//		
//	
//		
//		// ---------- ACDIV/VOCA DATA -----------------
//		// There should be mappings for all fixed data
//		// used on the phone.
////		<string-array name="distribution_point_names"> 
//		mappings.put(DISTRIBUTION_POINT_1,  ABBREV_D1 );
//		mappings.put(DISTRIBUTION_POINT_2,  ABBREV_D2 );
//		mappings.put(DISTRIBUTION_POINT_3,  ABBREV_D3 );
//		mappings.put(DISTRIBUTION_POINT_4,  ABBREV_D4 );
//		mappings.put(DISTRIBUTION_POINT_5,  ABBREV_D5 );
//		mappings.put(DISTRIBUTION_POINT_6,  ABBREV_D6 );
//		mappings.put(DISTRIBUTION_POINT_7, ABBREV_D7  );
//		mappings.put(DISTRIBUTION_POINT_8, ABBREV_D8  );
//		mappings.put(DISTRIBUTION_POINT_9, ABBREV_D9  );	
//		mappings.put(DISTRIBUTION_POINT_10, ABBREV_D10  );
//		mappings.put(DISTRIBUTION_POINT_11,   ABBREV_D11 );	
//		mappings.put(DISTRIBUTION_POINT_12,  ABBREV_D12  );	
//		mappings.put(DISTRIBUTION_POINT_13,   ABBREV_D13 );	
//		mappings.put(DISTRIBUTION_POINT_14,   ABBREV_D14 );	
//		mappings.put(DISTRIBUTION_POINT_15,  ABBREV_D15  );	
//		mappings.put(DISTRIBUTION_POINT_16, ABBREV_D16   );	
//		mappings.put(DISTRIBUTION_POINT_17,   ABBREV_D17 );	
//		mappings.put(DISTRIBUTION_POINT_18,  ABBREV_D18  );	
//		mappings.put(DISTRIBUTION_POINT_19,   ABBREV_D19 );	
//		mappings.put(DISTRIBUTION_POINT_20,   ABBREV_D20 );
//		mappings.put(DISTRIBUTION_POINT_21, ABBREV_D21   );
//		mappings.put(DISTRIBUTION_POINT_22,   ABBREV_D22 );
//		mappings.put(DISTRIBUTION_POINT_23, ABBREV_D23  );
//		mappings.put(DISTRIBUTION_POINT_24,   ABBREV_D24 );
//		mappings.put(DISTRIBUTION_POINT_25,  ABBREV_D25  );
//		mappings.put(DISTRIBUTION_POINT_26,   ABBREV_D26 );
//		
//		mappings.put(ABBREV_D1, DISTRIBUTION_POINT_1);
//		mappings.put(ABBREV_D2, DISTRIBUTION_POINT_2);
//		mappings.put(ABBREV_D3, DISTRIBUTION_POINT_3);
//		mappings.put(ABBREV_D4, DISTRIBUTION_POINT_4);
//		mappings.put(ABBREV_D5, DISTRIBUTION_POINT_5);
//		mappings.put(ABBREV_D6, DISTRIBUTION_POINT_6);
//		mappings.put(ABBREV_D7, DISTRIBUTION_POINT_7);
//		mappings.put(ABBREV_D8, DISTRIBUTION_POINT_8);
//		mappings.put(ABBREV_D9, DISTRIBUTION_POINT_9);
//		mappings.put(ABBREV_D10, DISTRIBUTION_POINT_10);
//		mappings.put(ABBREV_D11, DISTRIBUTION_POINT_11);
//		mappings.put(ABBREV_D12, DISTRIBUTION_POINT_12);
//		mappings.put(ABBREV_D13, DISTRIBUTION_POINT_13);
//		mappings.put(ABBREV_D14, DISTRIBUTION_POINT_14);
//		mappings.put(ABBREV_D15, DISTRIBUTION_POINT_15);
//		mappings.put(ABBREV_D16, DISTRIBUTION_POINT_16);
//		mappings.put(ABBREV_D17, DISTRIBUTION_POINT_17);
//		mappings.put(ABBREV_D18, DISTRIBUTION_POINT_18);
//		mappings.put(ABBREV_D19, DISTRIBUTION_POINT_19);
//		mappings.put(ABBREV_D20, DISTRIBUTION_POINT_20);
//		mappings.put(ABBREV_D21, DISTRIBUTION_POINT_21);
//		mappings.put(ABBREV_D22, DISTRIBUTION_POINT_22);
//		mappings.put(ABBREV_D23, DISTRIBUTION_POINT_23);
//		mappings.put(ABBREV_D24, DISTRIBUTION_POINT_24);
//		mappings.put(ABBREV_D25, DISTRIBUTION_POINT_25);
//		mappings.put(ABBREV_D26, DISTRIBUTION_POINT_26);
//		
////		<string-array name="health_center_names"> 
////		mappings.put("Centre de sant� une", "h1" );
////		mappings.put("Centre de sant� deux", "h2"  );
////		mappings.put("Centre de sant� trois", "h3" );
////		mappings.put("Centre de sant� quatre", "h4" );
////		mappings.put("Centre de sant� cinq", "h5" );
////		
////		mappings.put("h1", "Centre de sant� un");
////		mappings.put("h2", "Centre de sant� deux");
////		mappings.put("h3", "Centre de sant� trois");
////		mappings.put("h4", "Centre de sant� quatre");
////		mappings.put("h5", "Centre de sant� cinq");
//		
//	}
//	
//	
//	/* *******************
//	 * 
//	 * 
//	 * 
//Centre Platon Cedre	HEAP-014
//Point Fixe Ka Tousen	HEAP-017
//Anse a Pitres	HEAP-020
//dispensaire Banane	HEAP-021
//	
//Pt fixe Calumette	HEBA-016
//Centre Belle-Ance	HEBA-017
//Dispensaire Mapou	HEBA006
//Pt fixe Baie d_orange	HEBA007
//Dispensaire marbriole	HEBA008
//Pt fixe Corail Lamothe	HEBA009
//Pt fixe Pichon	HEBA010
//Pt Fixe Bel-air	HEBA015
//	
//Labiche	HECF-005
//Centre St Joseph	HECF001
//Dispensaire Ste rose de lima	HECF002
//Dispensaire Boucan Belier	HECF003
//Dispensaire Ricot	HECF004
//Pt Fixe Macieux	HECF023
//Point Fixe de Mayette	HECF024
//Point Fixe de Amazone	HECF025
//	
//Dispensaire Grand Gosier	HEGG-018
//Dispensaire Bodarie	HEGG-019
//Pt fixe Boulay	HEGG-020
//	
//Centre Sacre Coeur	HETH-024
//Dispensaire de Savane Zombi	HETH026
//Dispensaire de Bleck/ Mar Mirande	HETH027
//***********************************************	 */
//	
//	/**
//	 * Server side method -- Converted to remove reference to server side enum
//	 * Maps a short form attribute or value name to a long form
//	 * @param abbreviatedAttributes TRUE if abbreviated, FALSE if not
//	 * @param s the String to be mapped to long
//	 * @return the long form of the String
//	 */
////	public static String mapToLong(Beneficiary.Abbreviated abbreviatedAttributes, String s){
//	public static String mapToLong(boolean abbreviatedAttributes, String s){
//		if (abbreviatedAttributes) {
//			String str = mappings.get(s);
//			if (str != null)
//				return str;
//			else 
//				return "";
//		}
//		else
//			return s;
//	}
//	
//
//
//
//	
//	/**
//	 * A simple test method. 
//	 */
//	public void testAllAttributes() {
//		Iterator<String> it = mappings.keySet().iterator();
//		//Iterator<String> it = mappings.values().iterator();
//		while (it.hasNext()) { 
//			String s = it.next();
//			
//			//.println(s +  " = " + mappings.get(s));
//		}
//	}
//
//	/**
//	 * Replaces some attribute/value Y/N pairs with an encoded base-2 number.
//	 * Example:  'fa=1,ra=1' would be converted to 9 decimal, which 1001 in 
//	 * binary or 9 = 1001 = 2^0 + 2^3 where 'fa' and 'ra' are contained in the
//	 * array  ["fa", "fi", "mu", "ra", "st", "ot"] .  
//	 * @param smsMsg the message to be encoded
//	 * @param attributes an array of attribute names: 
//	 * @param newAttr a String giving the name for the int attribute in the message
//	 *  -- e.g., 'is' or 'hs'
//	 * @return  an SMS where an attr/val pair such as 'is=9' replaces 'fa=1,ra=1'.  
//	 */
//	public static String encodeBinaryFields(String smsMsg, String[] attributes, String newAttr) {
//		int sum = 0;
//		String result = "";
//		String pairs[] = smsMsg.split(PAIRS_SEPARATOR);  
//		
//		// Split each attr/value pair in the Sms message into its attr and its val
//		for (int k = 0; k < pairs.length; k++) {
//			String attrval[] = pairs[k].split(ATTR_VAL_SEPARATOR);
//			String attr = "", val = "";
//			if (attrval.length == 2) {
//				attr = attrval[0];
//				val = attrval[1];
//			} else if (attrval.length == 1) {
//				attr = attrval[0];
//			}
//			System.out.println(TAG + "attr = " + attr + " val = " + val);
//
//			// If the attribute (eg 'fa') is contained in the attributes array
//			// its value represents 2^x where x is its index in the array. Add
//			// that value to the running total.  If the attribute is not in the
//			// array, just put it back in the result string.
//			int x = getIndex(attributes, attr);
//			int valInt = 0;
//			if (x != -1) {
//				try {
//					if (val.equals("true"))
//						valInt = 1;
//					else if (val.equals("false"))
//						valInt = 0;
//					else 
//						valInt = Integer.parseInt(val);   // Shouldn't happen
//				} catch (NumberFormatException e) {
//					Log.e(TAG, "NumberFormatException on " + val);
//				}
//				if (valInt == 1)
//					sum += Math.pow(2, x);              // Compute a running total
//			} else {
//				if (attr.length() != 0)
//					result += attr + ATTR_VAL_SEPARATOR + val + PAIRS_SEPARATOR;
//			}
//		}
//		result += newAttr + ATTR_VAL_SEPARATOR + sum;	
//		
//		// To test the encoding, print out the decoding
//		//System.out.println(TAG +  "Decoded sum = " + decodeBinaryFieldsInt(sum, attributes));
//		//System.out.println(TAG +  "Result= " + result);
//
//		return result;
//	}
//	
//	/**
//	 * This is the reverse mapping of encodeBinaryFields.  It takes a
//	 * decimal (base-10) number and converts it to binary where 1 bits
//	 * correspond to the attributes in the attributes array.  Those are
//	 * returns as a String of attr=val pairs.  For example the codedInt
//	 * 9 would translate to 1001 or 2^0 + 2^3 or 'fa=1,ra=1' using the
//	 * attributes array ["fa", "fi", "mu", "ra", "st", "ot"]
//	 * @param codedInt is a base-10 number
//	 * @param attributes is an array of attribute abbreviations
//	 * @return  a string of attr=val pairs
//	 */
//	public static String decodeBinaryFieldsInt(int codedInt, String[] attributes) {
//		String result = "";
//		int len = attributes.length;    
//		Log.i(TAG,"AAAAAAAAAAAAAAAAAAAAAABBBBBBBBBBBREEEEEEEEEEVVVVVVVVVVVV");
//		Log.i(TAG,attributes.toString());
//		// Moving right to left in the attributes array, use the array index
//		// as the exponent in 2^k, subtracting 2^k from the codedInt on each 
//		// iteration. If 2^k can be subtracted from codedInt that means that
//		// attributes[k] contains an attribute whose value should be 1. So
//		// insert that attr=1 into the result string.
//		for (int k = len; k >= 0; k--)  {
//			int pow2 = (int) Math.pow(2, k);
//			if (codedInt >= pow2) {
//				codedInt -= pow2;     // Subtract that power of 2
//				result += attributes[k] + ATTR_VAL_SEPARATOR + "1" + PAIRS_SEPARATOR;
//			}
//		}
//		//System.out.println(TAG + "Result = " + result);
//		return result;	
//	}
//	
//
//	/**
//	 * Helper method to find the location of the String s in a String array.
//	 * @param arr
//	 * @param s
//	 * @return returns the index of s in arr or -1 if it is not contained therein
//	 */
//	private static int getIndex(String []arr, String s)  {
//		for (int k = 0; k < arr.length; k++) 
//			if (s.equals(arr[k])) 
//				return k;
//		return -1;
//	}
//	
//	
//	/**
//	 * TODO:  This method should thoroughly test this class. For example, 
//	 * print out all mappings of short to long.
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		System.out.println("Hello Attribute Manager");
//		CommodityAttributeManager am = CommodityAttributeManager.getInstance(); // new AttributeManager();
//		//System.out.print(am.mapToLong(false, "f"));
//		//am.testAllAttributes();
//		//System.out.println("Str= " + am.mappings.get("M"));
//		String ids[] = "1/2".split("/");
//		for (int k = 0; k < ids.length; k++)
//			System.out.println(ids[k]);
//	}
//
//}