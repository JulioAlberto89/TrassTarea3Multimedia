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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import iestrassierra.jlcamunas.trasstarea.R;
import iestrassierra.jlcamunas.trasstarea.formas.GameThread;


public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback{

    private SharedPreferences sharedPreferences;
    private GameThread gameThread;
    private Executor executor;
    private TextView eslogan;
    private ImageView logotipo;
    private Button btEmpezar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Oculta la barra de acción
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        setContentView(R.layout.activity_main);
        btEmpezar = findViewById(R.id.bt_main_empezar);
        btEmpezar.setOnClickListener(this::empezar);

        ///////////////////////////////////////////////////
        SurfaceView surfaceView = findViewById(R.id.surfaceView);
        surfaceView.getHolder().addCallback(this);

        gameThread = new GameThread(surfaceView.getHolder());
        //////////////////////////////////////////////////////
        executor = Executors.newFixedThreadPool(3);

        eslogan = findViewById(R.id.tv_main_eslogan);
        logotipo = findViewById(R.id.iv_main_logo);

        // Inicia las animaciones al crear la actividad
        animador(eslogan);
        animador(logotipo);
        animador(btEmpezar);

    }

    public void animador(View v) {
        int id = v.getId();
        View view;
        Animation animation;

        if (id == R.id.bt_main_empezar) {
            view = btEmpezar;
            animation = AnimationUtils.loadAnimation(this, R.anim.fundido);
        } else if (id == R.id.tv_main_eslogan) {
            view = eslogan;
            animation = AnimationUtils.loadAnimation(this, R.anim.slide);
        } else if (id == R.id.iv_main_logo) {
            view = logotipo;
            animation = AnimationUtils.loadAnimation(this, R.anim.scale_up_down);
        } else {
            animation = null;
            view = null;
        }

        // Ejecutamos las animaciones en hilos secundarios
        // Para comunicarnos con el UIThread usamos el método post de la vista
        if (view != null)
            executor.execute(() -> view.post(() -> view.startAnimation(animation)));
    }

    private void empezar(View v){
        Intent aListado = new Intent(this, ListadoTareasActivity.class);
        startActivity(aListado);
    }

    @Override
    protected void onResume() {
        super.onResume();
        establecerFuente();
    }

    //////////////////////////////////////////////////////
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        // No se necesita implementar en este ejemplo
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        // Detiene el hilo cuando la superficie es destruida
        gameThread.setRunning(false);
        try {
            gameThread.join();
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

}