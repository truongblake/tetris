package Tatris.Blocks;

import Tatris.GamePanel;

import java.awt.*;

public class OBlock extends Blocks{
    public OBlock(){
        for(int i = 0; i < blocks.length; i++){

            blocks[i] = new Block(Color.yellow);

            if(i > 1){
                blocks[i].setX((GamePanel.SCREEN_WIDTH/2) - ((i-2) * GamePanel.UNIT_SIZE));
                blocks[i].setY(2 * GamePanel.UNIT_SIZE);
            }else{
                blocks[i].setX((GamePanel.SCREEN_WIDTH/2) - (i * GamePanel.UNIT_SIZE));
                blocks[i].setY(GamePanel.UNIT_SIZE);
            }
        }

    }
}
