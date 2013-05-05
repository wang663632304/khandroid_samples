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

import static com.github.khandroid.misc.ActivityUtils.findTextView;
import static com.github.khandroid.misc.ActivityUtils.initButton;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.github.khandroid.core.HostActivity;
import com.github.khandroid.http.ActivityHttpFunctionality;
import com.github.khandroid.kat.ActivityKatExecutorFunctionality;
import com.github.khandroid.kat.KatExecutor.TaskExecutorListener;
import com.github.khandroid.misc.KhandroidLog;
import com.github.khandroid.samples.R;


public class Act_SessionDemo1 extends HostActivity {
    private ActivityHttpFunctionality mHttpFunc;
    private ActivityKatExecutorFunctionality<Void, Void, String> mKatExecutorFunc;

    private TextView mTvSessionVariable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act__session_demo1);

        mHttpFunc = new ActivityHttpFunctionality(this, MyHttpClientSingleton.getInstance());
        mHttpFunc.onCreate(savedInstanceState);

        mKatExecutorFunc = new ActivityKatExecutorFunctionality<Void, Void, String>(this, createTaskListener());
        attach(mKatExecutorFunc);
        mKatExecutorFunc.onCreate(savedInstanceState);
        initView();
    }


    private void initView() {
        View view = getWindow().getDecorView().findViewById(android.R.id.content);

        initButton(view, R.id.btn_execute_request, createBtnExecuteReqClickListener());
        initButton(view,
                   R.id.btn_start_act,
                   createBtnStartSecondAcitivtyClickListener());

        mTvSessionVariable = findTextView(view, R.id.tv_session_var_value);
        setTvText(getString(R.string.global_not_available));
    }


    private OnClickListener createBtnExecuteReqClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKatExecutorFunc.execute(new MySessionTask1(mHttpFunc));
            }
        };
    }


    private void setTvText(String str) {
        mTvSessionVariable.setText(String.format(getString(R.string.cookie_http_demo__tv_cookie),
                                                 str));
    }


    private TaskExecutorListener<Void, String> createTaskListener() {
        TaskExecutorListener<Void, String> listener = new TaskExecutorListener<Void, String>() {
            @Override
            public void onTaskCompleted(String result) {
                setTvText(result);
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


    private View.OnClickListener createBtnStartSecondAcitivtyClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Act_SessionDemo1.this, Act_SessionDemo2.class);
                startActivity(intent);
            }
        };
    }
    
    @Override
    public Object onRetainNonConfigurationInstance() {
        return mKatExecutorFunc.onRetainNonConfigurationInstance();
    }

}
