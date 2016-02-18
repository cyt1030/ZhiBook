package com.srtianxia.zhibook.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.srtianxia.zhibook.R;
import com.srtianxia.zhibook.model.bean.zhihu.Story;
import com.srtianxia.zhibook.view.viewholder.DailyViewHolder;

import java.util.List;

/**
 * Created by srtianxia on 2016/1/23.
 * 添加 headView 的 RecyclerView adapter
 */
public class DailyAdapter extends RecyclerView.Adapter<DailyViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_FOOTER = 2;

    private Context context;
    private LayoutInflater inflater;
    private View headView;
    private View footView;
    private List<Story> items;
    private OnItemClickListener onItemClickListener;



    public DailyAdapter(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public DailyAdapter(Context context,List<Story> items){
        this.context = context;
        this.items = items;
        inflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setHeadView(View headView) {
        this.headView = headView;
    }

    public void setFootView(View footView) {
        this.footView = footView;
    }

    @Override
    public DailyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (headView!=null && viewType == TYPE_HEADER) return new DailyViewHolder(headView);
        if (footView!=null && viewType == TYPE_FOOTER) return new DailyViewHolder(footView);
         View view = inflater.inflate(R.layout.item_find_daily,parent,false);
        return new DailyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DailyViewHolder holder, final int position) {
        if(getItemViewType(position) == TYPE_HEADER) return;
        if (getItemViewType(position) == TYPE_FOOTER) return;
        final int pos = getRealPosition(holder);
        holder.bindData(items.get(pos),pos);
        if (onItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(holder.itemView,pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onLongClick(holder.itemView,pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return headView == null ? items.size() : items.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(headView == null) return TYPE_NORMAL;
        if (position == 0) {
            return TYPE_HEADER;
        } else return TYPE_NORMAL;
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return headView == null ? position : position - 1;
    }
}
