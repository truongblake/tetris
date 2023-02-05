package Tatris.Blocks;

import Tatris.GamePanel;

import java.awt.*;

public class ZBlock extends Blocks{
    public ZBlock(){
        for(int i = 0; i < blocks.length; i++){
            blocks[i] = new Block(Color.red);
            if(i > 1){
                blocks[i].setX((GamePanel.SCREEN_WIDTH/2) - GamePanel.UNIT_SIZE);
                blocks[i].setY((i)*GamePanel.UNIT_SIZE);
            }else{
                blocks[i].setX(GamePanel.SCREEN_WIDTH/2);
                blocks[i].setY((i+1)*GamePanel.UNIT_SIZE);
            }
        }
    }
}
