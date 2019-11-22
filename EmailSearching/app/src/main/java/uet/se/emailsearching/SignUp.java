package uet.se.emailsearching;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {
    Button signup;
    EditText email;
    EditText password;
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email=findViewById(R.id.email_signup);
        password=findViewById(R.id.password_signup);
        signup = (Button) findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String currentEmail = email.getText().toString().trim();
                String currentPassword= password.getText().toString().trim();
                Character firstCh = currentEmail.charAt(0);
                SignUpData signUp=new SignUpData(currentEmail,currentPassword);
                db.collection(firstCh.toString()).document(currentEmail).set(signUp);
                Intent intent=new Intent(SignUp.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
