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

import org.json.JSONException;
import org.json.JSONObject;

import khandroid.ext.apache.http.client.methods.HttpUriRequest;

import com.github.khandroid.http.request.GetRequestBuilder;
import com.github.khandroid.rest.ActivityRestFunctionality;
import com.github.khandroid.rest.MalformedResponseException;
import com.github.khandroid.rest.RestAsyncTask;
import com.github.khandroid.rest.RestExchange;
import com.github.khandroid.rest.RestExchangeFailedException;
import com.github.khandroid.rest.RestRequest;
import com.github.khandroid.samples.AppConstants;


public class MyRestTask extends RestAsyncTask<Void, Void, MyRestResult> {
    private ActivityRestFunctionality mRestFunc;


    public MyRestTask(ActivityRestFunctionality restFunc) {
        if (restFunc != null) {
            mRestFunc = restFunc;
        } else {
            throw new IllegalStateException("Parameter restFunc is null");
        }
    }


    @Override
    protected ResultWrapper<MyRestResult> doInBackground(Void... params) {
        ResultWrapper<MyRestResult> ret;

        MyRestExchange x = new MyRestExchange();
        try {
            mRestFunc.execute(x);
            if (x.isOk()) {
                ret = new ResultWrapper<MyRestResult>(ResultWrapper.STATUS_OK, x.getResult());
            } else {
                ret = new ResultWrapper<MyRestResult>(ResultWrapper.STATUS_SOFT_ERROR, null);
            }
        } catch (RestExchangeFailedException e) {
            ret = new ResultWrapper<MyRestResult>(ResultWrapper.STATUS_HARD_ERROR, null);
        }

        return ret;
    }

    public class MyRestExchange extends RestExchange<MyRestResult> {
        public static final String RESP_STRING_VALUE = "some_string_val";
        public static final String RESP_INT_VALUE = "some_int_val";


        @Override
        protected RestRequest createRequest() {
            return new MyRestRequest();
        }


        @Override
        protected MyRestResult createResult(String source) throws MalformedResponseException {
            MyRestResult ret = null;

            try {
                JSONObject respJson = new JSONObject(source);
                ret = new MyRestResult(respJson.getString("some_string_val"),
                                     respJson.getInt("some_int_val"));
            } catch (JSONException e) {
                throw new MalformedResponseException("Cannot extract data.", e);
            }

            return ret;
        }

    }

    public class MyRestRequest extends RestRequest {
        public static final String PARAM_INPUT = "input";


        @Override
        public HttpUriRequest createHttpRequest() {
            GetRequestBuilder b = new GetRequestBuilder(AppConstants.HOST
                    + AppConstants.PAGE_REST_REQUEST);
            b.addParameter(PARAM_INPUT, Integer.toString(123));

            return b.build();
        }
    }
}
