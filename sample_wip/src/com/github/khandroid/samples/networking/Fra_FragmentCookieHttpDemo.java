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
import khandroid.ext.apache.http.impl.client.DefaultHttpClient;
import khandroid.ext.apache.http.impl.cookie.BasicClientCookie;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.khandroid.core.HostFragment;
import com.github.khandroid.http.FragmentHttpWCookiesFunctionality;
import com.github.khandroid.kat.FragmentKatExecutorFunctionality;
import com.github.khandroid.kat.KatExecutor.TaskExecutorListener;
import com.github.khandroid.misc.ActivityUtils;
import com.github.khandroid.misc.KhandroidLog;
import com.github.khandroid.samples.R;


public class Fra_FragmentCookieHttpDemo extends HostFragment {

    private FragmentHttpWCookiesFunctionality mHttpFunc;
    private FragmentKatExecutorFunctionality<Void, Void, String> mKatExecutorFunc;

    private TextView mTvCookie;
    private Button mBtnSetCookie;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fra__fragment_cookie_http_demo, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mHttpFunc = new FragmentHttpWCookiesFunctionality(this, new DefaultHttpClient());
        mHttpFunc.onCreate(savedInstanceState);

        mKatExecutorFunc = new FragmentKatExecutorFunctionality<Void, Void, String>(this, createTaskListener());
        attach(mKatExecutorFunc);
        mKatExecutorFunc.onCreate(savedInstanceState);

        initView();
    }


    private void initView() {
        View view = getView();

        initButton(view, R.id.btn_execute_request, createBtnExecuteRequestClickListener());
        mBtnSetCookie = initButton(view, R.id.btn_set_cookie, createBtnSetCookieClickListener());

        mTvCookie = findTextView(view, R.id.tv_cookie);
        setTvCookieText(getString(R.string.global_not_available));
    }


    private void setTvCookieText(String str) {
        mTvCookie.setText(String.format(getString(R.string.cookie_http_demo__tv_cookie), str));
    }


    private View.OnClickListener createBtnExecuteRequestClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityUtils.isOnline(getActivity())) {
                    mKatExecutorFunc.execute(new MyHttpCookieTask(mHttpFunc));
                } else {
                    NoInetDialogCreator.show(getActivity());
                }
            }
        };
    }


    private View.OnClickListener createBtnSetCookieClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BasicClientCookie c = (BasicClientCookie) mHttpFunc.getCookie("my_cookie");
                c.setValue("41");
                mHttpFunc.setCookie(c);
                mKatExecutorFunc.execute(new MyHttpCookieTask(mHttpFunc), (Void[]) null);
            }
        };
    }


    private TaskExecutorListener<Void, String> createTaskListener() {
        TaskExecutorListener<Void, String> listener = new TaskExecutorListener<Void, String>() {
            @Override
            public void onTaskCompleted(String result) {
                setTvCookieText(result);
                mBtnSetCookie.setEnabled(true);
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
