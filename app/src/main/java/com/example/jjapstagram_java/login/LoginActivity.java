package com.example.jjapstagram_java.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.jjapstagram_java.BaseActivity;
import com.example.jjapstagram_java.MainActivity;
import com.example.jjapstagram_java.R;
import com.example.jjapstagram_java.databinding.ActivityLoginBinding;
import com.example.jjapstagram_java.request.FacebookLoginRequest;
import com.example.jjapstagram_java.request.GoogleLoginRequest;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends BaseActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    public static final int GOOGLE_LOGIN_IN = 999;
    public static final int FACEBOOK_LOGIN_IN = 64206;

    private ActivityLoginBinding binding;

    GoogleSignInOptions mGoogleSignInOptions;
    GoogleSignInClient mGoogleSignInClient;

    private FirebaseAuth mFirebaseAuth;

    CallbackManager mCallbackManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);


        callGoogleLogin();
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();

        TextView textView = (TextView) binding.googleSignInBtn.getChildAt(0);
        textView.setText(getString(R.string.sign_in_google));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        binding.googleSignInBtn.setOnClickListener(this::signIn);
        binding.facebookSIgnInBtn.setOnClickListener(this::signIn);

    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        checkUser(firebaseUser, null);
    }

    private void callGoogleLogin() {
        mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "requestCode = " + requestCode);
        if (requestCode == GOOGLE_LOGIN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was Successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;
                new GoogleLoginRequest(this).firebaseAuthWithGoogle(account);
            } catch (Exception e) {
                Log.w(TAG, "Google sign in failed", e);
                //updateUI(null);
            }
        } else if(requestCode == FACEBOOK_LOGIN_IN){
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void signIn(View view) {
        switch (view.getId()) {
            case R.id.googleSignInBtn:
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, GOOGLE_LOGIN_IN);
                break;
            case R.id.facebookSIgnInBtn:
                binding.facebookSIgnInBtn.setPermissions(getString(R.string.facebook_login_key), getString(R.string.facebook_login_value));
                binding.facebookSIgnInBtn.registerCallback(mCallbackManager, new FacebookLoginRequest(this));
                break;
        }
    }

    public void checkUser(FirebaseUser user, String provider) {
        hideProgressDialog();
        if (user != null) {
            if(provider != null) {
                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.login_name), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getString(R.string.login_provider), provider);
                editor.apply();
            }
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
