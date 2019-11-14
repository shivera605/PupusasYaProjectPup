package pupusas.app.pupusasyaprojectpup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class ProductoEditarActivity extends AppCompatActivity {
    private EditText etnombre, etprecio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_editar);

        etnombre = findViewById(R.id.etNombre);
        etprecio = findViewById(R.id.etPrecio);

        Intent i = this.getIntent();
        String nombre = i.getStringExtra("nombre");
        String precio = i.getStringExtra("precio");

        etnombre.setText( nombre );
        etprecio.setText( precio );


    }
}
