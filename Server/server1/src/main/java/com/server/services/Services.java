package com.server.services;

public class Services {
    static final long a = 1;
    static final long b = 1;
    public static String getServiceOf(long serviceIdx,long param){
        return String.valueOf(a*serviceIdx+b*param);
    }
}
