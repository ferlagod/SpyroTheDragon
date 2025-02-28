package dam.pmdm.spyrothedragon;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import dam.pmdm.spyrothedragon.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private NavController navController = null;

    private ActivityMainBinding binding;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Verificar si la guia ya fue completada
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        boolean guideCompleted = sharedPreferences.getBoolean("guideCompleted", false);

        // Si no se completó
        if (!guideCompleted) {
            initGuide();}

        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(binding.navView, navController);
            NavigationUI.setupActionBarWithNavController(this, navController);
        }

        binding.navView.setOnItemSelectedListener(this::selectedBottomMenu);

        //Inicializo el mediaplayer
        mediaPlayer = new MediaPlayer();

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            getSupportActionBar().setDisplayHomeAsUpEnabled(destination.getId() != R.id.navigation_characters &&
                    destination.getId() != R.id.navigation_worlds &&
                    destination.getId() != R.id.navigation_collectibles);
        });

    }

    private void initGuide() {

        View includeLayout = findViewById(R.id.includeLayout);

        // Metodo para manejar el cierre de la guía
        View.OnClickListener closeGuideListener = v -> {
            hideGuide();
        };

        // Todas las vistas
        View guide1 = findViewById(R.id.guide1);
        View guide2 = findViewById(R.id.guide2);
        View guide3 = findViewById(R.id.guide3);
        View guide4 = findViewById(R.id.guide4);
        View guide5 = findViewById(R.id.guide5);
        View guide6 = findViewById(R.id.guide6);

        // Animaciones
        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation textAnimation = AnimationUtils.loadAnimation(this, R.anim.textview_animation);
        Animation slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_from_bottom); // Deslizamiento de abajo hacia arriba
        Animation slideOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_to_bottom); // Deslizamiento hacia abajo


        // Pantalla 1: Bienvenida
        Button buttonSkip1 = findViewById(R.id.button_skip_1);
        Button buttonStart = findViewById(R.id.button_start);
        guide1.setVisibility(View.VISIBLE);
        playSound(R.raw.spfx_star); // Reproduce el sonido de Spyro

        buttonSkip1.setOnClickListener(closeGuideListener);
        buttonStart.setOnClickListener(v -> {
            guide1.startAnimation(fadeOut);
            guide1.setVisibility(View.GONE);

            // Mostrar pantalla 2 con una animación
            guide2.setVisibility(View.VISIBLE);
            guide2.startAnimation(slideIn);
            playSound(R.raw.spfx_1);

            // Navegar a personajes
            if (navController != null) {
                navController.navigate(R.id.navigation_characters);
            }

            // Animar el TextView de pantalla 2
            TextView textCharacters = findViewById(R.id.info_texto);
            textCharacters.startAnimation(textAnimation);
        });

        // Pantalla 2: Personajes
        Button buttonNext2 = findViewById(R.id.button_next_2);
        Button buttonSkip2 = findViewById(R.id.button_skip_2);
        buttonNext2.setOnClickListener(v -> {
            guide2.startAnimation(slideOut);
            guide2.setVisibility(View.GONE);

            // Mostrar pantalla 3 con una animación
            guide3.setVisibility(View.VISIBLE);
            guide3.startAnimation(slideIn);
            playSound(R.raw.spfx_1);

            // Navegar a mundos
            if (navController != null) {
                navController.navigate(R.id.navigation_worlds);
            }

            // Animar el TextView de pantalla 3
            TextView textCharacters = findViewById(R.id.info_texto_mundos);
            textCharacters.startAnimation(textAnimation);
        });
        buttonSkip2.setOnClickListener(closeGuideListener);

        // Pantalla 3: Mundos
        Button buttonNext3 = findViewById(R.id.button_next_3);
        Button buttonSkip3 = findViewById(R.id.button_skip_3);
        buttonNext3.setOnClickListener(v -> {
            guide3.startAnimation(slideOut);
            guide3.setVisibility(View.GONE);

            // Mostrar pantalla 4 con una  animación
            guide4.setVisibility(View.VISIBLE);
            guide4.startAnimation(slideIn);
            playSound(R.raw.spfx_1);

            // Navegar a coleccionables
            if (navController != null) {
                navController.navigate(R.id.navigation_collectibles);
            }
        });
        buttonSkip3.setOnClickListener(closeGuideListener);


        // Pantalla 4: Coleccionables
        Button buttonNext4 = findViewById(R.id.button_next_4);
        Button buttonSkip4 = findViewById(R.id.button_skip_4);
        buttonNext4.setOnClickListener(v -> {
            guide4.startAnimation(slideOut);
            guide4.setVisibility(View.GONE);

            // Mostrar pantalla 5 con una animación
            guide5.setVisibility(View.VISIBLE);
            guide5.startAnimation(slideIn);
            playSound(R.raw.spfx_1);

            // Animar el TextView de pantalla 4
            TextView textCharacters = findViewById(R.id.info_texto_coleccionables);
            textCharacters.startAnimation(textAnimation);
        });
        buttonSkip4.setOnClickListener(closeGuideListener);


        // Pantalla 5: Icono de información
        Button buttonNext5 = findViewById(R.id.button_next_5);
        Button buttonSkip5 = findViewById(R.id.button_skip_5);
        buttonNext5.setOnClickListener(v -> {
            guide5.startAnimation(slideOut);
            guide5.setVisibility(View.GONE);

            // Mostrar pantalla 6 con una animación
            guide6.setVisibility(View.VISIBLE);
            guide6.startAnimation(slideIn);
            playSound(R.raw.spfx_1);

            // Animar el TextView de pantalla 5
            TextView textCharacters = findViewById(R.id.info_texto_info);
            textCharacters.startAnimation(textAnimation);
        });
        buttonSkip5.setOnClickListener(closeGuideListener);


        // Pantalla 6: Resumen final
        Button buttonFinish = findViewById(R.id.button_finish);
        buttonFinish.setOnClickListener(v -> {

            // Guardar que la guía fue completada
            SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("guideCompleted", true);
            editor.apply();
            hideGuide();
            playSound(R.raw.spfx_end);
        });
    }


    // Metodo para ocultar la guía
    private void hideGuide() {
        View includeLayout = findViewById(R.id.includeLayout);
        includeLayout.setVisibility(View.GONE);

    }

    private void playSound(int soundResource) {
        if (mediaPlayer != null) {
            mediaPlayer.release(); // Libera recursos antes de reproducir un nuevo sonido
        }
        mediaPlayer = MediaPlayer.create(this, soundResource);
        mediaPlayer.start(); // Reproduce el sonido
    }

    private boolean selectedBottomMenu(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.nav_characters)
            navController.navigate(R.id.navigation_characters);
        else if (menuItem.getItemId() == R.id.nav_worlds)
            navController.navigate(R.id.navigation_worlds);
        else
            navController.navigate(R.id.navigation_collectibles);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infla el menú
        getMenuInflater().inflate(R.menu.about_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Gestiona el clic en el ítem de información
        if (item.getItemId() == R.id.action_info) {
            showInfoDialog();  // Muestra el diálogo
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showInfoDialog() {
        // Crear un diálogo de información
        new AlertDialog.Builder(this)
                .setTitle(R.string.title_about)
                .setMessage(R.string.text_about)
                .setPositiveButton(R.string.accept, null)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release(); // Libera recursos
        }
    }
}
