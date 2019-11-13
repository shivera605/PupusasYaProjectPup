package pupusas.app.pupusasyaprojectpup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PerfilActivity extends AppCompatActivity {
    private TextView etid, etPupuseria, etDireccion, etemail, ettel, etcel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        etid = findViewById(R.id.tvCodigo);
        etPupuseria = findViewById(R.id.tvpupuseria);
        etDireccion = findViewById(R.id.tvdireccion);
        ettel = findViewById(R.id.tvtelefono);
        etcel = findViewById(R.id.tvcelular);
        etemail = findViewById(R.id.tvemail);

        Intent i = this.getIntent();
        String id = i.getStringExtra("id");
        String name = i.getStringExtra("name");
        String direccion = i.getStringExtra("direccion");
        String email = i.getStringExtra("email");
        String tel = i.getStringExtra("tel");
        String cel = i.getStringExtra("cel");

        etid.setText("Codigo de la Pupuseria: " + id );
        etPupuseria.setText("Pupusería " + name );
        etDireccion.setText("Direccion: " + direccion );
        ettel.setText("Telefono: " + tel );
        etcel.setText("Celular: " + cel );
        etemail.setText("Email: " + email );
    }
}
