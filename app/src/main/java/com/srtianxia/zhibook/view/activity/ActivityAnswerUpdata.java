package com.srtianxia.zhibook.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.srtianxia.zhibook.R;
import com.srtianxia.zhibook.app.BaseActivity;
import com.srtianxia.zhibook.model.bean.zhibook.Answer;
import com.srtianxia.zhibook.presenter.GetAnswerPresenter;
import com.srtianxia.zhibook.view.IView.IActivityAnswer;
import com.srtianxia.zhibook.view.adapter.AnswerAdapter;
import com.srtianxia.zhibook.view.adapter.OnItemClickListener;
import com.srtianxia.zhibook.view.fragment.HidingScrollListener;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by srtianxia on 2016/2/25.
 */
public class ActivityAnswerUpdata extends BaseActivity implements
        IActivityAnswer,SwipeRefreshLayout.OnRefreshListener,View.OnClickListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_answer)
    RecyclerView rvAnswer;
    @Bind(R.id.sw_answer)
    SwipeRefreshLayout swAnswer;
    @Bind(R.id.answer_fab)
    FloatingActionButton answerFab;

    private AnswerAdapter adapter;

    private String questionId;
    private String questionTitle;
    private String questionContent;

    private GetAnswerPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_updata);
        presenter = new GetAnswerPresenter(this);
        questionId = getIntent().getStringExtra("questionId");
        questionContent = getIntent().getStringExtra("content");
        questionTitle = getIntent().getStringExtra("title");
        ButterKnife.bind(this);
        answerFab.setOnClickListener(this);
        swAnswer.setOnRefreshListener(this);
        initToolbar();
        initRv();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        presenter.getAnswer();
        onRefresh();
    }

    private void initToolbar() {
        toolbar.setTitle(questionTitle);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRv() {
        adapter = new AnswerAdapter(this);
        rvAnswer.setAdapter(adapter);
        rvAnswer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        swAnswer.setColorSchemeColors(getResources().getColor(R.color.colorAccentBlue));
        swAnswer.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        swAnswer.setRefreshing(true);
        View view = LayoutInflater.from(this).inflate(R.layout.header_answer, rvAnswer, false);
        TextView answerQuestionContent = (TextView) view.findViewById(R.id.answer_question_content);
        answerQuestionContent.setText(questionContent);
        adapter.setHeadView(view);
    }

    @Override
    public String getQuestionId() {
        return questionId;
    }

    @Override
    public void initAnswerSuccess(final List<Answer> answers) {
        swAnswer.setRefreshing(false);
        adapter.setData(answers);
        rvAnswer.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                hideViews();
            }

            @Override
            public void onShow() {
                showViews();
            }
        });

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(ActivityAnswerUpdata.this, ActivityAnswerDetail.class);
                intent.putExtra("answerDetail", (Serializable) answers.get(position));
                intent.putExtra("questionTitle", questionTitle);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        });
    }

    private void hideViews() {
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) answerFab.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        answerFab.animate().translationY(answerFab.getHeight() + fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    private void showViews() {
        answerFab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    @Override
    public void initAnswerFailure(String s) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.answer_fab:
                Intent intent = new Intent(this, ActivityEditAnswer.class);
                intent.putExtra("questionId", questionId);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onRefresh() {
        presenter.getAnswer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        presenter.onRelieveView();
    }
}
