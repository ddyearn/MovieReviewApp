package ddwucom.mobile.finalreport;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.net.URL;
import java.util.ArrayList;

public class MovieDBManager {

    MovieDBHelper movieDBHelper;
    Cursor cursor = null;


    public MovieDBManager(Context context) {
        movieDBHelper = new MovieDBHelper(context);
    }


    // 모든 movie 반환
    public ArrayList<Movie> getAllMovie() {
        ArrayList movieList = new ArrayList();
        SQLiteDatabase db = movieDBHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + MovieDBHelper.TABLE_NAME, null);

        while (cursor.moveToNext()) {

            long id = cursor.getInt(0);
            String title = cursor.getString(1);
            String date = cursor.getString(2);
            String rate = cursor.getString(3);
            String characters = cursor.getString(4);
            String director = cursor.getString(5);
            String review = cursor.getString(6);
            int img = (int) id;
            movieList.add(new Movie(id, title, date, rate, characters, director, review, img));
            // cursor.getColumnIndex(MovieDBHelper.COL_ID)
        }
        close();

        return movieList;
    }

    // 새로운 movie 추가
    public boolean addNewMovie(Movie newMovie) {
        SQLiteDatabase db = movieDBHelper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(MovieDBHelper.COL_TITLE, newMovie.getTitle());
        value.put(MovieDBHelper.COL_DATE, newMovie.getReleaseDate());
        value.put(MovieDBHelper.COL_RATE, newMovie.getRate());
        value.put(MovieDBHelper.COL_CHARACTERS, newMovie.getCharacters());
        value.put(MovieDBHelper.COL_DIRECTOR, newMovie.getDirector());
        value.put(MovieDBHelper.COL_REVIEW, newMovie.getReview());

        long count = db.insert(MovieDBHelper.TABLE_NAME, null, value);

        close();

        if(count > 0) return true;
        return false;
    }

    // _id를 기준으로 movie의 정보 변경
    public boolean modifyMovie(Movie movie) {
        SQLiteDatabase sqLiteDatabase = movieDBHelper.getWritableDatabase();

        ContentValues row = new ContentValues();
        row.put(MovieDBHelper.COL_TITLE, movie.getTitle());
        row.put(MovieDBHelper.COL_DATE, movie.getReleaseDate());
        row.put(MovieDBHelper.COL_RATE, movie.getRate());
        row.put(MovieDBHelper.COL_CHARACTERS, movie.getCharacters());
        row.put(MovieDBHelper.COL_DIRECTOR, movie.getDirector());
        row.put(MovieDBHelper.COL_REVIEW, movie.getReview());
        String whereClause = MovieDBHelper.COL_ID + "=?";
        String[] whereArgs = new String[] { String.valueOf(movie.get_id()) };

        int result = sqLiteDatabase.update(MovieDBHelper.TABLE_NAME, row, whereClause, whereArgs);
        movieDBHelper.close();
        if(result > 0) return true;
        return false;
    }

    // _id 를 기준으로 DB에서 movie 삭제
    public boolean removeMovie(long id) {
        SQLiteDatabase sqLiteDatabase = movieDBHelper.getWritableDatabase();
        String whereClause = MovieDBHelper.COL_ID + "=?";
        String[] whereArgs = new String[] { String.valueOf(id) };
        int result = sqLiteDatabase.delete(MovieDBHelper.TABLE_NAME, whereClause, whereArgs);
        if(result > 0) return true;
        return false;
    }

    // 영화 제목으로 DB 검색
    public ArrayList<Movie> searchMovieByTitle(String title) {
        ArrayList movieList = new ArrayList();
        SQLiteDatabase db = movieDBHelper.getReadableDatabase();
        String selection = MovieDBHelper.COL_TITLE + " LIKE ?";
        String[] selectArgs = new String[]{"%" + title + "%"};

        cursor = db.query(MovieDBHelper.TABLE_NAME, null, selection, selectArgs,
                null, null, null, null);
        while (cursor.moveToNext()) {
            long id = cursor.getInt(0);
            String movieTitle = cursor.getString(1);
            String date = cursor.getString(2);
            String rate = cursor.getString(3);
            String characters = cursor.getString(4);
            String director = cursor.getString(5);
            String review = cursor.getString(6);
            int img = (int) id;
            movieList.add(new Movie(id, movieTitle, date, rate, characters, director, review, img));
            // cursor.getColumnIndex(MovieDBHelper.COL_ID)
        }
        return movieList;
    }

    // close 수행
    public void close() {
        if (movieDBHelper != null) movieDBHelper.close();
        if (cursor != null) cursor.close();
    };
}
