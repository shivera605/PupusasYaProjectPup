package pupusas.app.pupusasyaprojectpup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivityCli extends AppCompatActivity {
    private TextView m;
    private EditText k;
    private String nameCust, lastNameCust, addressCust, phoneCust, emailCust, idCust;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cli);
        m = findViewById(R.id.tvMessageCli);

        Intent i = this.getIntent();
        nameCust = i.getStringExtra("Nombre");
        lastNameCust = i.getStringExtra("Apellido");
        addressCust = i.getStringExtra("Direccion");
        emailCust = i.getStringExtra("Email");
        phoneCust = i.getStringExtra("Telefono");
        idCust = i.getStringExtra("IdCliente");
        m.setText("Hola " + nameCust + " " + lastNameCust + "!");


    }

    public void producto(View view) {
        Intent open = new Intent(MainActivityCli.this, ProductoActivity.class);
        //open.putExtra("name", name);
        //open.putExtra("id", id);
        MainActivityCli.this.startActivity(open);
    }


    public void perfil(View view) {
        Intent open = new Intent(MainActivityCli.this, PerfilActivity.class);
        /*open.putExtra("name", name);
        open.putExtra("id", id);
        open.putExtra("direccion", direcion);
        open.putExtra("email", email);
        open.putExtra("tel", tel);
        open.putExtra("cel", cel);*/
        MainActivityCli.this.startActivity(open);
    }

    public void Menu(View view) {
        Intent open = new Intent(MainActivityCli.this, MenuProductosActivity.class);
        //open.putExtra("name", name);
        //open.putExtra("id", id);
        MainActivityCli.this.startActivity(open);
    }

    public void cerrarsesion(View view) {
        Intent open = new Intent(MainActivityCli.this, LoginCli.class);
        MainActivityCli.this.startActivity(open);
        finish();
    }
}
