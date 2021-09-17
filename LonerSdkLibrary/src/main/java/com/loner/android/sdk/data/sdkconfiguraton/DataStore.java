package com.loner.android.sdk.data.sdkconfiguraton;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Model class to store the important attributes in the shared preference
 * @author cuelogic
 * @version 1
 * @since 2019
 */

public class DataStore {

	public static final String IS_MANUAL_CHECK_IN_ON = "isManualCheckinOn";
	public static final String CHECKIN_TIMER = "checkinTimerValue";

	public static final String ALLOW_USER_TO_CONFIGURE = "allow_user_to_configure";

	public static final String MANUAL_CHECK_IN = "manual_check_in";


	/**
	 * saves the boolean that for a particular attribute in the shared
	 * preferences
	 * @param con Context
	 * @param variable String
	 * @param data boolean value
	 */
	public static void saveBooleanData(Context con, String variable,  boolean data) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(con);
		prefs.edit().putBoolean(variable, data).apply();
	}
	
	/**
	 * get the boolean data from the shared preferences in accordance with the
	 * attribute pass to the function
	 * @param con  Context
	 * @param variable  String
	 * @param defaultValue boolean
	 * @return boolean value
	 */
	public static boolean getBooleanData(Context con, String variable, boolean defaultValue) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(con);
		return prefs.getBoolean(variable, defaultValue);
	}


	/**
	 * saves the Long that for a particular attribute in the shared
	 * preferences
	 * @param con Context
	 * @param variable string
	 * @param data Integer
	 */
	public static void saveLongData(Context con, String variable, long data) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(con);
		prefs.edit().putLong(variable, data).apply();
	}
	
	/**
	 * get the long data from the shared preferences in accordance with the
	 * attribute pass to the function
	 * @param con Context
	 * @param variable string
	 * @param defaultValue integer
	 * @return integer value
	 */
	public static long getLongData(Context con, String variable, long defaultValue) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(con);
		return prefs.getLong(variable, defaultValue);
	}
}
