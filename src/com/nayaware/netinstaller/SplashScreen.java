// TwSplashScreen.java
// Author Winston Prakash

package com.nayaware.netinstaller;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class SplashScreen {
    
    public static boolean applicationInvoked = false; 
    static JWindow progressWindow;
    static JLabel progressLabel;
    static String progressText = "";
    static Runnable splashUpdater;
    static ImageIcon splashImage;
    static JPanel splashPanel;
    static Rectangle rect = new Rectangle(10,100,400,20);
    
    public SplashScreen() {
    }
    
    public static void hide() {
	progressWindow.dispose();
    }
    public static void show() {
	splashUpdater = new Runnable() {
		public void run() {
			splashPanel.repaint();
		}
	};
	progressWindow = new JWindow();
	
        URL imgURL = SplashScreen.class.getClassLoader().getResource("com/nayaware/netinstaller/resources/splashImage.gif");
        splashImage = new ImageIcon(imgURL);
	splashPanel = new JPanel() {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(splashImage.getImage(), 0, 0, splashPanel);
			g.setColor(Color.white);
			g.drawString(progressText, 11, 100);
		}
	};
	progressWindow.getContentPane().add(splashPanel, BorderLayout.CENTER);
	progressWindow.pack();
	progressWindow.setSize(splashImage.getIconWidth(),splashImage.getIconHeight());
	centerComponent(progressWindow, null);
	Runnable splashViewer = new Runnable() {
		public void run() {
			progressWindow.show();
		}
	};   
        Thread splashWindowThread = new Thread(splashViewer);
        splashWindowThread.start();        
    }
    public static void setMessage(String v) {
	progressText = v;
	SwingUtilities.invokeLater(splashUpdater);
    }
    
    public static void centerComponent(Component comp, Component parent) {
	Dimension dlgDim = comp.getSize();
	Dimension frameDim = (parent == null) ?
		new Dimension(0,0) :
		parent.getSize();
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Point loc = (parent == null) ?
		new Point(screenSize.width/2, screenSize.height/2) :
		parent.getLocationOnScreen();
	loc.translate((frameDim.width - dlgDim.width)/2,
		(frameDim.height - dlgDim.height)/2
	);
	loc.x = Math.max(0, Math.min(loc.x, screenSize.width  - dlgDim.width));
	loc.y = Math.max(0, Math.min(loc.y, screenSize.height - dlgDim.height));
	comp.setLocation(loc.x, loc.y);
    }    
}
