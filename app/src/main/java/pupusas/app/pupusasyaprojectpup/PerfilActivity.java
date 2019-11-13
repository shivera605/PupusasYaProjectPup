package pupusas.app.pupusasyaprojectpup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PerfilActivity extends AppCompatActivity {
    private TextView etPupuseria, etDireccion, txtprecio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);


        Intent i = this.getIntent();
        String name = i.getStringExtra("name");
        String direccion = i.getStringExtra("direccion");
        etPupuseria.setText("Pupusería " + name );
        etDireccion.setText("Direccion: " + direccion );

    }
}
