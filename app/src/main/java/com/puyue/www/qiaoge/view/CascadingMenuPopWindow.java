package com.puyue.www.qiaoge.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.home.CityChangeModel;
import com.puyue.www.qiaoge.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LILIN
 * 下午1:48:27
 * 提供PopWindow调用方法
 */
public class CascadingMenuPopWindow extends PopupWindow {


	private Activity context;
	private CascadingMenuViews cascadingMenuView;
	private ArrayList<CityChangeModel.DataBean> areas;
    //提供给外的接口
	private CascadingMenuViewOnSelectListener menuViewOnSelectListener;

	public CascadingMenuPopWindow(Activity mActivity, ArrayList<CityChangeModel.DataBean> listCity) {
		super(mActivity);
		this.context=mActivity;
		this.areas=listCity;
		init();
	}


	public void setMenuItems() {
		this.areas = areas;
	}

	public void setMenuViewOnSelectListener(CascadingMenuViewOnSelectListener menuViewOnSelectListener) {
		this.menuViewOnSelectListener = menuViewOnSelectListener;
	}

	public void init(){
		//实例化级联菜单
		cascadingMenuView=new CascadingMenuViews(context,areas);
		setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
		setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
		setContentView(cascadingMenuView);
		//设置回调接口
		cascadingMenuView.setCascadingMenuViewOnSelectListener(new MCascadingMenuViewOnSelectListener());
	}
	//级联菜单选择回调接口
	class MCascadingMenuViewOnSelectListener implements CascadingMenuViewOnSelectListener{

		@Override
		public void getValue(CityChangeModel.DataBean menuItem) {
			if(menuViewOnSelectListener!=null){
				menuViewOnSelectListener.getValue(menuItem);
			}
		}

		@Override
		public void getValues(CityChangeModel.DataBean.CityNamesBean area) {
			if(menuViewOnSelectListener!=null){
				menuViewOnSelectListener.getValues(area);
				dismiss();
			}
		}

		@Override
		public void cloese() {
			if(menuViewOnSelectListener!=null){
				dismiss();
			}
		}

	}
}
