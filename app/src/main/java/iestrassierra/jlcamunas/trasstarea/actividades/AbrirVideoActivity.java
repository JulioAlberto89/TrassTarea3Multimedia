package iestrassierra.jlcamunas.trasstarea.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import iestrassierra.jlcamunas.trasstarea.R;

public class AbrirVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abrir_video);

        // Obtener la Uri del vídeo de la intención
        Uri videoUri = getIntent().getData();

        // Configurar el VideoView para reproducir el vídeo
        VideoView videoView = findViewById(R.id.videoView);
        videoView.setVideoURI(videoUri);

        // Configurar MediaController para controlar la reproducción del vídeo
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        // Iniciar la reproducción del vídeo
        videoView.start();
    }
}