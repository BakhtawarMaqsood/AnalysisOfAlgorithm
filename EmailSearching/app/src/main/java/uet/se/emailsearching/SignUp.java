package uet.se.emailsearching;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SignUp<settings> extends AppCompatActivity {
    Button signup;
    TextInputEditText email;
    TextInputEditText password;
    TextView tologin,error;
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    int signupcheck = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email=(TextInputEditText) findViewById(R.id.email_signup);
        password=(TextInputEditText) findViewById(R.id.password_signup);
        signup = (Button) findViewById(R.id.signup);
        tologin = (TextView) findViewById(R.id.signup_to_login);
        error = (TextView) findViewById(R.id.error_signup);
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String currentEmail = email.getText().toString().trim();
                final String currentPassword= password.getText().toString().trim();
                Character firstCh = currentEmail.charAt(0);
                SignUpData signUp=new SignUpData(currentEmail,currentPassword);
                db.collection((firstCh.toString())).get().addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUp.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                            SignUpData signUpData = documentSnapshot.toObject(SignUpData.class);
                            if(currentEmail.equals(signUpData.getEmail())){
                                error.setText("you already have an Account");
                                signupcheck = 1;
                            }
                        }
                    }
                });
                if(signupcheck == 0)
                {
                    db.collection(firstCh.toString()).document(currentEmail).set(signUp).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            error.setText("");
                            Toast.makeText(SignUp.this, "added", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUp.this, "failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //Intent intent=new Intent(SignUp.this,MainActivity.class);
                    //startActivity(intent);
                }

            }
        });
        tologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUp.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
