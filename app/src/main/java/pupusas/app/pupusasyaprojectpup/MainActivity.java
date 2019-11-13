package pupusas.app.pupusasyaprojectpup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView m;
    private EditText k;
    private String name, id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m = findViewById(R.id.tvMessage);

        Intent i = this.getIntent();
        name = i.getStringExtra("n");
        id = i.getStringExtra("d");
        m.setText("Pupusería " + name );
    }

    public void producto(View view) {
        Intent open = new Intent(MainActivity.this, ProductoActivity.class);
        open.putExtra("name", name);
        open.putExtra("id", id);
        MainActivity.this.startActivity(open);
    }


    public void perfil(View view) {
        Intent open = new Intent(MainActivity.this, PerfilActivity.class);
        open.putExtra("name", name);
        MainActivity.this.startActivity(open);
    }

    public void Menu(View view) {
        Intent open = new Intent(MainActivity.this, MenuProductosActivity.class);
        open.putExtra("name", name);
        open.putExtra("id", id);
        MainActivity.this.startActivity(open);
    }
}
