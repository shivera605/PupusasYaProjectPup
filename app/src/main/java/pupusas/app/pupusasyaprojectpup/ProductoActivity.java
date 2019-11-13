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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ProductoActivity extends AppCompatActivity {
    private TextView txtpupuseria, txtnombre, txtprecio;
    private Spinner spProducto;
    private AsyncHttpClient producto1, producto2;
    private Button bnproducto;
    private boolean status = false;
    private String resultado, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        txtpupuseria = findViewById(R.id.tvpupuseria);
        txtnombre = findViewById(R.id.etNombre);
        txtprecio = findViewById(R.id.etPrecio);
        bnproducto = findViewById(R.id.btnNuevoProducto);

        Intent i = this.getIntent();
        String name = i.getStringExtra("name");
        id = i.getStringExtra("id");
        txtpupuseria.setText("Pupusería " + name );

        producto2 = new AsyncHttpClient();
        producto1 =  new AsyncHttpClient();
        spProducto = (Spinner) findViewById(R.id.SpTipo);
        llenar();
    }

    private void llenar(){
        String url = "https://pupusasapp.000webhostapp.com/TipoProducto.php";
        producto1.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200){
                    cargarSpinner(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void cargarSpinner(String respuesta){
        ArrayList<TipoProducto> lista = new ArrayList<TipoProducto>();
        try {

            JSONArray jsonArreglo = new JSONArray(respuesta);
            for (int i=0; i < jsonArreglo.length(); i++){
                TipoProducto t = new TipoProducto();
                t.setNombre(jsonArreglo.getJSONObject(i).getString("Tipo"));
                lista.add(t);
            }
            ArrayAdapter<TipoProducto> a = new ArrayAdapter<TipoProducto>(this, android.R.layout.simple_dropdown_item_1line, lista);
            spProducto.setAdapter(a);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void Save(View view) {
        try {
            if(veryfyEt() == true ) initSignUp();
        } catch (Exception e) { mensaje("Ups... Parece que no tienes conexión a internet"); }

    }


    private boolean veryfyEt(){
        boolean v;
        if (txtnombre.getText().toString().isEmpty() || txtprecio.getText().toString().isEmpty()){

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
        String nombre = txtnombre.getText().toString();
        double precio = Double.parseDouble(txtprecio.getText().toString());
        int tipo = spProducto.getSelectedItemPosition();

            try {
                AsyncHttpClient client = new AsyncHttpClient();
                String url = "https://pupusasapp.000webhostapp.com/Producto.php";
                RequestParams parametros = new RequestParams();
                parametros.put("idpupuseria", idpupuseria);
                parametros.put("idtipo", tipo);
                parametros.put("nombre", nombre);
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
                                    //Toast.makeText(SignUp.this, resultado, Toast.LENGTH_LONG).show();
                                    status = true;
                                }
                                else {
                                    resultado = "Acceso incorrecto";
                                    Toast.makeText(ProductoActivity.this, resultado, Toast.LENGTH_LONG).show();
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
                    mensaje("Producto creado con éxito.");
                }
            }
            catch (Exception e) {
                Toast.makeText(ProductoActivity.this, "Revisar conexión a internet", Toast.LENGTH_LONG).show();
            }

    }

    private void mensaje(String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(ProductoActivity.this);
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


