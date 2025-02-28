package dam.pmdm.spyrothedragon.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import dam.pmdm.spyrothedragon.R;

public class FullScreenVideoActivity extends Activity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_video);

        VideoView videoView = findViewById(R.id.fullscreenVideoView);

        // Obtener la URI del video en la carpeta raw
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.easter_egg_video);
        videoView.setVideoURI(videoUri);

        // Agregar controles multimedia
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        // Iniciar la reproducción automática
        videoView.setOnPreparedListener(mp -> videoView.start());

        // Cerrar la actividad cuando el video termine
        videoView.setOnCompletionListener(mp -> finish());

        // Cerrar la actividad al tocar la pantalla
        videoView.setOnTouchListener((v, event) -> {
            finish();
            return true;
        });
    }
}
