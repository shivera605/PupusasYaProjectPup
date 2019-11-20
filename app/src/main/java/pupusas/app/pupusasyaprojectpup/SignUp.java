package pupusas.app.pupusasyaprojectpup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import org.json.JSONObject;
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.Header;

public class SignUp extends AppCompatActivity {

    private EditText txtName, txtUserName, txtEmail, txtCellPhone, txtPhone, txtAddress, txtPassword, txtConfPassword;
    private EditText clave;
    private String user, pasw, url, resultado, n, a;
    private boolean status = false;
    private ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        imagen = findViewById(R.id.foto);
        txtName = findViewById(R.id.etNames);
        txtEmail = findViewById(R.id.etEmail);
        txtPhone = findViewById(R.id.etPhone);
        txtCellPhone = findViewById(R.id.etCellPhone);
        txtAddress = findViewById(R.id.etAddress2);
        txtPassword = findViewById(R.id.etPassword);
        txtConfPassword = findViewById(R.id.etConfPassword);
        txtUserName = findViewById(R.id.etUserName);
    }

    public void Save(View view) {
        try {
            if(veryfyEt() == true && veryfyPass() == true) initSignUp();
        } catch (Exception e) { mensaje("Ups... Parece que no tienes conexión a internet"); }

    }

    private boolean veryfyPass(){
        boolean v = false;

        if (txtPassword.getText().length() < 8){
            mensaje("La contraseña no puede ser menor de ocho carácteres.");
        }
        else { v = true; }

        return v;
    }

    private boolean veryfyEt(){
        boolean v;
        if (txtName.getText().toString().isEmpty() || txtCellPhone.getText().toString().isEmpty()
                || txtAddress.getText().toString().isEmpty() || txtPassword.getText().toString().isEmpty()
                || txtConfPassword.getText().toString().isEmpty() || txtUserName.getText().toString().isEmpty()){

            mensaje("Uno de los campos requeridos está vacío.");
            v = false;
        }
        else {
            v = true;
        }

        return v;
    }

    private void initSignUp(){
        String name = txtName.getText().toString();
        String email = txtEmail.getText().toString();
        String phone = txtPhone.getText().toString();
        String cellPhone = txtCellPhone.getText().toString();
        String address = txtAddress.getText().toString();
        String password = txtPassword.getText().toString();
        String confPassword = txtConfPassword.getText().toString();
        String userName = txtUserName.getText().toString();

        if (password.equals(confPassword)){
            try {
                AsyncHttpClient client = new AsyncHttpClient();
                url = "https://pupusasapp.000webhostapp.com/PupSignUp.php";
                RequestParams parametros = new RequestParams();
                parametros.put("nombre", name);
                parametros.put("direccion", address);
                parametros.put("telefono", phone);
                parametros.put("celular", cellPhone);
                parametros.put("email", email);
                parametros.put("usuario", userName);
                parametros.put("contrasennia", password);
                parametros.put("imagen", imagen);

                client.post(url, parametros, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        if (statusCode == 200){
                            try {
                                String respuesta = new String(responseBody);
                                JSONObject json = new JSONObject(respuesta);
                                if (json.names().get(0).equals("exito")){
                                    resultado = json.getString("exito");
                                    //Toast.makeText(SignUp.this, resultado, Toast.LENGTH_LONG).show();
                                    status = true;
                                }
                                else {
                                    resultado = "Acceso incorrecto";
                                    Toast.makeText(SignUp.this, resultado, Toast.LENGTH_LONG).show();
                                    status = false;
                                }
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });

                if (status == true){
                    mensaje("Usuario creado con éxito.");
                    Intent openLogin = new Intent(SignUp.this, Login.class);
                    SignUp.this.startActivity(openLogin);
                    finish();
                }
            }
            catch (Exception e) {
                Toast.makeText(SignUp.this, "Revisar conexión a internet", Toast.LENGTH_LONG).show();
            }
        }
        else {
            mensaje("Las contraseñas no coinciden.");
        }


    }

    private void mensaje(String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
        builder.setTitle("Aviso").setMessage(mensaje)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private boolean verifyConexion(){
        boolean r = false;

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) r = true;
        return r;
    }

    public void imagen(View view) {
      cargarimagen();
    }

    private void cargarimagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("imagen/");
        startActivityForResult(intent.createChooser(intent,"Seleccionar Aplicacion"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Uri path = data.getData();
            imagen.setImageURI(path);
        }
    }
}
