package com.borysfan;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Host {

    public static String getName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "UNKNOWN-HOST-NAME";
        }
    }
}
