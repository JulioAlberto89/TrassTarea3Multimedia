package iestrassierra.jlcamunas.trasstarea.formas;

// Clase Shape
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.Random;

// Clase Shape
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.Random;

public class Shape {
    public static final int CIRCLE = 0;
    public static final int SQUARE = 1;
    public static final int TRIANGLE = 2;
    public static final int STAR = 3;

    private float x, y, speed, direction, rotation, rotationSpeed;
    private int type;

    public Shape(float x, float y, float speed, int type, float rotationSpeed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.type = type;
        this.direction = new Random().nextFloat() * 360;
        this.rotationSpeed = rotationSpeed;
    }

    public void update(int screenWidth, int screenHeight) {
        x += speed * Math.cos(Math.toRadians(direction));
        y += speed * Math.sin(Math.toRadians(direction));
        rotation += rotationSpeed;
        handleBoundaries(screenWidth, screenHeight);
    }

    private void handleBoundaries(int screenWidth, int screenHeight) {
        if (x < 0 || x > screenWidth) {
            direction = 180 - direction;
        }

        if (y < 0 || y > screenHeight) {
            direction = -direction;
        }
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        canvas.save();  // Guarda el estado actual del lienzo
        canvas.rotate(rotation, x, y);  // Aplica la rotación al lienzo

        switch (type) {
            case CIRCLE:
                paint.setColor(Color.RED);
                canvas.drawCircle(x, y, 50, paint);
                break;
            case SQUARE:
                paint.setColor(Color.BLUE);
                canvas.drawRect(x - 50, y - 50, x + 50, y + 50, paint);
                break;
            case TRIANGLE:
                paint.setColor(Color.GREEN);
                drawTriangle(canvas, x, y, 50, paint);
                break;
            case STAR:
                paint.setColor(Color.YELLOW);
                drawStar(canvas, x, y, 50, 5, paint); // Cambiado a 5 puntas
                break;
        }

        canvas.restore();  // Restaura el estado del lienzo al original
    }

    private void drawTriangle(Canvas canvas, float cx, float cy, float size, Paint paint) {
        Path path = new Path();
        path.moveTo(cx, cy - size);
        path.lineTo(cx - size / 2, cy + size / 2);
        path.lineTo(cx + size / 2, cy + size / 2);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawStar(Canvas canvas, float cx, float cy, float radius, int numPoints, Paint paint) {
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
