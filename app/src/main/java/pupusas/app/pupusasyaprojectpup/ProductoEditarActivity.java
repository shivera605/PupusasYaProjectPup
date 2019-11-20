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
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProductoEditarActivity extends AppCompatActivity {
    private EditText etnombre, etprecio;
    private String nombre, precio, id, resultado;
    private boolean status = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_editar);

        etnombre = findViewById(R.id.etNombre);
        etprecio = findViewById(R.id.etPrecio);

        Intent i = this.getIntent();
        nombre = i.getStringExtra("nombre");
        precio = i.getStringExtra("precio");
        id = i.getStringExtra("id");

        etnombre.setText( nombre );
        etprecio.setText(precio );

    }

    public void Save2(View view) {
        try {
            if(veryfyEt() == true ) initSignUp();
            Intent openLogin = new Intent(ProductoEditarActivity.this, MenuProductosActivity.class);
            ProductoEditarActivity.this.startActivity(openLogin);

        } catch (Exception e) { mensaje("Ups... Parece que no tienes conexión a internet"); }

    }


    private boolean veryfyEt(){
        boolean v;
        if (etnombre.getText().toString().isEmpty() || etprecio.getText().toString().isEmpty()){

            mensaje("Uno de los campos está vacío.");
            v = false;
        }
        else {
            v = true;
        }

        return v;
    }

    private void initSignUp(){
        int idproducto = Integer.parseInt(id);
        String name = etnombre.getText().toString();
        float precio = Float.parseFloat(etprecio.getText().toString());


        try {
            AsyncHttpClient client = new AsyncHttpClient();
            String url = "https://pupusasapp.000webhostapp.com/Editarproducto.php";
            RequestParams parametros = new RequestParams();
            parametros.put("idproducto", idproducto);
            parametros.put("nombre", name);
            parametros.put("precio", precio);

            client.post(url, parametros, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (statusCode == 200){
                        try {
                            String respuesta = new String(responseBody);
                            JSONObject json = new JSONObject(respuesta);
                            if (json.names().get(0).equals("exito")){
                                resultado = json.getString("exito");

                                status = true;
                            }
                            else {
                                resultado = "Acceso incorrecto";
                                Toast.makeText(ProductoEditarActivity.this, resultado, Toast.LENGTH_LONG).show();
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
            }
        }
        catch (Exception e) {
            Toast.makeText(ProductoEditarActivity.this, "Revisar conexión a internet", Toast.LENGTH_LONG).show();
        }

    }

    private void mensaje(String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(ProductoEditarActivity.this);
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

    public void elinimar(View view) {
        try {
            if(veryfyEt() == true ) initSignUp2();
            Intent openLogin = new Intent(ProductoEditarActivity.this, MainActivity.class);
            ProductoEditarActivity.this.startActivity(openLogin);

        } catch (Exception e) { mensaje("Ups... Parece que no tienes conexión a internet"); }
    }

    private void initSignUp2(){
        int idproducto = Integer.parseInt(id);

        try {
            AsyncHttpClient client = new AsyncHttpClient();
            String url = "https://pupusasapp.000webhostapp.com/EliminarProducto.php";
            RequestParams parametros = new RequestParams();
            parametros.put("idproducto", idproducto);

            client.post(url, parametros, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (statusCode == 200){
                        try {
                            String respuesta = new String(responseBody);
                            JSONObject json = new JSONObject(respuesta);
                            if (json.names().get(0).equals("exito")){
                                resultado = json.getString("exito");

                                status = true;
                            }
                            else {
                                resultado = "Acceso incorrecto";
                                Toast.makeText(ProductoEditarActivity.this, resultado, Toast.LENGTH_LONG).show();
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
                mensaje("Producto Eliminado.");

            }
        }
        catch (Exception e) {
            Toast.makeText(ProductoEditarActivity.this, "Revisar conexión a internet", Toast.LENGTH_LONG).show();
        }

    }

}
