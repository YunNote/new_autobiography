package com.example.study;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.study.databinding.ActivityLoginBinding;
import com.example.study.fragment.InviteUserFragment;
import com.example.study.helper.DataBaseHelper;
import com.example.study.utils.SQLiteUtils;
import com.example.study.utils.SharedPreferencesUtils;
import com.example.study.vo.UserVO;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {

    private static String LOG_NAME = "LoginActivity :: ";
    private static ActivityLoginBinding binding = null;
    private Context context = this;
    private final int EXTERNAL_STORAGE_CODE = 4000;
    private DataBaseHelper helper;
    private SQLiteUtils sqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setLoginLayout(this);

        int writeExternalStorage = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readExternalStorage = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (writeExternalStorage != PackageManager.PERMISSION_GRANTED || readExternalStorage != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "Read/Write external storage", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        EXTERNAL_STORAGE_CODE);
            }
        } else {
            helper = new DataBaseHelper(LoginActivity.this, getString(R.string.app_db), null, 1);
            sqlite = new SQLiteUtils(helper);
            helper.getWritableDatabase();
        }

        binding.rightUnderline.setPaintFlags(binding.rightUnderline.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        binding.leftUnderline.setPaintFlags(binding.leftUnderline.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        binding.userId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() > 0) {
                    binding.userIdClear.setVisibility(View.VISIBLE);
                }else {
                    binding.userIdClear.setVisibility(View.INVISIBLE);
                }
            }
        });

        binding.userPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() > 0) {
                    binding.userPasswordClear.setVisibility(View.VISIBLE);
                }else {
                    binding.userPasswordClear.setVisibility(View.INVISIBLE);
                }
            }
        });

        binding.userId.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    loginEvent(v);
                }
                return false;
            }
        });

        binding.userPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    loginEvent(v);
                }
                return false;
            }
        });
    }

    public void clearEditText(View v) {
        switch (v.getId()) {
            case R.id.user_id_clear :
                binding.userId.setText("");
                break;

            case R.id.user_password_clear :
                binding.userPassword.setText("");
                break;
        }
    }

    public void loginEvent(View v) {

        String inputUserId = binding.userId.getText().toString();
        String inputUserPassword = binding.userPassword.getText().toString();

//        if (checkLogin(inputUserId, inputUserPassword)) {
        if (true) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        Toast.makeText(this, getString(R.string.not_match_account), Toast.LENGTH_LONG).show();
    }

    public boolean checkLogin(String userId, String userPassword) {
        UserVO user = sqlite.checkUser(userId, userPassword);

        if (user != null) {
            Gson gson = new Gson();
            String stringUser = gson.toJson(user);
            SharedPreferencesUtils.saveData(this, getString(R.string.user_info), stringUser);

            return true;
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        View currentFocusView = getCurrentFocus();

        if (currentFocusView != null) {
            Rect rect = new Rect();
            currentFocusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if (!rect.contains(x, y)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(currentFocusView.getWindowToken(), 0);
                currentFocusView.clearFocus();
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case EXTERNAL_STORAGE_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    helper = new DataBaseHelper(LoginActivity.this, getString(R.string.app_db), null, 1);
                    sqlite = new SQLiteUtils(helper);
                } else {
                    Toast.makeText(this, "설정에서 파일 저장권한을 허락해줘야 합니다.", Toast.LENGTH_SHORT).show();
                }
            }

            return;
        }
    }

}