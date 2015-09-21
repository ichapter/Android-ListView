package com.ichapter.listviewandcheckbox.entity;

/**
 * @author: liuzhang
 * @Description:
 * @Date: 2015/9/10 14:41
 */
public class GoodsInfo extends BaseInfo {

    private int position;

    private String desc;

    private boolean isDelete;

    public GoodsInfo() {
        super();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "GoodsInfo{" +
                "position=" + position +
                ", desc='" + desc + '\'' +
                ", isDelete=" + isDelete +
                '}';
    }
}
