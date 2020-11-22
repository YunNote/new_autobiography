package com.example.study.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.study.helper.DataBaseHelper;
import com.example.study.vo.SilverTownVO;
import com.example.study.vo.UserVO;

import java.util.ArrayList;
import java.util.List;

public class SQLiteUtils {

    DataBaseHelper helper;
    SQLiteDatabase sqlite;

    public SQLiteUtils(DataBaseHelper helper) {
        this.helper = helper;
    }

    public void insertUser(UserVO user){
        sqlite = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(helper.USER_ID , user.getId());
        contentValues.put(helper.USER_PASSWORD , user.getPassword());
        contentValues.put(helper.USER_NAME, user.getName());
        contentValues.put(helper.USER_EMAIL, user.getEmail());
        contentValues.put(helper.USER_ADDRESS, user.getAddress());
        contentValues.put(helper.USER_BIRTH, user.getBirth());
        contentValues.put(helper.USER_GRADE, user.getGrade());
        contentValues.put(helper.USER_INVITE, user.getIdx());
        contentValues.put(helper.USER_CASH  , user.getCash());

        sqlite.insert(helper.USER_TABLE_NAME, null , contentValues);
    }

    public UserVO checkUser(String id, String password) {
        sqlite = helper.getReadableDatabase();
        UserVO user = null;
        String selectUser = "SELECT * FROM " + helper.USER_TABLE_NAME + " WHERE " + helper.USER_ID + " = '"
                + id + "' AND " + helper.USER_PASSWORD + " = '" + password + "';";

        Cursor cursor = sqlite.rawQuery(selectUser, null);

        while (cursor.moveToNext()) {

            user = new UserVO(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getInt(8),
                    cursor.getInt(9)
            );
        }

        return user;
    }

    public List<SilverTownVO> findSilverTownBySearchKeyword (String searchKeyword) {
        sqlite = helper.getReadableDatabase();
        String selectSilverTown = "SELECT * FROM " + helper.SILVER_TABLE_NAME + " ";

        if(searchKeyword.length() > 0){
            selectSilverTown = selectSilverTown + "WHERE " + helper.SILVER_ADDRESS + " LIKE '%" + searchKeyword + "%' ";
        }

        selectSilverTown += ";";


        Cursor cursor = sqlite.rawQuery(selectSilverTown, null);
        List<SilverTownVO> silverTownVOList = new ArrayList<>();

        while (cursor.moveToNext()) {

            silverTownVOList.add(new SilverTownVO(
                    cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
            ));
        }

        return silverTownVOList;
    }


    public void close() {
        sqlite.close();
        helper.close();
    }
}
