package cn.bingoogolapple.qrcode.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 大姨妈数据表
 * @author Administrator zxm
 *
 */
public class MenstruationDBHelper extends SQLiteOpenHelper {

	public static final String TB_NAME_MT= "menstruation";
	public static final String TB_NAME_MT_CYCLE= "menstruation_cycle";
	public static final String TB_NAME_MT_TIME= "menstruation_time";

	public MenstruationDBHelper(Context context) {
		super(context, "xmjk.db", null, 1);
	}

	public MenstruationDBHelper(Context context, String name,
			SQLiteDatabase.CursorFactory factory, int version) {
		super(context, "xmjk.db", null, 1);
	}

    @Override
    public void onCreate(SQLiteDatabase db) {
    	/**
    	 * 大姨妈平均周期与平均天数�?
    	 */
    	db.execSQL( "CREATE TABLE IF NOT EXISTS " +
    			TB_NAME_MT_CYCLE+ " ( " +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " number  INTEGER, " + //月经天数
                " cycle INTEGER " + //月经周期
                " )"
    		);
    	/**
    	 * 大姨妈开始结束时间等数据�?
    	 */
    	db.execSQL( "CREATE TABLE IF NOT EXISTS " +
    			TB_NAME_MT_TIME+ " ( " +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " date INTEGER, "+ //月份
                " startTime  INTEGER, " + //月经�?��时间
                " endTime INTEGER, " + //月经结束时间
                " cycle  INTEGER, " + //月经周期
                " number  INTEGER " + //月经天数
                " )"
    		);
    	/**
    	 * 大姨妈流量痛经表
    	 */
    	db.execSQL( "CREATE TABLE IF NOT EXISTS " +
    			TB_NAME_MT+ " ( " +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " date INTEGER,"+ //日期
                " quantity  INTEGER, " + //月经流量程度�?~5�?
                " pain  INTEGER " + //痛经程度�?~5�?
                " )"
    		);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + TB_NAME_MT );
        onCreate(db);
    }
}
