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

package com.github.khandroid.samples;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import com.github.khandroid.activity.HostActivity;
import com.github.khandroid.activity.functionalities.defaults.DefaultActivityRestFunctionality;
import com.github.khandroid.kat.ActivityKat3ExecutorFunctionality;
import com.github.khandroid.kat.Kat3Executor.TaskExecutorListener;
import com.github.khandroid.kat.KhandroidAsyncTask3;
import com.github.khandroid.misc.KhandroidLog;
import static com.github.khandroid.misc.ActivityUtils.*;


public class Act_ActivityDemo extends HostActivity implements ActivityKat3ExecutorFunctionality.HostingAble<Integer, Long>{
    private DefaultActivityRestFunctionality mRestFunc;
    private ActivityKat3ExecutorFunctionality<Void, Integer, Long> mKatExecutorFunc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act__activity_demo);

        KhandroidLog.initLogTag("KhandroidSample");

        mRestFunc = new DefaultActivityRestFunctionality(this);

        mKatExecutorFunc = new ActivityKat3ExecutorFunctionality<Void, Integer, Long>(this);
        attach(mKatExecutorFunc);
        mKatExecutorFunc.onCreate(savedInstanceState);
        initView();

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads()
                .detectDiskWrites().penaltyLog().build());
    }


    @Override
    @Deprecated
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
                execute();
            }
        };
    }


    private void execute() {
        mKatExecutorFunc.execute(new NewTask(), (Void[]) null);
    }
    

    private class NewTask extends KhandroidAsyncTask3<Void, Integer, Long> {
        @Override
        protected Long doInBackground(Void... params) {
            KhandroidLog.d("taskaaaa");
            try {
                for (int i = 0; i < 50; i++) {
                    Thread.sleep(100);
                    publishProgress(i);
                }
            } catch (InterruptedException e) {
                // it is ok to get interrupted
            }

            return 1l;
        }
    }


    private TaskExecutorListener<Integer, Long> createListener() {
        TaskExecutorListener<Integer, Long> listener = new TaskExecutorListener<Integer, Long>() {
            @Override
            public void onTaskCompleted(Long result) {
                KhandroidLog.d("onTaskCompleted " + result);
            }


            @Override
            public void onTaskPublishProgress(Integer... progress) {
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


            @Override
            public void onTaskHasBeenCompleted(Long result) {
                KhandroidLog.d("onTaskHasBeenCompleted " + result);
            }


            @Override
            public void onTaskHasBeenCancelled() {
                KhandroidLog.d("onTaskHasBeenCancelled");
            }
        };

        return listener;
    }


    @Override
    public TaskExecutorListener<Integer, Long> getKatExecutorListener() {
        return createListener();
    }
}
