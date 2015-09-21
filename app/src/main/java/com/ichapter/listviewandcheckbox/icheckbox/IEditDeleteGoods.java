package com.ichapter.listviewandcheckbox.icheckbox;

/**
 * 显示与删除商品布局接口
 *
 * @author: liuzhang
 * @Description:
 * @Date: 2015/9/11 14:46
 */
public interface IEditDeleteGoods {

    /**
     * 根据一级标题的位置显示删除和隐藏对象
     *
     * @param position 一级标题的位置
     * @param isDelete 显示和隐藏两种状态来显示删除布局
     */
    void isDelete(int position, boolean isDelete);
}
