package uet.se.emailsearching;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class SignUp<settings> extends AppCompatActivity {
    Button signup;
    TextInputEditText email;
    TextInputEditText password;
    TextView tologin;
    FirebaseFirestore db= FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email=(TextInputEditText) findViewById(R.id.email_signup);
        password=(TextInputEditText) findViewById(R.id.password_signup);
        signup = (Button) findViewById(R.id.signup);
        tologin = (TextView) findViewById(R.id.signup_to_login);
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentEmail = email.getText().toString().trim();
                String currentPassword= password.getText().toString().trim();
                Character firstCh = currentEmail.charAt(0);
                SignUpData signUp=new SignUpData(currentEmail,currentPassword);
                db.collection(firstCh.toString()).document(currentEmail).set(signUp).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
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
