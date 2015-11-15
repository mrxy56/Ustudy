package sg.edu.nus.ustudy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by Mac on 15/7/18.
 */
public class contactDB {
    final Context context;
    contactDBHelper DBHelper;
    SQLiteDatabase db;

    public contactDB(Context ctx){
        this.context = ctx;
        DBHelper = new contactDBHelper(this.context);
    }

    public contactDB open(){
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        DBHelper.close();
    }

    public long insertContact(String name,String number){
        ContentValues initialValues = new ContentValues();

        initialValues.put(contactDBHelper.COLUMN_NAME_CONTACTNAME, name);

        initialValues.put(contactDBHelper.COLUMN_NAME_CONTACTNUMBER, number);

        return db.insert(contactDBHelper.TABLE_NAME,null,initialValues);
    }

    public Cursor getAllContacts(){
        return db.query(contactDBHelper.TABLE_NAME, new String[]{
                        contactDBHelper.COLUMN_NAME_ID,
                        contactDBHelper.COLUMN_NAME_CONTACTNAME,
                        contactDBHelper.COLUMN_NAME_CONTACTNUMBER},
                null, null, null, null, null);
    }

    public Cursor getContact(long rowId){
        Cursor mCursor =
                db.query(true,contactDBHelper.TABLE_NAME,
                        new String[]{
                                contactDBHelper.COLUMN_NAME_ID,
                                contactDBHelper.COLUMN_NAME_CONTACTNAME,
                                contactDBHelper.COLUMN_NAME_CONTACTNUMBER},
                        contactDBHelper.COLUMN_NAME_ID + "=" + rowId,
                        null,null,null,null,null);

                    if(mCursor!=null) {
                        mCursor.moveToFirst();
                    }
        return mCursor;
    }

    public boolean deleteContact(long rowId){
        return db.delete(contactDBHelper.TABLE_NAME,
                contactDBHelper.COLUMN_NAME_ID + "=" +rowId,null)>0;
    }

    public boolean updateContact(long rowId,String name,String number){
        ContentValues initialValues = new ContentValues();
        initialValues.put(contactDBHelper.COLUMN_NAME_CONTACTNAME,name);

        initialValues.put(contactDBHelper.COLUMN_NAME_CONTACTNUMBER,number);

        return db.update(contactDBHelper.TABLE_NAME, initialValues, contactDBHelper.COLUMN_NAME_ID + "=" +
                rowId, null)>0;
    }

    public boolean deleteAll() {
        return db.delete(contactDBHelper.TABLE_NAME,
                null,null)>0;
    }
}
