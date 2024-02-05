package iestrassierra.jlcamunas.trasstarea.formas;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.Random;

public class HilosFormas extends Thread {
    private ArrayList<Formas> formas;
    private SurfaceHolder surfaceHolder;
    private boolean running = true;

    public HilosFormas(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        formas = new ArrayList<>();

        // Crea 3 círculos, 3 cuadrados, 3 triángulos y 3 estrellas
        for (int i = 0; i < 3; i++) {
            formas.add(new Formas(getRandomX(surfaceHolder.getSurfaceFrame().right),
                    getRandomY(surfaceHolder.getSurfaceFrame().bottom),
                    5 + new Random().nextFloat() * 10, Formas.CIRCULO,
                    2 + new Random().nextFloat() * 5)); // Velocidad de rotación entre 2 y 7 grados por iteración
            formas.add(new Formas(getRandomX(surfaceHolder.getSurfaceFrame().right),
                    getRandomY(surfaceHolder.getSurfaceFrame().bottom),
                    5 + new Random().nextFloat() * 10, Formas.CUADRADO,
                    2 + new Random().nextFloat() * 5));
            formas.add(new Formas(getRandomX(surfaceHolder.getSurfaceFrame().right),
                    getRandomY(surfaceHolder.getSurfaceFrame().bottom),
                    5 + new Random().nextFloat() * 10, Formas.TRIANGULO,
                    2 + new Random().nextFloat() * 5));
            formas.add(new Formas(getRandomX(surfaceHolder.getSurfaceFrame().right),
                    getRandomY(surfaceHolder.getSurfaceFrame().bottom),
                    5 + new Random().nextFloat() * 10, Formas.ESTRELLA,
                    2 + new Random().nextFloat() * 5));
        }
    }

    private float getRandomX(int maxX) {
        return new Random().nextFloat() * maxX;
    }

    private float getRandomY(int maxY) {
        return new Random().nextFloat() * maxY;
    }

    @Override
    public void run() {
        while (running) {
            Canvas canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas();
                if (canvas != null) {
                    // Actualiza la posición y la rotación de todas las formas
                    update();
                    // Dibuja todas las formas en la pantalla
                    draw(canvas);
                }
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    private void update() {
        for (Formas shape : formas) {
            // Actualiza la posición y la rotación de cada forma y maneja los rebotes
            shape.update(surfaceHolder.getSurfaceFrame().right, surfaceHolder.getSurfaceFrame().bottom);
        }
    }

    private void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        // Dibuja todas las formas en la pantalla
        for (Formas shape : formas) {
            shape.dibujar(canvas);
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
