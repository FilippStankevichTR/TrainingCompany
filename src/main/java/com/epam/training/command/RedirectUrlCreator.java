package com.epam.training.command;

public class RedirectUrlCreator {

    public static String create(String command){
        return "{request.contextPath}controller?command=" + command;
    }
}
