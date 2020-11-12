package com.example.study.fragment;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.study.R;
import com.example.study.adapter.InviteListAdapter;
import com.example.study.adapter.ItemDecoration;
import com.example.study.vo.AddressVO;
import com.example.study.vo.TestAddressVO;

import java.util.ArrayList;
import java.util.List;

public class InviteUserFragment extends Fragment {

    RecyclerView recyclerView;
    private InviteListAdapter inviteListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        List<TestAddressVO> userList = new ArrayList<>();
        userList.add(new TestAddressVO(1, "img_profile_1", "오준수 목사님", "010-0000-0000"));
        userList.add(new TestAddressVO(2, "img_profile_2", "이준구 학생", "010-0000-0000"));
        userList.add(new TestAddressVO(3, "img_profile_3", "강다니엘", "010-0000-0000"));
        userList.add(new TestAddressVO(4, "img_profile_4", "이원호", "010-0000-0000"));

        View inflate = inflater.inflate(R.layout.fragment_invite, container, false);

        recyclerView = (RecyclerView)inflate.findViewById(R.id.invite_recycler_view);

        recyclerView.setHasFixedSize(true);
//        recyclerView.addItemDecoration(new ItemDecoration(20));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        inviteListAdapter = new InviteListAdapter(getContext(), userList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(inviteListAdapter);

        TextView viewById = container.getRootView().findViewById(R.id.toolbar_title);
        viewById.setText("초대하기");

//        getContacts();


        return  inflate;
    }


    public List<AddressVO> getContacts() {
        // 데이터베이스 혹은 content resolver 를 통해 가져온 데이터를 적재할 저장소를 먼저 정의
        List<AddressVO> datas = new ArrayList<>();

        // 1. Resolver 가져오기(데이터베이스 열어주기)
        // 전화번호부에 이미 만들어져 있는 ContentProvider 를 통해 데이터를 가져올 수 있음
        // 다른 앱에 데이터를 제공할 수 있도록 하고 싶으면 ContentProvider 를 설정
        // 핸드폰 기본 앱 들 중 데이터가 존재하는 앱들은 Content Provider 를 갖는다
        // ContentResolver 는 ContentProvider 를 가져오는 통신 수단
        ContentResolver resolver = getContext().getContentResolver();

        // 2. 전화번호가 저장되어 있는 테이블 주소값(Uri)을 가져오기
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        // 3. 테이블에 정의된 칼럼 가져오기
        // ContactsContract.CommonDataKinds.Phone 이 경로에 상수로 칼럼이 정의
        String[] projection = { ContactsContract.CommonDataKinds.Phone.CONTACT_ID // 인덱스 값, 중복될 수 있음 -- 한 사람 번호가 여러개인 경우
                ,  ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                ,  ContactsContract.CommonDataKinds.Phone.NUMBER};

        // 4. ContentResolver로 쿼리를 날림 -> resolver 가 provider 에게 쿼리하겠다고 요청
        Cursor cursor = resolver.query(phoneUri, projection, null, null, null);

        // 4. 커서로 리턴된다. 반복문을 돌면서 cursor 에 담긴 데이터를 하나씩 추출
        if(cursor != null){
            while(cursor.moveToNext()){
                // 4.1 이름으로 인덱스를 찾아준다
                int idIndex = cursor.getColumnIndex(projection[0]); // 이름을 넣어주면 그 칼럼을 가져와준다.
                int nameIndex = cursor.getColumnIndex(projection[1]);
                int numberIndex = cursor.getColumnIndex(projection[2]);
                // 4.2 해당 index 를 사용해서 실제 값을 가져온다.
                String id = cursor.getString(idIndex);
                String name = cursor.getString(nameIndex);
                String number = cursor.getString(numberIndex);

                AddressVO phoneBook = new AddressVO(id , name , number);
                datas.add(phoneBook);
            }
        }
        // 데이터 계열은 반드시 닫아줘야 한다.
        cursor.close();

        return datas;
    }
}
