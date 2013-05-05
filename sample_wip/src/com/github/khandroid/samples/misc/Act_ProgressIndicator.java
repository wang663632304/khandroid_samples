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

import static com.github.khandroid.misc.ActivityUtils.initButton;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import com.github.khandroid.core.HostActivity;
import com.github.khandroid.kat.ActivityKatExecutorFunctionality;
import com.github.khandroid.kat.KhandroidAsyncTask;
import com.github.khandroid.kat.KatExecutor.TaskExecutorListener;
import com.github.khandroid.misc.KhandroidLog;
import com.github.khandroid.samples.R;


public class Act_ProgressIndicator extends HostActivity {
    private ActivityKatExecutorFunctionality<Void, Integer, Void> mKatExecutorFunc;
    private ProgressDialog mProgressDialog;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act__progress_indicator);
        
        mKatExecutorFunc = new ActivityKatExecutorFunctionality<Void, Integer, Void>(this, createTaskListener());
        attach(mKatExecutorFunc);
        mKatExecutorFunc.onCreate(savedInstanceState);

        initView();
    }
    
    
    private void initView() {
        View view = getWindow().getDecorView().findViewById(android.R.id.content);

        initButton(view, R.id.btn_start, createBtnStartClickListener());
        
    }
    
    
    private TaskExecutorListener<Integer, Void> createTaskListener() {
        TaskExecutorListener<Integer, Void> listener = new TaskExecutorListener<Integer, Void>() {
            @Override
            public void onTaskCompleted(Void result) {
                closeProgressDialog();
            }


            @Override
            public void onTaskPublishProgress(Integer... progress) {
                KhandroidLog.d("onTaskPublishProgress");
                if (progress != null && progress.length > 0) {
                    mProgressDialog.setProgress(progress[0]);
                }
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
    
    
    
    private static class MyWaitTask extends KhandroidAsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                int c = 0;
                while (c < 12) {
                    Thread.sleep(600);
                    c++;
                    publishProgress(Integer.valueOf(c));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            return null;
        }
    }
    
    
    private void showWaitDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Please wait ...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setProgress(0);
        mProgressDialog.setMax(12);
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
