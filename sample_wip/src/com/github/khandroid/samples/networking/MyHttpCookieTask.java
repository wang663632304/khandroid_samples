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

import java.io.IOException;

import khandroid.ext.apache.http.client.ClientProtocolException;
import khandroid.ext.apache.http.client.methods.HttpUriRequest;

import com.github.khandroid.functionality.HttpFunctionalityWCookies;
import com.github.khandroid.http.request.GetRequestBuilder;
import com.github.khandroid.kat.KhandroidAsyncTask;
import com.github.khandroid.samples.AppConstants;


public class MyHttpCookieTask extends KhandroidAsyncTask<Void, Void, String> {
    private HttpFunctionalityWCookies mHttpFunc;

    public MyHttpCookieTask(HttpFunctionalityWCookies httpFunc) {
        if (httpFunc != null) {
            mHttpFunc = httpFunc;
        } else {
            throw new IllegalStateException("Parameter httpFunc is null");
        }
    }
    

    @Override
    protected String doInBackground(Void... params) {
        String ret = null;

        HttpUriRequest req = new GetRequestBuilder(AppConstants.HOST + AppConstants.PAGE_COOKIE).build();

        try {
            mHttpFunc.execute(req);
            ret = mHttpFunc.getCookieValue("my_cookie");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }
}