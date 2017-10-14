package com.majd.popmovies2.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by majd on 8/29/17.
 */

public class MovieProvider extends ContentProvider {

    private static final int MOVIE=100;
    private static final int MOVIE_ID=101;
    //this class used to access the database itself
    private MovieDBHelper mMovieDBHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();


    public static UriMatcher buildUriMatcher(){

        String content = MovieContract.CONTENT_AUTHORITY;
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(content, MovieContract.PATH_MOVIE, MOVIE);
        matcher.addURI(content, MovieContract.PATH_MOVIE + "/#", MOVIE_ID);

        return matcher;

    }
    @Override
    public boolean onCreate() {
        mMovieDBHelper=new MovieDBHelper(getContext());



        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
         final SQLiteDatabase db=mMovieDBHelper.getReadableDatabase();
        Cursor retCursor;
        switch (sUriMatcher.match(uri)){
            case MOVIE:
                retCursor=db.query(MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case MOVIE_ID:
                long _id = ContentUris.parseId(uri);
                retCursor=db.query(MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        MovieContract.MovieEntry._ID+"=?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(),uri);

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)){
            case MOVIE:
                return "vnd.android.cursor.dir/" + MovieContract.MovieEntry.CONTENT_URI+ "/" + MovieContract.PATH_MOVIE;
            case MOVIE_ID:
                return "vnd.android.cursor.item/" + MovieContract.MovieEntry.CONTENT_URI+ "/" + MovieContract.PATH_MOVIE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);


        }


    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = mMovieDBHelper.getWritableDatabase();
        Uri returnUri;

        switch (sUriMatcher.match(uri)) {
            case MOVIE: {
                long _id = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, contentValues);
                if (_id > 0) {
                    returnUri = MovieContract.MovieEntry.buildMovieUri(_id);
                }
                else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[]  selectionArgs) {

        final SQLiteDatabase db = mMovieDBHelper.getWritableDatabase();
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if (null == selection) selection = "1";
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                rowsDeleted = db.delete(
                        MovieContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db=mMovieDBHelper.getWritableDatabase();
        int rowsUpdated;
        switch (sUriMatcher.match(uri)){
            case MOVIE:
                rowsUpdated=db.update(MovieContract.MovieEntry.TABLE_NAME,
                        contentValues,
                        selection,
                        selectionArgs);
                break;
            case MOVIE_ID:
                String id =uri.getLastPathSegment();
                String where= MovieContract.MovieEntry._ID+" = "+id;
                if(!TextUtils.isEmpty(selection)){
                    where+=" AND "+selection;
                }
                rowsUpdated=db.update(MovieContract.MovieEntry.TABLE_NAME,
                        contentValues,
                        where,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        if (rowsUpdated > 0) {//need revision
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
