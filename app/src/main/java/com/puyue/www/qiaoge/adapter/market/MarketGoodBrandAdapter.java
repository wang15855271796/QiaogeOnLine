package com.puyue.www.qiaoge.adapter.market;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.cart.MarketBeanModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王文博} on 2019/5/21
 */
public class MarketGoodBrandAdapter extends RecyclerView.Adapter<MarketGoodBrandAdapter.BrandViewHolder> {

    private Context context;
    private List<MarketBeanModel> list;
    private List<Integer> lists=new ArrayList<>();
    private List<MarketBeanModel> selectList =new ArrayList<>();
    BrandViewHolder holder;
    private OnItemClickListener onItemClickListener;

    public MarketGoodBrandAdapter(Context context, List<MarketBeanModel> list) {
        this.context = context;
        this.list = list;
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setStat() {
        for (MarketBeanModel bean:list) {
            bean.setState(false);

        }

        for (int i = 0; i < list.size(); i++) {
//            holder.mTvContent.setText("");
            selectList.remove(list.get(i));

        }
        onItemClickListener.onItemClick(selectList);

        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.market_brand, parent, false);
        return new BrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandViewHolder holder, int position) {
        this.holder = holder;
        if (list!=null&&list.get(position) !=null){
            if (list.get(position).isState()){

                holder.mTvContent.setTextColor(Color.parseColor("#FF5000"));

            }else {
                holder.mTvContent.setTextColor(Color.parseColor("#636363"));
            }
            holder.mTvContent.setText(list.get(position).getS());
        }


        holder.mTvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mTvContent.isChecked()){
                    selectList.add(list.get(position));
                    holder.mTvContent.setTextColor(Color.parseColor("#FF5000"));

                }else {
                    selectList.remove(list.get(position));
                    holder.mTvContent.setTextColor(Color.parseColor("#636363"));

                }
                onItemClickListener.onItemClick(selectList);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

  /*  private TextView mTvContent;

    public MarketGoodBrandAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        mTvContent = helper.getView(R.id.tv_content);
        mTvContent.setText(item);

    }*/


    public class BrandViewHolder extends RecyclerView.ViewHolder {
        CheckBox mTvContent;

        public BrandViewHolder(View itemView) {
            super(itemView);
            mTvContent = itemView.findViewById(R.id.tv_content);


        }
    }



    public interface OnItemClickListener {
        void onItemClick(List<MarketBeanModel> position);


    }
}
