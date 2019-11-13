package pupusas.app.pupusasyaprojectpup;

public class Perfil {
    private int id;
    private String nombre;
    private String direccion;
    private String email;
    private String telefono;
    private String celular;


    public Perfil(){
    }

    public Perfil(int id, String nombre, String direccion, String email, String telefono, String celular){
        this.id=id;
        this.nombre=nombre;
        this.direccion=direccion;
        this.email=email;
        this.telefono=telefono;
        this.celular=celular;
    }

    public void setId (int id){
        this.id=id;
    }

    public  void setNombre(String nombre){
        this.nombre=nombre;
    }

    public  void setDireccion(String direccion){
        this.direccion=direccion;
    }

    public  void setEmail(String email){
        this.email=email;
    }

    public  void setTelefono(String telefono){
        this.telefono=telefono;
    }

    public  void setCelular(String celular){
        this.celular=celular;
    }

    @Override
    public String toString(){
        return nombre;
    }
}
