package pupusas.app.pupusasyaprojectpup;

import androidx.appcompat.app.AppCompatActivity;

public class Refrescar {

public  static void finishApp(AppCompatActivity appCompatActivity){
    appCompatActivity.finish();
}

    public  static void refreshApp(AppCompatActivity appCompatActivity){
        appCompatActivity.recreate();
    }

}
