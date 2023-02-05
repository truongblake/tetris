package Tatris.Blocks;

import java.awt.*;

public class Block implements Comparable<Block>{

    private Color color;
    private int x = 0;
    private int y = 0;

    public Block(){
        this.color = Color.BLACK;
    }

    public Block(Color color){
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    @Override
    public int compareTo(Block o) {
        return Integer.compare(o.y, this.y);
    }
}
