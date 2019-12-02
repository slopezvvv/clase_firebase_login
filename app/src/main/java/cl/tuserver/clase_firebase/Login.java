package cl.tuserver.clase_firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;

public class Login extends AppCompatActivity {

    private EditText correo, pass;
    private Button ingresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        correo = findViewById(R.id.txtCorreo);
        pass = findViewById(R.id.txtPassword);
        ingresar = findViewById(R.id.btnLogin);

        ingresar.setOnClickListener(v -> procesoDeLogin(
                correo.getText().toString(),
                pass.getText().toString()
        ));
    }

    private void procesoDeLogin(@NotNull String correo, String pass){
        if(correo.isEmpty()){
            // mandar un mensaje
            Toast.makeText(this, getString(R.string.errorLogin), Toast.LENGTH_SHORT).show();
            return;
        }
        if (pass.isEmpty()){
            // mandar un mensaje
            Toast.makeText(this, getString(R.string.errorLogin), Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(correo, pass)
        .addOnSuccessListener(auth -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        })
        .addOnFailureListener(auth -> {
            Toast.makeText(this, getString(R.string.errorLogin), Toast.LENGTH_SHORT).show();
            System.out.println("ERROR_AUTH_FIREBASE: "+auth.getMessage());
        });
    }
}
