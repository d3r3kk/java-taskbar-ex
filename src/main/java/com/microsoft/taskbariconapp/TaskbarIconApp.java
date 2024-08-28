package com.microsoft.taskbariconapp;

import javax.swing.SwingUtilities;

/**
 * The TaskbarIconApp class is the entry point for the application.
 * It initializes and runs the MyTaskbarIconApp to demonstrate taskbar icon manipulation.
 */
public class TaskbarIconApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TaskbarIconApp::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        MyTaskbarIconApp app = new MyTaskbarIconApp();
        app.run();
    }
}
