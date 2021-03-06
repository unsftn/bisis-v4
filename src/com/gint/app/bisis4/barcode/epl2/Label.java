package com.gint.app.bisis4.barcode.epl2;

import static com.gint.app.bisis4.barcode.epl2.Rotation.R0;

import java.util.ArrayList;
import java.util.List;

public class Label {
  
  private int width;
  private int height;
  private int resolution;
  private int widebar;
  private int narrowbar;
  private int barwidth;
  private String pageCode;
  private int currentY = 20;
  private List<Component> components = new ArrayList<Component>();

  public Label(int width, int height, int resolution,int widebar, int narrowbar, 
      int barwidth, String pageCode) {
    this.width = width;
    this.height = height;
    this.resolution = resolution;
    this.barwidth = barwidth;
    this.widebar = widebar;
    this.narrowbar = narrowbar;
    this.pageCode = pageCode;
  }

  public Label() {
  }
  
  public void appendText(String text,int size) {
    Text t = new Text(20, currentY, R0, size, text,pageCode);
    currentY += 22;
    components.add(t);
  }
  
  public void appendBlankLine() {
    currentY += 22;
  }
  
  public void appendCode128(String code) {
    Code128 code128 = new Code128(20, currentY, R0,widebar, narrowbar, 
        barwidth, code);
    currentY += 82;
    components.add(code128);
  }
  
  public String getCommands() {
    StringBuffer buff = new StringBuffer();
    buff.append('q');
    buff.append(width);
    buff.append("\n\nN\n");
    for (Component c : components) {
      buff.append(c.getCommand());
      buff.append('\n');
    }
    buff.append("P1\n");
    return buff.toString();
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public int getResolution() {
    return resolution;
  }

  public void setResolution(int resolution) {
    this.resolution = resolution;
  }

  public List<Component> getComponents() {
    return components;
  }

  public void setComponents(List<Component> components) {
    this.components = components;
  }

}
