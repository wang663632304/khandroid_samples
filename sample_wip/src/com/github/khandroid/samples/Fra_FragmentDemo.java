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

import static com.github.khandroid.misc.ActivityUtils.initButton;

import com.github.khandroid.fragment.HostFragment;
import com.github.khandroid.kat.FragmentKatExecutorFunctionality;
import com.github.khandroid.kat.KhandroidAsyncTask;
import com.github.khandroid.kat.KatExecutor.TaskExecutorListener;
import com.github.khandroid.misc.KhandroidLog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Fra_FragmentDemo extends HostFragment implements FragmentKatExecutorFunctionality.HostingAble<Integer, Long> {
    private FragmentKatExecutorFunctionality<Void, Integer, Long> mKatExecutorFunc;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        KhandroidLog.initLogTag("KhandroidSample");
        return inflater.inflate(R.layout.fra__fragment_demo, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mKatExecutorFunc = new FragmentKatExecutorFunctionality<Void, Integer, Long>(this);
        attach(mKatExecutorFunc);
        mKatExecutorFunc.onCreate(savedInstanceState);

        initView();
    }


    private void initView() {
        View view = getView();

        initButton(view, R.id.btn_make_request, createBtnClickListener());
    }

    private class NewTask extends KhandroidAsyncTask<Void, Integer, Long> {
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
                KhandroidLog.d("onTaskPublishProgress " + progress[0]);
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


    @Override
    public TaskExecutorListener<Integer, Long> getKatExecutorListener() {
        return createListener();
    }
}
