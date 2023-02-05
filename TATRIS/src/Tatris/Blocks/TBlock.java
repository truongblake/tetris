package Tatris.Blocks;

import Tatris.GamePanel;

import java.awt.*;

public class TBlock extends Blocks{
    public TBlock(){
        for(int i = 0; i < blocks.length; i++){
            blocks[i] = new Block(Color.magenta);
            if(i == 3){
                blocks[i].setX(GamePanel.SCREEN_WIDTH/2);
                blocks[i].setY(GamePanel.UNIT_SIZE);
            }else{
                blocks[i].setX((GamePanel.SCREEN_WIDTH/2) - (i - 1) * GamePanel.UNIT_SIZE);
                blocks[i].setY(2 * GamePanel.UNIT_SIZE);
            }
        }

    }
}
