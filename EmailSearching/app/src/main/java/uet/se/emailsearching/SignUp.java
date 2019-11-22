package uet.se.emailsearching;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUp extends AppCompatActivity {
    Button btn;
    EditText email;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email=findViewById(R.id.email_id);
        password=findViewById(R.id.password_id);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUp.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
