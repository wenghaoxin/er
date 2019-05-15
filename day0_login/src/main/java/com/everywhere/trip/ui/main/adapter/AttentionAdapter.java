package com.everywhere.trip.ui.main.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.everywhere.trip.R;
import com.everywhere.trip.base.BaseApp;
import com.everywhere.trip.bean.BanmiEntity;
import com.everywhere.trip.bean.FollowedBanmi;
import com.everywhere.trip.dao.DaoSession;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class AttentionAdapter extends RecyclerView.Adapter<AttentionAdapter.ViewHolder> {
    private Context context;
    private List<FollowedBanmi.ResultBean.BanmiBean> mMist;

    public AttentionAdapter(Context context, List<FollowedBanmi.ResultBean.BanmiBean> mMist) {
        this.context = context;
        this.mMist = mMist;
    }

    public void setmMist(List<FollowedBanmi.ResultBean.BanmiBean> mMist) {
        this.mMist = mMist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AttentionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = View.inflate(context, R.layout.menter_view, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final AttentionAdapter.ViewHolder holder, int position) {
        holder.tv_name.setText(mMist.get(position).getName());
        holder.tv_man.setText(mMist.get(position).getFollowing()+"人关注");
        holder.tv_author.setText(mMist.get(position).getOccupation());
        holder.tv_location.setText(mMist.get(position).getLocation());
        Glide.with(context).load(mMist.get(position).getPhoto()).into(holder.view_img);



    }

    @Override
    public int getItemCount() {
        return mMist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView view_img;
        private TextView tv_name;
        private TextView tv_man;
        private TextView tv_location;
        private TextView tv_author;
        private ImageView img_follow;
        public ViewHolder(View itemView) {
            super(itemView);
            view_img=itemView.findViewById(R.id.view_img);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_man=itemView.findViewById(R.id.tv_man);
            tv_location=itemView.findViewById(R.id.tv_location);
            tv_author=itemView.findViewById(R.id.tv_author);
            img_follow=itemView.findViewById(R.id.img_follow);
        }
    }
}
