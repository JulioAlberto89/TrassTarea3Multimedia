package iestrassierra.jlcamunas.trasstarea.actividades;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
        tv_detallar_documento.setText(rutaANombre(tareaDetalle.getRutaDocumento()));

        TextView tv_detallar_imagen = findViewById(R.id.tv_detallar_imagen);
        tv_detallar_imagen.setText(rutaANombre(tareaDetalle.getRutaImagen()));

        TextView tv_detallar_audio = findViewById(R.id.tv_detallar_audio);
        tv_detallar_audio.setText(rutaANombre(tareaDetalle.getRutaAudio()));

        TextView tv_detallar_video = findViewById(R.id.tv_detallar_video);
        tv_detallar_video.setText(rutaANombre(tareaDetalle.getRutaVideo()));

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
}