package com.ichapter.listviewandcheckbox;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.ichapter.listviewandcheckbox.adapter.ParentAdapter;
import com.ichapter.listviewandcheckbox.entity.GoodsInfo;
import com.ichapter.listviewandcheckbox.entity.GroupInfo;
import com.ichapter.listviewandcheckbox.icheckbox.ICheckBoxInterface;
import com.ichapter.listviewandcheckbox.icheckbox.IEditDeleteGoods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements View.OnClickListener, ICheckBoxInterface, IEditDeleteGoods {

    private ListView parent_listView;
    private ArrayList<HashMap<String, Object>> parentList;
    private ArrayList<HashMap<String, Object>> childList;
    private ParentAdapter parentAdapter;

    private List<GroupInfo> groups = new ArrayList<GroupInfo>();
    private Map<String, List<GoodsInfo>> children = new HashMap<>();
    private CheckBox checkBoxSelectAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkBoxSelectAll = (CheckBox) findViewById(R.id.checkbox_select_all);
        checkBoxSelectAll.setOnClickListener(this);
        parent_listView = (ListView) findViewById(R.id.listView_Parent);
        initView();
    }


    public void initView() {
        String[] groupNames = new String[]{"雨润雨润雨润", "雨润雨润雨润", "雨润雨润雨润"};
        String[] groupIds = new String[]{"shop1", "shop2", "shop3"};
        parentList = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < groupNames.length; i++) {
            childList = new ArrayList<HashMap<String, Object>>();
            List<GoodsInfo> childs = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                HashMap<String, Object> childMap = new HashMap<String, Object>();
                GoodsInfo goods = new GoodsInfo();
                goods.setDesc("雨润冻猪带皮大五花雨润冻猪带皮大五花雨润冻猪带皮大五花" + j);
                childMap.put(ParentAdapter.SHOPCART_PARENT_ID, groupIds[i]);
                childMap.put(ParentAdapter.SHOPCART_PARENT_POSITION, i);
                childMap.put(ParentAdapter.SHOPCART_DATA, goods);
                childList.add(childMap);
                childs.add(goods);
            }

            HashMap<String, Object> parentMap = new HashMap<String, Object>();
            GroupInfo groupInfo = new GroupInfo();
            groupInfo.setName(groupNames[i]);
            groupInfo.setId(groupIds[i]);

            parentMap.put(ParentAdapter.SHOPCART_DATA, groupInfo);
            parentMap.put("parent_lv", childList);
            parentList.add(parentMap);
            groups.add(groupInfo);

            //用于选中全部
            children.put(groupIds[i], childs);
        }
        parentAdapter = new ParentAdapter(parentList, this);

        parent_listView.setAdapter(parentAdapter);
        parentAdapter.setCheckBoxInterface(this);
        parentAdapter.setEditDeleteGoods(this);
        parent_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,
                        "第" + position + "个条目", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void checkedAll() {
        for (int i = 0; i < groups.size(); i++) {
            groups.get(i).setChoosed(checkBoxSelectAll.isChecked());
            List<GoodsInfo> child = children.get(groups.get(i).getId());
            for (int j = 0; j < child.size(); j++) {
                child.get(j).setChoosed(checkBoxSelectAll.isChecked());
            }
        }
        parentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        checkedAll();
    }

    @Override
    public void checkGroup(int position, boolean isChecked) {
        Map<String, Object> parent = parentList.get(position);
        String parentId = ((GroupInfo) (parent.get(ParentAdapter.SHOPCART_DATA))).getId();
        for (int i = 0; i < parentList.size(); i++) {
            Map<String, Object> map = parentList.get(i);
            childList = (ArrayList<HashMap<String, Object>>) map.get("parent_lv");
            for (int j = 0; j < childList.size(); j++) {
                Map<String, Object> childMap = childList.get(j);
                String parent_Id = (String) childMap.get(ParentAdapter.SHOPCART_PARENT_ID);
                if (parentId.equals(parent_Id)) {
                    GoodsInfo goodInfo = (GoodsInfo) childMap.get(ParentAdapter.SHOPCART_DATA);
                    goodInfo.setChoosed(isChecked);
                }
            }
        }

        boolean allGroupSameState = true;
        for (int i = 0; i < groups.size(); i++) {
            if (isChecked != groups.get(i).isChoosed()) {
                allGroupSameState = false;
                break;
            }
        }
        if (allGroupSameState) {
            checkBoxSelectAll.setChecked(isChecked);
        } else {
            checkBoxSelectAll.setChecked(false);
        }
        parentAdapter.notifyDataSetChanged();
    }

    @Override
    public void checkChild(int position, boolean isChecked) {
        Map<String, Object> child = parentList.get(position);
        System.out.println("child:" + child);
        childList = (ArrayList<HashMap<String, Object>>) child.get("parent_lv");
        GroupInfo parent = null;
        for (int i = 0; i < childList.size(); i++) {
            Map<String, Object> childMap = childList.get(i);
            int parentPosition = (int) childMap.get(ParentAdapter.SHOPCART_PARENT_POSITION);
            parent = groups.get(parentPosition);
        }
        List<GoodsInfo> childs = children.get(parent.getId());
        boolean allChildSameState = true;
        for (int j = 0; j < childs.size(); j++) {
            if (childs.get(j).isChoosed() != isChecked) {
                allChildSameState = false;
                break;
            }
        }
        if (allChildSameState) {
            parent.setChoosed(isChecked);
        } else {
            parent.setChoosed(false);
        }
        boolean allGroupSameState = true;
        boolean firstState = groups.get(0).isChoosed();
        for (int i = 0; i < groups.size(); i++) {
            if (firstState != groups.get(i).isChoosed()) {
                allGroupSameState = false;
                break;
            }
        }
        if (allGroupSameState) {
            checkBoxSelectAll.setChecked(firstState);
        } else {
            checkBoxSelectAll.setChecked(false);
        }
        parentAdapter.notifyDataSetChanged();
    }

    @Override
    public void isDelete(int position, boolean isDelete) {
        Map<String, Object> parent = parentList.get(position);
        String parentId = ((GroupInfo) (parent.get(ParentAdapter.SHOPCART_DATA))).getId();
        for (int i = 0; i < parentList.size(); i++) {
            Map<String, Object> map = parentList.get(i);
            childList = (ArrayList<HashMap<String, Object>>) map.get("parent_lv");
            for (int j = 0; j < childList.size(); j++) {
                Map<String, Object> childMap = childList.get(j);
                String parent_Id = (String) childMap.get(ParentAdapter.SHOPCART_PARENT_ID);
                if (parentId.equals(parent_Id)) {
                    GoodsInfo goodInfo = (GoodsInfo) childMap.get(ParentAdapter.SHOPCART_DATA);
                    goodInfo.setDelete(isDelete);
                }
            }
        }
        parentAdapter.notifyDataSetChanged();
    }
}
