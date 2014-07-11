package com.example.haushaltskassen_app;



import java.util.LinkedList;
import java.util.List;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class MySQLiteHelper extends SQLiteOpenHelper {

   // Database Version
   private static final int DATABASE_VERSION = 1;
   // Database Name
   private static final String DATABASE_NAME = "AusgabeDB";

   public MySQLiteHelper(Context context) {
       super(context, DATABASE_NAME, null, DATABASE_VERSION); 
   }

   @Override
   public void onCreate(SQLiteDatabase db) {
       // SQL statement to create ausgabe table
       String CREATE_AUSGABE_TABLE = "CREATE TABLE ausgabe ( " +
               "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
       		"person INTEGER,"+
               "godera INTEGER, "+
               "betrag TEXT, "+"date TEXT"+" )";

       // create ausgabe table
       db.execSQL(CREATE_AUSGABE_TABLE);
       
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // Drop older ausgabe table if existed
       db.execSQL("DROP TABLE IF EXISTS ausgabe");

       // create fresh ausgabe table
       this.onCreate(db);
   }
   //---------------------------------------------------------------------

   /**
    * CRUD operations (create "add", read "get", update, delete) book + get all books + delete all books
    */

   // Ausgabe table name
   public static final String TABLE_AUSGABE = "ausgabe";

   // Ausgabe Table Columns names
  public  static final String KEY_ID = "id";
  public  static final String KEY_PERSON = "person";
  public  static final String KEY_GODERA = "godera";
  public  static final String KEY_BETRAG = "betrag";
  public  static final String KEY_DATE = "date";

    static final String[] COLUMNS = {KEY_ID,KEY_PERSON,KEY_GODERA,KEY_BETRAG,KEY_DATE};
    static final String[] DISPLAY_COLUMNS = {KEY_PERSON,KEY_GODERA,KEY_BETRAG,KEY_DATE};
   public void addAusgabe(Ausgabe ausgabe){
      // Log.d("addAusgabe", ausgabe.toString());
       // 1. get reference to writable DB
       SQLiteDatabase db = this.getWritableDatabase();

       // 2. create ContentValues to add key "column"/value
       ContentValues values = new ContentValues();
       values.put(KEY_PERSON, ausgabe.getPerson()); // get person
       values.put(KEY_GODERA, ausgabe.getGodera()); // get gemeinsam oder ausgelegt
       values.put(KEY_BETRAG, ausgabe.getBetrag());
       values.put(KEY_DATE, ausgabe.getDate());
       // 3. insert
       db.insert(TABLE_AUSGABE, // table
               null, //nullColumnHack
               values); // key/value -> keys = column names/ values = column values
     
       // 4. close
       db.close();
   }

   public Ausgabe getAusgabe(int id){

       // 1. get reference to readable DB
       SQLiteDatabase db = this.getReadableDatabase();

       // 2. build query
       Cursor cursor =
               db.query(TABLE_AUSGABE, // a. table
               COLUMNS, // b. column names
               " id = ?", // c. selections
               new String[] { String.valueOf(id) }, // d. selections args
               null, // e. group by
               null, // f. having
               null, // g. order by
               null); // h. limit
       
       // 3. if we got results get the first one
       if (cursor != null)
           cursor.moveToFirst();

       // 4. build book object
       Ausgabe ausgabe = new Ausgabe();
       ausgabe.setId(Integer.parseInt(cursor.getString(0)));
       ausgabe.setPerson(Integer.parseInt(cursor.getString(1)));
       ausgabe.setGodera(Integer.parseInt(cursor.getString(2)));
       ausgabe.setBetrag(cursor.getString(3));
       ausgabe.setDate(cursor.getString(4));
     //  Log.d("getBook("+id+")", ausgabe.toString());
      // cursor.close();
       // 5. return book
       return ausgabe;
   }

   // Get All Books
   public List<Ausgabe> getAllAusgaben() {
       List<Ausgabe> ausgaben = new LinkedList<Ausgabe>();

       // 1. build the query
       String query = "SELECT  * FROM " + TABLE_AUSGABE;

       // 2. get reference to writable DB
       SQLiteDatabase db = this.getWritableDatabase();
       Cursor cursor = db.rawQuery(query, null);
       
       // 3. go over each row, build book and add it to list
       Ausgabe ausgabe = null;
       if (cursor.moveToFirst()) {
           do {
               ausgabe = new Ausgabe();
               ausgabe.setId(Integer.parseInt(cursor.getString(0)));
               ausgabe.setPerson(Integer.parseInt(cursor.getString(1)));
               ausgabe.setGodera(Integer.parseInt(cursor.getString(2)));
               ausgabe.setBetrag(cursor.getString(3));
               ausgabe.setDate(cursor.getString(4));
               
               // Add book to books
               ausgaben.add(ausgabe);
               
           } while (cursor.moveToNext());
       }

   //  Log.d("getAllAusgabe()","ausg"+ ausgaben.toString());

       // return books
       return ausgaben;
   }

   
    // Updating single book
   public int updateBook(Ausgabe ausgabe) {

       // 1. get reference to writable DB
       SQLiteDatabase db = this.getWritableDatabase();

       // 2. create ContentValues to add key "column"/value
       ContentValues values = new ContentValues();
       values.put(KEY_PERSON, ausgabe.getPerson()); // get person
       values.put(KEY_GODERA, ausgabe.getGodera()); // get gemeinsam oder ausgelegt
       values.put(KEY_BETRAG, ausgabe.getBetrag());
       values.put(KEY_DATE, ausgabe.getDate());
       // 3. updating row
       int i = db.update(TABLE_AUSGABE, //table
               values, // column/value
               KEY_ID+" = ?", // selections
               new String[] { String.valueOf(ausgabe.getId()) }); //selection args

       // 4. close
       db.close();

       return i;

   }

   // Deleting single book
   public void deleteAusgabe(Ausgabe ausgabe) {

       // 1. get reference to writable DB
       SQLiteDatabase db = this.getWritableDatabase();

       // 2. delete
       db.delete(TABLE_AUSGABE,
               KEY_ID+" = ?",
               new String[] { String.valueOf(ausgabe.getId()) });

       // 3. close
       db.close();

       //Log.d("deleteausgabe", ausgabe.toString());

   }
   // Delete All Books
 /*  public void deleteAllAusgaben() {
       // 1. build the query
       String query = "SELECT  * FROM " + TABLE_AUSGABE;

       // 2. get reference to writable DB
       SQLiteDatabase db = this.getWritableDatabase();
       Cursor cursor = db.rawQuery(query, null);

       // 3. go over each row, build book and add it to list
       Ausgabe ausgabe = null;
       if (cursor.moveToFirst()) {
           do {
               ausgabe = new Ausgabe();
               deleteAusgabe(ausgabe);

               
           } while (cursor.moveToNext());
       }

   }  */
   
}