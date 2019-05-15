package com.everywhere.trip.ui.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.everywhere.trip.R;
import com.everywhere.trip.bean.MainDataBean;

import java.util.List;

public class DeViewAdapter extends RecyclerView.Adapter<DeViewAdapter.ViewHolder> {
    private Context context;
    private List<MainDataBean.ResultBean.RoutesBean> mRoutesList;

    public DeViewAdapter(Context context, List<MainDataBean.ResultBean.RoutesBean> mRoutesList) {
        this.context = context;
        this.mRoutesList = mRoutesList;
    }

    public void setmRoutesList(List<MainDataBean.ResultBean.RoutesBean> mRoutesList) {
        this.mRoutesList = mRoutesList;
    }

    @NonNull
    @Override
    public DeViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(View.inflate(context, R.layout.item_routes, null));
    }

    @Override
    public void onBindViewHolder(@NonNull DeViewAdapter.ViewHolder holder, int position) {
        holder.title.setText(mRoutesList.get(position).getTitle());
        Glide.with(context).load(mRoutesList.get(position).getCardURL()).into(holder.bigImg);
    }

    @Override
    public int getItemCount() {
        return mRoutesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bigImg;
        TextView title;
        TextView city;
        Button btnPay;
        TextView intro;
        TextView purchasedTimes;
        public ViewHolder(View itemView) {
            super(itemView);
            bigImg = itemView.findViewById(R.id.bg_img);
            title = itemView.findViewById(R.id.title);
            city = itemView.findViewById(R.id.city);
            btnPay = itemView.findViewById(R.id.btn_pay);
            intro = itemView.findViewById(R.id.intro);
            purchasedTimes = itemView.findViewById(R.id.purchasedTimes);
        }
    }
}
