package pupusas.app.pupusasyaprojectpup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class PerfilEditarActivity extends AppCompatActivity {
    private EditText etPupuseria, etDireccion, etemail, ettel, etcel;
    private String resultado, name, id, direccion, email, tel, cel;
    private boolean status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_editar);

        etPupuseria = findViewById(R.id.etNames);
        etDireccion = findViewById(R.id.etAddress2);
        ettel = findViewById(R.id.etPhone);
        etcel = findViewById(R.id.etCellPhone);
        etemail = findViewById(R.id.etEmail);

        Intent i = this.getIntent();
         id = i.getStringExtra("id");
         name = i.getStringExtra("name");
         direccion = i.getStringExtra("direccion");
         email = i.getStringExtra("email");
         tel = i.getStringExtra("tel");
         cel = i.getStringExtra("cel");

        etPupuseria.setText( name );
        etDireccion.setText(direccion );
        ettel.setText(tel );
        etcel.setText(cel );
        etemail.setText(email );
    }

    public void Save(View view) {
        try {
            if(veryfyEt() == true ) initSignUp();
        } catch (Exception e) { mensaje("Ups... Parece que no tienes conexión a internet"); }

    }


    private boolean veryfyEt(){
        boolean v;
        if (etPupuseria.getText().toString().isEmpty() || etDireccion.getText().toString().isEmpty()){

            mensaje("Uno de los campos está vacío.");
            v = false;
        }
        else {
            v = true;
        }

        return v;
    }

    private void initSignUp(){
        int idpupuseria = Integer.parseInt(id);
        String name = etPupuseria.getText().toString();
        String address = etDireccion.getText().toString();
        String phone = ettel.getText().toString();
        String cellPhone = etcel.getText().toString();
        String email = etemail.getText().toString();

        try {
            AsyncHttpClient client = new AsyncHttpClient();
            String url = "https://pupusasapp.000webhostapp.com/Editarperfil.php";
            RequestParams parametros = new RequestParams();
            parametros.put("idpupuseria", idpupuseria);
            parametros.put("nombre", name);
            parametros.put("direccion", address);
            parametros.put("telefono", phone);
            parametros.put("celular", cellPhone);
            parametros.put("email", email);

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
                                Toast.makeText(PerfilEditarActivity.this, resultado, Toast.LENGTH_LONG).show();
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
                mensaje("Cambios Guardados.");
                Intent openLogin = new Intent(PerfilEditarActivity.this, PerfilActivity.class);
                PerfilEditarActivity.this.startActivity(openLogin);
                finish();
            }
        }
        catch (Exception e) {
            Toast.makeText(PerfilEditarActivity.this, "Revisar conexión a internet", Toast.LENGTH_LONG).show();
        }

    }

    private void mensaje(String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(PerfilEditarActivity.this);
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
