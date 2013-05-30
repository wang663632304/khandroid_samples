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

import static com.github.khandroid.misc.ActivityUtils.initButton;
import com.github.khandroid.core.HostFragment;
import com.github.khandroid.http.FragmentHttpFunctionality;
import com.github.khandroid.kat.FragmentKatExecutorFunctionality;
import com.github.khandroid.kat.KatExecutor.TaskExecutorListener;
import com.github.khandroid.misc.ActivityUtils;
import com.github.khandroid.misc.KhandroidLog;
import com.github.khandroid.samples.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Fra_FragmentSimpleHttpDemo extends HostFragment {
    private FragmentHttpFunctionality mHttpFunc;
    private FragmentKatExecutorFunctionality<Void, Void, String> mKatExecutorFunc;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fra__fragment_simple_http_demo, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mHttpFunc = new FragmentHttpFunctionality(this, MyHttpClientSingleton.getInstance());
        mHttpFunc.onCreate(savedInstanceState);
        
        mKatExecutorFunc = new FragmentKatExecutorFunctionality<Void, Void, String>(this, createTaskListener());
        attach(mKatExecutorFunc);
        mKatExecutorFunc.onCreate(savedInstanceState);

        initView();
    }


    private void initView() {
        View view = getView();

        initButton(view, R.id.btn_execute_request, createBtnClickListener());
    }



    private View.OnClickListener createBtnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityUtils.isOnline(getActivity())) {
                    mKatExecutorFunc.execute(new MyHttpTask(mHttpFunc));
                } else {
                    NoInetDialogCreator.show(getActivity());
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
