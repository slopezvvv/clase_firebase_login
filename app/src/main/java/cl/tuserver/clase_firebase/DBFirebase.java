package cl.tuserver.clase_firebase;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DBFirebase {
    private final FirebaseDatabase db;

    private final DatabaseReference root;
    private final DatabaseReference refPublicaciones;

    public DBFirebase(){
        db = FirebaseDatabase.getInstance();
        root = db.getReference(); // Directorio Raiz firebase
        refPublicaciones = root.child(Referencias.PUBLICACIONES.toString()); // Collection PUBLICACIONES

        // Listener de cambios en datos de la nube firebase
        // Se activa cuando:
        // 1) Hay cambios en los datos (nuevos, actualizacion, borrados, etc).
        // 2) Se abre por primera vez la app.
        refPublicaciones.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot usuario : dataSnapshot.getChildren()){


                    for(DataSnapshot publicacion : usuario.getChildren()) {
                        final long fecha = Long.parseLong(String.valueOf(publicacion.getKey()));
                        final String texto = String.valueOf(publicacion.getValue());

                        Publicacion p = new Publicacion(texto, fecha);

                        // Guardaras en base de datos...
                        MainActivity.PUBLICACIONES.put(fecha, p);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("FIREBASE_ERROR_DATABASE: "+databaseError.getMessage());
            }
        });
    }

    // Metodo que agrega publicaciones dentro de la collection PUBLICACIONES.
    // Con el UID respectivo al usuario logeado.
    public void agregarPublicacion(Publicacion publicacion){
        Map<String, Object> valoresPublicacion = new HashMap<>();
        valoresPublicacion.put(
            String.valueOf(publicacion.getFecha()), // Clave fecha
            publicacion.getValor()); // Texto publicacion
        refPublicaciones.child(Login.UID).updateChildren(valoresPublicacion);
    }
}
