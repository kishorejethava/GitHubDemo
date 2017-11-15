package net.giniguru.githubdemo;

import android.app.Application;

import net.giniguru.githubdemo.storage.DatabaseUtil;

/**
 * Created by KK on 11/15/2017.
 */

public class GitHubDemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseUtil.init(this,getString(R.string.database_name),1,null);
    }
}
