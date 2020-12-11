package com.company;

import chat.ChatSever;
import chat.Client;

import java.io.Console;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
	// write your code here
        new Thread(){
            @Override
            public void run() {
                ChatSever.main(null);
            }
        };

        new Thread(){
            @Override
            public void run() {
                Client.main(null);
            }
        };
    }
}
