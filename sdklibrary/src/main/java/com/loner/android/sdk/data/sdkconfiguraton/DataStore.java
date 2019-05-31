package com.loner.android.sdk.data.sdkconfiguraton;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.jetbrains.annotations.NotNull;

/**
 * Model class to store the important attributes in the shared preference
 * @author cuelogic
 * @version 1
 * @since 
 */

public class DataStore {

    public static final String BASE_URL = "base_url";

	public static final String DEVICE_ID = "device_id";
	public static final String ACCESS_TOKEN = "access_token";
    public static final String INTRO_DISPLAYED = "intro_displayed";
    public static final String FIRST_TIME_DISPLAYED = "first_time_displayed";
    public static final String IS_FIRST_RUN = "isFirstRunOfApp";
	public static final String IS_MANUAL_CHECK_IN_ON = "isManualCheckinOn";
	public static final String IS_EMERGENCY_ON = "isEmergencyOn";
	public static final String IS_SILENT_ALERT_VIBRATE_ON = "isSilentVibrateOn";
	public static final String IS_GPS_MOTION_ON = "isGpsMotionOn";
	public static final String IS_ALARM_SOUND_ON = "isAlarmSoundOn";
	public static final String IS_AUDIBLE_ALERTS_ON = "isAudibleAlertsOn";
	public static final String CHECKIN_TIMER = "checkinTimerValue";
	public static final String CUTOFF_SPEED = "cutoffSpeedInt";
	public static final String FDU_ADDRESS = "fduAdress";
	public static final String FDU_BATTERY_VALUE = "fduBatteryValue";
	public static final String FDU_CONNECTION_STATE = "disconnected";
	public static final String DEFAULT_DEVICE_NAME = "defaultdevicename";
	public static final String DEFAULT_DEVICE_ADDR = "defaultdeviceaddress";

	public static final String EMERGENCY_ENABLED = "emergency_enabled";
	public static final String EMERGENCY_NUMBER = "emergency_number";
	public static final String GPS_PUBLISH_PERIOD = "gps_publish_period";
	public static final String ALLOW_USER_TO_CONFIGURE = "allow_user_to_configure";
	public static final String LOCATION_TIMER = "location_timer";
	public static final String CHECKIN_DIALOG_TIME = "checkin_dialog_time";
	public static final String PHONE_NUMBER = "device_phone_number";

	// V.BTTN Setting configuration for long press.
	public static final String LONG_PRESS_VBTTN_CONFIGURE = "VBTTN_ALERT";
	
	public static final String IS_FALL_DETECTION_ENABLED = "is_fall_detection_enabled";

	public static final String IS_NO_MOTION_ENABLED = "no_motion_enabled";

	public static final String NO_MOTION_TIMEOUT = "no_motion_timeout";

	public static final String IS_WATCH_VIBRATION = "is_watch_vibration";
	public static final String MOTION_SENSITIVITY = "motion_sensitivity";
	public static final String IS_PHONE_NO_MOTION_ENABLED = "phone_no_motion_enabled";
	public static final String PHONE_NO_MOTION_TIMEOUT = "phone_no_motion_timeout";
	public static final String PHONE_MOTION_SENSITIVITY = "phone_motion_sensitivity";

	public static final String IS_SILENT_ALARM_ENABLED = "silent_alarm_enabled";
	public static final String IS_VISUAL_ALARM_ENABLED = "visual_alarm_enabled";
	public static final String ALARM_ACKNOWLEDGE_TIMEOUT = "alarm_acknowledge_timeout";
	public static final String GSM_LOST_ALARM_TIMEOUT = "gsm_lost_alarm_timeout";
	public static final String LOW_BATTERY_LEVEL = "low_battery_alarm_level";
	public static final String SOS_BATTERY_LEVEL = "sos_battery_level";
	public static final String IS_GSM_LOST_ENABLED = "gsm_lost_alarm_enabled";
	public static final String LONER_LANGUAGE = "language";
	public static final String IS_FDU_REQUIRED = "fdu_required";
	public static final String IS_CHECKIN_ON_FDU_MOTION = "checkin_on_fdu_motion";
	public static final String AUTOMATIC_SHUTDOWN_THRESHOLD = "automatic_shutdown_threshold";
	public static final String IS_ALARM_VIBRATION_ENABLED = "alarm_vibration_enabled";
	public static final String IS_PHONE_ALARM_VIBRATION_ENABLED = "phone_alarm_vibration_enabled";
	public static final String IS_DATA_LOGGING_ENABLED = "data_logging_enabled";
	public static final String IS_DEVELOPER_MODE_ENABLED = "developer_mode_enabled";
	public static final String IS_DEBUG_MODE_ENABLED = "isDEbugModeEnabled";
	public static final String INDEX_OF_STAGING_SERVER = "index_of_staging_server";
    public static final String IS_PROGRAMABLE_KEY_ENABLED = "isProgramableKeyEnabled";
    public static final String ALERT_FOR_PROGRAMABLE_KEY = "alertForProgramableKey";
	public static final String DATE_APP_VERSION_CHECK = "dateAppVersionCheck";

	public static final String SERVER_APP_VERSION = "serverAppVersionCheck";
	public static final String VBTTN_BATTERY_STATUS = "vbttnBatteryStatus";
	public static final String VBTTN_BATTERY_VALUE = "vbttnBatteryValue";
	public static final String LOCALE_VALUE = "localeValue";
	public static final String LANGUAGE_NAME = "langauge_name";
	public static final String CURRENT_BLE_ADDRESS = "currentBleDeviceAddress";
	public static final String MANUAL_CHECK_IN = "manual_check_in";
	public static final String IS_PERMISSION_GRANTED = "isPermissionGranted";

	public static final String node_id = "node_id";


	/**
	 * saves the string that for a particular attribute in the shared
	 * preferences
	 * @param con Context
	 * @param variable String
	 * @param data String
	 */
	public static void saveStringData(Context con, String variable, String data) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(con);
		prefs.edit().putString(variable, data).commit();
	}
	
	/**
	 * get the string data from the shared preferences in accordance with the
	 * attribute pass to the function
	 * @param con Context
	 * @param variable String
	 * @param defaultValue String
	 * @return String value
	 */
	public static String getStringData(Context con, String variable, String defaultValue) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(con);
		String data = prefs.getString(variable, defaultValue);
		return data;
	}
	public static String getStringDataId(Context con, String variable, String defaultValue) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(con);
		String data = prefs.getString(variable, null);
		return data;
	}
	/**
	 * saves the boolean that for a particular attribute in the shared
	 * preferences
	 * @param con Context
	 * @param variable String
	 * @param data boolean value
	 */
	public static void saveBooleanData(Context con, String variable,  boolean data) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(con);
		prefs.edit().putBoolean(variable, data).commit();
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
		boolean data = prefs.getBoolean(variable, defaultValue);
		return data;
	}
	
	/**
	 * saves the Integer that for a particular attribute in the shared
	 * preferences
	 * @param con Context
	 * @param variable string
	 * @param data Integer
	 */
	public static void saveIntData(Context con, String variable, int data) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(con);
		prefs.edit().putInt(variable, data).commit();
	}


	/**
	 * get the Integer data from the shared preferences in accordance with the
	 * attribute pass to the function
	 * @param con Context
	 * @param variable string
	 * @param defaultValue integer
	 * @return integer value
	 */
	public static int getIntData(Context con, String variable, int defaultValue) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(con);
		int data = prefs.getInt(variable, defaultValue);
		return data;
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
		prefs.edit().putLong(variable, data).commit();
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
		long data = prefs.getLong(variable, defaultValue);
		return data;
	}
}
