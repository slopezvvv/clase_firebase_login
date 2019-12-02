package cl.tuserver.clase_firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    // TEST Base de datos
    public static final HashMap<Long, Publicacion> PUBLICACIONES = new HashMap<>();
    // END-TEST Base de datos
    private DBFirebase dbFirebase;
    private EditText txtPublicacion;
    private Button btnPublicar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbFirebase = new DBFirebase();

        txtPublicacion = findViewById(R.id.txtPublicacion);
        btnPublicar = findViewById(R.id.btnPublicar);

        btnPublicar.setOnClickListener(v ->
            publicar(txtPublicacion.getText().toString())
        );
    }

    private void publicar(String texto){
        if(texto.isEmpty()){
            Toast.makeText(this, getString(R.string.errorTextoVacio), Toast.LENGTH_SHORT).show();
            return;
        }

        long fecha = System.currentTimeMillis();
        Publicacion publicacion = new Publicacion(texto, fecha);

        dbFirebase.agregarPublicacion(publicacion);

        Toast.makeText(this, "Se publico exitosamente!", Toast.LENGTH_SHORT).show();
    }
}
