package Tatris;

import Tatris.Blocks.*;

import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;

public class GamePanel extends JPanel implements ActionListener {

    //Panel Size and Game Unit Size
    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 600;
    public static final int UNIT_SIZE = 25;

    public static final int TATRIS_LEFT_BOUND = (SCREEN_WIDTH/2) - (5 * UNIT_SIZE);
    public static final int TATRIS_RIGHT_BOUND = (SCREEN_WIDTH/2) + (4 * UNIT_SIZE);
    public static final int TATRIS_LOWER_BOUND = (SCREEN_HEIGHT) - (2 * UNIT_SIZE);
    public static final int TATRIS_UPPER_BOUND = UNIT_SIZE;
    public static final int TATRIS_WIDTH = TATRIS_RIGHT_BOUND + UNIT_SIZE - TATRIS_LEFT_BOUND;
    public static final int TATRIS_HEIGHT = TATRIS_LOWER_BOUND - TATRIS_UPPER_BOUND;

    //Game Speed
    static final int DELAY = 10;

    //GameOver State
    boolean AreYouAGamer = true;

    //timer (GameSpeed)
    Timer timer;
    Timer timer2;

    //score
    static int lineCleared = 0;

    //User Block
    Blocks userBlock = newUserBlock();

    //Block Speed
    final int BLOCK_SPEED = 500;

    //Held Block
    Blocks userHeldBlock = newUserBlock();
    private int heldNumber = 0;

    //All Blocks on Screen
    ArrayList<Block> allBlocks = new ArrayList<>();


    GamePanel(){

        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT)); //SetPreferredSize vs SetSize?
        this.setBackground(Color.BLACK);
        this.setFocusable(true); //Unsure about what this does.
        this.addKeyListener(new MyKeyAdapter());
        startGame();

    }

    public void startGame(){

        AreYouAGamer = true;

        timer = new Timer(DELAY, this);
        timer.start();

        timer2 = new Timer(BLOCK_SPEED, new BlockMovement());
        timer2.start();

    }

    public void gameOver(Graphics g){

        AreYouAGamer = false;

        g.setColor(new Color(253,0,0));

        g.setFont(new Font("TimesRoman", Font.PLAIN, 47));

        g.drawString("Game Over", TATRIS_LEFT_BOUND,125 );


    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){

        //draw Background
        g.setColor(Color.WHITE);
        g.fillRect(TATRIS_LEFT_BOUND,TATRIS_UPPER_BOUND,TATRIS_WIDTH,TATRIS_HEIGHT);

        //draw score board
        g.setColor(Color.RED);
        g.drawString("Lines Cleared: " + lineCleared, 2 * UNIT_SIZE, UNIT_SIZE);


        //draw all Blocks
        if(allBlocks != null){
            for (Block allBlock : allBlocks) {
                g.setColor(allBlock.getColor());
                g.fillRect(allBlock.getX(), allBlock.getY(), UNIT_SIZE, UNIT_SIZE);
            }
        }
        //draw userBlocks
        for(int i = 0; i < userBlock.getBlocks().length; i++){
            g.setColor(userBlock.getBlocks()[i].getColor());
            g.fillRect(userBlock.getBlocks()[i].getX(),userBlock.getBlocks()[i].getY(),UNIT_SIZE,UNIT_SIZE);
        }
        //draw heldBlocks
        for(int i = 0; i < userHeldBlock.getBlocks().length; i++){
            g.setColor(userHeldBlock.getBlocks()[i].getColor());
            g.fillRect(userHeldBlock.getBlocks()[i].getX() + 200,userHeldBlock.getBlocks()[i].getY() + 150,UNIT_SIZE,UNIT_SIZE);
        }

        //draw vertical grid
        for (int i = 0; i < 11; i++) {
            g.setColor(Color.GRAY);
            g.drawLine((SCREEN_WIDTH / 2) - ((5 - i) * UNIT_SIZE), UNIT_SIZE, (SCREEN_WIDTH / 2) - ((5 - i) * UNIT_SIZE), SCREEN_HEIGHT - (2 * UNIT_SIZE));
        }

        //draw horizontal grid
        for (int i = 0; i < 22; i++) {
            g.setColor(Color.GRAY);
            g.drawLine((SCREEN_WIDTH / 2) - (5 * UNIT_SIZE), UNIT_SIZE + (i * UNIT_SIZE), (SCREEN_WIDTH / 2) + (5 * UNIT_SIZE), UNIT_SIZE + (i * UNIT_SIZE));
        }


        if(!AreYouAGamer){
            gameOver(g);
        }



    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(AreYouAGamer){
            if(checkCollision()){
                allBlocks.addAll(Arrays.asList(userBlock.getBlocks()));
                userBlock = newUserBlock();
                if(heldNumber == 1){
                    heldNumber--;
                }
            }
            sortBlocks();
            checkClearLines();
            checkGameState();
        }

        repaint();

    }
    public void checkGameState(){
        for(int i = 0; i < userBlock.getBlocks().length; i++){
            for(Block blocks: allBlocks){
                if(userBlock.getBlocks()[i].getY() == blocks.getY()
                        && userBlock.getBlocks()[i].getX() == blocks.getX()
                        && userBlock.getBlocks()[i].getY() > TATRIS_UPPER_BOUND){
                    AreYouAGamer = false;
                }
            }
        }
    }

    //Key Events
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if(AreYouAGamer) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        moveLeft();
                        break;
                    case KeyEvent.VK_RIGHT:
                        moveRight();
                        break;
                    case KeyEvent.VK_UP:
                        //rotate pieces
                        rotate();
                        break;
                    case KeyEvent.VK_DOWN:
                        moveDown();
                        break;
                    case KeyEvent.VK_SPACE:
                        placeDown();
                        break;
                    case KeyEvent.VK_SHIFT:
                        hold();
                        break;
                }
            }
        }
    }
    //Block Speed
    public class BlockMovement implements ActionListener {
        //move tatris pieces down one unit over time.
        @Override
        public void actionPerformed(ActionEvent e) {
            if(AreYouAGamer) {
                if (!checkCollision()) {
                    for (Block blocks : userBlock.getBlocks()) {
                        blocks.setY(blocks.getY() + UNIT_SIZE);
                    }
                }
            }
        }
    }
    public boolean checkCollision(){

        //can be optimized
        for(Block blocks: userBlock.getBlocks()){
            if(blocks.getY() + UNIT_SIZE >= TATRIS_LOWER_BOUND){
                return true;
            }
            for (Block allBlock : allBlocks) {
                if (allBlock.getY() == blocks.getY() + UNIT_SIZE && allBlock.getX() == blocks.getX()) {
                    return true;
                }
            }
        }
        return false;
    }
    //Returns a random Block to user
    public Blocks newUserBlock(){

        int randomBlock = new Random().nextInt(7);

        return switch (randomBlock) {
            case (0) -> new JBlock();
            case (1) -> new SBlock();
            case (2) -> new LBlock();
            case (3) -> new IBlock();
            case (4) -> new TBlock();
            case (5) -> new OBlock();
            case (6) -> new ZBlock();
            default -> newUserBlock();
        };

    }
    //Key Movements
    public void moveLeft(){
        boolean movable = true;
        for(int i = 0; i < userBlock.getBlocks().length; i++){
            if(userBlock.getBlocks()[i].getX() - UNIT_SIZE < TATRIS_LEFT_BOUND){
                movable = false;
                break;
            }
            for (Block allBlock : allBlocks) {
                if (allBlock.getY() == userBlock.getBlocks()[i].getY() && allBlock.getX() == userBlock.getBlocks()[i].getX() - UNIT_SIZE) {
                    movable = false;
                }
            }
        }
        if(movable){
            for(int i = 0; i < userBlock.getBlocks().length; i++){
                userBlock.getBlocks()[i].setX(userBlock.getBlocks()[i].getX() - UNIT_SIZE);
            }
        }
    }
    public void moveRight(){
        boolean movable = true;
        for(int i = 0; i < userBlock.getBlocks().length; i++){
            if(userBlock.getBlocks()[i].getX() + UNIT_SIZE > TATRIS_RIGHT_BOUND){
                movable = false;
                break;
            }
            for (Block allBlock : allBlocks) {
                if (allBlock.getY() == userBlock.getBlocks()[i].getY() && allBlock.getX() == userBlock.getBlocks()[i].getX() + UNIT_SIZE) {
                    movable = false;
                }
            }
        }
        if(movable){
            for(int i = 0; i < userBlock.getBlocks().length; i++){
                userBlock.getBlocks()[i].setX(userBlock.getBlocks()[i].getX() + UNIT_SIZE);
            }
        }
    }
    public void moveDown(){
        boolean movable = true;
        for(int i = 0; i < userBlock.getBlocks().length; i++){
            for (Block allBlock : allBlocks) {
                if (allBlock.getY() == userBlock.getBlocks()[i].getY() + UNIT_SIZE && allBlock.getX() == userBlock.getBlocks()[i].getX()) {
                    movable = false;
                }
            }
        }
        if(movable) {
            for (int i = 0; i < userBlock.getBlocks().length; i++) {
                userBlock.getBlocks()[i].setY(userBlock.getBlocks()[i].getY() + UNIT_SIZE);
            }
        }
    }
    public void rotate(){
        //take current location of block
        int x = userBlock.getBlocks()[1].getX();
        int y = userBlock.getBlocks()[1].getY();

        for(int i = 0; i < userBlock.getBlocks().length; i++){

            //set blocks to origin
            userBlock.getBlocks()[i].setX(userBlock.getBlocks()[i].getX() - x);
            userBlock.getBlocks()[i].setY(userBlock.getBlocks()[i].getY() - y);

            //rotate blocks
            int reversedX = userBlock.getBlocks()[i].getX();
            int reversedY = userBlock.getBlocks()[i].getY();
            userBlock.getBlocks()[i].setX(reversedY);
            userBlock.getBlocks()[i].setY(-reversedX);

            //set rotated block back to current location of block
            userBlock.getBlocks()[i].setX(userBlock.getBlocks()[i].getX() + x);
            userBlock.getBlocks()[i].setY(userBlock.getBlocks()[i].getY() + y);

        }

    } //Fix Rotating block colliding with other blocks
    public void hold(){
        if(heldNumber == 0) {

            int x = SCREEN_WIDTH/2 - userBlock.getBlocks()[0].getX();
            int y = UNIT_SIZE - userBlock.getBlocks()[0].getY();

            //reset userHeldBlock location
            for(int i = 0; i < userBlock.getBlocks().length; i++){
                userBlock.getBlocks()[i].setY(userBlock.getBlocks()[i].getY() + y);
                userBlock.getBlocks()[i].setX(userBlock.getBlocks()[i].getX() + x);
            }
            //switch blocks
            Blocks temp = userBlock;
            userBlock = userHeldBlock;
            userHeldBlock = temp;
            heldNumber++;
        }
    }
    public void placeDown(){
        boolean movable = true;
        while(movable){
            for(Block blocks: userBlock.getBlocks()){
                blocks.setY(blocks.getY()+UNIT_SIZE);
            }
            if(checkCollision()){
                movable = false;
            }
        }
    }
    //Line Clear Logic
    public void sortBlocks(){
        Collections.sort(allBlocks);
    }
    public void checkClearLines(){

        if(!allBlocks.isEmpty()) {

            Map<Integer,Integer> map = new HashMap<>();
            //create our hashmap with all the y coordinates with their corresponding frequency
            for(int i = 21; i > 0; i--){
                map.put((UNIT_SIZE*i), 0);
            }

            //add frequency to hashmap
            for(int i = 0; i < allBlocks.size(); i++){
                map.put(allBlocks.get(i).getY(), map.get(allBlocks.get(i).getY()) + 1);
                if(map.get(allBlocks.get(i).getY()) == 10){
                    map.put(allBlocks.get(i).getY(), 0);
                    lineCleared++;

                    //increment all blocks down
                    for(int j = i; j < allBlocks.size(); j++){
                        allBlocks.get(j).setY(allBlocks.get(j).getY() + UNIT_SIZE);
                    }

                    //remove line
                    for(int j = 0; j < 10; j++){
                        allBlocks.remove(i--);
                    }
                    if(lineCleared%10 == 0){
                        timer2.setDelay(timer2.getDelay() - 50);
                    }
                }
            }
        }
    }
}
