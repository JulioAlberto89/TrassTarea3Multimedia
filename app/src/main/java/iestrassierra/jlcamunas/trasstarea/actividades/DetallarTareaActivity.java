package iestrassierra.jlcamunas.trasstarea.actividades;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;

import iestrassierra.jlcamunas.trasstarea.R;
import iestrassierra.jlcamunas.trasstarea.modelo.entidades.Tarea;

public class DetallarTareaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detallar_tarea);

        // Oculta la barra de acción
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //Recibimos la tarea que va a ser editada
        Tarea tareaDetalle = getIntent().getParcelableExtra("tareaDetallar");

        //Seteamos las informaciones en las distintas Vistas
        TextView tvDetallarTitulo = findViewById(R.id.tv_detallar_titulo_contenido);
        tvDetallarTitulo.setText(tareaDetalle.getTitulo());

        TextView tvDetallarDias = findViewById(R.id.tv_detallar_dias_contenido);
        int dias = Tarea.diasHastaHoy(tareaDetalle.getFechaObjetivo());
        tvDetallarDias.setText(String.valueOf(dias));

        ProgressBar pgDetallarProgreso = findViewById(R.id.pg_detallar_progreso);
        pgDetallarProgreso.setProgress(tareaDetalle.getProgreso());

        TextView tvDetallarDescripcion = findViewById(R.id.tv_detallar_descripcion_contenido);
        tvDetallarDescripcion.setText(tareaDetalle.getDescripcion());

        //Mostramos los nombres de los documentos a partir de su ruta completa
        TextView tv_detallar_documento = findViewById(R.id.tv_detallar_documento);
        //Tanto si pulsas el documento, como la imagen, se abre.
        ImageView imageViewDocumento = findViewById(R.id.iv_detallar_documento);

        tv_detallar_documento.setText(rutaANombre(tareaDetalle.getRutaDocumento()));

        tv_detallar_documento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(tareaDetalle.getRutaDocumento())) {
                    abrirDocumento(tareaDetalle.getRutaDocumento());
                } else {
                    Toast.makeText(getApplicationContext(), "Esta tarea no tiene ningún archivo seleccionado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageViewDocumento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(tareaDetalle.getRutaDocumento())) {
                    abrirDocumento(tareaDetalle.getRutaDocumento());
                } else {
                    Toast.makeText(getApplicationContext(), "Esta tarea no tiene ningún archivo seleccionado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView tv_detallar_imagen = findViewById(R.id.tv_detallar_imagen);
        tv_detallar_imagen.setText(rutaANombre(tareaDetalle.getRutaImagen()));
        ImageView imageViewImagen = findViewById(R.id.iv_detallar_imagen);

        // Agregar OnClickListener para abrir la imagen
        tv_detallar_imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(tareaDetalle.getRutaImagen())) {
                    abrirFoto(tareaDetalle.getRutaImagen());
                } else {
                    Toast.makeText(getApplicationContext(), "Esta tarea no tiene ninguna imagen seleccionada", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imageViewImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(tareaDetalle.getRutaImagen())) {
                    abrirFoto(tareaDetalle.getRutaImagen());
                } else {
                    Toast.makeText(getApplicationContext(), "Esta tarea no tiene ninguna imagen seleccionada", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView tv_detallar_audio = findViewById(R.id.tv_detallar_audio);
        tv_detallar_audio.setText(rutaANombre(tareaDetalle.getRutaAudio()));
        ImageView imageViewAudio = findViewById(R.id.iv_detallar_audio);
        tv_detallar_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(tareaDetalle.getRutaAudio())) {
                    abrirAudio(tareaDetalle.getRutaAudio());
                } else {
                    Toast.makeText(getApplicationContext(), "Esta tarea no tiene ningún audio seleccionado", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imageViewAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(tareaDetalle.getRutaAudio())) {
                    abrirAudio(tareaDetalle.getRutaAudio());
                } else {
                    Toast.makeText(getApplicationContext(), "Esta tarea no tiene ningún audio seleccionado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView tv_detallar_video = findViewById(R.id.tv_detallar_video);
        tv_detallar_video.setText(rutaANombre(tareaDetalle.getRutaVideo()));
        ImageView imageViewVideo = findViewById(R.id.iv_detallar_video);
        tv_detallar_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(tareaDetalle.getRutaVideo())) {
                    abrirVideo(tareaDetalle.getRutaVideo());
                } else {
                    Toast.makeText(getApplicationContext(), "Esta tarea no tiene ningún vídeo seleccionado", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imageViewVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(tareaDetalle.getRutaVideo())) {
                    abrirVideo(tareaDetalle.getRutaVideo());
                } else {
                    Toast.makeText(getApplicationContext(), "Esta tarea no tiene ningún vídeo seleccionado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Control del botón volver
        Button btDetallarVolver = findViewById(R.id.bt_detallar_cerrar);
        btDetallarVolver.setOnClickListener(this::onBotonVolverClicked);
    }

    public void onBotonVolverClicked(View view) {
        finish();
    }

    private String rutaANombre(@Nullable String ruta) {
        if (ruta != null){
            File file = new File(ruta);
            return file.getName();
        }else
            return getString(R.string.tv_f2_not_present);
    }

    private void abrirFoto(String rutaFoto) {
        // Crear un intent para abrir la actividad de la foto
        Intent intent = new Intent(this, AbrirFotoActivity.class);
        // Pasar la ruta de la foto como datos en el intent
        intent.setData(Uri.parse("file://" + rutaFoto));
        startActivity(intent);
    }

    private void abrirVideo(String rutaVideo) {
        // Crear un intent para abrir la actividad de reproducción de vídeo
        Intent intent = new Intent(this, AbrirVideoActivity.class);
        // Pasar la ruta del vídeo como datos en el intent
        intent.setData(Uri.parse("file://" + rutaVideo));
        startActivity(intent);
    }

    private void abrirAudio(String rutaAudio) {
        // Crear un intent para abrir la actividad de reproducción de audio
        Intent intent = new Intent(this, ReproducirAudioActivity.class);
        // Pasar la ruta del vídeo como datos en el intent
        intent.setData(Uri.parse("file://" + rutaAudio));
        startActivity(intent);
    }

    private void abrirDocumento(String rutaDocumento) {
        // Crear un intent para abrir la actividad de reproducción de audio
        Intent intent = new Intent(this, AbrirDocumentoActivity.class);
        // Pasar la ruta del vídeo como datos en el intent
        intent.setData(Uri.parse("file://" + rutaDocumento));
        startActivity(intent);
    }

}