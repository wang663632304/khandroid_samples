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

package com.github.khandroid.samples;

import java.io.IOException;
import khandroid.ext.apache.http.client.ClientProtocolException;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import com.github.khandroid.activity.HostActivity;
import com.github.khandroid.activity.functionalities.defaults.DefaultActivityRestFunctionality;
import com.github.khandroid.rest.request.GetRequestBuilder;

import static com.github.khandroid.misc.ActivityUtils.*;


public class Act_ActivityDemo extends HostActivity {
    private DefaultActivityRestFunctionality mHttpFunc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act__activity_demo);

        mHttpFunc = new DefaultActivityRestFunctionality(this);
        initView();

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork() // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
    }


    private void initView() {
        View view = getWindow().getDecorView().findViewById(android.R.id.content);

        initButton(view, R.id.btn_make_request, createBtnClickListener());
    }


    private View.OnClickListener createBtnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetRequestBuilder gb = new GetRequestBuilder("http://khogre.bolyartech.com/");
                String rez;
                try {
                    rez = mHttpFunc.execute(gb.build());
                    int i = 1;
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
    }
}
