package ddwucom.mobile.finalreport;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.Nullable;

public class MovieDBHelper extends SQLiteOpenHelper {

    final static String TAG = "MovieDBHelper";
    final static String DB_NAME = "movies.db";
    public final static String TABLE_NAME = "movie_table";
    public static String COL_ID = "_id";
    public final static String COL_TITLE = "title";
    public final static String COL_DATE = "date";
    public final static String COL_RATE = "rate";
    public final static String COL_CHARACTERS = "characters";
    public final static String COL_DIRECTOR = "director";
    public final static String COL_REVIEW = "review";
    public final static String COL_IMAGE = "image";

    public MovieDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " integer primary key autoincrement, " +
                COL_TITLE + " TEXT, " + COL_DATE + " TEXT, " + COL_RATE + " TEXT, " +
                COL_CHARACTERS + " TEXT, " + COL_DIRECTOR + " TEXT, " + COL_REVIEW + " TEXT)";
        Log.d(TAG, sql);
        sqLiteDatabase.execSQL(sql);
        insertSample(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        onCreate(db);
        if(i1 < 2) {
            try {
                db.beginTransaction();
                db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN "
                        + COL_CHARACTERS + " TEXT, " + COL_DIRECTOR + " TEXT, "
                        + COL_REVIEW + " TEXT, " + COL_IMAGE + " integer DEFAULT " + R.mipmap.ic_launcher);
                db.setTransactionSuccessful();
            } catch (IllegalStateException e) {

            } finally {
                db.endTransaction();
            }
        }
        if(i1 < 3) {
            try {
                db.beginTransaction();
                db.execSQL("CREATE TABLE movie_table2 (" + COL_ID + " integer primary key autoincrement, " +
                        COL_TITLE + " TEXT, " + COL_DATE + " TEXT, " + COL_RATE + " TEXT, " +
                        COL_CHARACTERS + " TEXT, " + COL_DIRECTOR + " TEXT, " + COL_REVIEW + " TEXT, " + COL_IMAGE + " BLOB)");
                db.execSQL("DROP TABLE " + TABLE_NAME);
                db.execSQL("ALTER TABLE movie_table2 RENAME TO " + TABLE_NAME);
                db.setTransactionSuccessful();
            } catch (IllegalStateException e) {

            } finally {
                db.endTransaction();
            }
        }
        if(i1 < 4) {
            try {
                db.beginTransaction();
                db.execSQL("CREATE TABLE movie_table2 (" + COL_ID + " integer primary key autoincrement, " +
                        COL_TITLE + " TEXT, " + COL_DATE + " TEXT, " + COL_RATE + " TEXT, " +
                        COL_CHARACTERS + " TEXT, " + COL_DIRECTOR + " TEXT, " + COL_REVIEW + " TEXT, " + COL_IMAGE + " VARCHAR)");
                db.execSQL("DROP TABLE " + TABLE_NAME);
                db.execSQL("ALTER TABLE movie_table2 RENAME TO " + TABLE_NAME);
                db.setTransactionSuccessful();
            } catch (IllegalStateException e) {

            } finally {
                db.endTransaction();
            }
        }
        if(i1 < 5) {
            try {
                db.beginTransaction();
                db.execSQL("CREATE TABLE movie_table2 (" + COL_ID + " integer primary key autoincrement, " +
                        COL_TITLE + " TEXT, " + COL_DATE + " TEXT, " + COL_RATE + " TEXT, " +
                        COL_CHARACTERS + " TEXT, " + COL_DIRECTOR + " TEXT, " + COL_REVIEW + " TEXT)");
                db.execSQL("DROP TABLE " + TABLE_NAME);
                db.execSQL("ALTER TABLE movie_table2 RENAME TO " + TABLE_NAME);
                db.setTransactionSuccessful();
            } catch (IllegalStateException e) {

            } finally {
                db.endTransaction();
            }
        }
    }

    private void insertSample(SQLiteDatabase db) {

        db.execSQL("insert into " + TABLE_NAME + " values (null, '탑건:매버릭', '2022년 6월 22일', '9.57', '톰 크루즈', '조셉 코신스키', '배우, 인물, 영화의 40년 숙성은 이런 것!');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '범죄도시2', '2022년 5월 18일', '9.04', '마동석, 손석구', '이상용', '총칼 든 악당들아, 마블리의 맨주먹을 받아랏!');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '마녀 Part2. The Other One', '2022년 6월 15일', '6.85', '신시아, 박은빈', '박훈정', '야심이 사기를 높여도 각본은 포즈만 취한다');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '버즈 라이트이어', '2022년 6월 15일', '7.55', '크리스 에반스', '앤거스 맥클레인', '혼재하는 시간대를 바라보는 일은 늘 좀 슬프다');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '쥬라기 월드:도미니언', '2022년 6월 1일', '6.82', '크리스 프랫, 브라이스 달라스 하워드', '콜린 트레보로우', '폐장을 앞둔 테마파크 공룡과 인간들의 혼신의 안간힘');");
    }
}
