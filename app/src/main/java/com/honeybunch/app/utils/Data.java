package com.honeybunch.app.utils;

import android.content.Context;
import android.content.res.TypedArray;

import com.honeybunch.app.R;
import com.honeybunch.app.models.Person;

import java.util.ArrayList;
import java.util.List;

public class Data {


    public static List<Person> getPersonsData(Context context) {

        List<Person> people = new ArrayList<>();
        String[] persons = context.getResources().getStringArray(R.array.people);
        TypedArray images = context.getResources().obtainTypedArray(R.array.images);

        for (int i = 0; i < persons.length; i++) {
            Person person = new Person();
            person.setName(persons[i]);
            person.setImage(images.getResourceId(i, -1));
            people.add(person);
        }

        return people;

    }

}