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

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class LoginCli extends AppCompatActivity {

    private EditText usuario;
    private EditText clave;
    private String user, pasw, url, resultado, n, d, di, e, t, c, a;
    private boolean status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        usuario = findViewById(R.id.etUsuario);
        clave = findViewById(R.id.etPassword);
    }

    public void LogIn(View view) {

        if (usuario.getText().toString().isEmpty()){
            mError("No ha introducido el nombre de usuario.");
        }
        else if (clave.getText().toString().isEmpty()){
            mError("No ha introducido su contraseña.");
        }
        else {
            try {
                if(verifyConexion() == true)initLogIn();
                if(verifyConexion() == false) mError("Ups... parece que no tienes conexión a internet");
            } catch (Exception e) { mError("Ups... Parece que hubo un problema, vuelve a intentarlo.");}
        }
    }

    public void SignUp(View view) {
        Intent open = new Intent(LoginCli.this, SignUp.class);
        LoginCli.this.startActivity(open);
    }

    private boolean verifyConexion(){
        boolean r = false;

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) r = true;
        return r;
    }

    private void mError(String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginCli.this);
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

    private void initLogIn(){
        user = usuario.getText().toString();
        pasw = clave.getText().toString();
        AsyncHttpClient client = new AsyncHttpClient();
        url = "https://pupusasapp.000webhostapp.com/PupLogin2.php";
        RequestParams parametros = new RequestParams();
        parametros.put("usu", user);
        parametros.put("pas", pasw);
        client.post(url, parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        String respuesta = new String(responseBody);

                        JSONObject json = new JSONObject(respuesta);
                        if (json.names().get(0).equals("exito")){
                            n = json.getString("Nombre");
                            d = json.getString("IdCliente");
                            di = json.getString("Direccion");
                            e = json.getString("Email");
                            t = json.getString("Usuario");
                            c = json.getString("Celular");
                            status = true;
                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginCli.this);
                            builder.setTitle("Aviso").setMessage("Usuario o contraseña incorrectos.")
                                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });

                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                            status = false;
                        }

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure (int statusCode, Header[] headers, byte[] responseBody, Throwable error){

            }
        });

        if (status == true){
            Intent openMain = new Intent(LoginCli.this, MainActivity.class);
            openMain.putExtra("n", n);
            openMain.putExtra("a", a);
            LoginCli.this.startActivity(openMain);
            usuario.setText("");
            clave.setText("");
        }
    }
}
