package uet.se.emailsearching;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    Button login;
    EditText email;
    EditText pass;
    TextView tosignup;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    int i = 0;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        final String[] currentCollection={""};

        login=findViewById(R.id.login);
        email=findViewById(R.id.email_login);
        pass=findViewById(R.id.password_login);
        tosignup = (TextView)findViewById(R.id.login_to_signup);

        tosignup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUp.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentEmail = email.getText().toString().trim();
                String currentPassword = pass.getText().toString().trim();
                Character firstCh = currentEmail.charAt(0);
                db.collection(firstCh.toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                        {
                            SignUpData data = documentSnapshot.toObject(SignUpData.class);
                            String index = String.valueOf(i);
                            Log.d(TAG,index);
                            Log.d(TAG, data.getEmail());
                            i=i+1;
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
