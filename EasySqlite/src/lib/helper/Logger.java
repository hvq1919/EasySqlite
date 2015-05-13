package lib.helper;

import android.util.Log;

class Logger {
	private static final String TAG = "TAG";
	private static final boolean shouldLog = true;

	public static void log(String sql) {
		if (shouldLog)
			Log.d(TAG, sql);
	}
}