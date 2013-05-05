/*
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
import android.view.View;

import com.github.khandroid.core.HostActivity;
import com.github.khandroid.http.ActivityHttpFunctionality;
import com.github.khandroid.kat.ActivityKatExecutorFunctionality;
import com.github.khandroid.kat.KatExecutor.TaskExecutorListener;
import com.github.khandroid.misc.ActivityUtils;
import com.github.khandroid.misc.KhandroidLog;
import com.github.khandroid.samples.R;

import static com.github.khandroid.misc.ActivityUtils.*;


public class Act_ActivitySimpleHttpDemo extends HostActivity {
    private ActivityHttpFunctionality mHttpFunc;
    private ActivityKatExecutorFunctionality<Void, Void, String> mKatExecutorFunc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act__activity_simple_http_demo);

        mHttpFunc = new ActivityHttpFunctionality(this, MyHttpClientSingleton.getInstance());
        mHttpFunc.onCreate(savedInstanceState);

        mKatExecutorFunc = new ActivityKatExecutorFunctionality<Void, Void, String>(this, createTaskListener());
        attach(mKatExecutorFunc);
        mKatExecutorFunc.onCreate(savedInstanceState);
        initView();
    }


    @Override
    public Object onRetainNonConfigurationInstance() {
        return mKatExecutorFunc.onRetainNonConfigurationInstance();
    }


    private void initView() {
        View view = getWindow().getDecorView().findViewById(android.R.id.content);

        initButton(view, R.id.btn_execute_request, createBtnClickListener());
    }


    private View.OnClickListener createBtnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityUtils.isOnline(Act_ActivitySimpleHttpDemo.this)) {
                    mKatExecutorFunc.execute(new MyHttpTask(mHttpFunc));
                } else {
                    NoInetDialogCreator.show(Act_ActivitySimpleHttpDemo.this);
                }
            }
        };
    }
    

    private TaskExecutorListener<Void, String> createTaskListener() {
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
}
