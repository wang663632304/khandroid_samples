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

import static com.github.khandroid.misc.ActivityUtils.initButton;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.github.khandroid.core.HostActivity;
import com.github.khandroid.functionality.HttpFunctionalityImpl;
import com.github.khandroid.misc.ActivityUtils;
import com.github.khandroid.misc.KhandroidLog;
import com.github.khandroid.rest.ActivityRestFunctionality;
import com.github.khandroid.samples.AppConstants;
import com.github.khandroid.samples.R;
import com.github.khandroid.volley.GetVRestRequestBuilder;
import com.github.khandroid.volley.PostJsonObjectRequestBuilder;
import com.github.khandroid.volley.VRestRequest;


public class Act_ActivityVolleyRest extends Activity {

    private TextView mTvResultString;
    private TextView mTvResultInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act__new_rest);
        

        initView(); 
    }

    
    private void initView() {
        View view = getWindow().getDecorView().findViewById(android.R.id.content);

        mTvResultString = ActivityUtils.findTextView(view, R.id.tv_result_string);
        mTvResultInt = ActivityUtils.findTextView(view, R.id.tv_result_int);
        initButton(view, R.id.btn_execute_request, createBtnExecuteRequestClickListener());
        
        
        setTvText(mTvResultString,
                  R.string.rest_http_demo__tv_result_string,
                  "");

        setTvText(mTvResultInt,
                  R.string.rest_http_demo__tv_result_int,
                  "");
    }

    
    private void setTvText(TextView tv, int res, String str) {
        tv.setText(String.format(getString(res), str));
    }
    
    
    private View.OnClickListener createBtnExecuteRequestClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityUtils.isOnline(Act_ActivityVolleyRest.this)) {
                    GetVRestRequestBuilder b = new GetVRestRequestBuilder(AppConstants.HOST
                                                                          + AppConstants.PAGE_REST_REQUEST);
                    b.setRestListener(new VRestRequest.Listener() {
                        @Override
                        public void onSuccess(JSONObject json, int responseCode) {
                            try {
                                setTvText(mTvResultString,
                                          R.string.rest_http_demo__tv_result_string,
                                          json.getString("some_string_val"));
            
                                setTvText(mTvResultInt,
                                          R.string.rest_http_demo__tv_result_int,
                                          json.getString("some_int_val"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        
                        
                        @Override
                        public void onSoftError(int responseCode) {
                            // TODO Auto-generated method stub
                            
                        }
                        
                        
                        @Override
                        public void onHardError() {
                            // TODO Auto-generated method stub
                            
                        }
                    });
                    
                    b.setParameter("input", "123");
                    RequestQueue q = Volley.newRequestQueue(Act_ActivityVolleyRest.this);
                    q.add(b.build());
                   
                } else {
                    NoInetDialogCreator.show(Act_ActivityVolleyRest.this);
                }
            }
        };
    }
}
