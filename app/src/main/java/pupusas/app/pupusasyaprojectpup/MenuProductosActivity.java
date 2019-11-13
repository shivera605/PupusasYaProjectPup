package pupusas.app.pupusasyaprojectpup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MenuProductosActivity extends AppCompatActivity {
    private ListView lista;
    private ArrayList nombre, precio;
    private TextView m;
    private EditText txtSearch;
    private String id, resultado;
    private boolean status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_productos);

        m = findViewById(R.id.tvMessage1);

        Intent i = this.getIntent();
        String name = i.getStringExtra("name");
        id = i.getStringExtra("id");
        m.setText("Menu Pupusería " + name);

        lista = (ListView) findViewById(R.id.listamenu);
        txtSearch = findViewById(R.id.etSearchP);
        nombre = new ArrayList();
        precio = new ArrayList();

        descargardatos();

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String name = txtSearch.getText().toString();
                SearchByName(name);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void descargardatos() {
        int idpupuseria = Integer.parseInt(id);
        nombre.clear();
        precio.clear();

        final ProgressDialog progressDialog = new ProgressDialog(MenuProductosActivity.this);
        progressDialog.setMessage("Cargar Datos...");
        progressDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://pupusasapp.000webhostapp.com/verPoductos.php";
        RequestParams parametros = new RequestParams();
        parametros.put("idpupuseria", idpupuseria);
        client.get(url, parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    progressDialog.dismiss();
                    try {

                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            nombre.add(jsonArray.getJSONObject(i).getString("Nombre"));
                            precio.add(jsonArray.getJSONObject(i).getString("Precio"));

                        }

                        lista.setAdapter(new CustonAdater(getApplicationContext()));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void SearchByName(String name) {
        int idPupuseria = Integer.parseInt(id);
        nombre.clear();
        precio.clear();

        //final ProgressDialog progressDialog = new ProgressDialog(MenuProductosActivity.this);
        //progressDialog.setMessage("Cargar Datos...");
        //progressDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://pupusasapp.000webhostapp.com/ProductsByName.php";
        RequestParams parametros = new RequestParams();
        parametros.put("idpupuseria", idPupuseria);
        parametros.put("name", name);

        client.get(url, parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    //progressDialog.dismiss();
                    try {

                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            nombre.add(jsonArray.getJSONObject(i).getString("Nombre"));
                            precio.add(jsonArray.getJSONObject(i).getString("Precio"));

                        }

                        lista.setAdapter(new CustonAdater(getApplicationContext()));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private class CustonAdater extends BaseAdapter {
        Context ctx;
        LayoutInflater layoutInflater;
        TextView etnombre, etprecio;

        public CustonAdater(Context applicationContext) {
            this.ctx = applicationContext;
            layoutInflater = (LayoutInflater) this.ctx.getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return nombre.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View converView, ViewGroup parent) {
            ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.lista_producto, null);
            etnombre = (TextView) viewGroup.findViewById(R.id.etProducto);
            etprecio = (TextView) viewGroup.findViewById(R.id.etPrecio1);

            etnombre.setText(nombre.get(position).toString());
            etprecio.setText(precio.get(position).toString());

            return viewGroup;
        }

    }


}
