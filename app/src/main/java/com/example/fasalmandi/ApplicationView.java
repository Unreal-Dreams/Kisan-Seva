package com.example.fasalmandi;

import android.app.Application;
import android.content.Context;

public class ApplicationView extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageChanger.onAttach(base,"en"));
    }
}
