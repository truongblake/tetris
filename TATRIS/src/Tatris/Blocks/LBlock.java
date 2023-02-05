package Tatris.Blocks;

import Tatris.GamePanel;

import java.awt.*;

public class LBlock extends Blocks{
    public LBlock(){
        for(int i = 0; i < blocks.length; i++){
            blocks[i] = new Block(Color.blue);
            blocks[i].setX(GamePanel.SCREEN_WIDTH/2);
            if(i == 3){
                blocks[i].setY((i)*GamePanel.UNIT_SIZE);
                blocks[i].setX((GamePanel.SCREEN_WIDTH/2)+ GamePanel.UNIT_SIZE);
            }else{
                blocks[i].setY((i+1)*GamePanel.UNIT_SIZE);
            }
        }

    }
}
