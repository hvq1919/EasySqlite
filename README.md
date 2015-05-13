# EasySqlite
The easy way to use sqlite in android



# **Usage**


 [1].  Create sample Object <br>
       _You create object like normally and implements some interfaces to use this lib :smile: _
   
```
public class UserSqlite {
	@PrimaryKey(autoIncrement = true)
	@Persistence
	private long _id;
	
	@Persistence
	private String firstName;
	@Persistence
	private String lastName;
	
	
	private String email; 
	
	@Persistence
	private int age;
	
	@Persistence
	private boolean isMale;

        // getter & setter 
        ................

```
 [2].  Create database with default name
```
     SQLiteHelper mSqLiteHelper = new SQLiteHelper(getApplicationContext());
```

 [3].   CREATE User table
```
		mSqLiteHelper.createTable(UserSqlite.class);
```
		// You can create list table
		// mSqLiteHelper.createTable(new Class[]{User.class,User2.class,....});

 [4].        INSERT row into table.<br>
                _In this sample, it will create 5 rows with the statements below_
		
```
		for (UserSqlite u : mUsers)
			mSqLiteHelper.insert(u);// INSERT INTO User(id,name,...) VALUES(....)
```
  [5].   GET data from table 
```
		List<UserSqlite> listUsers = mSqLiteHelper.get(UserSqlite.class); // SELECT * FROM USER
```
		// You can get Table with others options 
		// Type mSqLiteHelper.get and (CTR+SPACE) to see others options

  [6].  UPDATE 
```
		User user = new User();
		user.setLastName("New LastName");
		mSqLiteHelper.update(user, "firstName=?", new String[]{"firstName2"}); 
```
		//The above code like : UPDATE User SET lastName='New LastName' WHERE firstName='firstName2'
 [7].   DELETE 
```
		mSqLiteHelper.delete(User.class, "firstName=?", new String[]{"firstName4"});
```
		// DELETE FROM User WHERE lastName='New LastName'

 [8]. COUNT 
```
		int count = mSqLiteHelper.count(User.class, "lastName=?",  new String[]{"LASTNAME"});
```
                _ you can use others options_
 [9].  DROP table
```		
mSqLiteHelper.dropTable(User.class);
```
 _ you can drop list table too_
