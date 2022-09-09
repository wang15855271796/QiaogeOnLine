package com.puyue.www.qiaoge.event;

import com.puyue.www.qiaoge.model.home.GetProductListModel;

import java.util.List;

public class GetProductNumModel {
    /**
     * code : 1
     * message : 成功
     * data : {"pageNum":1,"pageSize":9,"startRow":0,"pages":2,"total":11,"list":[{"productId":46,"productName":"鸡肉","spec":"200*10","price":"￥100/箱","monthSalesVolume":"月销:50","inventory":"库存:1000","flag":1,"imgUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/879fdf10a6a247479f4649cdeb65145a.jpg","collectionIcon":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/staticImg/collection_icon_soldout.png"},{"productId":47,"productName":"包心菜","spec":"200*10","price":"￥800/箱","monthSalesVolume":"月销:0","inventory":"库存:150","flag":1,"imgUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/879fdf10a6a247479f4649cdeb65145a.jpg","collectionIcon":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/staticImg/collection_icon_soldout.png"},{"productId":48,"productName":"鸡肉","spec":"200*10","price":"￥900/箱","monthSalesVolume":"月销:10","inventory":"库存:1000","flag":1,"imgUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/879fdf10a6a247479f4649cdeb65145a.jpg","collectionIcon":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/staticImg/collection_icon_soldout.png"},{"productId":49,"productName":"鸡肉","spec":"200*10","price":"￥800/箱","monthSalesVolume":"月销:100","inventory":"库存:150","flag":1,"imgUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/879fdf10a6a247479f4649cdeb65145a.jpg","collectionIcon":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/staticImg/collection_icon_soldout.png"},{"productId":60,"productName":"铁针","spec":"200*10","price":"￥200/箱","monthSalesVolume":"月销:0","inventory":"库存:15","flag":1,"imgUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/879fdf10a6a247479f4649cdeb65145a.jpg","collectionIcon":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/staticImg/collection_icon_soldout.png"},{"productId":667,"productName":"铁针","spec":"200*10","price":"￥200/箱","monthSalesVolume":"月销:0","inventory":"库存:15","flag":1,"imgUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/879fdf10a6a247479f4649cdeb65145a.jpg","collectionIcon":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/staticImg/collection_icon_soldout.png"},{"productId":668,"productName":"铁针","spec":"200*10","price":"￥200/箱","monthSalesVolume":"月销:0","inventory":"库存:15","flag":1,"imgUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/879fdf10a6a247479f4649cdeb65145a.jpg","collectionIcon":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/staticImg/collection_icon_soldout.png"},{"productId":669,"productName":"铁针","spec":"200*10","price":"￥200/箱","monthSalesVolume":"月销:0","inventory":"库存:15","flag":1,"imgUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/879fdf10a6a247479f4649cdeb65145a.jpg","collectionIcon":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/staticImg/collection_icon_soldout.png"},{"productId":670,"productName":"铁针","spec":"200*10","price":"￥200/箱","monthSalesVolume":"月销:0","inventory":"库存:15","flag":1,"imgUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/879fdf10a6a247479f4649cdeb65145a.jpg","collectionIcon":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/staticImg/collection_icon_soldout.png"}],"hasPrePage":false,"hasNextPage":true}
     * success : true
     * error : false
     */

    public int code;
    public String message;
    public String data;
    public boolean success;
    public boolean error;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
