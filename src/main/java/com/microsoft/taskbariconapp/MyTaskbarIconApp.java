package com.microsoft.taskbariconapp;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyTaskbarIconApp implements Runnable {

    private Taskbar taskbar;
    private JFrame frame;

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
      
    }


    public void run() {
        
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new BoxLayout(this.frame.getContentPane(), BoxLayout.Y_AXIS));
        this.frame.setSize(300, 200);

        JButton goButton = new JButton("Go");
        goButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startProgress();
            }
        });

        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pauseProgress();
            }
        });

        JButton errorButton = new JButton("Error");
        errorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showError();
            }
        });

        JButton supportedButton = new JButton("Supported");
        supportedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkSupported();
            }
        });

        JButton fileOpButton = new JButton("File Op");
        supportedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileOps();
            }
        });

        JPanel panel = new JPanel();
        panel.add(goButton);
        panel.add(pauseButton);
        panel.add(errorButton);
        JPanel panel2 = new JPanel();
        panel2.add(supportedButton);
        panel2.add(fileOpButton);

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
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    private void pauseProgress() {
        taskbar.setWindowProgressValue(frame, 50);
        taskbar.setWindowProgressState(frame, Taskbar.State.PAUSED);
    }

    private void showError() {
        taskbar.setWindowProgressValue(frame, 100);
        taskbar.setWindowProgressState(frame, Taskbar.State.ERROR);
    }

    private void checkSupported() {
        boolean iconBadgeText=taskbar.isSupported(Taskbar.Feature.ICON_BADGE_TEXT);
        boolean iconBadgeNumber=taskbar.isSupported(Taskbar.Feature.ICON_BADGE_NUMBER);
        boolean iconBadgeImageWindow=taskbar.isSupported(Taskbar.Feature.ICON_BADGE_IMAGE_WINDOW);
        boolean iconImage=taskbar.isSupported(Taskbar.Feature.ICON_IMAGE);
        boolean menu=taskbar.isSupported(Taskbar.Feature.MENU);
        boolean progressStateWindow=taskbar.isSupported(Taskbar.Feature.PROGRESS_STATE_WINDOW);
        boolean progressValue=taskbar.isSupported(Taskbar.Feature.PROGRESS_VALUE);
        boolean progressValueWindow=taskbar.isSupported(Taskbar.Feature.PROGRESS_VALUE_WINDOW);
        boolean userAttention=taskbar.isSupported(Taskbar.Feature.USER_ATTENTION);
        boolean userAttentionWindow=taskbar.isSupported(Taskbar.Feature.USER_ATTENTION_WINDOW);

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

    private void fileOps() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("text.tmp"));

        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            // Perform operations with the selected file
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        }
    }
}
