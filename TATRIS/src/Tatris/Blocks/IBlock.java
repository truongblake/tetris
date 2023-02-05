package Tatris.Blocks;

import Tatris.GamePanel;

import java.awt.*;

public class IBlock extends Blocks {
    public IBlock(){
        for(int i = 0; i < blocks.length; i++){
            blocks[i] = new Block(Color.cyan);
            blocks[i].setX((i-2) * GamePanel.UNIT_SIZE + GamePanel.SCREEN_WIDTH/2);
            blocks[i].setY(GamePanel.UNIT_SIZE);
        }
    }
}
