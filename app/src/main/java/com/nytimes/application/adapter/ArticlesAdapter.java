package com.nytimes.application.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nytimes.application.api.Result;
import com.nytimes.application.databinding.ArticleItemBinding;
import com.nytimes.application.eventbus.DeleteNewsEvent;

import java.util.List;


public class ArticlesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Result> articleList;
    private final Context context;
    private final OnArticleItemClickListener clickListener;

    public ArticlesAdapter(Context context, List<Result> articleList, OnArticleItemClickListener clickListener) {
        this.articleList = articleList;
        this.context = context;
        this.clickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        LayoutInflater layoutInflater =
                LayoutInflater.from(viewGroup.getContext());

        ArticleItemBinding itemBinding =
                ArticleItemBinding.inflate(layoutInflater, viewGroup, false);

        return new RecentViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecentViewHolder itemViewHolder = (RecentViewHolder) holder;
        final Result article = articleList.get(holder.getAdapterPosition());

        itemViewHolder.bind(article);

        try {
            Glide.with(context).load(article.getMedia().get(0).getMediaMetadata().get(0).getUrl()).into((itemViewHolder.imageView));
        } catch (Exception e) {
            e.printStackTrace();
        }


        itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(article, itemViewHolder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public void notifyDeleteItem(DeleteNewsEvent deleteNewsEvent) {
        articleList.remove(deleteNewsEvent.getPosition());
        notifyItemRemoved(deleteNewsEvent.getPosition());
        notifyItemRangeChanged(deleteNewsEvent.getPosition(), articleList.size());
    }

    private class RecentViewHolder extends RecyclerView.ViewHolder {
        private ArticleItemBinding binding;
        private ImageView imageView;

        public RecentViewHolder(ArticleItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.imageView = binding.ivArticle;
        }

        public void bind(Result result) {
            binding.setArticle(result);
            binding.executePendingBindings();
        }
    }


    public interface OnArticleItemClickListener {
        void onItemClick(Result article, int position);
    }
}
