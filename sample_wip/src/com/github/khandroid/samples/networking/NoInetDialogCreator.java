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

import com.github.khandroid.samples.R;

import android.app.AlertDialog;
import android.content.Context;


public class NoInetDialogCreator  {
    public static AlertDialog create(Context context) {
        AlertDialog.Builder b = new AlertDialog.Builder(context);
        b.setMessage(context.getString(R.string.dlg__no_inet_msg));
        b.setNeutralButton(R.string.global_btn_close, null);
        b.setCancelable(true);
        return b.create();
    }

    
    public static void show(Context context) {
        create(context).show();
    }
}
