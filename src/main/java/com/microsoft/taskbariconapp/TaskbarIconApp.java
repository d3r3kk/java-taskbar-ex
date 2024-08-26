package com.microsoft.taskbariconapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TaskbarIconApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TaskbarIconApp::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        MyTaskbarIconApp app = new MyTaskbarIconApp();
        app.run();
    }
}



