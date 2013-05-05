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

package com.github.khandroid.samples.misc;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import static com.github.khandroid.misc.ActivityUtils.initButton;
import com.github.khandroid.core.HostActivity;
import com.github.khandroid.kat.ActivityKatExecutorFunctionality;
import com.github.khandroid.kat.KatExecutor.TaskExecutorListener;
import com.github.khandroid.kat.KhandroidAsyncTask;
import com.github.khandroid.misc.KhandroidLog;
import com.github.khandroid.samples.R;


public class Act_WaitIndicator extends HostActivity {
    private ActivityKatExecutorFunctionality<Void, Void, Void> mKatExecutorFunc;
    private ProgressDialog mProgressDialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act__wait_indicator);

        mKatExecutorFunc = new ActivityKatExecutorFunctionality<Void, Void, Void>(this, createTaskListener());
        attach(mKatExecutorFunc);
        mKatExecutorFunc.onCreate(savedInstanceState);
        
        initView();
    }

    private void initView() {
        View view = getWindow().getDecorView().findViewById(android.R.id.content);

        initButton(view, R.id.btn_start, createBtnStartClickListener());
        
    }
    
    
    private TaskExecutorListener<Void, Void> createTaskListener() {
        TaskExecutorListener<Void, Void> listener = new TaskExecutorListener<Void, Void>() {
            @Override
            public void onTaskCompleted(Void result) {
                closeProgressDialog();
            }


            @Override
            public void onTaskPublishProgress(Void... progress) {
                KhandroidLog.d("onTaskPublishProgress");
            }


            @Override
            public void onTaskCancelled() {
                KhandroidLog.d("onTaskCancelled");
                closeProgressDialog();
            }


            @Override
            public void onContinueWithTask() {
                KhandroidLog.d("onContinueWithTask");
                showWaitDialog();
            }
        };

        return listener;
    }
    
    
    private View.OnClickListener createBtnStartClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWaitDialog();
                mKatExecutorFunc.execute(new MyWaitTask());
            }
        };
    }
    
    
    
    private static class MyWaitTask extends KhandroidAsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            return null;
        }
    }
    
    
    private void showWaitDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Please wait ...");
        mProgressDialog.show();
    }
    
    
    private void closeProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
    
    
    @Override
    protected void onStop() {
        super.onStop();
        
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            closeProgressDialog();
        }
    }
    
    
    @Override
    public Object onRetainNonConfigurationInstance() {
        return mKatExecutorFunc.onRetainNonConfigurationInstance();
    }
}
