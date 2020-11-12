package com.example.study.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public final String USER_TABLE_NAME = "users";
    public final String USER_IDX = "idx";
    public final String USER_ID = "id";
    public final String USER_PASSWORD = "password";
    public final String USER_NAME = "name";
    public final String USER_EMAIL = "email";
    public final String USER_ADDRESS = "address";
    public final String USER_BIRTH = "birth";
    public final String USER_GRADE = "grade";
    public final String USER_INVITE = "invite";
    public final String USER_CASH = "cash";

    public final String SILVER_TABLE_NAME = "silver_town";
    public final String SILVER_IDX = "idx";
    public final String SILVER_TITLE = "title";
    public final String SILVER_DESCRIPTION = "description";
    public final String SILVER_ADDRESS = "address";
    public final String SILVER_NUMBER = "number";
    public final String SILVER_THUMBNAIL = "thumbnail_url";

    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        initUsers(db);
        initSilverTown(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropQuery = "DROP TABLE " + USER_TABLE_NAME + ";";
        db.execSQL(dropQuery);

        this.onCreate(db);
    }

    public void initUsers(SQLiteDatabase db) {
        StringBuilder builder = new StringBuilder();
        builder.append(" CREATE TABLE IF NOT EXISTS ");
        builder.append(USER_TABLE_NAME);
        builder.append(" ( ");
        builder.append(USER_IDX + " INTEGER PRIMARY KEY AUTOINCREMENT , ");
        builder.append(USER_ID + " TEXT NOT NULL , ");
        builder.append(USER_PASSWORD + " TEXT NOT NULL , ");
        builder.append(USER_NAME + " TEXT NOT NULL , ");
        builder.append(USER_EMAIL + " TEXT NOT NULL , ");
        builder.append(USER_ADDRESS + " TEXT NOT NULL , ");
        builder.append(USER_BIRTH + " TEXT NOT NULL , ");
        builder.append(USER_GRADE + " TEXT NOT NULL , ");
        builder.append(USER_INVITE + " INTEGER NOT NULL default 0, ");
        builder.append(USER_CASH + " INTEGER NOT NULL default 0 );");

        db.execSQL(builder.toString());

        String insertDefaultUser = "INSERT INTO " + USER_TABLE_NAME + " values (null, 'happy','1234', '이영길', " +
                " 'yuhan@Yuhan.ac.kr', '경기도 부천시 괴안동 31-5', '1965-11-08', '골드 1단계', 0 , 200000 ); ";

        db.execSQL(insertDefaultUser);
    }

    public void initSilverTown(SQLiteDatabase db) {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS ");
        builder.append(SILVER_TABLE_NAME);
        builder.append(" ( ");
        builder.append(SILVER_IDX + " INTEGER PRIMARY KEY AUTOINCREMENT , ");
        builder.append(SILVER_TITLE + " TEXT NOT NULL , ");
        builder.append(SILVER_NUMBER + " TEXT NOT NULL , ");
        builder.append(SILVER_ADDRESS + " TEXT NOT NULL , ");
        builder.append(SILVER_DESCRIPTION + " TEXT NOT NULL , ");
        builder.append(SILVER_THUMBNAIL + " TEXT NOT NULL );");
        db.execSQL(builder.toString());

        List<String> dummyData = new ArrayList<>();
        dummyData.add("INSERT INTO " + SILVER_TABLE_NAME + " values (null, '청심빌리지', '031-589-5300', '경기도 가평군 미사리로 191-16' " +
                ", '청심빌리지는 섬김과 위함이라는 경영이념으로 입주하고 계신 어르신에게 편안한 안식처를 제공하고 있는 실버타운 청심빌리지 입니다.' ,'csvillage.png' );");

        dummyData.add("INSERT INTO " + SILVER_TABLE_NAME + " values (null, '힐링 실버타운', '031-376-3940', '경기도 오산시 세마역로 49' " +
                ", '저희 힐링실버타운은 최상의 시설과 해독 자가치유 프로그램, 주변환경등을 통하여 어르신들의 삶에 건강과 행복을 드리도록 최선의 노력을 다할 것입니다.' ,'healingsilver.png' );");

        dummyData.add("INSERT INTO " + SILVER_TABLE_NAME + " values (null, '헤리티지너싱홈', '031-8022-0100', ' 경기도 성남시 분당구 대왕판교로 155' " +
                ", '정기적인 진료와 24시간 전문 간호사는 어르신의 건강을 책임지며, 요양보호사는 어르신들을 위해 쾌적하고 청결한 상태를 유지하고 있으며, 물리치료사는 첨단 장비를 이용한 건강 회복에 주력하고 있으며, 사회복지사는 프로그램의 다양화로 즐겁고 활기찬 생활을 드리며, 영양사는 기호도 및 선호도에 따른 건강한 식단을 제공해 드리고 있습니다.' ,'heritage.png' );");

        dummyData.add("INSERT INTO " + SILVER_TABLE_NAME + " values (null, 'The Classic 500', '02-2218-5000', '서울특별시 광진구 능동로 90 더 클래식 500' " +
                ", '최고의 품격과 품위, 여유를 갖추고 자신의 삶을 아름답게 가꾸며 건강하고 즐거운 생활을 선사해 드립니다.' ,'the_classic.png' );");

        dummyData.add("INSERT INTO " + SILVER_TABLE_NAME + " values (null, '더 시그넘하우스', '02-576-4400', '서울시 강남구 자곡로 204-25 (자곡동 664번지)' " +
                ", '한 번의 소중한 인연으로 백 년을 함께 할 아름다운 인연을 꿈꾸며 더 시그넘 하우스가 귀하의 자부심이 될 수 있도록 최고의 브랜드 가치를 실현하겠습니다.' ,'the_signum_haus.png' );");

        dummyData.add("INSERT INTO " + SILVER_TABLE_NAME + " values (null, '더 케이서드 에이지', '055-530-8100', '경남 창녕군 고암면 상대2길 16' " +
                ", '모든 어르신들이 편안하고 풍요로운 노후생활을 보낼 수 있도록 이곳 경남 창녕군에 특급 호텔식 고품격 실버타운인 “The-K 서드에이지” 를 2007년 11월 개원하여 대표주자로 자리매김하고 있습니다. 신체적, 정신적으로 건강한 삶을 유지하면서 생활할 수 있는 제 3의 인생을 더케이 서드에이지에서 맞이할 수 있도록 모든 준비를 해 놓았습니다.' ,'third_age.png' );");


        for (int i = 0; i < dummyData.size(); i++) {
            db.execSQL(dummyData.get(i));
        }
    }
}
