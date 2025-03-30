package org.cdc.framework.utils;

import java.util.Locale;

public enum BlocklyInnerColor {
    LOGIC,MATH,TEXTS;

    public String toString(int hue){
        return "%{BKY_"+this.name().toUpperCase(Locale.ROOT)+"_"+hue+"}";
    }
}
