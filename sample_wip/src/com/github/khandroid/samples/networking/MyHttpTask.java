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

import com.github.khandroid.functionality.HttpFunctionality;
import com.github.khandroid.http.request.GetRequestBuilder;
import com.github.khandroid.kat.KhandroidAsyncTask;


public class MyHttpTask extends KhandroidAsyncTask<Void, Void, String> {
    private HttpFunctionality mHttpFunc;

    public MyHttpTask(HttpFunctionality httpFunc) {
        mHttpFunc = httpFunc;
    }
    

    @Override
    protected String doInBackground(Void... params) {
        String ret = null;

        HttpUriRequest req = new GetRequestBuilder("http://khs.bolyartech.com/").build();

        try {
            ret = mHttpFunc.execute(req);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

}
