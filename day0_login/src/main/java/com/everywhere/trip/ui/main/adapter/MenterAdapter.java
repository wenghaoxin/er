package com.everywhere.trip.ui.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.everywhere.trip.R;
import com.everywhere.trip.bean.BanmiEntity;

import java.util.List;



public class MenterAdapter extends RecyclerView.Adapter<MenterAdapter.ViewHolder> {
    private Context context;
    private List<BanmiEntity> mMist;

    public MenterAdapter(Context context, List<BanmiEntity> mMist) {
        this.context = context;
        this.mMist = mMist;
    }

    public void setmMist(List<BanmiEntity> mMist) {
        this.mMist = mMist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MenterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = View.inflate(context, R.layout.menter_view, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final MenterAdapter.ViewHolder holder, final int position) {
        holder.tv_name.setText(mMist.get(position).getName());
        holder.tv_man.setText(mMist.get(position).getFollowing()+"人关注");
        holder.tv_author.setText(mMist.get(position).getOccupation());
        holder.tv_location.setText(mMist.get(position).getLocation());
        Glide.with(context).load(mMist.get(position).getPhoto()).into(holder.view_img);
        //心形
        holder.img_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMist.get(position).isIsFollowed()){
                    like.remove(mMist.get(position).getId());
                    Glide.with(context)
                            .load(R.mipmap.follow_unselected)
                            .into(holder.img_follow);
                    mMist.get(position).setIsFollowed(false);

                }else {
                    like.like(mMist.get(position).getId());
                    Glide.with(context)
                            .load(R.mipmap.follow)
                            .into(holder.img_follow);
                    mMist.get(position).setIsFollowed(true);
                }
            }
        });
        //接口回调
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onBanmiClick!=null){
                    onBanmiClick.setonBanminClick(v,position);
                }

            }
        });
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
//定义一个接口
    public interface onBanmiClick{
            void setonBanminClick(View view,int i);
    }
    private  onBanmiClick onBanmiClick;

    public void setOnBanmiClick(MenterAdapter.onBanmiClick onBanmiClick) {
        this.onBanmiClick = onBanmiClick;
    }

//心形
    public interface OnItenClickListener{
        void like(int id);
        void remove(int id);
    }
    private OnItenClickListener like;

    public void setLike(OnItenClickListener like) {
        this.like = like;
    }
}
