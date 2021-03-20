package com.puyue.www.qiaoge.model;

/**
 * Created by ${王涛} on 2020/9/5
 */
public class QueryProdModel {


    /**
     * code : 1
     * message : 成功
     * data : 【3935】冷冻猪后脚（猪脚）【约10kg（38-42片左右）/箱】;【味千】拉面-G【(单包5人份）约500g*16包/箱】;【五丰冷食】香菇菜包【1.02kg*6包/箱】;【五丰冷食】猪肉馅【1.02kg*6包/箱】;
     * error : false
     * success : true
     */

    private int code;
    private String message;
    private String data;
    private boolean error;
    private boolean success;

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

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
