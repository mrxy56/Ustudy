package sg.edu.nus.ustudy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mac on 15/7/18.
 */
public class contactDBHelper extends SQLiteOpenHelper{
    public static final String TABLE_NAME = "contacts";
    public static final String COLUMN_NAME_ID = "_id";
    public static final String COLUMN_NAME_CONTACTNAME = "name";
    public static final String COLUMN_NAME_CONTACTNUMBER = "number";
    private static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME_CONTACTNAME + " TEXT NOT NULL," +
                    COLUMN_NAME_CONTACTNUMBER + " TEXT NOT NULL);";
    private static final String SQL_DELETE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "contactDB";

    public contactDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE);
        onCreate(db);
    }
}
