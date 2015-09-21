package com.ichapter.listviewandcheckbox.icheckbox;

/**
 * @author: liuzhang
 * @Description:
 * @Date: 2015/9/10 16:22
 */
public interface ICheckBoxInterface {

    /**
     * 组选框是否选中
     *
     * @param position  组元素的绝对位置(在整个源数据list中的位置)
     * @param isChecked 组选框选中状态
     */
    void checkGroup(int position, boolean isChecked);

    /**
     * 子选框是否选中
     *
     * @param position  子元素的绝对位置(在整个源数据list中的位置)
     * @param isChecked 子选框选中状态
     */
    void checkChild(int position, boolean isChecked);

}
