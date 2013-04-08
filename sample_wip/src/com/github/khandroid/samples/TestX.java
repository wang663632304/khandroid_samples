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

import org.json.JSONException;
import org.json.JSONObject;

import khandroid.ext.apache.http.client.methods.HttpUriRequest;

import com.github.khandroid.rest.MalformedResponseException;
import com.github.khandroid.rest.RestExchange;
import com.github.khandroid.rest.RestRequest;
import com.github.khandroid.rest.request.GetRequestBuilder;

public class TestX extends RestExchange<Long> {

   @Override
   protected RestRequest createRequest() {
      RestRequest ret = new RestRequest() {
         
         @Override
         public HttpUriRequest createHttpRequest() {
            GetRequestBuilder gb = new GetRequestBuilder("http://khs.bolyartech.com/req1.php");
            return gb.build();
         }
      };
      
      return ret;
   }

   @Override
   protected Long createResult(String source) throws MalformedResponseException {
      Long ret;
      
      try {
          JSONObject respJson = new JSONObject(source);
          long locationId = respJson.getLong("location_id");
          if (locationId > 0) {
              ret = locationId;
          } else {
              throw new MalformedResponseException("Invalid location ID: " + locationId);
          }
      } catch (JSONException e) {
          throw new MalformedResponseException("Cannot extract data.", e);
      }       

      
      return ret;
   }

   
   
}
