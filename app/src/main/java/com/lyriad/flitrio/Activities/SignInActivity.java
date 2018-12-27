package com.lyriad.flitrio.Activities;

import android.content.Intent;
import com.google.android.material.textfield.TextInputEditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lyriad.flitrio.Classes.StaticData;
import com.lyriad.flitrio.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseFirestore fireDatabase;
    private FirebaseAuth fireAuthentication;
    private FirebaseUser currentUser;

    InputMethodManager keyboard;
    TextInputEditText textUsernameEmail, textPassword;
    TextView textSignUp;
    Button buttonSignIn;
    LinearLayout progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fireDatabase = FirebaseFirestore.getInstance();
        fireAuthentication = FirebaseAuth.getInstance();
        keyboard = (InputMethodManager) getSystemService(SignInActivity.this.
                INPUT_METHOD_SERVICE);

        setContentView(R.layout.activity_sign_in);
        buttonSignIn = findViewById(R.id.sign_in_button);
        textUsernameEmail = findViewById(R.id.sign_in_username_email);
        textPassword = findViewById(R.id.sign_in_password);
        textSignUp = findViewById(R.id.sign_in_create_account);
        progress = findViewById(R.id.sign_in_progress_layout);

        buttonSignIn.setOnClickListener(this);
        textSignUp.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        currentUser = fireAuthentication.getCurrentUser();
        checkInputFields();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.sign_in_create_account){
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        }else if (v.getId() == R.id.sign_in_button){
            checkInputFields();
        }
    }

    private void checkInputFields(){

        String usernameEmail = textUsernameEmail.getText().toString().trim();
        String password = textPassword.getText().toString();

        if (TextUtils.isEmpty(usernameEmail)){
            Toast.makeText(this, "Please enter a username or email",
                    Toast.LENGTH_SHORT).show();
            textUsernameEmail.requestFocus();
            keyboard.showSoftInput(textUsernameEmail, InputMethodManager.SHOW_IMPLICIT);
            return;
        }else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter a password",
                    Toast.LENGTH_SHORT).show();
            textPassword.requestFocus();
            keyboard.showSoftInput(textPassword, InputMethodManager.SHOW_IMPLICIT);
            return;
        }

        keyboard.hideSoftInputFromWindow(textUsernameEmail.getWindowToken(), 0);
        keyboard.hideSoftInputFromWindow(textPassword.getWindowToken(), 0);
        buttonSignIn.setVisibility(View.INVISIBLE);
        progress.setVisibility(View.VISIBLE);

        if (Patterns.EMAIL_ADDRESS.matcher(usernameEmail).matches()){
            logIn(usernameEmail, password);
        }else{
            logInWithUsername(usernameEmail);
        }

    }

    private void logInWithUsername(final String username){

        fireDatabase.collection(StaticData.getUserCollection()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    String email = "";
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.get("Username").toString().equals(username)){
                            email = document.get("Email").toString();
                            break;
                        }
                    }
                    logIn(email, textPassword.getText().toString());
                } else if(task.getException() != null){
                    progress.setVisibility(View.INVISIBLE);
                    buttonSignIn.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                }else{
                    progress.setVisibility(View.INVISIBLE);
                    buttonSignIn.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "SignIn failed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void logIn(String email, String password){
        fireAuthentication.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progress.setVisibility(View.INVISIBLE);
                        buttonSignIn.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(),
                                "Welcome", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignInActivity.this,
                                MainActivity.class));
                        finish();
                    }else if (task.getException() != null){
                        progress.setVisibility(View.INVISIBLE);
                        buttonSignIn.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(),
                                task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
}
