package org.example;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;


import static org.example.Main.checkBCPrice;

public class GUI {
    private static JLabel curp;
    private static String previousPrice;
    private static int counter;


    public GUI() {
        final JFrame frame = new JFrame();
        JButton startButton = getjButton(frame);
        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(new Color(200, 161, 224));
        exitButton.setForeground(new Color(103, 65, 136));
        exitButton.setFont(new Font("Daydream", Font.BOLD, 20));
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        JLabel programTitle = new JLabel("CRYPTO NUKE", 0);
        programTitle.setFont(new Font("daydream",Font.BOLD,40));
        programTitle.setForeground(new Color(103, 65, 136));

        JLabel author = new JLabel("<html>By  Douglas  Serrano<br>https://github.com/chi-qui<html>", 0);
        author.setFont(new Font("minecraft", ~Font.BOLD, 20));
        author.setForeground(new Color(50, 170, 225));

        JLabel programAbstract = new JLabel("<html>Crypto-Nuke is a program that monitors the price of bitcoin.<br>When the price increases a cheering sound will play,<br>and when it decreases a deafening nuke sound will play.</html>", 0);
        programAbstract.setFont(new Font("minecraft", ~Font.BOLD, 20));
        programAbstract.setForeground(new Color(103, 65, 136));


        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(programTitle);
        panel.add(author);
        panel.add(programAbstract);
        panel.add(startButton);
        panel.add(exitButton);
        panel.setBackground(new Color(247, 239, 229));
        Image icon = Toolkit.getDefaultToolkit().getImage("crypto-nuke.png");
        frame.setIconImage(icon);
        frame.add(panel, "Center");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("CRYPTO-NUKE");
        frame.pack();
        frame.setSize(700, 600);
        frame.setVisible(true);
    }

    private static JButton getjButton(JFrame frame) {
        JButton startButton = new JButton("Start");
        startButton.setBackground(new Color(200, 161, 224));
        startButton.setForeground(new Color(103, 65, 136));
        startButton.setFont(new Font("Daydream", Font.BOLD, 20));
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                this.startCN(frame);
            }

            private void startCN(JFrame frame) {
                String price = checkBCPrice();
                String sp = price;

                // Labels for new screen
                JLabel startingPrice = new JLabel("Starting Price:", 0);
                startingPrice.setFont(new Font("Daydream", Font.BOLD, 20));
                startingPrice.setForeground(new Color(103, 65, 136));

                JLabel startPrice = new JLabel("$" + sp, SwingConstants.CENTER);
                startPrice.setFont(new Font("minecraft", ~Font.BOLD, 30));
                startPrice.setForeground(new Color(200, 161, 224));

                JLabel currPrice = new JLabel("Current Price:", SwingConstants.CENTER);
                currPrice.setFont(new Font("Daydream", Font.BOLD, 20));
                currPrice.setForeground(new Color(103, 65, 136));

                curp = new JLabel("$" + sp, SwingConstants.CENTER);
                curp.setFont(new Font("minecraft", ~Font.BOLD, 30));
                curp.setForeground(new Color(225, 225, 225));

                //Reset frame contents
                frame.getContentPane().removeAll();
                frame.repaint();

                //Add panels here
                JPanel startPanel = new JPanel();
                startPanel.add(startingPrice);
                startPanel.add(startPrice);
                startPanel.add(currPrice);
                startPanel.add(curp);

                startPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
                startPanel.setLayout(new GridLayout(0, 1));
                startPanel.setBackground(new Color(247, 239, 229));

                //New frame
                frame.add(startPanel, "Center");
                frame.pack();
                frame.setSize(500, 320);
                frame.setVisible(true);

                // Update the current price every second
                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        updateLabel();
                    }
                });
                timer.start();
            }

            private void updateLabel() {
                counter++;
                System.out.println("Checking price... for " + counter + " second/s...");
                String currentPrice = checkBCPrice();

                if (previousPrice != null) {
                    double current = Double.parseDouble(currentPrice);
                    double previous = Double.parseDouble(previousPrice);

                    if (current > previous) {
                        curp.setForeground(new Color(0, 225, 0)); // Green for increase
                        playSound("fnaf_yay_f.wav");
                        System.out.println("sweet");
                    } else if (current < previous) {
                        curp.setForeground(new Color(255, 0, 0)); // Red for decrease
                        System.out.println("tragic");
                        playSound("tragic_f.wav");
                    }
                }
                curp.setText("$" + currentPrice);
                previousPrice = currentPrice;
            }
        });
        return startButton;
    }

    //Used to play sound
    private static void playSound(String soundFile) {
        try {
            InputStream audioStream = GUI.class.getResourceAsStream("/" + soundFile);
            if (audioStream == null) {
                System.err.println("Resource not found: " + soundFile);
                throw new RuntimeException("Resource not found: " + soundFile);
            }

            System.out.println("Successfully loaded resource: " + soundFile);

            byte[] audioBytes = audioStream.readAllBytes();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(audioBytes);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(byteArrayInputStream);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();

            System.out.println("Playing sound: " + soundFile);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error playing sound: " + soundFile);
            e.printStackTrace();
        }
    }

}
