package com.everywhere.trip.ui.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.everywhere.trip.R;
import com.everywhere.trip.bean.DyData;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.List;


public class DyViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<DyData.ResultBean.ActivitiesBean> mDylist;


    public DyViewAdapter(Context context, List<DyData.ResultBean.ActivitiesBean> mDylist) {
        this.context = context;
        this.mDylist = mDylist;
    }

    public void setmDylist(List<DyData.ResultBean.ActivitiesBean> mDylist) {
        this.mDylist = mDylist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(context, R.layout.dyreview, null);
            return new TextHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            TextHolder textHolder = (TextHolder) holder;
            textHolder.tv_context.setText(mDylist.get(position).getContent());
            final List<String> images = mDylist.get(position).getImages();
            textHolder.bannner.setImages(images)
                    .isAutoPlay(true)
                    .setImageLoader(new ImageLoader() {
                        @Override
                        public void displayImage(Context context, Object path, ImageView imageView) {

                            Glide.with(context).load(path).into(imageView);
                        }
                    })
                    .start();

    }


    @Override
    public int getItemCount() {
        return mDylist.size();
    }


    public class TextHolder extends RecyclerView.ViewHolder {
            private TextView tv_context;
        private Banner bannner;
        public TextHolder(View itemView) {
            super(itemView);
            tv_context=itemView.findViewById(R.id.tv_context);
            bannner=itemView.findViewById(R.id.dyview_banner);
        }
    }
}
