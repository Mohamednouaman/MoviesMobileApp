package dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;



    public class DataBaseManager extends SQLiteOpenHelper {
        private static  final String DATABASE_NAME="MOVIES_YC";
        private static  final  int DATABASE_VERSION=1;

        private static  final String querySqlCreate="create table "+ FeedReaderContract.FeedEntry.TABLE_NAME
                +"("+ FeedReaderContract.FeedEntry._ID+" integer primary key autoincrement"
                +","+ FeedReaderContract.FeedEntry.COLUMN_NAME_USERNAME+" text not null"
                +","+ FeedReaderContract.FeedEntry.COLUMN_NAME_EMAIL+" text not null"
                +","+ FeedReaderContract.FeedEntry.COLUMN_NAME_PASSWORD+" text not null"
                +")";
        private static  final  String querySqlDelete=
                "DROP TABLE IF EXISTS "+FeedReaderContract.FeedEntry.TABLE_NAME;
        public DataBaseManager(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("myTag","onCreate invoked");
            db.execSQL(querySqlCreate);
            Log.i("myTag","onCreate invoked");

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            Log.i("tagUp", "onUpgrade: invoked");
            sqLiteDatabase.execSQL(querySqlDelete);

            this.onCreate(sqLiteDatabase);
            Log.i("tagUp", "onUpgrade: invoked");
        }
        public void insertData(String username,String email,String password){

//          name.replace("'","''");
//        String queryInsert="insert into "+ FeedReaderContract.FeedEntry.TABLE_NAME
//                +"("+ FeedReaderContract.FeedEntry.COLUMN_NAME_NAME
//                +","+ FeedReaderContract.FeedEntry.COLUMN_NAME_SCORE +")"
//                +"VALUES('"+name+"',"+score+")";
            ContentValues contentValues=new ContentValues();
            contentValues.put(FeedReaderContract.FeedEntry.COLUMN_NAME_USERNAME,username);
            contentValues.put(FeedReaderContract.FeedEntry.COLUMN_NAME_EMAIL,email);
            contentValues.put(FeedReaderContract.FeedEntry.COLUMN_NAME_PASSWORD,password);
            SQLiteDatabase  sqLiteDatabase=this.getWritableDatabase();
//                         sqLiteDatabase.beginTransaction();
            sqLiteDatabase.insert(FeedReaderContract.FeedEntry.TABLE_NAME,null,contentValues);
//                         sqLiteDatabase.endTransaction();
//                        sqLiteDatabase.execSQL(queryInsert);
            Log.i("tagInsert","Method insert invoked after");



        }
        public boolean  verifyAccount(String email){
            SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
            Cursor dataCursor = sqLiteDatabase.rawQuery("select "+FeedReaderContract.FeedEntry.COLUMN_NAME_EMAIL+" from " + FeedReaderContract.FeedEntry.TABLE_NAME+" where "+FeedReaderContract.FeedEntry.COLUMN_NAME_EMAIL+"=?", new String[]{email});
          return   dataCursor.moveToNext();

        }
        @SuppressLint("Range")
        public String authenticateUser(String email, String password){
            SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
            Cursor dataCursor = sqLiteDatabase.rawQuery("select username from " + FeedReaderContract.FeedEntry.TABLE_NAME+" where "+FeedReaderContract.FeedEntry.COLUMN_NAME_EMAIL+"=? AND "+FeedReaderContract.FeedEntry.COLUMN_NAME_PASSWORD+"=?", new String[]{email,password});
                   boolean t=false;
                    if(dataCursor!=null){
                       t= dataCursor.moveToNext();
                    }
            return    t ? dataCursor.getString(0):"";
        }

}
