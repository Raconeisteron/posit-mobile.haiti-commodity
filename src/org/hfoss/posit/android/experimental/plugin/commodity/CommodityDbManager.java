package org.hfoss.posit.android.experimental.plugin.commodity;
/**
 * 
 */

import org.hfoss.posit.android.experimental.api.Find;
import org.hfoss.posit.android.experimental.api.database.DbManager;
import org.hfoss.posit.android.experimental.plugin.acdivoca.AcdiVocaMessage;

import android.content.Context;
import java.sql.SQLException;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * 
 *
 */
public class CommodityDbManager extends DbManager {


		private static final String TAG = "CommodityDbManager";

		// DAO objects used to access the Db tables
		private Dao<CommodityFind, Integer> commodityFindDao = null;
		private Dao<CommodityMessage, Integer> CommodityMessageDao = null;
		
		/**
		 * Constructor just saves and opens the Db.
		 * @param context
		 */
		public CommodityDbManager(Context context) {
			super(context);
		}
		
		/**
		 * Invoked automatically if the Database does not exist.
		 */
		@Override
		public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
				Log.i(TAG, "onCreate");
				super.onCreate(db, connectionSource);
				//CommodityFind.createTable(connectionSource);
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
			try {
				Log.i(TAG, "onUpgrade");
				TableUtils.dropTable(connectionSource, CommodityFind.class, true);
				// after we drop the old databases, we create the new ones
				onCreate(db, connectionSource);
			} catch (SQLException e) {
				Log.e(TAG, "Can't drop databases", e);
				throw new RuntimeException(e);
			}
		}
//			

//		
		/**
		 * Returns the Database Access Object (DAO) for the CommodityFind class. 
		 * It will create it or just give the cached value.
		 */
		public Dao<CommodityFind, Integer> getCommodityFindDao(){
			if (commodityFindDao == null) {
				try {
					commodityFindDao = getDao(CommodityFind.class);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return commodityFindDao;
		}
		
		
		/**
		 * Looks up a find by its ID.
		 * @param id the id of the find to look up
		 * @return the find
		 */
		public Find getFindById(int id) {
			CommodityFind find = null;
			try {
				find = getCommodityFindDao().queryForId(id);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return find;
		}
		
		/**
		 * Fetches all finds currently in the database.
		 * @return A list of all the finds.
		 */
		public List<? extends Find> getAllFinds() {
			List<CommodityFind> list = null;
			try {
				list = getCommodityFindDao().queryForAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;

		}
		
		/**
		 * Returns the Database Access Object (DAO) for the AcdiVocaFind class. 
		 * It will create it or just give the cached value.
		 */
		public Dao<CommodityMessage, Integer> getCommodityMessageDao() throws SQLException {
			if (CommodityMessageDao == null) {
				CommodityMessageDao = getDao(CommodityMessage.class);
			}
			return CommodityMessageDao;
		}
		
		
		/**
		 * Close the database connections and clear any cached DAOs.
		 */
		@Override
		public void close() {
			super.close();
			commodityFindDao = null;

		}

	}

