package iestrassierra.jlcamunas.trasstarea.formas;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.Random;

public class Formas {
    public static final int CIRCULO = 0;
    public static final int CUADRADO = 1;
    public static final int TRIANGULO = 2;
    public static final int ESTRELLA = 3;

    private float x, y, velocidad, direccion, rotacion, velocidadRotacion;
    private int type;

    public Formas(float x, float y, float velocidad, int tipo, float velocidadRotacion) {
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        this.type = tipo;
        this.direccion = new Random().nextFloat() * 360;
        this.velocidadRotacion = velocidadRotacion;
    }

    public void update(int anchoPantalla, int altoPantalla) {
        x += velocidad * Math.cos(Math.toRadians(direccion));
        y += velocidad * Math.sin(Math.toRadians(direccion));
        rotacion += velocidadRotacion;
        handleBoundaries(anchoPantalla, altoPantalla);
    }

    private void handleBoundaries(int anchoPantalla, int altoPantalla) {
        if (x < 0 || x > anchoPantalla) {
            direccion = 180 - direccion;
        }

        if (y < 0 || y > altoPantalla) {
            direccion = -direccion;
        }
    }

    public void dibujar(Canvas canvas) {
        Paint paint = new Paint();
        canvas.save();  // Guarda el estado actual del lienzo
        canvas.rotate(rotacion, x, y);  // Aplica la rotación al lienzo

        switch (type) {
            case CIRCULO:
                paint.setColor(Color.CYAN);
                canvas.drawCircle(x, y, 50, paint);
                break;
            case CUADRADO:
                paint.setColor(Color.YELLOW);
                canvas.drawRect(x - 50, y - 50, x + 50, y + 50, paint);
                break;
            case TRIANGULO:
                paint.setColor(Color.GREEN);
                dibujarTriangulo(canvas, x, y, 50, paint);
                break;
            case ESTRELLA:
                paint.setColor(Color.MAGENTA);
                dibujarEstrella(canvas, x, y, 50, 5, paint);
                break;
        }

        canvas.restore();  // Restaura el estado del lienzo al original
    }

    private void dibujarTriangulo(Canvas canvas, float cx, float cy, float size, Paint paint) {
        Path path = new Path();
        path.moveTo(cx, cy - size);
        path.lineTo(cx - size / 2, cy + size / 2);
        path.lineTo(cx + size / 2, cy + size / 2);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void dibujarEstrella(Canvas canvas, float cx, float cy, float radius, int numPoints, Paint paint) {
        Path path = new Path();
        float angle = (float) Math.toRadians(360 / numPoints);

        for (int i = 0; i < numPoints; i++) {
            float outerX = cx + (float) Math.cos(angle * i) * radius;
            float outerY = cy + (float) Math.sin(angle * i) * radius;
            if (i == 0) {
                path.moveTo(outerX, outerY);
            } else {
                path.lineTo(outerX, outerY);
            }

            float innerX = cx + (float) Math.cos(angle * i + angle / 2) * (radius / 2);
            float innerY = cy + (float) Math.sin(angle * i + angle / 2) * (radius / 2);
            path.lineTo(innerX, innerY);
        }

        path.close();
        canvas.drawPath(path, paint);
    }
}
