package pupusas.app.pupusasyaprojectpup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SignUpCli extends AppCompatActivity {

    private EditText txtName, txtLastName, txtUserName, txtEmail, txtPhone, txtAddress, txtPassword, txtConfPassword;
    private EditText clave;
    private String user, pasw, url, resultado, n, a;
    private boolean status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        txtName = findViewById(R.id.etNames);
        txtLastName = findViewById(R.id.etLastNames);
        txtEmail = findViewById(R.id.etEmail);
        txtPhone = findViewById(R.id.etPhone);
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
        if (txtName.getText().toString().isEmpty() || txtLastName.getText().toString().isEmpty() || txtEmail.getText().toString().isEmpty()
            || txtPhone.getText().toString().isEmpty() || txtAddress.getText().toString().isEmpty() || txtPassword.getText().toString().isEmpty()
            || txtConfPassword.getText().toString().isEmpty() || txtUserName.getText().toString().isEmpty()){

            mensaje("Uno de los campos está vacío.");
            v = false;
        }
        else {
            v = true;
        }

        return v;
    }

    private void initSignUp(){
        String name = txtName.getText().toString();
        String lastName = txtLastName.getText().toString();
        String email = txtEmail.getText().toString();
        String phone = txtPhone.getText().toString();
        String address = txtAddress.getText().toString();
        String password = txtPassword.getText().toString();
        String confPassword = txtConfPassword.getText().toString();
        String userName = txtUserName.getText().toString();

        if (password.equals(confPassword)){
            try {
                AsyncHttpClient client = new AsyncHttpClient();
                url = "https://pupusasapp.000webhostapp.com/SignUp.php";
                RequestParams parametros = new RequestParams();
                parametros.put("nombre", name);
                parametros.put("apellido", lastName);
                parametros.put("direccion", address);
                parametros.put("celular", phone);
                parametros.put("email", email);
                parametros.put("usuario", userName);
                parametros.put("contrasennia", password);

                client.post(url, parametros, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        if (statusCode == 200){
                            try {
                                String respuesta = new String(responseBody);
                                JSONObject json = new JSONObject(respuesta);
                                if (json.names().get(0).equals("exito")){
                                    resultado = json.getString("exito");
                                    //Toast.makeText(SignUpCli.this, resultado, Toast.LENGTH_LONG).show();
                                    status = true;
                                }
                                else {
                                    resultado = "Acceso incorrecto";
                                    Toast.makeText(SignUpCli.this, resultado, Toast.LENGTH_LONG).show();
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
                    Intent openLogin = new Intent(SignUpCli.this, LoginCli.class);
                    SignUpCli.this.startActivity(openLogin);
                    finish();
                }
            }
            catch (Exception e) {
                Toast.makeText(SignUpCli.this, "Revisar conexión a internet", Toast.LENGTH_LONG).show();
            }
        }
        else {
            mensaje("Las contraseñas no coinciden.");
        }


    }

    private void mensaje(String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpCli.this);
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
}
