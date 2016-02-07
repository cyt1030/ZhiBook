package com.srtianxia.zhibook.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.srtianxia.zhibook.R;
import com.srtianxia.zhibook.model.bean.zhihu.DailyBean;
import com.srtianxia.zhibook.presenter.DailyPresenter;
import com.srtianxia.zhibook.view.adapter.DailyAdapter;
import com.srtianxia.zhibook.view.adapter.OnItemClickListener;
import com.srtianxia.zhibook.view.IView.IFragmentDaily;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by srtianxia on 2016/1/23.
 */
public class FragmentDaily extends Fragment implements IFragmentDaily {

    private static final String TAG = "FragmentDaily";
    @Bind(R.id.rv_daily)
    RecyclerView rvDaily;
    private DailyPresenter presenter;

    private LayoutInflater inflater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhihu_main, container, false);
        this.inflater = inflater;
        presenter = new DailyPresenter(this);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showDaily(DailyBean dailyBean) {
        DailyAdapter adapter = new DailyAdapter(getActivity(),
                dailyBean.getStories());
        rvDaily.setAdapter(adapter);
        rvDaily.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}