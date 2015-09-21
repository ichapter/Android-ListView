package com.ichapter.listviewandcheckbox.entity;

/**
 * @author: liuzhang
 * @Description:
 * @Date: 2015/9/10 14:37
 */
public class BaseInfo {
    //商品ID
    private String id;
    //商品名称
    private String name;
    //用于记录是否全部选中
    private boolean isChoosed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean isChoosed) {
        this.isChoosed = isChoosed;
    }

    @Override
    public String toString() {
        return "BaseInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", isChoosed=" + isChoosed +
                '}';
    }
}
