package lib.helper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import lib.interfaces.Default;
import lib.interfaces.NotNull;
import lib.interfaces.Persistence;
import lib.interfaces.PrimaryKey;
import lib.interfaces.TableName;
import lib.interfaces.TablePrimaryKey;
import lib.interfaces.TableUnique;
import lib.interfaces.Unique;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * The sqlite helper class
 * 
 * @author quanhv
 *
 */
@SuppressWarnings("rawtypes")
public class SQLiteHelper extends SQLiteOpenHelper {
	private static final int DEFAULT_VERSION = 1;

	public static abstract interface Transaction {
		public abstract boolean execute(SQLiteHelper paramSQLiteDAO);
	}

	// //////////////////////////////////////////////////////////////////
	// -------------------- Constructor & Override ------------------- //
	// //////////////////////////////////////////////////////////////////

	/**
	 * Create database in internal storage with default name
	 * 
	 * @param context
	 *            The application context
	 */
	public SQLiteHelper(Context context) {
		super(context, getDefaultDatabaseName(context), null, DEFAULT_VERSION);
		
		Logger.log("Database path:"+ context.getDatabasePath(getDefaultDatabaseName(context)));
	}

	/**
	 * Create database in internal storage with database name
	 * 
	 * @param context
	 *            The application context
	 * @param databaseName
	 */
	public SQLiteHelper(Context context, String databaseName) {
		super(context, databaseName, null, DEFAULT_VERSION);
	}

	/**
	 * Create database in internal storage with version
	 * 
	 * @param context
	 * @param databaseName
	 * @param version
	 */
	public SQLiteHelper(Context context, String databaseName, int version) {
		super(context, databaseName, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	// //////////////////////////////////////////////////////////////////
	// -------------------- Public Method -------------------- //
	// //////////////////////////////////////////////////////////////////

	/**
	 * To close database
	 */
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}

	/**
	 * TO  execute your SQLite
	 * @param sql
	 */
	public void executeSQL(String sql) {
		getWritableDatabase().execSQL(sql);
	}
	
	// ------------------ CREATE ------------------- //
	/**
	 * To create table
	 * 
	 * @param clz
	 */
	public void createTable(Class<?> clz) {
		createTable(getWritableDatabase(), clz);
	}

	/**
	 * To create list table
	 * 
	 * @param clz
	 */
	public void createTable(Class<?>[] clzs) {
		for (Class<?> clz : clzs)
			createTable(clz);
	}

	// ------------------ DROP ------------------- //

	/**
	 * To drop table
	 * 
	 * @param clz
	 */
	public void dropTable(Class<?> clz) {
		dropTable(getTableName(clz));
	}

	/**
	 * To drop list table
	 * 
	 * @param clz
	 */
	public void dropTable(Class<?>[] clzs) {
		for (Class<?> clz : clzs)
			dropTable(clz);
	}

	// ------------------ DELETE ------------------- //
	/**
	 * 
	 * @param clz
	 *            The class(table) to delete from
	 * @param whereClause
	 *            the optional WHERE clause to apply when deleting. Passing null
	 *            will delete all rows.
	 * @param whereArgs
	 *            You may include ?s in the where clause, which will be replaced
	 *            by the values from whereArgs. The values will be bound as
	 *            Strings.
	 * @return the number of rows affected if a whereClause is passed in, 0
	 *         otherwise. To remove all rows and get a count pass "1" as the
	 *         whereClause.
	 */
	public int delete(Class<?> clz, String whereClause, String[] whereArgs) {
		SQLiteDatabase db = getWritableDatabase();
		try {
			int i = delete(db, clz, whereClause, whereArgs);
			return i;
		} finally {
			if (!db.inTransaction())
				db.close();
		}
	}

	// -------------- INSERT ------------------------- //
	/**
	 * To insert a array list into table
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Long> insertAll(List<?> list) throws Exception {
		SQLiteDatabase db = getWritableDatabase();
		try {
			List result = new ArrayList();
			for (Iterator localIterator = list.iterator(); localIterator
					.hasNext();) {
				Object o = localIterator.next();
				result.add(Long.valueOf(insert(db, o)));
			}
			List localList1 = result;
			return localList1;
		} finally {
			if (!db.inTransaction())
				db.close();
		}
	}

	/**
	 * Insert an array into table
	 * @param objects
	 */
	public void insertAll(ArrayList<Object> objects){
		for(Object o: objects) insert(o);
	}
	
	/**
	 * Insert a row into table
	 * 
	 * @param o
	 * @return
	 */
	public long insert(Object o) {
		SQLiteDatabase db = getWritableDatabase();
		try {
			long l = insert(db, o);
			return l;
		} catch (Exception e) {
			return -1L;
		} finally {
			if (!db.inTransaction())
				db.close();
		}
	}

	// --------------------- UPDATE ---------------------------//
	/**
	 * To update a table with some options
	 * 
	 * @param o
	 *            table need to update
	 * @param whereClause
	 * @param whereArgs
	 * @return
	 */
	public int update(Object o, String whereClause, String[] whereArgs) {
		SQLiteDatabase db = getWritableDatabase();
		try {
			int i = update(db, o, whereClause, whereArgs);
			return i;
		} catch (Exception e) {
			return -1;
		} finally {
			if (!db.inTransaction())
				db.close();
		}

	}

	// --------------- Options for get data ------------------- //
	public <T> List<T> get(Class<T> clz) {
		return get(clz, null, null, null, null, null, null);
	}

	public <T> List<T> get(Class<T> clz, int limit) {
		return get(clz, null, null, null, null, null, null, limit);
	}

	public <T> List<T> get(Class<T> clz, int limit, String selection,
			String[] selectionArgs) {
		return get(clz, null, selection, selectionArgs, null, null, null, limit);
	}

	public <T> List<T> get(Class<T> clz, String selection,
			String[] selectionArgs) {
		return get(clz, null, selection, selectionArgs, null, null, null);
	}

	public <T> List<T> get(Class<T> clz, String selection,
			String[] selectionArgs, int limit) {
		return get(clz, null, selection, selectionArgs, null, null, null, limit);
	}

	public <T> List<T> get(Class<T> clz, String selection,
			String[] selectionArgs, String orderBy) {
		return get(clz, null, selection, selectionArgs, null, null, orderBy);
	}

	public <T> List<T> get(Class<T> clz, String selection,
			String[] selectionArgs, String orderBy, int limit) {
		return get(clz, null, selection, selectionArgs, null, null, orderBy,
				limit);
	}

	public <T> List<T> get(Class<T> clz, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		return get(clz, null, selection, selectionArgs, groupBy, having,
				orderBy);
	}

	public <T> List<T> get(Class<T> clz, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy, int limit) {
		return get(clz, null, selection, selectionArgs, groupBy, having,
				orderBy, limit);
	}

	public <T> List<T> get(Class<T> clz, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		return get(clz, columns, selection, selectionArgs, groupBy, having,
				orderBy, -1);
	}

	public <T> List<T> get(Class<T> clz, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy, int limit) {
		SQLiteDatabase db = getReadableDatabase();
		return get(db, clz, columns, selection, selectionArgs, groupBy, having,
				orderBy, limit);
	}

	// ----------------- Options for count query ------------------ //

	public <T> int count(Class<T> clz) {
		return count(clz, null, null, null, null, null, null);
	}

	public <T> int count(Class<T> clz, int limit) {
		return count(clz, null, null, null, null, null, null, limit);
	}

	public <T> int count(Class<T> clz, int limit, String selection,
			String[] selectionArgs) {
		return count(clz, null, selection, selectionArgs, null, null, null,
				limit);
	}

	public <T> int count(Class<T> clz, String selection, String[] selectionArgs) {
		return count(clz, null, selection, selectionArgs, null, null, null);
	}

	public <T> int count(Class<T> clz, String selection,
			String[] selectionArgs, int limit) {
		return count(clz, null, selection, selectionArgs, null, null, null,
				limit);
	}

	public <T> int count(Class<T> clz, String selection,
			String[] selectionArgs, String orderBy) {
		return count(clz, null, selection, selectionArgs, null, null, orderBy);
	}

	public <T> int count(Class<T> clz, String selection,
			String[] selectionArgs, String orderBy, int limit) {
		return count(clz, null, selection, selectionArgs, null, null, orderBy,
				limit);
	}

	public <T> int count(Class<T> clz, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		return count(clz, null, selection, selectionArgs, groupBy, having,
				orderBy);
	}

	public <T> int count(Class<T> clz, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy, int limit) {
		return count(clz, null, selection, selectionArgs, groupBy, having,
				orderBy, limit);
	}

	// //////////////////////////////////////////////////////////////////
	// -------------------- Private & Support Method ----------------- //
	// //////////////////////////////////////////////////////////////////

	/**
	 * To convert fields of class o to content values
	 * 
	 * @param o
	 *            Class convert
	 * @return content values
	 */
	private ContentValues createContentValues(Object o) {
		try {
			ContentValues values = new ContentValues();
			for (Field f : getPersistenceFields(o.getClass())) {
				if ((f.isAnnotationPresent(PrimaryKey.class))
						&& (((PrimaryKey) f.getAnnotation(PrimaryKey.class))
								.autoIncrement()))
					continue;
				Class type = f.getType();
				String name = f.getName();

				if (type == Integer.TYPE) {
					values.put(name, Integer.valueOf(f.getInt(o)));
				} else if (type == Long.TYPE) {
					values.put(name, Long.valueOf(f.getLong(o)));
				} else if (type == Float.TYPE) {
					values.put(name, Float.valueOf(f.getFloat(o)));
				} else if (type == Double.TYPE) {
					values.put(name, Double.valueOf(f.getDouble(o)));
				} else if (type == String.class) {
					values.put(name, (String) f.get(o));
				} else if (type == Byte.class) {
					values.put(name, (byte[]) f.get(o));
				} else if (type == Boolean.TYPE) {
					values.put(name, Boolean.valueOf(f.getBoolean(o)));
				} else if (type == Short.TYPE) {
					values.put(name, Short.valueOf(f.getShort(o)));
				} else if (type == Date.class) {
					Date d = (Date) f.get(o);
					values.put(name, d != null ? Long.valueOf(d.getTime())
							: null);
				} else if (type.isEnum()) {
					Enum e = (Enum) f.get(o);
					values.put(name, e != null ? e.name() : null);
				}
			}
			return values;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * To get default name
	 * 
	 * @param context
	 *            The application context
	 * @return
	 */
	private static String getDefaultDatabaseName(Context context) {
		return context.getClass().getSimpleName();
	}

	/**
	 * To get all persistence fields
	 * 
	 * @param clz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Set<Field> getPersistenceFields(Class<?> clz) {
		Set fields = new HashSet();
		for (Field f : clz.getDeclaredFields()) {
			if (f.isAnnotationPresent(Persistence.class)) {
				f.setAccessible(true);
				fields.add(f);
			}
		}
		return fields;
	}

	/**
	 * To get table name from class
	 * 
	 * @param clz
	 * @return
	 */
	private String getTableName(Class<?> clz) {
		if (clz.isAnnotationPresent(TableName.class)) {
			return ((TableName) clz.getAnnotation(TableName.class)).value();
		}
		return clz.getSimpleName();
	}

	/**
	 * Create table
	 * 
	 * @param db
	 * @param clz
	 */
	private void createTable(SQLiteDatabase db, Class<?> clz) {
		String tableName = getTableName(clz);
		StringBuilder columnDefs = new StringBuilder();

		for (Field field : getPersistenceFields(clz)) {
			columnDefs.append(",").append(field.getName());

			Class type = field.getType();

			columnDefs.append(" ").append(SQLBuildHelper.getSQLType(type));

			if (field.isAnnotationPresent(PrimaryKey.class)) {
				PrimaryKey annotation = (PrimaryKey) field
						.getAnnotation(PrimaryKey.class);
				columnDefs.append(SQLBuildHelper.getPrimaryKeySQL(annotation));
			}

			if (field.isAnnotationPresent(NotNull.class)) {
				NotNull annotation = (NotNull) field
						.getAnnotation(NotNull.class);
				columnDefs.append(SQLBuildHelper.getNotNullSQL(annotation));
			}

			if (field.isAnnotationPresent(Unique.class)) {
				Unique annotation = (Unique) field.getAnnotation(Unique.class);
				columnDefs.append(SQLBuildHelper.getUniqueSQL(annotation));
			}

			if (field.isAnnotationPresent(Default.class)) {
				Default annotation = (Default) field
						.getAnnotation(Default.class);
				columnDefs.append(SQLBuildHelper.getDefaultSQL(annotation));
			}

			// if (field.isAnnotationPresent(Collate.class)) {
			// Collate annotation = (Collate) field
			// .getAnnotation(Collate.class);
			// columnDefs.append(SQLBuildHelper.getCollateSQL(annotation));
			// }

		}

		StringBuilder tableConstraints = new StringBuilder();

		if (clz.isAnnotationPresent(TablePrimaryKey.class)) {
			TablePrimaryKey annotation = (TablePrimaryKey) clz
					.getAnnotation(TablePrimaryKey.class);
			tableConstraints.append(",").append(
					SQLBuildHelper.getTablePrimaryKeySQL(annotation));
		}

		if (clz.isAnnotationPresent(TableUnique.class)) {
			TableUnique annotation = (TableUnique) clz
					.getAnnotation(TableUnique.class);
			tableConstraints.append(",").append(
					SQLBuildHelper.getTableUniqueSQL(annotation));
		}

		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE IF NOT EXISTS ").append(tableName);
		sb.append(" (");
		sb.append(columnDefs.substring(1));
		sb.append(tableConstraints.toString());
		sb.append(" )");
		String sql = sb.toString();
		Logger.log(sql);
		db.execSQL(sql);
	}

	private int delete(SQLiteDatabase db, Class<?> clz, String whereClause,
			String[] whereArgs) {
		return db.delete(getTableName(clz), whereClause, whereArgs);
	}

	private void dropTable(String tableName){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + tableName);
	}
	
	/**
	 * Get data in table and cast to list objects
	 * 
	 * @param db
	 * @param clz
	 * @param columns
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> List<T> get(SQLiteDatabase db, Class<T> clz, String[] columns,
			String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy, int limit) {
		Cursor c = query(db, clz, columns, selection, selectionArgs, groupBy,
				having, orderBy, limit);
		List result = new ArrayList();
		try {
			while (c.moveToNext()) {
				Object o = clz.newInstance();
				for (Field f : getPersistenceFields(clz)) {
					int idx = c.getColumnIndex(f.getName());
					if (idx > -1) {
						Class type = f.getType();
						if (!c.isNull(idx)) {
							if (type == Integer.TYPE)
								f.set(o, Integer.valueOf(c.getInt(idx)));
							else if (type == Long.TYPE)
								f.set(o, Long.valueOf(c.getLong(idx)));
							else if (type == Float.TYPE)
								f.set(o, Float.valueOf(c.getFloat(idx)));
							else if (type == Double.TYPE)
								f.set(o, Double.valueOf(c.getDouble(idx)));
							else if (type == String.class)
								f.set(o, c.getString(idx));
							else if (type == Byte.class)
								f.set(o, c.getBlob(idx));
							else if (type == Boolean.TYPE)
								f.set(o, Boolean.valueOf(c.getInt(idx) == 1));
							else if (type == Short.TYPE)
								f.set(o, Short.valueOf(c.getShort(idx)));
							else if (type == Date.class)
								f.set(o, new Date(c.getLong(idx)));
							else if (type.isEnum()) {
								f.set(o, Enum.valueOf(type, c.getString(idx)));
							}

						} else if ((type == Integer.TYPE)
								|| (type == Long.TYPE) || (type == Float.TYPE)
								|| (type == Double.TYPE)
								|| (type == Short.TYPE))
							f.set(o, Integer.valueOf(0));
						else if (!type.isPrimitive()) {
							f.set(o, null);
						}
					}
				}
				result.add(o);
			}
			List localList1 = result;
			return localList1;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			if (!db.inTransaction()) {
				c.close();
			}
		}
		return null;
	}

	/**
	 * To insert a row into table
	 * 
	 * @param db
	 * @param o
	 * @return
	 */
	private long insert(SQLiteDatabase db, Object o) {
		ContentValues values = createContentValues(o);
		if (values != null) {
			return db.insert(getTableName(o.getClass()), null, values);
		}
		return -1L;
	}

	public Cursor query(Class<?> clz) {
		return query(clz, null, null, null, null, null, null);
	}

	public Cursor query(Class<?> clz, int limit) {
		return query(clz, null, null, null, null, null, null, limit);
	}

	public Cursor query(Class<?> clz, int limit, String selection,
			String[] selectionArgs) {
		return query(clz, null, selection, selectionArgs, null, null, null,
				limit);
	}

	public Cursor query(Class<?> clz, String selection, String[] selectionArgs) {
		return query(clz, null, selection, selectionArgs, null, null, null);
	}

	public Cursor query(Class<?> clz, String selection, String[] selectionArgs,
			int limit) {
		return query(clz, null, selection, selectionArgs, null, null, null,
				limit);
	}

	public Cursor query(Class<?> clz, String selection, String[] selectionArgs,
			String orderBy) {
		return query(clz, null, selection, selectionArgs, null, null, orderBy);
	}

	public Cursor query(Class<?> clz, String selection, String[] selectionArgs,
			String orderBy, int limit) {
		return query(clz, null, selection, selectionArgs, null, null, orderBy,
				limit);
	}

	public Cursor query(Class<?> clz, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy) {
		return query(clz, null, selection, selectionArgs, groupBy, having,
				orderBy);
	}

	public Cursor query(Class<?> clz, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy, int limit) {
		return query(clz, null, selection, selectionArgs, groupBy, having,
				orderBy, limit);
	}

	public Cursor query(Class<?> clz, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		return query(clz, columns, selection, selectionArgs, groupBy, having,
				orderBy, -1);
	}

	public Cursor query(Class<?> clz, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy, int limit) {
		SQLiteDatabase db = getReadableDatabase();
		return query(db, clz, columns, selection, selectionArgs, groupBy,
				having, orderBy, limit);
	}

	private Cursor query(SQLiteDatabase db, Class<?> clz, String[] columns,
			String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy, int limit) {
		if (limit >= 0) {
			return db.query(getTableName(clz), columns, selection,
					selectionArgs, groupBy, having, orderBy,
					Integer.toString(limit));
		}
		return db.query(getTableName(clz), columns, selection, selectionArgs,
				groupBy, having, orderBy);
	}

	public void transaction(Transaction t) {
		SQLiteDatabase db = getWritableDatabase();
		db.beginTransaction();
		try {
			if (t.execute(this))
				db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

	/**
	 * Update with clauses and args
	 * 
	 * @param db
	 * @param o
	 * @param whereClause
	 * @param whereArgs
	 * @return
	 */
	private int update(SQLiteDatabase db, Object o, String whereClause,
			String[] whereArgs) {
		ContentValues values = createContentValues(o);
		if (values != null) {
			return db.update(getTableName(o.getClass()), values, whereClause,
					whereArgs);
		}
		return -1;
	}

	

	public <T> int count(Class<T> clz, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		return count(clz, columns, selection, selectionArgs, groupBy, having,
				orderBy, -1);
	}

	public <T> int count(Class<T> clz, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy, int limit) {
		SQLiteDatabase db = getReadableDatabase();
		try {
			int i = count(db, clz, columns, selection, selectionArgs, groupBy,
					having, orderBy, limit);
			return i;
		} catch (Exception e) {
			return -1;
		} finally {
			if (!db.inTransaction())
				db.close();
		}
	}

	private <T> int count(SQLiteDatabase db, Class<T> clz, String[] columns,
			String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy, int limit) {
		Cursor c = query(db, clz, columns, selection, selectionArgs, groupBy,
				having, orderBy, limit);
		int count = c.getCount();
		if (!db.inTransaction()) {
			c.close();
		}
		return count;
	}

}