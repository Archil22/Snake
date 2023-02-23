import acm.graphics.GCompound;
import acm.program.GraphicsProgram;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

public class Main extends GraphicsProgram implements SnakeConstants, ComponentListener {
    private final ArrayList<GCompound> SCENES = new ArrayList<>();
    private int currentScene;
    private Snake snake;
    private Dim Meal;
    private boolean start = false;

    public void init(){
        addKeyListeners();
        addComponentListener(this);
    }
    public void initScenes(int index){
        try{
            remove(SCENES.get(index-1));
        }catch(Exception e){
            println(e.getMessage());
        }
        currentScene = index;
        add(SCENES.get(currentScene));
        snake = new Snake(((SceneHandler)SCENES.get(currentScene)));
        Iterator<Dim> i = snake.getBodyParts();
        while (i.hasNext()){
            ((SceneHandler)SCENES.get(currentScene)).setColor(i.next(), Color.green,false);
        }
        Meal = ((SceneHandler)SCENES.get(currentScene)).generateMeal();
        ((SceneHandler)SCENES.get(currentScene)).setColor(Meal,Color.cyan,false);
        SCENES.get(currentScene).addMouseListener(this);
    }

    public void run(){
        initializeScenes();
        for(int i=0;i<SCENES.size();i++){
            initScenes(i);
            while (!start){
                pause(1);
            }
            int r;
            while ((r=snake.Collided(Meal))!=0&&snake.getScore()!=((Scene)SCENES.get(currentScene)).getMaxScore()){
                if(r==1){
                    snake.eat();
                    Meal = ((SceneHandler)SCENES.get(currentScene)).generateMeal();
                    ((SceneHandler)SCENES.get(currentScene)).setColor(Meal,Color.cyan,false);
                }
                snake.move(Meal);
                pause(100);
            }
            ((SceneHandler)SCENES.get(currentScene)).setColor(snake.getHead(),Color.PINK,false);
            println("your Score:"+" "+snake.getScore());
            start=false;
        }
    }

    private void initializeScenes(){
        SCENES.add(new Scene(getWidth()/initialBodySize,getHeight()/initialBodySize,50));
        SCENES.add(new Scene("25_25.json",50));
        SCENES.add(new Scene("10_10.json",20));
    }

    public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_UP){
            snake.changeDir("UP");
        } else if (e.getKeyCode()==KeyEvent.VK_DOWN) {
            snake.changeDir("DOWN");
        }else if(e.getKeyCode()==KeyEvent.VK_LEFT){
            snake.changeDir("LEFT");
        } else if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
            snake.changeDir("RIGHT");
        }
    }

    public void mouseClicked(MouseEvent e){
        start=true;
    }

    public void componentResized(ComponentEvent e){}
    public void componentMoved(ComponentEvent e){}
    public void componentShown(ComponentEvent e){}
    public void componentHidden(ComponentEvent e){}
}