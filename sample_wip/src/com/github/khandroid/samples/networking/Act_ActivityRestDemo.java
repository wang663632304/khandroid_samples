/*
 * Copyright (C) 2012-2013 Ognyan Bankov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.khandroid.samples.networking;

import static com.github.khandroid.misc.ActivityUtils.initButton;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.khandroid.core.HostActivity;
import com.github.khandroid.functionality.HttpFunctionalityImpl;
import com.github.khandroid.kat.ActivityKatExecutorFunctionality;
import com.github.khandroid.kat.KatExecutor.TaskExecutorListener;
import com.github.khandroid.misc.ActivityUtils;
import com.github.khandroid.misc.KhandroidLog;
import com.github.khandroid.rest.ActivityRestFunctionality;
import com.github.khandroid.rest.RestResult;
import com.github.khandroid.samples.R;


public class Act_ActivityRestDemo extends HostActivity {
    private ActivityRestFunctionality mRestFunc;
    private ActivityKatExecutorFunctionality<Void, Void, RestResult<MyRestResult>> mKatExecutorFunc;

    private TextView mTvResultString;
    private TextView mTvResultInt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act__activity_rest_demo);

        mRestFunc = new ActivityRestFunctionality(this, new HttpFunctionalityImpl(MyHttpClientSingleton.getInstance()));
        mRestFunc.onCreate(savedInstanceState);

        mKatExecutorFunc = new ActivityKatExecutorFunctionality<Void, Void, RestResult<MyRestResult>>(this, createListener());
        attach(mKatExecutorFunc);
        mKatExecutorFunc.onCreate(savedInstanceState);
        initView();
    }


    private void initView() {
        View view = getWindow().getDecorView().findViewById(android.R.id.content);

        mTvResultString = ActivityUtils.findTextView(view, R.id.tv_result_string);
        mTvResultInt = ActivityUtils.findTextView(view, R.id.tv_result_int);
        initButton(view, R.id.btn_execute_request, createBtnExecuteRequestClickListener());
        
        
        setTvText(mTvResultString,
                  R.string.rest_http_demo__tv_result_string,
                  "");

        setTvText(mTvResultInt,
                  R.string.rest_http_demo__tv_result_int,
                  "");
    }


    private TaskExecutorListener<Void, RestResult<MyRestResult>> createListener() {
        TaskExecutorListener<Void, RestResult<MyRestResult>> listener = new TaskExecutorListener<Void, RestResult<MyRestResult>>() {
            @Override
            public void onTaskCompleted(RestResult<MyRestResult> result) {
                switch(result.getStatus()) {
                    case RestResult.STATUS_OK:
                        MyRestResult rez = result.getData();
                        setTvText(mTvResultString,
                                  R.string.rest_http_demo__tv_result_string,
                                  rez.getStringValue());
        
                        setTvText(mTvResultInt,
                                  R.string.rest_http_demo__tv_result_int,
                                  Integer.toString(rez.getIntValue()));
                        
                        break;
                    case RestResult.STATUS_SOFT_ERROR:
                        break;
                    case RestResult.STATUS_HARD_ERROR:
                        break;
                }
                    
            }


            @Override
            public void onTaskPublishProgress(Void... progress) {
                KhandroidLog.d("onTaskPublishProgress");
            }


            @Override
            public void onTaskCancelled() {
                KhandroidLog.d("onTaskCancelled");
            }


            @Override
            public void onContinueWithTask() {
                KhandroidLog.d("onContinueWithTask");
            }
        };

        return listener;
    }


    private void setTvText(TextView tv, int res, String str) {
        tv.setText(String.format(getString(res), str));
    }


    private View.OnClickListener createBtnExecuteRequestClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityUtils.isOnline(Act_ActivityRestDemo.this)) {
                    mKatExecutorFunc.execute(new MyRestTask(mRestFunc));
                } else {
                    NoInetDialogCreator.show(Act_ActivityRestDemo.this);
                }
            }
        };
    }
    
    
    @Override
    public Object onRetainNonConfigurationInstance() {
        return mKatExecutorFunc.onRetainNonConfigurationInstance();
    }
}
