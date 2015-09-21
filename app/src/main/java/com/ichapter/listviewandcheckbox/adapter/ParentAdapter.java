package com.ichapter.listviewandcheckbox.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.ichapter.listviewandcheckbox.R;
import com.ichapter.listviewandcheckbox.entity.GroupInfo;
import com.ichapter.listviewandcheckbox.icheckbox.ICheckBoxInterface;
import com.ichapter.listviewandcheckbox.icheckbox.IEditDeleteGoods;
import com.ichapter.listviewandcheckbox.listview.ChildListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: liuzhang
 * @Description:
 * @Date: 2015/9/10 11:17
 */
public class ParentAdapter extends BaseAdapter implements ListAdapter {

    public static final String SHOPCART_DATA = "data";
    /**
     * 子元素所属组元素的id
     */
    public static final String SHOPCART_PARENT_ID = "parent_id";
    /**
     * 子元素所属组元素的相对位置
     */
    public static final String SHOPCART_PARENT_POSITION = "parent_position";

    private ICheckBoxInterface checkBoxInterface;
    private IEditDeleteGoods editDeleteGoods;

    private ArrayList<HashMap<String, Object>> parent_list;
    private Context mContext;
    private LayoutInflater mInflater;

    private boolean isDelete = true;

    public ParentAdapter(ArrayList<HashMap<String, Object>> parent_list, Context context) {
        super();
        this.parent_list = parent_list;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public void setCheckBoxInterface(ICheckBoxInterface checkBoxInterface) {
        this.checkBoxInterface = checkBoxInterface;
    }

    public void setEditDeleteGoods(IEditDeleteGoods editDeleteGoods) {
        this.editDeleteGoods = editDeleteGoods;
    }

    @Override
    public int getCount() {
        return parent_list.size();
    }

    @Override
    public Object getItem(int position) {
        return parent_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ParentViewHolder parentViewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_item_group, null, false);
            parentViewHolder = new ParentViewHolder();

            parentViewHolder.tv_click_edit = (TextView) convertView.findViewById(R.id.textview_click_edited);

            parentViewHolder.tv_shop_name = (TextView) convertView.findViewById(R.id.textview_shop_name);
            parentViewHolder.check_group = (CheckBox) convertView.findViewById(R.id.checkbox_parent_shop);
            parentViewHolder.childListView = (ChildListView) convertView.findViewById(R.id.listView_Child);
            convertView.setTag(parentViewHolder);
        } else {
            parentViewHolder = (ParentViewHolder) convertView.getTag();
        }

        final ChildAdapter adapter = new ChildAdapter(mContext);
        final ParentViewHolder viewHolder = parentViewHolder;
        adapter.addAll((ArrayList<HashMap<String, Object>>) parent_list.get(position).get("parent_lv"));
        parentViewHolder.childListView.setAdapter(adapter);
        adapter.setCheckBoxInterface(checkBoxInterface);

        Map<String, Object> group = (Map<String, Object>) getItem(position);
        final GroupInfo groupInfo = (GroupInfo) group.get(SHOPCART_DATA);
        if (groupInfo != null) {
            parentViewHolder.tv_shop_name.setText(groupInfo.getName());
            parentViewHolder.check_group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    groupInfo.setChoosed(viewHolder.check_group.isChecked());
                    checkBoxInterface.checkGroup(position, viewHolder.check_group.isChecked());
                }
            });
            parentViewHolder.check_group.setChecked(groupInfo.isChoosed());
        }

        //点击编辑
        final ParentViewHolder parent_view_holder = parentViewHolder;
        //点击编辑事件
        parentViewHolder.tv_click_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDelete) {//显示
                    parent_view_holder.tv_click_edit.setText("完成");
                    editDeleteGoods.isDelete(position, true);
                    isDelete = false;
                } else {//隐藏
                    parent_view_holder.tv_click_edit.setText("编辑");
                    editDeleteGoods.isDelete(position, false);
                    isDelete = true;
                }
            }
        });
        return convertView;
    }

    static class ParentViewHolder {
        TextView tv_shop_name;
        TextView tv_click_edit;
        CheckBox check_group;
        ChildListView childListView;
    }
}
