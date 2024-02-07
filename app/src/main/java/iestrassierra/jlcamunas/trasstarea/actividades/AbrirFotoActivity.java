package iestrassierra.jlcamunas.trasstarea.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import iestrassierra.jlcamunas.trasstarea.R;

public class AbrirFotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abrir_foto);

        // Obtener la Uri de la foto de la intención
        Intent intent = getIntent();
        Uri fotoUri = intent.getData();

        // Mostrar la foto en el ImageView
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageURI(fotoUri);
    }
}