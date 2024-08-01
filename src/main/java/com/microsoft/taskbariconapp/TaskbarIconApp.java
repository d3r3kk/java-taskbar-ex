package com.example.taskbariconapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaskbarIconApp {

    private static final Taskbar taskbar = Taskbar.getTaskbar();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TaskbarIconApp::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Taskbar Icon App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

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

        JPanel panel = new JPanel();
        panel.add(goButton);
        panel.add(pauseButton);
        panel.add(errorButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void startProgress() {
        if (taskbar.isSupported(Taskbar.Feature.PROGRESS_VALUE)) {
            new Thread(() -> {
                for (int i = 0; i <= 100; i++) {
                    final int progress = i;
                    SwingUtilities.invokeLater(() -> taskbar.setProgressValue(progress));
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }).start();
        } else {
            System.out.println("Taskbar progress value is not supported.");
        }
    }

    private static void pauseProgress() {
        if (taskbar.isSupported(Taskbar.Feature.PROGRESS_VALUE) && taskbar.isSupported(Taskbar.Feature.PROGRESS_STATE)) {
            taskbar.setProgressValue(50);
            taskbar.setProgressState(Taskbar.ProgressState.PAUSED);
        } else {
            System.out.println("Taskbar progress state is not supported.");
        }
    }

    private static void showError() {
        if (taskbar.isSupported(Taskbar.Feature.PROGRESS_VALUE) && taskbar.isSupported(Taskbar.Feature.PROGRESS_STATE)) {
            taskbar.setProgressValue(0);
            taskbar.setProgressState(Taskbar.ProgressState.ERROR);
        } else {
            System.out.println("Taskbar progress state is not supported.");
        }
    }
}
