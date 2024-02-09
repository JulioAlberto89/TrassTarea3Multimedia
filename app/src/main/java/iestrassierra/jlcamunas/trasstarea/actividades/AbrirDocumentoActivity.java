package iestrassierra.jlcamunas.trasstarea.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import iestrassierra.jlcamunas.trasstarea.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.pdfview.PDFView;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class AbrirDocumentoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abrir_documento);

        FrameLayout container = findViewById(R.id.container);
        PDFView pdfView = new PDFView(this);
        container.addView(pdfView);
        // Obtener la Uri del documento de la intención
        Intent intent = getIntent();
        Uri documentoUri = intent.getData();


        // Obtener una referencia al PDFView
        pdfView = (PDFView) findViewById(R.id.pdfView);

        // Cargar el PDF en el PDFView
        try {
            // Convertir la Uri a un archivo
            File file = new File(new URI(documentoUri.toString()));

            // Cargar el archivo en el PDFView
            pdfView.fromFile(file).show();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}