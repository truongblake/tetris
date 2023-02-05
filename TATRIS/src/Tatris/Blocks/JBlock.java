package Tatris.Blocks;

import Tatris.GamePanel;

import java.awt.*;

public class JBlock extends Blocks{
    public JBlock(){
        for(int i = 0; i < blocks.length; i++){
            blocks[i] = new Block(Color.orange);
            blocks[i].setX(GamePanel.SCREEN_WIDTH/2);
            if(i == 3){
                blocks[i].setY((i)*GamePanel.UNIT_SIZE);
                blocks[i].setX((GamePanel.SCREEN_WIDTH/2) - GamePanel.UNIT_SIZE);
            }else{
                blocks[i].setY((i+1)*GamePanel.UNIT_SIZE);
            }
        }
    }
}
