package cl.tuserver.clase_firebase;

import java.io.Serializable;

public class Publicacion implements Serializable {
    private final String valor;
    private final long fecha;

    public Publicacion(String valor, long fecha){
        this.valor = valor;
        this.fecha = fecha;
    }


    public String getValor() {
        return valor;
    }

    public long getFecha() {
        return fecha;
    }
}
