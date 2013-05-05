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

import com.github.khandroid.misc.ActivityUtils;
import com.github.khandroid.samples.R;
import com.github.khandroid.samples.misc.Act_FragmentProgressIndicator;
import com.github.khandroid.samples.networking.Act_ActivityCookieHttpDemo;
import com.github.khandroid.samples.networking.Act_ActivitySimpleHttpDemo;
import com.github.khandroid.samples.networking.Act_FragmentCookieHttpDemo;
import com.github.khandroid.samples.networking.Act_FragmentSimpleHttpDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Act_FragmentDemos extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act__fragment_demos);
        
        View view = getWindow().getDecorView().findViewById(android.R.id.content);
        ActivityUtils.initButton(view, R.id.btn_http_simple, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Act_FragmentDemos.this, Act_FragmentSimpleHttpDemo.class);
                startActivity(intent);
            }
        });
        
        ActivityUtils.initButton(view, R.id.btn_http_cookie, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Act_FragmentDemos.this, Act_FragmentCookieHttpDemo.class);
                startActivity(intent);
            }
        });
        
        ActivityUtils.initButton(view, R.id.btn_task_progress, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Act_FragmentDemos.this, Act_FragmentProgressIndicator.class);
                startActivity(intent);
            }
        });
    }
}
