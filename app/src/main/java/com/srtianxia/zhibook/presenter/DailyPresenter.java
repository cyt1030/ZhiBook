package com.srtianxia.zhibook.presenter;

import com.srtianxia.zhibook.model.Imodel.IZhiHuModel;
import com.srtianxia.zhibook.model.ZhiHuModel;
import com.srtianxia.zhibook.model.bean.zhihu.DailyBean;
import com.srtianxia.zhibook.model.callback.OnGetDailyListener;
import com.srtianxia.zhibook.view.IView.IFragmentDaily;

/**
 * Created by srtianxia on 2016/1/23.
 */
public class DailyPresenter {
    private IFragmentDaily iFragmentDaily;
    private IZhiHuModel iZhiHuModel;

    public DailyPresenter(IFragmentDaily iFragmentDaily){
        this.iFragmentDaily = iFragmentDaily;
        iZhiHuModel = ZhiHuModel.getInstance();
    }

    public void initData(){
        iZhiHuModel.getDaily(new OnGetDailyListener() {
            @Override
            public void onGetDaily(DailyBean dailyBean) {
                iFragmentDaily.showDaily(dailyBean);
            }
        });
    }
}