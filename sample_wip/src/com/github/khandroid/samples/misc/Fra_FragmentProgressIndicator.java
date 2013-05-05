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
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.khandroid.core.HostFragment;
import com.github.khandroid.kat.FragmentKatExecutorFunctionality;
import com.github.khandroid.kat.KhandroidAsyncTask;
import com.github.khandroid.kat.KatExecutor.TaskExecutorListener;
import com.github.khandroid.misc.KhandroidLog;
import com.github.khandroid.samples.R;


public class Fra_FragmentProgressIndicator extends HostFragment {
    private static final String PROGRESS_FRAGMENT_TAG = "progress fragment";

    private FragmentKatExecutorFunctionality<Void, Integer, Void> mKatExecutorFunc;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fra__fragment_progress_indicator, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mKatExecutorFunc = new FragmentKatExecutorFunctionality<Void, Integer, Void>(this, createTaskListener());
        attach(mKatExecutorFunc);
        mKatExecutorFunc.onCreate(savedInstanceState);

        initView();
    }


    private void initView() {
        View view = getView();

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
                    DialogFragment diaFragment = (DialogFragment) getFragmentManager()
                            .findFragmentByTag(PROGRESS_FRAGMENT_TAG);
                    if (diaFragment != null) {
                        ProgressDialog dia = (ProgressDialog) diaFragment.getDialog();
                        dia.setProgress(progress[0]);
                    }
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
//                showWaitDialog();
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
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(PROGRESS_FRAGMENT_TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment newFragment = ProgressFragment.newInstance();
        newFragment.show(ft, PROGRESS_FRAGMENT_TAG);
    }


    private void closeProgressDialog() {
        DialogFragment dia = (DialogFragment) getFragmentManager()
                .findFragmentByTag(PROGRESS_FRAGMENT_TAG);

        if (dia != null) {
            dia.dismiss();
        }
    }

    public static class ProgressFragment extends DialogFragment {
        public static ProgressFragment newInstance() {
            return new ProgressFragment();
        }


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            ProgressDialog pd = new ProgressDialog(getActivity());
            pd.setCancelable(false);
            pd.setMessage("Please wait ...");
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setProgress(0);
            pd.setMax(12);
            return pd;
        }
    }
}
