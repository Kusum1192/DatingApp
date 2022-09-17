package com.honeybunch.app.utils;

import android.content.Context;
import android.graphics.Typeface;

public class TypeFactory {

    private String A_BOLD= "HelveticaNeue-Bold.otf";
    private String A_LIGHT="HelveticaNeue-Light.otf";
    private String A_REGULAR= "HelveticaNeue-Regular.otf";


    Typeface ambleBold;
    Typeface ambleLight;
    Typeface ambleRegular;


    public TypeFactory(Context context){
        ambleBold = Typeface.createFromAsset(context.getAssets(),A_BOLD);
        ambleLight = Typeface.createFromAsset(context.getAssets(),A_LIGHT);
        ambleRegular = Typeface.createFromAsset(context.getAssets(),A_REGULAR);

    }

}

