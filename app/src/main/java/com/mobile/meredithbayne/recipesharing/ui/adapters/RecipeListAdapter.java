package com.mobile.meredithbayne.recipesharing.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.meredithbayne.recipesharing.R;
import com.mobile.meredithbayne.recipesharing.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {
    private Context mContext;

    private List<Recipe> mRecipeList;
    private RecipeClickListener listener;

    public interface RecipeClickListener {
        void onRecipeClick(int position);
    }

    public RecipeListAdapter(Context mContext, List<Recipe> mRecipeList, RecipeClickListener listener) {
        this.mContext = mContext;
        this.mRecipeList = mRecipeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.mRecipeName.setText(mRecipeList.get(position).getName());
        holder.mServings.setText(String.valueOf(mRecipeList.get(position).getServings()));

        String imagePath = mRecipeList.get(position).getImage();
        Picasso.Builder builder = new Picasso.Builder(mContext);
        if (TextUtils.isEmpty(imagePath)) {
            holder.mRecipeImage.setImageResource(R.drawable.ic_recipe_placeholder);
        } else {
            builder.build().load(imagePath)
                    .noFade()
                    .error(R.drawable.ic_error_outline_black)
                    .into(holder.mRecipeImage);
        }
    }

    public Recipe getRecipeItem(int id) {
        return mRecipeList == null ? null : mRecipeList.get(id);
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_name)
        TextView mRecipeName;

        @BindView(R.id.recipe_image)
        AppCompatImageView mRecipeImage;

        @BindView(R.id.servings)
        TextView mServings;

        RecipeViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> listener.onRecipeClick(getAdapterPosition()));
        }
    }
}
