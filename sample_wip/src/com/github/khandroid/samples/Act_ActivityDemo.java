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

public class Act_ActivityDemo extends HostActivity {
   private DefaultActivityRestFunctionality mRestFunc;
   private ActivityKat3ExecutorFunctionality<Void, Void, Long> mKatExecutorFunc;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.act__activity_demo);

      KhandroidLog.initLogTag("PRESNI");

      mRestFunc = new DefaultActivityRestFunctionality(this);

      mKatExecutorFunc = new ActivityKat3ExecutorFunctionality<Void, Void, Long>(this);
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
            onClickie();
         }
      };
   }

   private void onClickie() {
      a();
   }

   private class NewTask extends KhandroidAsyncTask3<Void, Void, Long> {
      private Long ret = 1l;

      @Override
      protected Long doInBackground(Void... params) {
         KhandroidLog.d("taskaaaa");
         try {
            Thread.sleep(4000);
         } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }

         return 1l;
      }
   }

   private void a() {
      TaskExecutorListener<Void, Long> listener = new TaskExecutorListener<Void, Long>() {
         @Override
         public void onTaskCompleted(Long result) {
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

         @Override
         public void onTaskHasBeenCompleted(Long result) {
            KhandroidLog.d("onTaskHasBeenCompleted " + result);
         }

         @Override
         public void onTaskHasBeenCancelled() {
            KhandroidLog.d("onTaskHasBeenCancelled");
         }
      };

      mKatExecutorFunc.execute(new NewTask(), listener, (Void[]) null);
   }

}
