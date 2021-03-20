package com.puyue.www.qiaoge.dialog;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.home.CityChangeModel;
import com.puyue.www.qiaoge.view.CascadingMenuPopWindow;
import com.puyue.www.qiaoge.view.CascadingMenuViewOnSelectListener;
import com.puyue.www.qiaoge.view.CascadingMenuViews;

import java.util.ArrayList;

/**
 * Created by ${王涛} on 2021/1/8
 */
public class ChooseCityPopWindow extends PopupWindow {
    private Activity context;
    private ChooseViews cascadingMenuView;
    private ArrayList<CityChangeModel.DataBean> areas;
    //提供给外的接口
    private CascadingMenuViewOnSelectListener menuViewOnSelectListener;

    public ChooseCityPopWindow(Activity mActivity, ArrayList<CityChangeModel.DataBean> listCity) {
        super(mActivity);
        this.context=mActivity;
        this.areas=listCity;
        init();
    }

    public void setMenuViewOnSelectListener(CascadingMenuViewOnSelectListener menuViewOnSelectListener) {
        this.menuViewOnSelectListener = menuViewOnSelectListener;
    }

    public void init(){
        //实例化级联菜单
        cascadingMenuView=new ChooseViews(context,areas);
        setContentView(cascadingMenuView);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
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
