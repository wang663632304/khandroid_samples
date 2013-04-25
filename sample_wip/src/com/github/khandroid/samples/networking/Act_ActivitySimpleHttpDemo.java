/*
 * Copyright (C) 2012-2013 Ognyan Bankov
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.khandroid.samples.networking;

import khandroid.ext.apache.http.impl.client.DefaultHttpClient;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import com.github.khandroid.core.HostActivity;
import com.github.khandroid.http.ActivityHttpFunctionality;
import com.github.khandroid.kat.ActivityKatExecutorFunctionality;
import com.github.khandroid.kat.KatExecutor.TaskExecutorListener;
import com.github.khandroid.misc.KhandroidLog;
import com.github.khandroid.samples.R;
import com.github.khandroid.samples.R.id;
import com.github.khandroid.samples.R.layout;

import static com.github.khandroid.misc.ActivityUtils.*;


public class Act_ActivitySimpleHttpDemo extends HostActivity implements ActivityKatExecutorFunctionality.HostingAble<Void, String>{
    private ActivityHttpFunctionality mHttpFunc;
    private ActivityKatExecutorFunctionality<Void, Void, String> mKatExecutorFunc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act__activity_demo);

        KhandroidLog.enableLogging();
        KhandroidLog.initLogTag("KhandroidSample");

        mHttpFunc = new ActivityHttpFunctionality(this, new DefaultHttpClient());
        mHttpFunc.onCreate(savedInstanceState);

        mKatExecutorFunc = new ActivityKatExecutorFunctionality<Void, Void, String>(this);
        attach(mKatExecutorFunc);
        mKatExecutorFunc.onCreate(savedInstanceState);
        initView();

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads()
                .detectDiskWrites().penaltyLog().build());
    }


    @Override
    public Object onRetainNonConfigurationInstance() {
        return mKatExecutorFunc.onRetainNonConfigurationInstance();
    }


    private void initView() {
        View view = getWindow().getDecorView().findViewById(android.R.id.content);

        initButton(view, R.id.btn_make_request, createBtnClickListener());
    }


    private View.OnClickListener createBtnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKatExecutorFunc.execute(new MyHttpTask(mHttpFunc), (Void[]) null);
            }
        };
    }
    

    private TaskExecutorListener<Void, String> createListener() {
        TaskExecutorListener<Void, String> listener = new TaskExecutorListener<Void, String>() {
            @Override
            public void onTaskCompleted(String result) {
                KhandroidLog.d("onTaskCompleted " + result);
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


    @Override
    public TaskExecutorListener<Void, String> getKatExecutorListener() {
        return createListener();
    }
}
