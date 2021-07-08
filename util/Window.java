package util;

import javax.swing.*;
import java.awt.*;
public class Window {
    private final JFrame frame;

    /**
     * 窗口类
     * @param title 窗口标题
     * @param size 窗口大小
     */
    public Window(String title, Dimension size){
        frame = new JFrame(title);
        frame.setSize(size);
    }
    public void setBackground(Color color){
        frame.setBackground(color);
    }
    public void setLocation(int width,int height){
        frame.setLocation(new Point(width,height));
    }

    public void show(){
        frame.setVisible(true);
    }
    public void vanish(){
        frame.setVisible(false);
    }

    public void addComp(Component comp){
        frame.add(comp);
    }

    public JFrame getJFrame() {
        return frame;
    }
}