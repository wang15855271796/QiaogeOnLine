package com.puyue.www.qiaoge.view;


import com.puyue.www.qiaoge.model.home.CityChangeModel;

/**
 * 通用级联菜单接口
 * @author LILIN
 * 下午3:21:35
 */
public interface CascadingMenuViewOnSelectListener {
	public void getValue(CityChangeModel.DataBean area);
	void getValues(CityChangeModel.DataBean.CityNamesBean area);
	void cloese();
}
