package pupusas.app.pupusasyaprojectpup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PerfilActivity extends AppCompatActivity {
    private TextView etid, etPupuseria, etDireccion, etemail, ettel, etcel;
    private String name, id, direccion, email, tel, cel;

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
        id = i.getStringExtra("id");
        name = i.getStringExtra("name");
        direccion = i.getStringExtra("direccion");
        email = i.getStringExtra("email");
        tel = i.getStringExtra("tel");
        cel = i.getStringExtra("cel");

        etid.setText("Codigo de la Pupuseria: " + id );
        etPupuseria.setText("Pupusería " + name );
        etDireccion.setText("Direccion: " + direccion );
        ettel.setText("Telefono: " + tel );
        etcel.setText("Celular: " + cel );
        etemail.setText("Email: " + email );
    }

    public void Editar(View view) {
        Intent open = new Intent(PerfilActivity.this, PerfilEditarActivity.class);
        open.putExtra("id", id);
        open.putExtra("name", name);
        open.putExtra("direccion", direccion);
        open.putExtra("email", email);
        open.putExtra("tel", tel);
        open.putExtra("cel", cel);
        PerfilActivity.this.startActivity(open);
    }
}
