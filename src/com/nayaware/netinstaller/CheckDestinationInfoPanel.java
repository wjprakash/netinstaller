// CheckDestinationInfoPanel.java
// Last Modified on Feb 22, 2002 - Winston Prakash

package com.nayaware.netinstaller;

import java.io.*;
import java.awt.*;
import java.awt.event.*;

import com.installshield.util.*;
import com.installshield.wizard.*;
import com.installshield.wizard.awt.*;
import com.installshield.wizard.console.*;
import com.installshield.wizardx.panels.*;
import com.installshield.wizardx.ui.*;
import com.installshield.wizardx.i18n.*;
import com.installshield.wizard.service.system.*;

public class CheckDestinationInfoPanel extends ExtendedWizardPanel {
    
    public CheckDestinationInfoPanel() {
    }
    
    protected void initialize() {
        super.initialize();
        String title = resolveString("$L(com.nayaware.netinstaller.InstallerResources,INFO_UPG_PANEL)");
        setTitle(title);
        
        Panel p = new Panel();
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        p.setLayout(gridbag);
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 5, 0, 5);
        c.weightx = 1.0;
        c.gridy = 0;
        
        String text = resolveString("$L(com.nayaware.netinstaller.InstallerResources,UPG_UNINSTALL_ERROR)")+"\n";
        MultilineLabel textDisplay = new MultilineLabel(text);
        gridbag.setConstraints(textDisplay, c);
        p.add(textDisplay);
        getContentPane().add(p, BorderLayout.NORTH);
    }

    public boolean queryEnter(WizardBeanEvent wizardpanelevent1) {
        String uninstalled = (String) System.getProperties().get("uninstalled");
        if (uninstalled != null ) {
            if (uninstalled.equals("yes")) return false;
        } else
            return false;
                
        return true;
    }
}

