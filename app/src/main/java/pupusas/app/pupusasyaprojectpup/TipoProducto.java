package pupusas.app.pupusasyaprojectpup;

import android.widget.TextView;

public class TipoProducto {
    private int id;
    private String nombre;

    public TipoProducto(){
    }

    public TipoProducto(int id, String nombre){
        this.id=id;
        this.nombre=nombre;
    }

    public void setId (int id){
        this.id=id;
    }

    public  void setNombre(String nombre){
        this.nombre=nombre;
    }

    @Override
    public String toString(){
        return nombre;
    }
}

