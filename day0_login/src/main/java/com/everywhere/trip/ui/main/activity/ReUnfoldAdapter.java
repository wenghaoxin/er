package com.everywhere.trip.ui.main.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.everywhere.trip.R;
import com.everywhere.trip.bean.MainDataUnFOld;
import com.everywhere.trip.ui.main.adapter.RecImageAdapter;
import com.everywhere.trip.util.GlideUtil;
import com.everywhere.trip.util.ToastUtil;

import java.util.List;

class ReUnfoldAdapter  extends RecyclerView.Adapter{
    private final int IMAGE_TYPE = 0;
    private final int AUTHOR_TYPE = 1;
    private final int REVIEW_TYPE = 2;
    private final int LIKE_TYPE = 3;

    private Context context;
    private MainDataUnFOld.ResultEntity resultEntity;

    public ReUnfoldAdapter(Context context, MainDataUnFOld.ResultEntity resultEntity) {
        this.context = context;
        this.resultEntity = resultEntity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == IMAGE_TYPE) {
            return new ReUnfoldAdapter.ImageHolder(View.inflate(context, R.layout.item_img, null));
        } else if (viewType == AUTHOR_TYPE) {
            return new ReUnfoldAdapter.AuthorHolder(View.inflate(context, R.layout.item_author, null));
        } else if (viewType == REVIEW_TYPE) {
            return new ReUnfoldAdapter.ReviewsHolder(View.inflate(context, R.layout.item_review, null));
        } else {
            return new ReUnfoldAdapter.LikeHolder(View.inflate(context, R.layout.item_like_share, null));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        int index = position;
        if (viewType == IMAGE_TYPE) {
            ReUnfoldAdapter.ImageHolder imageHolder = (ReUnfoldAdapter.ImageHolder) holder;
            GlideUtil.loadUrlImage(R.mipmap.zhanweitu_xianluxiangqing, R.mipmap.zhanweitu_xianluxiangqing,
                    resultEntity.getCarousel().get(0), imageHolder.img, context);
        } else if (viewType == AUTHOR_TYPE) {
            MainDataUnFOld.ResultEntity.BanmiEntity banmi = resultEntity.getBanmi();
            ReUnfoldAdapter.AuthorHolder authorHolder = (ReUnfoldAdapter.AuthorHolder) holder;
            GlideUtil.loadUrlCircleImage(R.mipmap.zhanweitu_touxiang, R.mipmap.zhanweitu_touxiang,
                    banmi.getPhoto(), authorHolder.ivHeader, context);
            authorHolder.tvName.setText(banmi.getName());
            authorHolder.tvOccupation.setText(banmi.getOccupation());
            authorHolder.tvCity.setText(banmi.getLocation());
            authorHolder.tvIntro.setText(banmi.getIntroduction());
        } else if (viewType == REVIEW_TYPE) {
            List<MainDataUnFOld.ResultEntity.ReviewsEntity> list = resultEntity.getReviews();
            ReUnfoldAdapter.ReviewsHolder reviewsHolder = (ReUnfoldAdapter.ReviewsHolder) holder;
            reviewsHolder.tvName.setText(list.get(position - 2).getUserName());
            reviewsHolder.tvDate.setText(list.get(position - 2).getCreatedAt());
            reviewsHolder.tvContent.setText(list.get(position - 2).getContent());
            GlideUtil.loadUrlCircleImage(R.mipmap.zhanweitu_touxiang, R.mipmap.zhanweitu_touxiang,
                    list.get(position - 2).getUserPhoto(), reviewsHolder.ivHeader, context);
            if (list.get(position - 2).getImages() != null && list.get(position - 2).getImages().size() != 0) {
                reviewsHolder.recImgs.setLayoutManager(new LinearLayoutManager(context,LinearLayout.HORIZONTAL,false));
                RecImageAdapter adapter = new RecImageAdapter(context, list.get(position - 2).getImages());
                reviewsHolder.recImgs.setAdapter(adapter);
                adapter.setOnImageClickListener(new RecImageAdapter.OnImageClickListener() {
                    @Override
                    public void onClick(String imgUrl) {
                        if (onImageClickListener != null){
                            onImageClickListener.onClick(imgUrl);
                        }
                    }
                });
            }
        } else {
            final ReUnfoldAdapter.LikeHolder likeHolder = (ReUnfoldAdapter.LikeHolder) holder;
            if (resultEntity.getRoute().isIsCollected()) {
                GlideUtil.loadResImage(R.mipmap.collect_default, R.mipmap.collect_default,
                        R.mipmap.collect_highlight, likeHolder.ivLike, context);
                likeHolder.tvLike.setText("已收藏");
            } else {
                GlideUtil.loadResImage(R.mipmap.collect_default, R.mipmap.collect_default,
                        R.mipmap.collect_default, likeHolder.ivLike, context);
                likeHolder.tvLike.setText("收藏");
            }
            likeHolder.showAllContent.setText("查看全部" + resultEntity.getReviewsCount() + "条评价");
            likeHolder.showAllContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (showAllReviews != null){
                        showAllReviews.showAllReview(resultEntity.getRoute().getId());
                    }
                }
            });
            likeHolder.rlLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (resultEntity.getRoute().isIsCollected()) {
                        if (onItemLikeListener != null) {
                            onItemLikeListener.disLike(resultEntity.getRoute().getId());
                            GlideUtil.loadResImage(R.drawable.back_black, R.drawable.back_black,
                                    R.mipmap.collect_default, likeHolder.ivLike, context);
                            ToastUtil.showShort("已取消收藏");
                            likeHolder.tvLike.setText("收藏");
                            resultEntity.getRoute().setIsCollected(false);
                        }
                    } else {
                        if (onItemLikeListener != null) {
                            onItemLikeListener.addLike(resultEntity.getRoute().getId());
                            GlideUtil.loadResImage(R.drawable.back_black, R.drawable.back_black,
                                    R.mipmap.collect_highlight, likeHolder.ivLike, context);
                            ToastUtil.showShort("已收藏");
                            likeHolder.tvLike.setText("已收藏");
                            resultEntity.getRoute().setIsCollected(true);
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 3 + resultEntity.getReviews().size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return IMAGE_TYPE;
        } else if (position == 1) {
            return AUTHOR_TYPE;
        } else if (position == getItemCount() - 1) {
            return LIKE_TYPE;
        } else {
            return REVIEW_TYPE;
        }
    }

    class ImageHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public ImageHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
        }
    }

    class AuthorHolder extends RecyclerView.ViewHolder {
        ImageView ivHeader;
        TextView tvName;
        TextView tvOccupation;
        TextView tvCity;
        TextView tvIntro;

        public AuthorHolder(View itemView) {
            super(itemView);
            ivHeader = itemView.findViewById(R.id.iv_header);
            tvName = itemView.findViewById(R.id.tv_name);
            tvOccupation = itemView.findViewById(R.id.tv_occupation);
            tvCity = itemView.findViewById(R.id.tv_city);
            tvIntro = itemView.findViewById(R.id.tv_intro);
        }
    }

    class ReviewsHolder extends RecyclerView.ViewHolder {
        ImageView ivHeader;
        TextView tvName;
        TextView tvDate;
        TextView tvContent;
        RecyclerView recImgs;

        public ReviewsHolder(View itemView) {
            super(itemView);
            ivHeader = itemView.findViewById(R.id.iv_header);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvContent = itemView.findViewById(R.id.tv_content);
            recImgs = itemView.findViewById(R.id.rec_imgs);
        }
    }

    class LikeHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlLike;
        ImageView ivLike;
        TextView showAllContent;
        TextView tvLike;

        public LikeHolder(View itemView) {
            super(itemView);
            rlLike = itemView.findViewById(R.id.rl_like);
            ivLike = itemView.findViewById(R.id.iv_like);
            showAllContent = itemView.findViewById(R.id.show_all_content);
            tvLike = itemView.findViewById(R.id.tv_like);
        }
    }

    private ReUnfoldAdapter.OnItemLikeListener onItemLikeListener;

    public void setOnItemLikeListener(ReUnfoldAdapter.OnItemLikeListener onItemLikeListener) {
        this.onItemLikeListener = onItemLikeListener;
    }

    public interface OnItemLikeListener {
        void addLike(int id);

        void disLike(int id);
    }
    private ReUnfoldAdapter.OnImageClickListener onImageClickListener;

    public void setOnImageClickListener(ReUnfoldAdapter.OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    public interface OnImageClickListener{
        void onClick(String imgUrl);
    }

    private ReUnfoldAdapter.ShowAllReviews showAllReviews;

    public void setShowAllReviews(ReUnfoldAdapter.ShowAllReviews showAllReviews) {
        this.showAllReviews = showAllReviews;
    }

    public interface ShowAllReviews {
        void showAllReview(int id);
    }
}
