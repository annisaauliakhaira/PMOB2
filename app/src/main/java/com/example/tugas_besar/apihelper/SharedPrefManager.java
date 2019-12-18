package com.example.tugas_besar.apihelper;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    public static final String SP_PMOB_APP = "spPMOBApp";
    private static final String SP_TOKEN = null;

    public static final String SP_SUDAH_LOGIN = "spSudahLogin";

    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_PMOB_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveToken(String value){
        spEditor.putString(SP_TOKEN, value);
        spEditor.commit();
    }

    public String getToken(){
        return sp.getString(SP_TOKEN, "");
    }

}
