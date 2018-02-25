package com.example.cheaptriptravel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by waiwai on 25/2/2018.
 */

public class TimelineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private List<Timeline> timeList = new ArrayList<>(1);
    private static final int TYPE_TOP = 0x0000;
    private static final int TYPE_NORMAL= 0x0001;
    private OnItemPressedListener onItemPressedListener;
    private String traveltool;

    public void setOnItemPressedListener(
            OnItemPressedListener onItemPressedListener) {
        this.onItemPressedListener = onItemPressedListener;
    }

    protected static interface OnItemPressedListener{
        void onItemClick(int position);
        boolean OnItemLongClick(int position);
    }


    public TimelineAdapter(Context context, List<Timeline> timeList, String traveltool) {
        inflater = LayoutInflater.from(context);
        this.timeList = timeList;
        this.traveltool=traveltool;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_timeline, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder itemHolder = (ViewHolder) holder;
        if (getItemViewType(position) == TYPE_TOP) {
            // 第一行头的竖线不显示
            itemHolder.tvTopLine.setVisibility(View.INVISIBLE);
            // 字体颜色加深
            itemHolder.tvAcceptTime.setTextColor(0xff555555);
            itemHolder.tvAcceptStation.setTextColor(0xff555555);
            itemHolder.tvDot.setBackgroundResource(R.drawable.timeline_firstdot);
        } else if (getItemViewType(position) == TYPE_NORMAL) {
            itemHolder.tvTopLine.setVisibility(View.VISIBLE);
            itemHolder.tvAcceptTime.setTextColor(0xff999999);
            itemHolder.tvAcceptStation.setTextColor(0xff999999);
            itemHolder.traveltool.setTextColor(0xff999999);
            itemHolder.tvDot.setBackgroundResource(R.drawable.timeline_normaldot);
        }

        itemHolder.bindHolder(timeList.get(position));
    }

    @Override
    public int getItemCount() {
        return timeList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TOP;
        }
        return TYPE_NORMAL;
    }

    public void setOnItemPressedListener() {
    }

    public  class ViewHolder extends RecyclerView.ViewHolder  {
        private TextView tvAcceptTime, tvAcceptStation, traveltool;
        private TextView tvTopLine, tvDot;
        public ViewHolder(View itemView) {
            super(itemView);
            tvAcceptTime = (TextView) itemView.findViewById(R.id.tvAcceptTime);
            tvAcceptStation = (TextView) itemView.findViewById(R.id.tvAcceptStation);
            tvTopLine = (TextView) itemView.findViewById(R.id.tvTopLine);
            tvDot = (TextView) itemView.findViewById(R.id.tvDot);
            traveltool = (TextView) itemView.findViewById(R.id.traveltool);

            if(itemView!=null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemPressedListener != null) {
                            onItemPressedListener.onItemClick(getPosition());
                        }
                    }
                });
            }
        }

        public void bindHolder(Timeline time) {
            tvAcceptTime.setText(time.getAcceptTime());
            tvAcceptStation.setText(time.getAcceptStation());
            traveltool.setText(time.getTravelTool());
        }


    }
}

