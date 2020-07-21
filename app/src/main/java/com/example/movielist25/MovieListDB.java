package com.example.movielist25;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class MovieListDB {
    public static String[] list_movies;
    public static String[] list_dates;
    public static String[] list_summary;
    public static String[] list_votes;
    public static String[] list_poster;
    public static String[] list_backdrop;
    public static String[] list_id;
    static final String AUTHORITY_Uri = "content://" + MovieContract.AUTHORITY;

    public boolean isMovieFavorited(ContentResolver contentResolver, int id){
        boolean ret = false;
        Cursor cursor = contentResolver.query(Uri.parse(AUTHORITY_Uri + "/" + id), null, null, null, null, null);
        if (cursor != null && cursor.moveToNext()){
            ret = true;
            cursor.close();
        }
        return ret;
    }

    public void addMovie(ContentResolver contentResolver, int i){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContractList.MovieEntry.COLUMN_ID, MainActivity.id[i]);
        contentValues.put(MovieContractList.MovieEntry.COLUMN_NAME, MainActivity.movies[i]);
        contentValues.put(MovieContractList.MovieEntry.COLUMN_OVERVIEW, MainActivity.summary[i]);
        contentValues.put(MovieContractList.MovieEntry.COLUMN_POSTER, MainActivity.poster[i]);
        contentValues.put(MovieContractList.MovieEntry.COLUMN_BACKDROP, MainActivity.backdrop[i]);
        contentValues.put(MovieContractList.MovieEntry.COLUMN_RATING, MainActivity.votes[i]);
        contentValues.put(MovieContractList.MovieEntry.COLUMN_RELEASE, MainActivity.dates[i]);
        contentResolver.insert(Uri.parse(AUTHORITY_Uri + "/list"), contentValues);
    }

    public void removeMovie(ContentResolver contentResolver, int id){
        Uri uri = Uri.parse(AUTHORITY_Uri + "/" + id);
        contentResolver.delete(uri, null, new String[]{id + ""});
    }

    public void getFavoriteMovies(ContentResolver contentResolver){
        Uri uri = Uri.parse(AUTHORITY_Uri + "/list");
        Cursor cursor = contentResolver.query(uri, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()){

            list_id=new String[cursor.getCount()];
            list_backdrop=new String[cursor.getCount()];
            list_dates=new String[cursor.getCount()];
            list_movies=new String[cursor.getCount()];
            list_poster=new String[cursor.getCount()];
            list_summary=new String[cursor.getCount()];
            list_votes=new String[cursor.getCount()];

            for(int i=0;i<cursor.getCount();i++){
                list_id[i] = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ID))+"";
                list_movies[i] = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME));
                list_summary[i] = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW));
                list_votes[i] = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RATING));
                list_poster[i] = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER));
                list_backdrop[i]=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_BACKDROP));
                list_dates[i]= cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE));
                cursor.moveToNext();
            }
            cursor.close();
        }else{
            list_id=null;
            list_backdrop=null;
            list_dates=null;
            list_movies=null;
            list_poster=null;
            list_summary=null;
            list_votes=null;
        }
    }


}