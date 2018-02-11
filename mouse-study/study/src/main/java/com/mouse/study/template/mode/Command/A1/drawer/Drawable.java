package com.mouse.study.template.mode.Command.A1.drawer;

import java.awt.Color;                          

public interface Drawable {
    public abstract void init();                
    public abstract void draw(int x, int y);
    public abstract void setColor(Color color); 
}
