package com.example.testui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.testui.fcm.AccessToken;

public class LoginActivity extends AppCompatActivity {
    EditText edt_Username, edt_Password;
    Button btn_Login;
    TextView tv_SignUp;
    LinearLayout lln_SignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setTitleToolbar();
        initUI();
        getAccessToken();
        getDataIntent();
        initListener();

    }

    private void setTitleToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Main Activity");
        }
    }

    private void initUI() {
        edt_Username = findViewById(R.id.edt_Username);
        edt_Password = findViewById(R.id.edt_Password);
        btn_Login = findViewById(R.id.btn_Login);
        tv_SignUp = findViewById(R.id.tv_SignUp);
    }

    private void getAccessToken() {
        new Thread( () -> {
            AccessToken accessToken = new AccessToken();
            final String token = accessToken.getAccessToken();

            new Handler(Looper.getMainLooper()).post(()->{
                if (token != null) {
                    Log.e("Access Token: ", "" + token);
                }
                else {
                    Log.e("Access Token: ", "Failed to obtain access token");
                }
            });

        }).start();
    }

    private void getDataIntent() {
        String strPhoneNumber = getIntent().getStringExtra("phone_number");
        edt_Username.setText(strPhoneNumber);
    }

    private void initListener() {
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("email", edt_Username.toString());
                bundle.putString("password", edt_Password.toString());

                intent.putExtra("login", bundle);
                startActivity(intent);
            }
        });

        tv_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

}