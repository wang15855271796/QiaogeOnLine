package com.puyue.www.qiaoge.model;

import java.io.Serializable;
import java.util.List;

public class CarStyleModel {

    /**
     * code : 1
     * message : 成功
     * data : {"city_id":1017,"name":"北京","name_en":"beijing","city_info_revision":690,"vehicle_list":[{"order_vehicle_id":2870,"vehicle_name":"微面","image_url_high_light":"https://oimg.huolala.cn/ops/resource/20211029151054-00d1-84286626.png","vehicle_volume_text":"1.7方","vehicle_weight_text":"300.0公斤","vehicle_size":"1.4*1.2*1.0","text_desc":"","price_text_item":{"base_distancekm":5,"base_price_fen":2800,"exceed_segment_price":[{"end_exdistancekm":999999,"price_extra_fen":285,"start_exdistancekm":6}]},"vehicle_std_item":[]},{"order_vehicle_id":49,"vehicle_name":"小面","image_url_high_light":"https://oimg.huolala.cn/ops/prd/2021-04-15/74676ef7-01ad-bed4-060a-38d76f73c0c0.png","vehicle_volume_text":"2.4方","vehicle_weight_text":"500.0公斤","vehicle_size":"1.8*1.2*1.1","text_desc":"上货、卸货免费等候时长总共为40分钟，过后每10分钟加收5元，不足10分钟按10分钟计算（如已收取搬运费，则无需加收等候费）。高速及停车场等费用，根据实际产生的金额由客户支付。","price_text_item":{"base_distancekm":5,"base_price_fen":3000,"exceed_segment_price":[{"end_exdistancekm":999999,"price_extra_fen":300,"start_exdistancekm":6}]},"vehicle_std_item":[{"desc":null,"price_fen":0,"name":"新能源"}]},{"order_vehicle_id":50,"vehicle_name":"中面","image_url_high_light":"https://oimg.huolala.cn/ops/prd/2021-04-15/4b314eaa-894d-6322-2838-8de34681f5da.png","vehicle_volume_text":"3.7方","vehicle_weight_text":"800.0公斤","vehicle_size":"2.4*1.3*1.2","text_desc":"上货、卸货免费等候时长总共为40分钟，过后每10分钟加收5元，不足10分钟按10分钟计算（如已收取搬运费，则无需加收等候费）。高速及停车场等费用，根据实际产生的金额由客户支付。","price_text_item":{"base_distancekm":5,"base_price_fen":5600,"exceed_segment_price":[{"end_exdistancekm":999999,"price_extra_fen":400,"start_exdistancekm":6}]},"vehicle_std_item":[{"desc":null,"price_fen":0,"name":"新能源"}]},{"order_vehicle_id":721,"vehicle_name":"依维柯","image_url_high_light":"https://oimg.huolala.cn/ops/prd/2021-04-15/8497d8ea-1dee-b71c-9d4c-30c9f1847725.png","vehicle_volume_text":"6.1方","vehicle_weight_text":"1.0吨","vehicle_size":"2.4*1.7*1.5","text_desc":"上货、卸货免费等候时长总共为40分钟，过后每10分钟加收5元，不足10分钟按10分钟计算（如已收取搬运费，则无需加收等候费）。高速及停车场等费用，根据实际产生的金额由客户支付。","price_text_item":{"base_distancekm":5,"base_price_fen":6900,"exceed_segment_price":[{"end_exdistancekm":999999,"price_extra_fen":400,"start_exdistancekm":6}]},"vehicle_std_item":[]},{"order_vehicle_id":51,"vehicle_name":"小货","image_url_high_light":"https://oimg.huolala.cn/ops/prd/2021-04-15/4cac921e-863f-9f6c-f420-3062087b6daa.png","vehicle_volume_text":"4.2方","vehicle_weight_text":"1.0吨","vehicle_size":"2.0*1.4*1.5","text_desc":"上货、卸货免费等候时长总共为40分钟，过后每10分钟加收5元，不足10分钟按10分钟计算（如已收取搬运费，则无需加收等候费）。高速及停车场等费用，根据实际产生的金额由客户支付。","price_text_item":{"base_distancekm":5,"base_price_fen":6800,"exceed_segment_price":[{"end_exdistancekm":999999,"price_extra_fen":400,"start_exdistancekm":6}]},"vehicle_std_item":[{"desc":null,"price_fen":0,"name":"侧开门"},{"desc":null,"price_fen":0,"name":"后双轮"},{"desc":null,"price_fen":0,"name":"新能源"},{"desc":null,"price_fen":0,"name":"冷藏车"},{"desc":"车厢顶部已拆除","price_fen":0,"name":"开顶"},{"desc":"有4个座椅，适用于多人跟车","price_fen":0,"name":"双排座"},{"desc":null,"price_fen":0,"name":"厢式货车"},{"desc":null,"price_fen":0,"name":"篷布"},{"desc":null,"price_fen":0,"name":"平板货车"},{"desc":null,"price_fen":0,"name":"高栏货车"}]},{"order_vehicle_id":284,"vehicle_name":"中货","image_url_high_light":"https://oimg.huolala.cn/ops/prd/2021-04-15/1b56d000-3e80-f168-0afc-83ff755d6122.png","vehicle_volume_text":"12.3方","vehicle_weight_text":"1.5吨","vehicle_size":"3.8*1.8*1.8","text_desc":"上货、卸货免费等候时长总共为60分钟，过后每15分钟加收10元，不足15分钟按15分钟计算（如已收取搬运费，则无需加收等候费）。高速及停车场等费用，根据实际产生的金额由客户支付。","price_text_item":{"base_distancekm":5,"base_price_fen":12000,"exceed_segment_price":[{"end_exdistancekm":999999,"price_extra_fen":500,"start_exdistancekm":6}]},"vehicle_std_item":[{"desc":null,"price_fen":0,"name":"侧开门"},{"desc":null,"price_fen":0,"name":"冷藏车"},{"desc":"车厢顶部已拆除","price_fen":0,"name":"开顶"},{"desc":"液压起重装卸设备，方便装卸货","price_fen":0,"name":"带尾板"},{"desc":null,"price_fen":0,"name":"厢式货车"},{"desc":null,"price_fen":0,"name":"篷布"},{"desc":null,"price_fen":0,"name":"平板货车"},{"desc":null,"price_fen":0,"name":"高栏货车"}]},{"order_vehicle_id":1755,"vehicle_name":"5米2","image_url_high_light":"https://oimg.huolala.cn/ops/prd/2021-04-15/95eb85f2-49ba-7dd6-b65f-b2a9e8adc4f1.png","vehicle_volume_text":"21.0方","vehicle_weight_text":"4.0吨","vehicle_size":"5.0*2.1*2.0","text_desc":"","price_text_item":{"base_distancekm":15,"base_price_fen":26500,"exceed_segment_price":[{"end_exdistancekm":50,"price_extra_fen":500,"start_exdistancekm":16},{"end_exdistancekm":80,"price_extra_fen":400,"start_exdistancekm":51},{"end_exdistancekm":100,"price_extra_fen":300,"start_exdistancekm":81},{"end_exdistancekm":150,"price_extra_fen":300,"start_exdistancekm":101},{"end_exdistancekm":200,"price_extra_fen":400,"start_exdistancekm":151},{"end_exdistancekm":999999,"price_extra_fen":400,"start_exdistancekm":201}]},"vehicle_std_item":[{"desc":null,"price_fen":0,"name":"厢式货车"},{"desc":null,"price_fen":0,"name":"平板货车"},{"desc":null,"price_fen":0,"name":"高栏货车"}]},{"order_vehicle_id":567,"vehicle_name":"6米8","image_url_high_light":"https://oimg.huolala.cn/ops/prd/2021-04-15/95eb85f2-49ba-7dd6-b65f-b2a9e8adc4f1.png","vehicle_volume_text":"35.3方","vehicle_weight_text":"8.0吨","vehicle_size":"6.4*2.3*2.4","text_desc":"","price_text_item":{"base_distancekm":15,"base_price_fen":28000,"exceed_segment_price":[{"end_exdistancekm":50,"price_extra_fen":600,"start_exdistancekm":16},{"end_exdistancekm":80,"price_extra_fen":550,"start_exdistancekm":51},{"end_exdistancekm":400,"price_extra_fen":200,"start_exdistancekm":81},{"end_exdistancekm":700,"price_extra_fen":220,"start_exdistancekm":401},{"end_exdistancekm":999999,"price_extra_fen":249,"start_exdistancekm":701}]},"vehicle_std_item":[{"desc":null,"price_fen":0,"name":"厢式货车"},{"desc":null,"price_fen":0,"name":"平板货车"},{"desc":null,"price_fen":0,"name":"高栏货车"}]},{"order_vehicle_id":702,"vehicle_name":"7米6","image_url_high_light":"https://oimg.huolala.cn/ops/prd/2021-04-15/fbc4e421-a2cc-0534-f983-2d366138b2a5.png","vehicle_volume_text":"42.0方","vehicle_weight_text":"8.0吨","vehicle_size":"7.3*2.3*2.5","text_desc":"","price_text_item":{"base_distancekm":15,"base_price_fen":33000,"exceed_segment_price":[{"end_exdistancekm":50,"price_extra_fen":600,"start_exdistancekm":16},{"end_exdistancekm":80,"price_extra_fen":550,"start_exdistancekm":51},{"end_exdistancekm":999999,"price_extra_fen":400,"start_exdistancekm":81}]},"vehicle_std_item":[{"desc":null,"price_fen":0,"name":"厢式货车"},{"desc":null,"price_fen":0,"name":"平板货车"},{"desc":null,"price_fen":0,"name":"高栏货车"}]},{"order_vehicle_id":813,"vehicle_name":"9米6","image_url_high_light":"https://oimg.huolala.cn/ops/prd/2021-04-15/fff27c44-be8a-3a0f-8ebf-998993bae11f.png","vehicle_volume_text":"51.8方","vehicle_weight_text":"10.0吨","vehicle_size":"9.0*2.3*2.5","text_desc":"","price_text_item":{"base_distancekm":15,"base_price_fen":49000,"exceed_segment_price":[{"end_exdistancekm":50,"price_extra_fen":800,"start_exdistancekm":16},{"end_exdistancekm":80,"price_extra_fen":750,"start_exdistancekm":51},{"end_exdistancekm":100,"price_extra_fen":600,"start_exdistancekm":81},{"end_exdistancekm":999999,"price_extra_fen":500,"start_exdistancekm":101}]},"vehicle_std_item":[{"desc":null,"price_fen":0,"name":"厢式货车"},{"desc":null,"price_fen":0,"name":"平板货车"},{"desc":null,"price_fen":0,"name":"高栏货车"}]},{"order_vehicle_id":2053,"vehicle_name":"13米","image_url_high_light":"https://oimg.huolala.cn/ops/prd/2021-04-15/d2e0fd45-fe7f-9c59-58c5-9080ee424b9e.png","vehicle_volume_text":"67.3方","vehicle_weight_text":"22.0吨","vehicle_size":"11.7*2.3*2.5","text_desc":"","price_text_item":{"base_distancekm":30,"base_price_fen":95000,"exceed_segment_price":[{"end_exdistancekm":80,"price_extra_fen":900,"start_exdistancekm":31},{"end_exdistancekm":200,"price_extra_fen":850,"start_exdistancekm":81},{"end_exdistancekm":300,"price_extra_fen":750,"start_exdistancekm":201},{"end_exdistancekm":400,"price_extra_fen":700,"start_exdistancekm":301},{"end_exdistancekm":2000,"price_extra_fen":650,"start_exdistancekm":401},{"end_exdistancekm":999999,"price_extra_fen":550,"start_exdistancekm":2001}]},"vehicle_std_item":[{"desc":null,"price_fen":0,"name":"厢式货车"},{"desc":null,"price_fen":0,"name":"平板货车"},{"desc":null,"price_fen":0,"name":"高栏货车"}]},{"order_vehicle_id":2820,"vehicle_name":"17米5","image_url_high_light":"https://oimg.huolala.cn/ops/resource/20210906165142-00d1-28793249.png","vehicle_volume_text":"100.0方","vehicle_weight_text":"25.0吨","vehicle_size":"16.0*2.5*2.5","text_desc":"","price_text_item":{"base_distancekm":30,"base_price_fen":100000,"exceed_segment_price":[{"end_exdistancekm":80,"price_extra_fen":1400,"start_exdistancekm":31},{"end_exdistancekm":200,"price_extra_fen":850,"start_exdistancekm":81},{"end_exdistancekm":300,"price_extra_fen":750,"start_exdistancekm":201},{"end_exdistancekm":400,"price_extra_fen":700,"start_exdistancekm":301},{"end_exdistancekm":2000,"price_extra_fen":650,"start_exdistancekm":401},{"end_exdistancekm":999999,"price_extra_fen":550,"start_exdistancekm":2001}]},"vehicle_std_item":[{"desc":null,"price_fen":0,"name":"厢式货车"},{"desc":null,"price_fen":0,"name":"平板货车"},{"desc":null,"price_fen":0,"name":"高栏货车"}]}],"spec_req_item":[{"price_value_fen":0,"name":"搬运","price_type":4,"type":1,"desc":"司机另议"},{"price_value_fen":40,"name":"返程","price_type":2,"type":5,"desc":"附加40%路费"},{"price_value_fen":0,"name":"小推车","price_type":4,"type":2,"desc":"免费"},{"price_value_fen":0,"name":"拍照回单","price_type":4,"type":6,"desc":"免费"},{"price_value_fen":0,"name":"纸质回单","price_type":4,"type":3,"desc":"商议价格"},{"price_value_fen":0,"name":"代收货款","price_type":4,"type":8,"desc":"免费；货款上限一万元"}]}
     * error : false
     * success : true
     */

    private int code;
    private String message;
    private DataBean data;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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

    public static class DataBean {
        /**
         * city_id : 1017
         * name : 北京
         * name_en : beijing
         * city_info_revision : 690
         * vehicle_list : [{"order_vehicle_id":2870,"vehicle_name":"微面","image_url_high_light":"https://oimg.huolala.cn/ops/resource/20211029151054-00d1-84286626.png","vehicle_volume_text":"1.7方","vehicle_weight_text":"300.0公斤","vehicle_size":"1.4*1.2*1.0","text_desc":"","price_text_item":{"base_distancekm":5,"base_price_fen":2800,"exceed_segment_price":[{"end_exdistancekm":999999,"price_extra_fen":285,"start_exdistancekm":6}]},"vehicle_std_item":[]},{"order_vehicle_id":49,"vehicle_name":"小面","image_url_high_light":"https://oimg.huolala.cn/ops/prd/2021-04-15/74676ef7-01ad-bed4-060a-38d76f73c0c0.png","vehicle_volume_text":"2.4方","vehicle_weight_text":"500.0公斤","vehicle_size":"1.8*1.2*1.1","text_desc":"上货、卸货免费等候时长总共为40分钟，过后每10分钟加收5元，不足10分钟按10分钟计算（如已收取搬运费，则无需加收等候费）。高速及停车场等费用，根据实际产生的金额由客户支付。","price_text_item":{"base_distancekm":5,"base_price_fen":3000,"exceed_segment_price":[{"end_exdistancekm":999999,"price_extra_fen":300,"start_exdistancekm":6}]},"vehicle_std_item":[{"desc":null,"price_fen":0,"name":"新能源"}]},{"order_vehicle_id":50,"vehicle_name":"中面","image_url_high_light":"https://oimg.huolala.cn/ops/prd/2021-04-15/4b314eaa-894d-6322-2838-8de34681f5da.png","vehicle_volume_text":"3.7方","vehicle_weight_text":"800.0公斤","vehicle_size":"2.4*1.3*1.2","text_desc":"上货、卸货免费等候时长总共为40分钟，过后每10分钟加收5元，不足10分钟按10分钟计算（如已收取搬运费，则无需加收等候费）。高速及停车场等费用，根据实际产生的金额由客户支付。","price_text_item":{"base_distancekm":5,"base_price_fen":5600,"exceed_segment_price":[{"end_exdistancekm":999999,"price_extra_fen":400,"start_exdistancekm":6}]},"vehicle_std_item":[{"desc":null,"price_fen":0,"name":"新能源"}]},{"order_vehicle_id":721,"vehicle_name":"依维柯","image_url_high_light":"https://oimg.huolala.cn/ops/prd/2021-04-15/8497d8ea-1dee-b71c-9d4c-30c9f1847725.png","vehicle_volume_text":"6.1方","vehicle_weight_text":"1.0吨","vehicle_size":"2.4*1.7*1.5","text_desc":"上货、卸货免费等候时长总共为40分钟，过后每10分钟加收5元，不足10分钟按10分钟计算（如已收取搬运费，则无需加收等候费）。高速及停车场等费用，根据实际产生的金额由客户支付。","price_text_item":{"base_distancekm":5,"base_price_fen":6900,"exceed_segment_price":[{"end_exdistancekm":999999,"price_extra_fen":400,"start_exdistancekm":6}]},"vehicle_std_item":[]},{"order_vehicle_id":51,"vehicle_name":"小货","image_url_high_light":"https://oimg.huolala.cn/ops/prd/2021-04-15/4cac921e-863f-9f6c-f420-3062087b6daa.png","vehicle_volume_text":"4.2方","vehicle_weight_text":"1.0吨","vehicle_size":"2.0*1.4*1.5","text_desc":"上货、卸货免费等候时长总共为40分钟，过后每10分钟加收5元，不足10分钟按10分钟计算（如已收取搬运费，则无需加收等候费）。高速及停车场等费用，根据实际产生的金额由客户支付。","price_text_item":{"base_distancekm":5,"base_price_fen":6800,"exceed_segment_price":[{"end_exdistancekm":999999,"price_extra_fen":400,"start_exdistancekm":6}]},"vehicle_std_item":[{"desc":null,"price_fen":0,"name":"侧开门"},{"desc":null,"price_fen":0,"name":"后双轮"},{"desc":null,"price_fen":0,"name":"新能源"},{"desc":null,"price_fen":0,"name":"冷藏车"},{"desc":"车厢顶部已拆除","price_fen":0,"name":"开顶"},{"desc":"有4个座椅，适用于多人跟车","price_fen":0,"name":"双排座"},{"desc":null,"price_fen":0,"name":"厢式货车"},{"desc":null,"price_fen":0,"name":"篷布"},{"desc":null,"price_fen":0,"name":"平板货车"},{"desc":null,"price_fen":0,"name":"高栏货车"}]},{"order_vehicle_id":284,"vehicle_name":"中货","image_url_high_light":"https://oimg.huolala.cn/ops/prd/2021-04-15/1b56d000-3e80-f168-0afc-83ff755d6122.png","vehicle_volume_text":"12.3方","vehicle_weight_text":"1.5吨","vehicle_size":"3.8*1.8*1.8","text_desc":"上货、卸货免费等候时长总共为60分钟，过后每15分钟加收10元，不足15分钟按15分钟计算（如已收取搬运费，则无需加收等候费）。高速及停车场等费用，根据实际产生的金额由客户支付。","price_text_item":{"base_distancekm":5,"base_price_fen":12000,"exceed_segment_price":[{"end_exdistancekm":999999,"price_extra_fen":500,"start_exdistancekm":6}]},"vehicle_std_item":[{"desc":null,"price_fen":0,"name":"侧开门"},{"desc":null,"price_fen":0,"name":"冷藏车"},{"desc":"车厢顶部已拆除","price_fen":0,"name":"开顶"},{"desc":"液压起重装卸设备，方便装卸货","price_fen":0,"name":"带尾板"},{"desc":null,"price_fen":0,"name":"厢式货车"},{"desc":null,"price_fen":0,"name":"篷布"},{"desc":null,"price_fen":0,"name":"平板货车"},{"desc":null,"price_fen":0,"name":"高栏货车"}]},{"order_vehicle_id":1755,"vehicle_name":"5米2","image_url_high_light":"https://oimg.huolala.cn/ops/prd/2021-04-15/95eb85f2-49ba-7dd6-b65f-b2a9e8adc4f1.png","vehicle_volume_text":"21.0方","vehicle_weight_text":"4.0吨","vehicle_size":"5.0*2.1*2.0","text_desc":"","price_text_item":{"base_distancekm":15,"base_price_fen":26500,"exceed_segment_price":[{"end_exdistancekm":50,"price_extra_fen":500,"start_exdistancekm":16},{"end_exdistancekm":80,"price_extra_fen":400,"start_exdistancekm":51},{"end_exdistancekm":100,"price_extra_fen":300,"start_exdistancekm":81},{"end_exdistancekm":150,"price_extra_fen":300,"start_exdistancekm":101},{"end_exdistancekm":200,"price_extra_fen":400,"start_exdistancekm":151},{"end_exdistancekm":999999,"price_extra_fen":400,"start_exdistancekm":201}]},"vehicle_std_item":[{"desc":null,"price_fen":0,"name":"厢式货车"},{"desc":null,"price_fen":0,"name":"平板货车"},{"desc":null,"price_fen":0,"name":"高栏货车"}]},{"order_vehicle_id":567,"vehicle_name":"6米8","image_url_high_light":"https://oimg.huolala.cn/ops/prd/2021-04-15/95eb85f2-49ba-7dd6-b65f-b2a9e8adc4f1.png","vehicle_volume_text":"35.3方","vehicle_weight_text":"8.0吨","vehicle_size":"6.4*2.3*2.4","text_desc":"","price_text_item":{"base_distancekm":15,"base_price_fen":28000,"exceed_segment_price":[{"end_exdistancekm":50,"price_extra_fen":600,"start_exdistancekm":16},{"end_exdistancekm":80,"price_extra_fen":550,"start_exdistancekm":51},{"end_exdistancekm":400,"price_extra_fen":200,"start_exdistancekm":81},{"end_exdistancekm":700,"price_extra_fen":220,"start_exdistancekm":401},{"end_exdistancekm":999999,"price_extra_fen":249,"start_exdistancekm":701}]},"vehicle_std_item":[{"desc":null,"price_fen":0,"name":"厢式货车"},{"desc":null,"price_fen":0,"name":"平板货车"},{"desc":null,"price_fen":0,"name":"高栏货车"}]},{"order_vehicle_id":702,"vehicle_name":"7米6","image_url_high_light":"https://oimg.huolala.cn/ops/prd/2021-04-15/fbc4e421-a2cc-0534-f983-2d366138b2a5.png","vehicle_volume_text":"42.0方","vehicle_weight_text":"8.0吨","vehicle_size":"7.3*2.3*2.5","text_desc":"","price_text_item":{"base_distancekm":15,"base_price_fen":33000,"exceed_segment_price":[{"end_exdistancekm":50,"price_extra_fen":600,"start_exdistancekm":16},{"end_exdistancekm":80,"price_extra_fen":550,"start_exdistancekm":51},{"end_exdistancekm":999999,"price_extra_fen":400,"start_exdistancekm":81}]},"vehicle_std_item":[{"desc":null,"price_fen":0,"name":"厢式货车"},{"desc":null,"price_fen":0,"name":"平板货车"},{"desc":null,"price_fen":0,"name":"高栏货车"}]},{"order_vehicle_id":813,"vehicle_name":"9米6","image_url_high_light":"https://oimg.huolala.cn/ops/prd/2021-04-15/fff27c44-be8a-3a0f-8ebf-998993bae11f.png","vehicle_volume_text":"51.8方","vehicle_weight_text":"10.0吨","vehicle_size":"9.0*2.3*2.5","text_desc":"","price_text_item":{"base_distancekm":15,"base_price_fen":49000,"exceed_segment_price":[{"end_exdistancekm":50,"price_extra_fen":800,"start_exdistancekm":16},{"end_exdistancekm":80,"price_extra_fen":750,"start_exdistancekm":51},{"end_exdistancekm":100,"price_extra_fen":600,"start_exdistancekm":81},{"end_exdistancekm":999999,"price_extra_fen":500,"start_exdistancekm":101}]},"vehicle_std_item":[{"desc":null,"price_fen":0,"name":"厢式货车"},{"desc":null,"price_fen":0,"name":"平板货车"},{"desc":null,"price_fen":0,"name":"高栏货车"}]},{"order_vehicle_id":2053,"vehicle_name":"13米","image_url_high_light":"https://oimg.huolala.cn/ops/prd/2021-04-15/d2e0fd45-fe7f-9c59-58c5-9080ee424b9e.png","vehicle_volume_text":"67.3方","vehicle_weight_text":"22.0吨","vehicle_size":"11.7*2.3*2.5","text_desc":"","price_text_item":{"base_distancekm":30,"base_price_fen":95000,"exceed_segment_price":[{"end_exdistancekm":80,"price_extra_fen":900,"start_exdistancekm":31},{"end_exdistancekm":200,"price_extra_fen":850,"start_exdistancekm":81},{"end_exdistancekm":300,"price_extra_fen":750,"start_exdistancekm":201},{"end_exdistancekm":400,"price_extra_fen":700,"start_exdistancekm":301},{"end_exdistancekm":2000,"price_extra_fen":650,"start_exdistancekm":401},{"end_exdistancekm":999999,"price_extra_fen":550,"start_exdistancekm":2001}]},"vehicle_std_item":[{"desc":null,"price_fen":0,"name":"厢式货车"},{"desc":null,"price_fen":0,"name":"平板货车"},{"desc":null,"price_fen":0,"name":"高栏货车"}]},{"order_vehicle_id":2820,"vehicle_name":"17米5","image_url_high_light":"https://oimg.huolala.cn/ops/resource/20210906165142-00d1-28793249.png","vehicle_volume_text":"100.0方","vehicle_weight_text":"25.0吨","vehicle_size":"16.0*2.5*2.5","text_desc":"","price_text_item":{"base_distancekm":30,"base_price_fen":100000,"exceed_segment_price":[{"end_exdistancekm":80,"price_extra_fen":1400,"start_exdistancekm":31},{"end_exdistancekm":200,"price_extra_fen":850,"start_exdistancekm":81},{"end_exdistancekm":300,"price_extra_fen":750,"start_exdistancekm":201},{"end_exdistancekm":400,"price_extra_fen":700,"start_exdistancekm":301},{"end_exdistancekm":2000,"price_extra_fen":650,"start_exdistancekm":401},{"end_exdistancekm":999999,"price_extra_fen":550,"start_exdistancekm":2001}]},"vehicle_std_item":[{"desc":null,"price_fen":0,"name":"厢式货车"},{"desc":null,"price_fen":0,"name":"平板货车"},{"desc":null,"price_fen":0,"name":"高栏货车"}]}]
         * spec_req_item : [{"price_value_fen":0,"name":"搬运","price_type":4,"type":1,"desc":"司机另议"},{"price_value_fen":40,"name":"返程","price_type":2,"type":5,"desc":"附加40%路费"},{"price_value_fen":0,"name":"小推车","price_type":4,"type":2,"desc":"免费"},{"price_value_fen":0,"name":"拍照回单","price_type":4,"type":6,"desc":"免费"},{"price_value_fen":0,"name":"纸质回单","price_type":4,"type":3,"desc":"商议价格"},{"price_value_fen":0,"name":"代收货款","price_type":4,"type":8,"desc":"免费；货款上限一万元"}]
         */

        private String city_id;
        private String name;
        private String name_en;
        private String city_info_revision;
        private List<VehicleListBean> vehicle_list;
        private List<SpecReqItemBean> spec_req_item;

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName_en() {
            return name_en;
        }

        public void setName_en(String name_en) {
            this.name_en = name_en;
        }

        public String getCity_info_revision() {
            return city_info_revision;
        }

        public void setCity_info_revision(String city_info_revision) {
            this.city_info_revision = city_info_revision;
        }

        public List<VehicleListBean> getVehicle_list() {
            return vehicle_list;
        }

        public void setVehicle_list(List<VehicleListBean> vehicle_list) {
            this.vehicle_list = vehicle_list;
        }

        public List<SpecReqItemBean> getSpec_req_item() {
            return spec_req_item;
        }

        public void setSpec_req_item(List<SpecReqItemBean> spec_req_item) {
            this.spec_req_item = spec_req_item;
        }

        public static class VehicleListBean implements Serializable{
            /**
             * order_vehicle_id : 2870
             * vehicle_name : 微面
             * image_url_high_light : https://oimg.huolala.cn/ops/resource/20211029151054-00d1-84286626.png
             * vehicle_volume_text : 1.7方
             * vehicle_weight_text : 300.0公斤
             * vehicle_size : 1.4*1.2*1.0
             * text_desc :
             * price_text_item : {"base_distancekm":5,"base_price_fen":2800,"exceed_segment_price":[{"end_exdistancekm":999999,"price_extra_fen":285,"start_exdistancekm":6}]}
             * vehicle_std_item : []
             */

            private String order_vehicle_id;
            private String vehicle_name;
            private String image_url_high_light;
            private String vehicle_volume_text;
            private String vehicle_weight_text;
            private String vehicle_size;
            private String text_desc;
            private PriceTextItemBean price_text_item;
            private List<VehicleStdItem> vehicle_std_item;

            public String getOrder_vehicle_id() {
                return order_vehicle_id;
            }

            public List<VehicleStdItem> getVehicle_std_item() {
                return vehicle_std_item;
            }

            public void setVehicle_std_item(List<VehicleStdItem> vehicle_std_item) {
                this.vehicle_std_item = vehicle_std_item;
            }

            public void setOrder_vehicle_id(String order_vehicle_id) {
                this.order_vehicle_id = order_vehicle_id;
            }

            public String getVehicle_name() {
                return vehicle_name;
            }

            public void setVehicle_name(String vehicle_name) {
                this.vehicle_name = vehicle_name;
            }

            public String getImage_url_high_light() {
                return image_url_high_light;
            }

            public void setImage_url_high_light(String image_url_high_light) {
                this.image_url_high_light = image_url_high_light;
            }

            public String getVehicle_volume_text() {
                return vehicle_volume_text;
            }

            public void setVehicle_volume_text(String vehicle_volume_text) {
                this.vehicle_volume_text = vehicle_volume_text;
            }

            public String getVehicle_weight_text() {
                return vehicle_weight_text;
            }

            public void setVehicle_weight_text(String vehicle_weight_text) {
                this.vehicle_weight_text = vehicle_weight_text;
            }

            public String getVehicle_size() {
                return vehicle_size;
            }

            public void setVehicle_size(String vehicle_size) {
                this.vehicle_size = vehicle_size;
            }

            public String getText_desc() {
                return text_desc;
            }

            public void setText_desc(String text_desc) {
                this.text_desc = text_desc;
            }

            public PriceTextItemBean getPrice_text_item() {
                return price_text_item;
            }

            public void setPrice_text_item(PriceTextItemBean price_text_item) {
                this.price_text_item = price_text_item;
            }


            public static class PriceTextItemBean implements Serializable{
                /**
                 * base_distancekm : 5
                 * base_price_fen : 2800
                 * exceed_segment_price : [{"end_exdistancekm":999999,"price_extra_fen":285,"start_exdistancekm":6}]
                 */

                private int base_distancekm;
                private int base_price_fen;
                private List<ExceedSegmentPriceBean> exceed_segment_price;

                public int getBase_distancekm() {
                    return base_distancekm;
                }

                public void setBase_distancekm(int base_distancekm) {
                    this.base_distancekm = base_distancekm;
                }

                public int getBase_price_fen() {
                    return base_price_fen;
                }

                public void setBase_price_fen(int base_price_fen) {
                    this.base_price_fen = base_price_fen;
                }

                public List<ExceedSegmentPriceBean> getExceed_segment_price() {
                    return exceed_segment_price;
                }

                public void setExceed_segment_price(List<ExceedSegmentPriceBean> exceed_segment_price) {
                    this.exceed_segment_price = exceed_segment_price;
                }

                public static class ExceedSegmentPriceBean implements Serializable{
                    /**
                     * end_exdistancekm : 999999
                     * price_extra_fen : 285
                     * start_exdistancekm : 6
                     */

                    private int end_exdistancekm;
                    private int price_extra_fen;
                    private int start_exdistancekm;

                    public int getEnd_exdistancekm() {
                        return end_exdistancekm;
                    }

                    public void setEnd_exdistancekm(int end_exdistancekm) {
                        this.end_exdistancekm = end_exdistancekm;
                    }

                    public int getPrice_extra_fen() {
                        return price_extra_fen;
                    }

                    public void setPrice_extra_fen(int price_extra_fen) {
                        this.price_extra_fen = price_extra_fen;
                    }

                    public int getStart_exdistancekm() {
                        return start_exdistancekm;
                    }

                    public void setStart_exdistancekm(int start_exdistancekm) {
                        this.start_exdistancekm = start_exdistancekm;
                    }
                }
            }

            public class VehicleStdItem implements Serializable{
                String name;
                String desc;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }
            }
        }

        public static class SpecReqItemBean implements Serializable {
            /**
             * price_value_fen : 0
             * name : 搬运
             * price_type : 4
             * type : 1
             * desc : 司机另议
             */

            private int price_value_fen;
            private String name;
            private int price_type;
            private int type;
            private String desc;

            public int getPrice_value_fen() {
                return price_value_fen;
            }

            public void setPrice_value_fen(int price_value_fen) {
                this.price_value_fen = price_value_fen;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getPrice_type() {
                return price_type;
            }

            public void setPrice_type(int price_type) {
                this.price_type = price_type;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }
    }
}
