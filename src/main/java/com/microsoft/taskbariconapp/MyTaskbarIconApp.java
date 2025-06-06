package com.microsoft.taskbariconapp;

import java.awt.Taskbar;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * The MyTaskbarIconApp class is the entry point for the application.
 * 
 * The app creates a simple GUI with buttons to control the taskbar
 * icon's progress state and value.
 */
public class MyTaskbarIconApp implements Runnable {

    private final Taskbar taskbar;
    private final JFrame frame;

    public MyTaskbarIconApp() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.taskbar = Taskbar.getTaskbar();

        if (!this.taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
            System.out.println("Taskbar icon is not supported.");
        }

        this.frame = new JFrame("Taskbar Icon App");
        // Set application window icon from resources/app-icon.ico
        try {
            java.net.URL iconURL = getClass().getClassLoader().getResource("app-icon.png");
            if (iconURL != null) {
                java.awt.Image icon = javax.imageio.ImageIO.read(iconURL);
                System.out.println("Setting application icon from: " + iconURL);
                this.frame.setIconImage(icon);
            } else {
                System.out.println("FAILED: Setting application icon from: /src/main/resources/app-icon.png");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method gets called when this class is executed on its thread.
     */
    public void run() {

        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new BoxLayout(this.frame.getContentPane(), BoxLayout.Y_AXIS));
        this.frame.setSize(300, 200);

        JButton offButton = new JButton("Off");
        offButton.addActionListener(event -> offProgress());

        JButton normalButton = new JButton("Normal");
        normalButton.addActionListener(event -> startNormalProgress());

        JButton goButton = new JButton("Go");
        goButton.addActionListener(event -> startProgress());

        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(event -> pauseProgress());

        JButton errorButton = new JButton("Error");
        errorButton.addActionListener(event -> showError());

        JButton indeterminateButton = new JButton("Indeterminate");
        indeterminateButton.addActionListener(event -> setIndeterminate());

        JButton supportedButton = new JButton("Supported");
        supportedButton.addActionListener(event -> checkSupported());

        JPanel panel = new JPanel();
        panel.add(offButton);
        panel.add(normalButton);
        panel.add(goButton);
        panel.add(pauseButton);
        panel.add(indeterminateButton);
        panel.add(errorButton);

        JPanel panel2 = new JPanel();
        panel2.add(supportedButton);

        frame.add(panel);
        frame.add(panel2);
        frame.setVisible(true);
    }

    private void startProgress() {
        new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
                final int progress = i;
                SwingUtilities.invokeLater(() -> taskbar.setWindowProgressValue(frame, progress));
                try {
                    Thread.sleep(30);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    // Set the state of the TaskBar progress to OFF
    private void offProgress() {
        taskbar.setWindowProgressState(frame, Taskbar.State.OFF);
    }

    // Set the progress to be at 0% and show normal TaskBar progress
    private void startNormalProgress() {
        taskbar.setWindowProgressValue(frame, 0);
        taskbar.setWindowProgressState(frame, Taskbar.State.NORMAL);
    }

    // Set the progress to be at 50% and pause the TaskBar progress
    private void pauseProgress() {
        taskbar.setWindowProgressValue(frame, 50);
        taskbar.setWindowProgressState(frame, Taskbar.State.PAUSED);
    }

    // set the icon to show indeterminate state
    private void setIndeterminate() {
        taskbar.setWindowProgressValue(frame, 0);
        taskbar.setWindowProgressState(frame, Taskbar.State.INDETERMINATE);
    }

    // Set the progress to be at 100% and show an error for the TaskBar progress
    private void showError() {
        taskbar.setWindowProgressValue(frame, 100);
        taskbar.setWindowProgressState(frame, Taskbar.State.ERROR);
    }

    // Check if the platform this is running on supports the various TaskBar
    // features
    private void checkSupported() {
        boolean iconBadgeText = taskbar.isSupported(Taskbar.Feature.ICON_BADGE_TEXT);
        boolean iconBadgeNumber = taskbar.isSupported(Taskbar.Feature.ICON_BADGE_NUMBER);
        boolean iconBadgeImageWindow = taskbar.isSupported(Taskbar.Feature.ICON_BADGE_IMAGE_WINDOW);
        boolean iconImage = taskbar.isSupported(Taskbar.Feature.ICON_IMAGE);
        boolean menu = taskbar.isSupported(Taskbar.Feature.MENU);
        boolean progressStateWindow = taskbar.isSupported(Taskbar.Feature.PROGRESS_STATE_WINDOW);
        boolean progressValue = taskbar.isSupported(Taskbar.Feature.PROGRESS_VALUE);
        boolean progressValueWindow = taskbar.isSupported(Taskbar.Feature.PROGRESS_VALUE_WINDOW);
        boolean userAttention = taskbar.isSupported(Taskbar.Feature.USER_ATTENTION);
        boolean userAttentionWindow = taskbar.isSupported(Taskbar.Feature.USER_ATTENTION_WINDOW);

        System.out.println("ICON_BADGE_TEXT         " + iconBadgeText);
        System.out.println("ICON_BADGE_NUMBER       " + iconBadgeNumber);
        System.out.println("ICON_BADGE_IMAGE_WINDOW " + iconBadgeImageWindow);
        System.out.println("ICON_IMAGE              " + iconImage);
        System.out.println("MENU                    " + menu);
        System.out.println("PROGRESS_STATE_WINDOW   " + progressStateWindow);
        System.out.println("PROGRESS_VALUE          " + progressValue);
        System.out.println("PROGRESS_VALUE_WINDOW   " + progressValueWindow);
        System.out.println("USER_ATTENTION          " + userAttention);
        System.out.println("USER_ATTENTION_WINDOW   " + userAttentionWindow);
    }
}
