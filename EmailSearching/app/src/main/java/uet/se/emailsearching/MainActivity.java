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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button login;
    EditText email;
    EditText pass;
    TextView tosignup;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<String> currentCollectionData = new ArrayList<String>();
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);


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
                        int i = 0;
                        for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                        {
                            SignUpData data = documentSnapshot.toObject(SignUpData.class);

                            currentCollectionData.add(data.getEmail());
                           // String index = String.valueOf(i);
                           // Log.d(TAG,index);
                            //Log.d(TAG, data.getEmail())
                            Log.d(TAG, currentCollectionData.get(i));
                            i=i+1;
                    }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.d(TAG, String.valueOf(currentCollectionData.size()));
                int start = 0;
                int end = currentCollectionData.size();

            }
        });
    }
    int BinarySearch(String arr[],int s,int e, String x){
        s = 0;
        e = arr.length-1;
        int m = (s + e)/2;

        if(s>e)
            return -1;
        int result = x.compareTo(arr[m]);
        if(result == 0)
            return m;
        else if(result > 0)
            return BinarySearch(arr,m+1, e, x);
        else
            return BinarySearch(arr, s, m-1, x);
    }
}
