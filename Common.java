package com.example.zing_android;

import java.util.ArrayList;

public class Common {

    private  ArrayList<Person> personList = new ArrayList<>();

    private static Common instance;
    public static synchronized Common getInstance() {
        if (instance == null) {
            instance = new Common();
        }
        return instance;
    }

    public void  addUser(Person user) {

        personList.add(user);
    }

    public void removeUserByName(String name) {

        for (int index = 0; index < this.personList.size(); index++) {
            String userName = this.personList.get(index).getName();
            if(userName == name) {
                this.personList.remove(index);
            }
        }
    }


    public Person getUserByName(String name) {

        for (int index = 0; index < this.personList.size(); index++) {
            Person user = this.personList.get(index);
            String userName = user.getName();
            if(userName == name) {
                return user;
            }
        }
        return null;
    }

    public ArrayList<Person> getUserList() {
        return this.personList;
    }

}
