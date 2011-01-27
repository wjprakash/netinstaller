// SystemPropertyCondition.java
// Last Modified on Feb 22, 2002 - Winston Prakash
 
package com.nayaware.netinstaller;
import java.io.*;
import java.util.*;

import com.installshield.util.*;
import com.installshield.product.*;

 
public class SystemPropertyCondition extends ProductBeanCondition {
    private String propertyName = "";
    private String propertyValue = "";
    
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
    
    public String getPropertyName() {
        return propertyName;
    }
    
    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }
    
    public String getPropertyValue() {
        return propertyValue;
    }
    
    protected boolean evaluateTrueCondition() {
        Properties sysProps = System.getProperties();
        String s = (String) sysProps.getProperty(getPropertyName());
        if (System.getProperties().getProperty(getPropertyName()) != null) {
            String value = (String)System.getProperties().getProperty(getPropertyName());
            if (value.equals(getPropertyValue())) {
                return true;
            }
        }
        return false;
    }
    
    public String defaultName() {
        return "System Property Condition";
    }
    
    public String describe() {
        if (getEvaluate() == ProductBeanCondition.MUST_BE_MET) {
            return "System property " + propertyName + " must be equal to " + propertyValue;
        } else {
            return "System property" + propertyName + " must not be equal to " + propertyValue;
        }
    }
    
    public boolean isMet() {
        return evaluateTrueCondition();
    }
}
