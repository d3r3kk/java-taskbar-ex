package com.microsoft.taskbariconapp;

import java.awt.Taskbar;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * The MyTaskbarIconApp class is the entry point for the application. It implements Runnable interface
 * which means it can be executed on a separate thread.
 */
public class MyTaskbarIconApp implements Runnable {

    private final Taskbar taskbar;
    private final JFrame frame;

    public MyTaskbarIconApp() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace(); // TODO Improve error handling
        }

        this.taskbar = Taskbar.getTaskbar();

        if (!this.taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
            System.out.println("Taskbar icon is not supported.");
        }

        this.frame = new JFrame("Taskbar Icon App");
    }

    /**
     * This method gets called when this class is executed on its thread.
     */
    public void run() {
        
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new BoxLayout(this.frame.getContentPane(), BoxLayout.Y_AXIS));
        this.frame.setSize(300, 200);

        JButton goButton = new JButton("Go");
        goButton.addActionListener(event -> startProgress());

        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(event -> pauseProgress());

        JButton errorButton = new JButton("Error");
        errorButton.addActionListener(event -> showError());

        JButton supportedButton = new JButton("Supported");
        supportedButton.addActionListener(event -> checkSupported());

        // TODO Commented out code - remove?
        // JButton fileOpButton = new JButton("File Op");
        // supportedButton.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent e) {
        //         fileOps();
        //     }
        // });

        JPanel panel = new JPanel();
        panel.add(goButton);
        panel.add(pauseButton);
        panel.add(errorButton);
        JPanel panel2 = new JPanel();
        panel2.add(supportedButton);
        // panel2.add(fileOpButton);    // TODO Commented out code

        frame.add(panel);
        frame.add(panel2);
        frame.setVisible(true);
    }

    private void startProgress() {
        new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
                final int progress = i;
                SwingUtilities.invokeLater(() -> taskbar.setWindowProgressValue(frame,progress));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace(); // TODO Improve error handling
                }
            }
        }).start();
    }

    // Set the progress to be at 50% and pause the TaskBar progress
    private void pauseProgress() {
        taskbar.setWindowProgressValue(frame, 50);
        taskbar.setWindowProgressState(frame, Taskbar.State.PAUSED);
    }

    // Set the progress to be at 100% and show an error for the TaskBar progress (should not get here)
    private void showError() {
        taskbar.setWindowProgressValue(frame, 100);
        taskbar.setWindowProgressState(frame, Taskbar.State.ERROR);
    }

    // Check if the platform this is running on supports the various TaskBar features
    // TODO - note this does not return anything, consider returning a boolean
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

    // TODO Commented out code - remove?
    // private void fileOps() {
    //     JFileChooser fileChooser = new JFileChooser();
    //     fileChooser.setSelectedFile(new File("text.tmp"));

    //     int result = fileChooser.showOpenDialog(frame);
    //     if (result == JFileChooser.APPROVE_OPTION) {
    //         File selectedFile = fileChooser.getSelectedFile();
    //         // Perform operations with the selected file
    //         System.out.println("Selected file: " + selectedFile.getAbsolutePath());
    //     }
    // }
}
