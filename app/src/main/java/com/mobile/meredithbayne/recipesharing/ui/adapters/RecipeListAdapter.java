package com.mobile.meredithbayne.recipesharing.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mobile.meredithbayne.recipesharing.R;
import com.mobile.meredithbayne.recipesharing.model.Recipe;

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

    public RecipeListAdapter(Context mContext, List<Recipe> mRecipeList) {
        this.mContext = mContext;
        this.mRecipeList = mRecipeList;
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

        String imagePath = mRecipeList.get(position).getImage();
        if (!imagePath.isEmpty()) {
            Glide.with(mContext)
                    .load(imagePath)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_recipe_placeholder)
                            .centerCrop()
                            .dontAnimate()
                            .dontTransform())
                    .into(holder.mRecipeImage);
        }
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

        RecipeViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> listener.onRecipeClick(getAdapterPosition()));
        }
    }
}
