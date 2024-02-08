package iestrassierra.jlcamunas.trasstarea.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import iestrassierra.jlcamunas.trasstarea.R;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class ReproducirAudioActivity extends AppCompatActivity {

    private MediaPlayer mp;
    private boolean bucle = false;
    private int posicion = 0;

    private Button play, pause, resume, stop, loop, volver;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproducir_audio);

        // Obtener la Uri del vídeo de la intención
        Uri audioUri = getIntent().getData();

        play = findViewById(R.id.btn_reproducir);
        pause = findViewById(R.id.btn_pausar);
        resume = findViewById(R.id.btn_reanudar);
        stop = findViewById(R.id.btn_parar);
        loop = findViewById(R.id.btn_bucle);
        seekBar = findViewById(R.id.seekBar);
        volver = findViewById(R.id.btnVolver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un intent para ir a la actividad destino
                Intent intent = new Intent(ReproducirAudioActivity.this, ListadoTareasActivity.class);
                startActivity(intent);
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reproducir(audioUri);
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pausar();
            }
        });

        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continuar();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parar();
            }
        });

        loop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarModoBucle();
            }
        });
    }

    private void reproducir(Uri audioUri) {
        if (mp != null) {
            mp.release();
        }
        mp = MediaPlayer.create(this, audioUri);

        // Configuración de la barra de progreso
        if (mp != null) {
            seekBar.setMax(mp.getDuration()); // Establece el máximo de la barra según la duración del audio
            seekBar.setProgress(0);
        }

        mp.start();
        mp.setLooping(bucle);
        Toast.makeText(this, "Reproduciendo", Toast.LENGTH_SHORT).show();

        // Actualiza la barra de progreso en un hilo separado
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mp != null && mp.isPlaying()) {
                            seekBar.setProgress(mp.getCurrentPosition());
                        }
                    }
                });
            }
        }, 0, 1000); // Actualiza cada segundo
    }

    private void pausar() {
        if (mp != null && mp.isPlaying()) {
            posicion = mp.getCurrentPosition();
            mp.pause();
            Toast.makeText(this, "Pausa", Toast.LENGTH_SHORT).show();
        }
    }

    private void continuar() {
        if (mp != null && !mp.isPlaying()) {
            mp.seekTo(posicion);
            mp.start();
            Toast.makeText(this, "Continuar", Toast.LENGTH_SHORT).show();
        }
    }

    private void parar() {
        if (mp != null) {
            mp.stop();
            posicion = 0;

            // Establecer la posición de la barra de progreso a cero
            seekBar.setProgress(0);

            Toast.makeText(this, "Parada", Toast.LENGTH_SHORT).show();
        }
    }

    private void cambiarModoBucle() {
        if (mp != null) {
            if (bucle) {
                bucle = false;
                Toast.makeText(this, "Reproducción una vez", Toast.LENGTH_SHORT).show();
                mp.setLooping(false);
            } else {
                bucle = true;
                Toast.makeText(this, "Reproducción en bucle", Toast.LENGTH_SHORT).show();
                mp.setLooping(true);
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (mp != null) {
            mp.release();
        }
        super.onDestroy();
    }
}