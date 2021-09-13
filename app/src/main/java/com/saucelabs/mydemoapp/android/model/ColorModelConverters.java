package com.saucelabs.mydemoapp.android.model;

import androidx.room.TypeConverter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class ColorModelConverters {
    Gson gson = new Gson();

    @TypeConverter
    public static List<ColorModel> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<ColorModel>>() {}.getType();

        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<ColorModel> someObjects) {
        return new Gson().toJson(someObjects);
    }
}
