package uet.se.emailsearching;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;

public class SignUp<settings> extends AppCompatActivity {
    Button signup;
    TextInputEditText email;
    TextInputEditText password;
    TextView tologin, error;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public ArrayList<String> currentCollectionData = new ArrayList<String>();

    private static final String TAG = "SignUp";

    int signupcheck = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email = (TextInputEditText) findViewById(R.id.email_signup);
        password = (TextInputEditText) findViewById(R.id.password_signup);
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
                currentCollectionData.clear();
                final String CEmail = email.getText().toString().trim();
                final String currentPassword = password.getText().toString().trim();
                final Character firstCh = CEmail.charAt(0);
                final SignUpData signUp = new SignUpData(CEmail, currentPassword);

                Log.d(TAG,"mail is "+ CEmail);

                db.collection((firstCh.toString())).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        int i = 0;
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            SignUpData data = documentSnapshot.toObject(SignUpData.class);
                            currentCollectionData.add(data.getEmail());
                              //for debugging
                            Log.d(TAG, currentCollectionData.get(i));
                            i = i + 1;
                        }
                        int start = 0;
                        int end = currentCollectionData.size();

                        Log.d(TAG, "size is " + String.valueOf(currentCollectionData.size()));
                int index = BinarySearchh(currentCollectionData, start, end, CEmail);
                if (index != -1) {
                    error.setText("you already have an Account");
                }
                else {
                    db.collection(firstCh.toString()).document(CEmail).set(signUp).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            error.setText("");
                            Toast.makeText(SignUp.this, "added", Toast.LENGTH_LONG).show();
                        }
                    });
                    Intent intent = new Intent(SignUp.this, MainActivity.class);
                    startActivity(intent);
                }

                    }
                });

            }
        });
        tologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public int BinarySearchh(ArrayList<String> arr, int s, int e, String x) {
        int low = s;
        int high = e - 1;
        int m = (low + high) / 2;

        
        //for debugging
       Log.d(TAG, String.valueOf(low));
       Log.d(TAG, String.valueOf(m));
       Log.d(TAG, arr.get(m));
       Log.d(TAG, String.valueOf(high));

        if (low > high)
            return -1;
        int result = x.compareTo(arr.get(m));
        if (result == 0)
            return m;
        else if (result > 0)
            return BinarySearchh(arr, m + 1, high + 1, x);
        else
            return BinarySearchh(arr, low, m, x);
    }
}
