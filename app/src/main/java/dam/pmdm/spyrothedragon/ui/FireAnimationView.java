package dam.pmdm.spyrothedragon.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Random;

public class FireAnimationView extends View {
    private static final int NUM_FLAMES = 40;
    private static final int MAX_POINTS = 8;

    private Paint flamePaint;
    private ArrayList<Flame> flames;
    private Random random;
    private boolean isAnimating = false;

    public FireAnimationView(Context context) {
        super(context);
        init();
    }

    public FireAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FireAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        flamePaint = new Paint();
        flamePaint.setAntiAlias(true);
        flamePaint.setStyle(Paint.Style.FILL);

        flames = new ArrayList<>();
        random = new Random();

        // Inicializa las llamas
        resetFlames();
    }

    private void resetFlames() {
        flames.clear();
        for (int i = 0; i < NUM_FLAMES; i++) {
            flames.add(new Flame());
        }
    }

    public void startAnimation() {
        isAnimating = true;
        invalidate();
    }

    public void stopAnimation() {
        isAnimating = false;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        if (!isAnimating) return;

        // Boca de Spyro
        float mouthX = getWidth() * 0.5f;
        float mouthY = getHeight() * 0.65f;

        // Dibujar las llamas
        for (Flame flame : flames) {
            flame.update(mouthX, mouthY);
            flame.draw(canvas, flamePaint);
        }

        invalidate();
    }

    // Clase para representar una llama
    private class Flame {
        private Path path;
        private int alpha;
        private int color;
        private float[] xPoints;
        private float[] yPoints;
        private float speedY;
        private float speedX;
        private float size;
        private float lifespan;
        private float age;

        public Flame() {
            reset(0, 0);
        }

        public void reset(float x, float y) {
            path = new Path();

            // Colores del fuego
            int[] colors = {Color.RED, Color.rgb(255, 165, 0), Color.YELLOW};
            color = colors[random.nextInt(colors.length)];

            alpha = 200 + random.nextInt(56); // 200-255

            int numPoints = 3 + random.nextInt(MAX_POINTS - 2);
            xPoints = new float[numPoints];
            yPoints = new float[numPoints];

            speedY = -2f - random.nextFloat() * 3f;
            speedX = (random.nextFloat() - 0.5f) * 2f;

            size = 10f + random.nextFloat() * 30f;
            lifespan = 20f + random.nextFloat() * 30f;
            age = 0;

            // Punto inicial
            xPoints[0] = x;
            yPoints[0] = y;

            // Generar puntos aleatorios para la forma de la llama
            for (int i = 1; i < numPoints; i++) {
                xPoints[i] = x + (random.nextFloat() - 0.5f) * size;
                yPoints[i] = y - i * size / numPoints;
            }
        }

        public void update(float x, float y) {
            age++;

            // Reiniciar la llama
            if (age > lifespan) {
                reset(x, y);
                return;
            }

            // Actualizar posición
            for (int i = 0; i < xPoints.length; i++) {
                xPoints[i] += speedX;
                yPoints[i] += speedY * (i + 1) / xPoints.length; // Los puntos superiores se mueven más rápido
            }

            // Reducir la opacidad con el tiempo
            alpha = (int) (200 + 55 * (1 - age / lifespan));
        }

        public void draw(Canvas canvas, Paint paint) {
            if (xPoints.length < 3) return;

            // Configurar el color con opacidad
            paint.setColor(color);
            paint.setAlpha(alpha);

            // Crear la ruta de la llama
            path.reset();
            path.moveTo(xPoints[0], yPoints[0]);

            // Crear una forma suavizada
            for (int i = 1; i < xPoints.length; i++) {
                if (i < xPoints.length - 1) {
                    float x1 = xPoints[i];
                    float y1 = yPoints[i];
                    float x2 = xPoints[i + 1];
                    float y2 = yPoints[i + 1];

                    path.quadTo(x1, y1, (x1 + x2) / 2, (y1 + y2) / 2);
                } else {
                    path.lineTo(xPoints[i], yPoints[i]);
                }
            }

            // Cerrar la ruta
            path.lineTo(xPoints[xPoints.length - 1] + size/4, yPoints[yPoints.length - 1]);
            path.lineTo(xPoints[0], yPoints[0]);
            path.close();

            // Dibujar la llama
            canvas.drawPath(path, paint);
        }
    }
}
