package com.ichapter.listviewandcheckbox.entity;

import java.util.List;

/**
 * @author: liuzhang
 * @Description:
 * @Date: 2015/9/10 14:40
 */
public class GroupInfo extends BaseInfo {


    private GoodsInfo goodsInfo;

    private List<GoodsInfo> goodsInfos;

    public GroupInfo() {
        super();
    }

    public GoodsInfo getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public List<GoodsInfo> getGoodsInfos() {
        return goodsInfos;
    }

    public void setGoodsInfos(List<GoodsInfo> goodsInfos) {
        this.goodsInfos = goodsInfos;
    }


}

