package com.ds.todo.utils;

import java.awt.*;

public class NotificationsSender {
    public static void send(String caption, String message, TrayIcon.MessageType messageType){
        try {
            SystemTray tray = SystemTray.getSystemTray();

            TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().createImage("icon.png"));
            trayIcon.setImageAutoSize(true);
            tray.add(trayIcon);

            trayIcon.displayMessage(caption, message, messageType);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
