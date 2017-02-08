package rtsp.audio.andy.unomic.unomicaudiortsp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Beeoh on 2016-08-16.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE FREQUENCY (id INTEGER PRIMARY KEY AUTOINCREMENT, date DATE, frequency INTEGER);");
    }

    public void insert(String create_at, String date, int frequency) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL("INSERT INTO FREQUENCY VALUES(null, '" + date + "', '" + frequency + "')");
            db.close();
            Log.d("Success", "Success");
        } catch (Exception e) {
            db.close();
            Log.d("Failed", "Failed");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
