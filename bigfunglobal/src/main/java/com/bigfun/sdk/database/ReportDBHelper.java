package com.bigfun.sdk.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ReportDBHelper extends SQLiteOpenHelper {

    private static final String CREATE_TABLE = "create table event(" +
            "id integer primary key autoincrement," +
            "actionType text," +
            "actionContent text)";

    public ReportDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(SQLiteDatabase db, String actionType, String actionContent) {
        db.execSQL("insert into event (actionType,actionContent) values(?,?)", new String[]{actionType, actionContent});
    }

    public void delete(SQLiteDatabase db) {
        db.execSQL("DELETE FROM event");
    }

    public List<EventBean> query(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("select * from event", null);
        List<EventBean> events = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                String actionType = cursor.getString(cursor.getColumnIndex("actionType"));
                String actionContent = cursor.getString(cursor.getColumnIndex("actionContent"));
                EventBean eventBean = new EventBean();
                eventBean.setActionType(actionType);
                eventBean.setActionContent(actionContent);
                events.add(eventBean);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return events;
    }
}
