// CreateIcon.java
// Author  Winston Prakash

package com.nayaware.netinstaller;

import java.awt.*;
import javax.swing.*;

class CreateIcon implements Icon {
    
    public static final int TickMark = 1;
    private int width = 9;
    private int height = 9;
    
    private int[] xPoints = new int[6];
    private int[] yPoints = new int[6];
    
    public CreateIcon(int type) {  
        // type - reserved for creating different types of vector Icon
        xPoints[0] = 0;
        yPoints[0] = height*3/4;
        xPoints[1] = width/2;
        yPoints[1] = height;
        xPoints[2] = width;
        yPoints[2] = 0;
        xPoints[3] = width-1;
        yPoints[3] = 0;     
        xPoints[4] = width/2-1;
        yPoints[4] = height+1;       
        xPoints[5] = 1;
        yPoints[5] = height*3/4-1;        
    }
    
    public int getIconHeight() {
        return height;
    }
    
    public int getIconWidth() {
        return width;
    }
    
    public void paintIcon(Component c, Graphics g, int x, int y) {
        int length = xPoints.length;
        int adjustedXPoints[] = new int[length];
        int adjustedYPoints[] = new int[length];
        
        for (int i = 0; i < length; i++) {
            adjustedXPoints[i] = xPoints[i] + x;
            adjustedYPoints[i] = yPoints[i] + y;
        }
        
        if (c.isEnabled()) {
            g.setColor(Color.black);
        } else {
            g.setColor(Color.gray);
        }
        
        for (int i = 0; i < length-1; i++) {
            g.drawLine(adjustedXPoints[i],adjustedYPoints[i],adjustedXPoints[i+1],adjustedYPoints[i+1]);
        }
    }
}
