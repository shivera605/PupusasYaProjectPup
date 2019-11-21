package pupusas.app.pupusasyaprojectpup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.LogInterface;

public class activity_principal1 extends AppCompatActivity {

    private EditText usuario;
    private EditText clave;
    private String user, pasw, url, resultado, n, d, di, e, t, c;
    private boolean status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal1);
        usuario = findViewById(R.id.etUsuario);
        clave = findViewById(R.id.etPassword);
    }



    public void Clientes1(View v) {
        Intent clientes=new Intent(activity_principal1.this, LoginCli.class);
        startActivity(clientes);



    }
    public void Pupuserias1(View v) {
        Intent pupuserias=new Intent(activity_principal1.this, Login.class);
        startActivity(pupuserias);


    }
    /** pupuserias=(Button)findViewById(R.id.btnPupuserias);
     pupuserias.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
    Intent pupuserias=new Intent(principal.this, Login.class);
    startActivity(pupuserias);
    }
    }); **/
}
