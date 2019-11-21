package pupusas.app.pupusasyaprojectpup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
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

import org.json.JSONObject;

import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

public class Login extends AppCompatActivity {

    private EditText usuario;
    private EditText clave;
    private String user, pasw, url, resultado, n, d, di, e, t, c;
    private String nameCust, lastNameCust, addressCust, phoneCust, emailCust, idCust;
    private boolean status = false;
    private boolean statusOut = false;
    private boolean userType = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario = findViewById(R.id.etUsuario);
        clave = findViewById(R.id.etPassword);
    }

    public void LogIn(View view) {

        if (usuario.getText().toString().isEmpty()) {
            mError("No ha introducido el nombre de usuario.");
        } else if (clave.getText().toString().isEmpty()) {
            mError("No ha introducido su contraseña.");
        } else {
            try {
                initLogInPup();
                /*if (userType == false) {
                    userType = initLogInCli();
                    if (userType == false) {
                        mError("Pueda que el usuario o contraseña estén incorrectos. Si no está registrado, puede registrarse.");
                    }
                }*/
                if (verifyConexion() == false)
                    mError("Ups... parece que no tienes conexión a internet");
            } catch (Exception e) {
                mError("Ups... Parece que hubo un problema, vuelve a intentarlo.");
            }
        }
    }

    public void SignUp(View view) {
        Intent open = new Intent(Login.this, SignUp.class);
        Login.this.startActivity(open);
    }

    private boolean verifyConexion() {
        boolean r = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) r = true;
        return r;
    }

    private void mError(String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
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

    private void initLogInPup() {
        user = usuario.getText().toString();
        pasw = clave.getText().toString();
        AsyncHttpClient client = new AsyncHttpClient();
        url = "https://pupusasapp.000webhostapp.com/PupLogin.php";
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
                        if (json.names().get(0).equals("exito")) {
                            n = json.getString("Nombre");
                            d = json.getString("IdPupuseria");
                            di = json.getString("Direccion");
                            e = json.getString("Email");
                            t = json.getString("Telefono");
                            c = json.getString("Celular");

                            statusOut = true;
                            status = true;
                        } else {
                            statusOut = false;
                            status = false;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                //Toast.makeText(getApplicationContext(), "Vuelve a intentarlo.", Toast.LENGTH_LONG).show();
            }
        });

        if (status == true) {
            Intent openMain = new Intent(Login.this, MainActivity.class);
            openMain.putExtra("n", n);
            openMain.putExtra("d", d);
            openMain.putExtra("di", di);
            openMain.putExtra("e", e);
            openMain.putExtra("t", t);
            openMain.putExtra("c", c);
            Login.this.startActivity(openMain);
            usuario.setText("");
            clave.setText("");
            finish();
        }

        //return statusOut;
    }

    private boolean initLogInCli() {
        user = usuario.getText().toString();
        pasw = clave.getText().toString();
        AsyncHttpClient client = new AsyncHttpClient();
        url = "http://pupusasapp.000webhostapp.com/LogIn.php";
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
                        if (json.names().get(0).equals("exito")) {
                            nameCust = json.getString("Nombre");
                            lastNameCust = json.getString("Apellido");
                            addressCust = json.getString("Direccion");
                            phoneCust = json.getString("Celular");
                            emailCust = json.getString("Email");
                            idCust = json.getString("IdCliente");

                            statusOut = true;
                            status = true;
                        } else {

                            statusOut = false;
                            status = false;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Toast.makeText(getApplicationContext(), "Hubo un error.", Toast.LENGTH_LONG).show();
            }
        });

        if (status == true) {
            Intent openMain = new Intent(Login.this, MainActivityCli.class);
            openMain.putExtra("Nombre", nameCust);
            openMain.putExtra("Apellido", lastNameCust);
            openMain.putExtra("Direccion", addressCust);
            openMain.putExtra("Telefono", phoneCust);
            openMain.putExtra("Email", emailCust);
            openMain.putExtra("IdCliente", idCust);
            Login.this.startActivity(openMain);
            usuario.setText("");
            clave.setText("");

        }
        return statusOut;
    }
}
