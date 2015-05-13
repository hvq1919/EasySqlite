package com.example.easysqlite;

import java.util.ArrayList;
import java.util.List;

import lib.helper.SQLiteHelper;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends Activity {
	private static final String TAG = "TAG";

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        simpleTest();
        
    }
    
    
    private void simpleTest() {
		Log.d(TAG, "//////////-- on simpleTest -- ////////");
		/* Create data to test */
		ArrayList<UserSqlite> mUsers = createUser();

		/* Create database with default name */
		SQLiteHelper mSqLiteHelper = new SQLiteHelper(getApplicationContext());

		/* CREATE User table */
		mSqLiteHelper.createTable(UserSqlite.class);
		// You can create list table
		// mSqLiteHelper.createTable(new Class[]{User.class,User2.class,....});

		/* INSERT row into table. In this sample, it will create 5 rows with the
		 * statements below
		 */
		for (UserSqlite u : mUsers)
			mSqLiteHelper.insert(u);// INSERT INTO User(id,name,...) VALUES(....)

		/* GET data from table */
		List<UserSqlite> listUsers = mSqLiteHelper.get(UserSqlite.class); // SELECT * FROM USER
		// You can get Table with others options 
		// Type mSqLiteHelper.get and (CTR+SPACE) to see others options

		showList(listUsers);




//		/* UPDATE */
//		User user = new User();
//		user.setLastName("New LastName");
//		mSqLiteHelper.update(user, "firstName=?", new String[]{"firstName2"}); 
//		// UPDATE User SET lastName='New LastName' WHERE firstName='firstName2'
//		Log.d(TAG, "// --------------Data after update------------// ");
//		showList(mSqLiteHelper.get(User.class));


//		/* DELETE */
//		mSqLiteHelper.delete(User.class, "firstName=?", new String[]{"firstName4"});
//		// DELETE FROM User WHERE lastName='New LastName'
//		Log.d(TAG, "// --------------Data after Delete ------------// ");
//		showList(mSqLiteHelper.get(User.class));
		
//		/* COUNT , you can use others options */
//		int count = mSqLiteHelper.count(User.class, "lastName=?",  new String[]{"LASTNAME"});
//		Log.d(TAG, "// --------------COUNT  ------------// :" + count ); 
//		
		
//		
//
//		/* DROP table, you can drop list table too*/
//		mSqLiteHelper.dropTable(User.class);
//

	}
	
	
	private void showList(List<UserSqlite> listUsers) {
		Log.d(TAG, "//------------------ Get Lists --------------//");
		for (UserSqlite u : listUsers) {
			Log.d(TAG, "//////////-- Row -- ////////");
			
			// TODO notes: this value will be autoIncrement 
			Log.d("TAG", "ID:" + u.get_id());
			
			Log.d("TAG", "age:" + u.getAge());
			Log.d("TAG", "first name:" + u.getFirstName());
			Log.d("TAG", "last name:" + u.getLastName());

			// TODO Notes: Email will be return null ,
			// Because the email field not implement a @Persistence interface, so
			// SQLHelper will not create this field in table. See more in User object
			Log.d("TAG", "email:" + u.getEmail());

		}

	}
	
	/**
	 * Create list user to test
	 * 
	 * @return list of user
	 */
	private ArrayList<UserSqlite> createUser() {
		ArrayList<UserSqlite> users = new ArrayList<UserSqlite>();

		for (int i = 0; i < 5; i++) {
			UserSqlite user = new UserSqlite();
			user.set_id(i);
			user.setAge(i + 10);
			user.setEmail("Email" + i);
			user.setFirstName("firstName" + i);
			user.setLastName("LASTNAME");
			user.setMale(true);
			users.add(user);
		}

		return users;
	}
	
}
