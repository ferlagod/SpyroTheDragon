package dam.pmdm.spyrothedragon.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import dam.pmdm.spyrothedragon.R;
import dam.pmdm.spyrothedragon.models.Character;
import dam.pmdm.spyrothedragon.ui.FireAnimationDialog;

import java.util.List;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder> {

    private List<Character> list;

    public CharactersAdapter(List<Character> charactersList) {
        this.list = charactersList;
    }

    @Override
    public CharactersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new CharactersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CharactersViewHolder holder, int position) {
        Character character = list.get(position);
        holder.nameTextView.setText(character.getName());

        // Cargar la imagen
        int imageResId = holder.itemView.getContext().getResources().getIdentifier(
                character.getImage(), "drawable", holder.itemView.getContext().getPackageName());
        holder.imageImageView.setImageResource(imageResId);

        // Listener de pulsación prolongada solo para Spyro
        if ("Spyro".equals(character.getName())) {
            holder.itemView.setOnLongClickListener(v -> {
                // Animación de fuego
                FireAnimationDialog dialog = new FireAnimationDialog(v.getContext(), character);
                dialog.show();
                return true;
            });
        } else {
            // Eliminar el listener para otros personajes
            holder.itemView.setOnLongClickListener(null);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CharactersViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        ImageView imageImageView;

        public CharactersViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            imageImageView = itemView.findViewById(R.id.image);
        }
    }
}