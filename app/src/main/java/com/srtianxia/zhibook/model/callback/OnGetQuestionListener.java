package com.srtianxia.zhibook.model.callback;

import com.srtianxia.zhibook.model.bean.zhibook.Question;

import java.util.List;

/**
 * Created by srtianxia on 2016/2/11.
 */
public interface OnGetQuestionListener {
    void success(List<Question> questions);
    void failure(String s);
}
