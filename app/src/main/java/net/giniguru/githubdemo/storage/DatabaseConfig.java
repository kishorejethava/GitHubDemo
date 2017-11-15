package net.giniguru.githubdemo.storage;

import android.database.sqlite.SQLiteDatabase;

public abstract class DatabaseConfig {
	
	/**
	 * Override this method and execute the table creation 
	 * queries using <CODE>DatabaseHelper.executeSQL()</CODE> method.
	 */
	public abstract void onCreate(SQLiteDatabase db) throws Exception;
	
	
	/**
	 * Override this method and execute the upgrade 
	 * queries using <CODE>DatabaseHelper.executeSQL()</CODE> method.
	 * @param db
	 * @throws Exception
	 */
	public abstract void onUpgrade(SQLiteDatabase db) throws Exception;
	
}
