package Tatris.Blocks;

import Tatris.GamePanel;

import java.awt.*;

public class SBlock extends Blocks{
    public SBlock(){
        for(int i = 0; i < blocks.length; i++){
            blocks[i] = new Block(Color.GREEN);
            if(i <= 1){
                blocks[i].setX(GamePanel.SCREEN_WIDTH/2 - GamePanel.UNIT_SIZE);
                blocks[i].setY((i+1) * GamePanel.UNIT_SIZE);
            }else{
                blocks[i].setX(GamePanel.SCREEN_WIDTH/2);
                blocks[i].setY(i * GamePanel.UNIT_SIZE);
            }

        }
    }
}
