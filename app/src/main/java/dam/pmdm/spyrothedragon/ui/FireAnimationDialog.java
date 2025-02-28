package dam.pmdm.spyrothedragon.ui;

import static android.view.WindowManager.*;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import dam.pmdm.spyrothedragon.R;
import dam.pmdm.spyrothedragon.models.Character;

public class FireAnimationDialog extends Dialog {

    private Character character;
    private FireAnimationView fireAnimationView;

    public FireAnimationDialog(Context context, Character character) {
        super(context);
        this.character = character;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_fire_animation);

        // El diálogo ocupa toda la pantalla
        LayoutParams params = getWindow().getAttributes();
        params.width = LayoutParams.MATCH_PARENT;
        params.height = LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);

        ImageView characterImageView = findViewById(R.id.character_image);
        TextView characterNameTextView = findViewById(R.id.character_name);

        @SuppressLint("DiscouragedApi") int imageResId = getContext().getResources().getIdentifier(
                character.getImage(), "drawable", getContext().getPackageName());
        characterImageView.setImageResource(imageResId);
        characterNameTextView.setText(character.getName());

        // Inicializa la animación de fuego
        fireAnimationView = findViewById(R.id.fire_animation_view);
        fireAnimationView.startAnimation();

        // Cerrar el diálogo
        findViewById(R.id.close_button).setOnClickListener(v -> dismiss());
    }
}