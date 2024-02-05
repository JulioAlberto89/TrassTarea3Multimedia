package iestrassierra.jlcamunas.trasstarea.actividades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import iestrassierra.jlcamunas.trasstarea.R;
import iestrassierra.jlcamunas.trasstarea.formas.HilosFormas;


public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback{

    private SharedPreferences sharedPreferences;
    private HilosFormas hilosFormas;
    private SurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Oculta la barra de acción
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        setContentView(R.layout.activity_main);
        Button btEmpezar = findViewById(R.id.bt_main_empezar);
        btEmpezar.setOnClickListener(this::empezar);
        ///////////////////////////////////////////////////
        surfaceView = findViewById(R.id.surfaceView);
        surfaceView.getHolder().addCallback(this);

        // Inicia el hilo de juego y pasa el SurfaceHolder del SurfaceView
        hilosFormas = new HilosFormas(surfaceView.getHolder());
    }
    private void empezar(View v){
        Intent aListado = new Intent(this, ListadoTareasActivity.class);
        startActivity(aListado);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hilosFormas.setRunning(false);
        // Espera a que el hilo termine
        try {
            hilosFormas.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        establecerFuente();

        // Verifica que hilosFormas no sea nulo antes de intentar reiniciar hilosFormas
        if (hilosFormas != null) {
            // Detén el hilo antes de crear uno nuevo
            hilosFormas.setRunning(false);
            try {
                hilosFormas.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //////////////////////////////////////////////////////
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder)
    {
        if (surfaceView != null) {
            hilosFormas = new HilosFormas(surfaceView.getHolder());
            hilosFormas.start();
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        // No se necesita implementar en este ejemplo
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        // Detiene el hilo cuando la superficie es destruida
        hilosFormas.setRunning(false);
        try {
            hilosFormas.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //////////////////////////////////////////////////////

    private void establecerFuente() {

        //FUENTE
        String tamanoLetra = sharedPreferences.getString("fuente", "M");
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();

        switch (tamanoLetra) {
            case "S":
                configuration.fontScale = 0.8f;
                break;
            case "L":
                configuration.fontScale = 1.2f;
                break;
            default:
                configuration.fontScale = 1.0f;
        }
        resources.updateConfiguration(configuration, displayMetrics);
    }

    //Sobrescribiendo getTheme podemos establecer cualquier tema de nuestros recursos.
    @Override
    public Resources.Theme getTheme() {
        Resources.Theme theme = super.getTheme();
        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("contraste", false)){
            theme.applyStyle(R.style.Theme_TrassTarea_HighContrast, true);
        }else{
            theme.applyStyle(R.style.Theme_TrassTarea, true);
        }
        return theme;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Detén el hilo de juego cuando la actividad se destruye
        hilosFormas.setRunning(false);
    }

}