/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;


public class MainActivity extends AppCompatActivity {

  public void redirectActivity(){
    if(ParseUser.getCurrentUser().get("riderOrDriver").equals("rider")) {
      Intent intent = new Intent(getApplicationContext(), RiderActivity.class);
      startActivity(intent);
    }else{
      Intent intent = new Intent(getApplicationContext(),ViewRequestsActivity.class);
      startActivity(intent);
    }
  }

  public void getStarted(View view) throws ParseException {
    Switch simpleSwitch = (Switch)findViewById(R.id.simpleSwitch);
    Log.i("info", String.valueOf(simpleSwitch.isChecked()));
    String category="rider";
    if(simpleSwitch.isChecked()){
      category="driver";
    }
    ParseUser.getCurrentUser().put("riderOrDriver",category);
    Log.i("Info", "Redirecting as "+category);
    Log.i("Info", "riderOrDriver: "+ParseUser.getCurrentUser().get("riderOrDriver"));
    ParseUser.getCurrentUser().saveInBackground();
    redirectActivity();

  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    getSupportActionBar().hide();
    Log.i("Info", "Current user" +" "+ParseUser.getCurrentUser());
    if(ParseUser.getCurrentUser()==null){
      ParseAnonymousUtils.logIn(new LogInCallback() {
        @Override
        public void done(ParseUser user, ParseException e) {
          if(e==null){
            Log.i("Info", "Anonymous login successful ");
          }else{
            Log.i("Info", "Anonymous login failed ");
          }
        }
      });
    }else{
        Log.i("Info", "currently category " + ParseUser.getCurrentUser().get("riderOrDriver"));
        redirectActivity();
    }

    ParseAnalytics.trackAppOpenedInBackground(getIntent());

  }
}