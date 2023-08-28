package com.learn.architecture.mvvma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learn.architecture.databinding.GameCardBinding;
import com.learn.architecture.model.Game;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    private final Context context;
    private final List<Game> gamesList;

    public GameAdapter(Context context, List<Game> gamesList) {
        this.context = context;
        this.gamesList = gamesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GameCardBinding binding = GameCardBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(gamesList.get(position));
    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private GameCardBinding binding;

        public ViewHolder(GameCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(Game game) {
            binding.titleTV.setText(game.title);
            binding.descriptionTV.setText(game.description);
            binding.genreTV.setText(game.genre);
            binding.platformTV.setText(game.platform);
        }
    }

}
