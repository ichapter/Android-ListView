package com.ichapter.listviewandcheckbox.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ichapter.listviewandcheckbox.R;
import com.ichapter.listviewandcheckbox.entity.GoodsInfo;
import com.ichapter.listviewandcheckbox.icheckbox.ICheckBoxInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: liuzhang
 * @Description:
 * @Date: 2015/9/10 11:30
 */
public class ChildAdapter extends BaseAdapter {

    private List<HashMap<String, Object>> child_list;
    private Context mContext;
    private LayoutInflater mInflater;

    private ICheckBoxInterface checkBoxInterface;

    public ChildAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setCheckBoxInterface(ICheckBoxInterface checkBoxInterface) {
        this.checkBoxInterface = checkBoxInterface;
    }

    protected void addAll(List<HashMap<String, Object>> list) {
        this.child_list = list;
        notifyDataSetChanged();

    }

    protected void clearAll() {
        this.child_list.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return child_list.size();
    }

    @Override
    public Object getItem(int position) {
        return child_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_item_child, null);
            childViewHolder = new ChildViewHolder();
            childViewHolder.tv_goods_description = (TextView) convertView.findViewById(R.id.textView_goods_description);
            childViewHolder.tv_price_expired = (TextView) convertView.findViewById(R.id.textview_expired_price);
            childViewHolder.checkBox_goods = (CheckBox) convertView.findViewById(R.id.checkbox_child_goods);

            childViewHolder.layout_goods_delete = (RelativeLayout) convertView.findViewById(R.id.rl_goods_holder);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        //得到子item的数据
        Map<String, Object> child = (Map<String, Object>) getItem(position);
        //从子item数据集合中获取父类位置
        final int parentPosition = (int) child.get(ParentAdapter.SHOPCART_PARENT_POSITION);
        final GoodsInfo goodsInfo = (GoodsInfo) child.get(ParentAdapter.SHOPCART_DATA);
        if (goodsInfo != null) {
            childViewHolder.tv_goods_description.setText(goodsInfo.getDesc());
            final ChildViewHolder viewHolder = childViewHolder;
            childViewHolder.checkBox_goods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goodsInfo.setChoosed(viewHolder.checkBox_goods.isChecked());
                    /**
                     *  注意: 这里第一个参数,传入的参数是父类的位置(外层ListView第一个位置)，由于是两层ListView嵌套,
                     *  如果这里传入的是Position，那么根据的就是子ListView位置(这里不能根据子类的position，是根据父类的position)，而ListView第一行的位置一直都是0
                     *  之前的bug产生的原因，就是根据子ListView的位置来选择，所以有不能选中的原因
                     */
                    //  checkBoxInterface.checkChild(position, viewHolder.checkBox_goods.isChecked());
                    checkBoxInterface.checkChild(parentPosition, viewHolder.checkBox_goods.isChecked());
                }
            });
            childViewHolder.checkBox_goods.setChecked(goodsInfo.isChoosed());
            //根据保存在对象里的状态显示或是隐藏删除布局
            boolean delete = goodsInfo.isDelete();
            if (delete) {
                //显示删除
                childViewHolder.layout_goods_delete.setVisibility(View.VISIBLE);
            } else {//隐藏删除
                childViewHolder.layout_goods_delete.setVisibility(View.GONE);
            }

        }
        // childViewHolder.tv_goods_description.setText((CharSequence) child_list.get(position).get("goods_description"));
        childViewHolder.tv_price_expired.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

        //显示删除View
        return convertView;
    }


    static class ChildViewHolder {
        TextView tv_goods_description;
        TextView tv_price_expired;
        CheckBox checkBox_goods;
        RelativeLayout layout_goods_delete;
    }
}
