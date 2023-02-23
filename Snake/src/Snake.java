import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

enum Dir{RIGHT,LEFT,UP,DOWN}
public class Snake {
    private final ArrayList<Dim> bodyParts = new ArrayList<>();
    private final SceneHandler cells;
    private int score=0;
    private Dir d;
    private boolean justAdded = false;
    Snake(SceneHandler cells){
        bodyParts.add(new Dim(1,1));
        d=Dir.RIGHT;
        this.cells=cells;
    }

    Iterator<Dim> getBodyParts(){
        return bodyParts.iterator();
    }

    Dim getHead(){
        return bodyParts.get(0);
    }

    public void eat(){
        bodyParts.add(bodyParts.size()-1,new Dim(bodyParts.get(bodyParts.size()-1).getX(),bodyParts.get(bodyParts.size()-1).getY()));
        score++;
        justAdded=true;
    }

    public int getScore(){
        return score;
    }

    public void move(Dim meal) {
        if(!justAdded&&!bodyParts.get(bodyParts.size()-1).equals(meal)){
            cells.setColor(bodyParts.get(bodyParts.size()-1),Color.WHITE,false);
        }else {
            justAdded=false;
        }
        bodyParts.add(0,bodyParts.get(bodyParts.size()-1));
        //bodyParts.add(0,new Dim(bodyParts.get(bodyParts.size()-1).getX(),bodyParts.get(bodyParts.size()-1).getY()));
        switch (d){
            case LEFT: moveLeft();break;
            case RIGHT: moveRight();break;
            case UP: moveUp();break;
            case DOWN: moveDown();break;
        }
        bodyParts.remove(bodyParts.size()-1);
        cells.setColor(bodyParts.get(0),Color.GREEN,false);
    }


    public int Collided(Dim Meal) {
        Dim head = bodyParts.get(0);
        bodyParts.remove(head);
        if(bodyParts.contains(head)){
            bodyParts.add(0,head);
            return 0;
        }
        bodyParts.add(0,head);
        if(cells.containsBarrier(head)){
            return 0;
        } else if (head.equals(Meal)) {
            return 1;
        }return 2;
    }

    private void moveDown() {
        bodyParts.get(0).setDim(bodyParts.get(1).getX(),bodyParts.get(1).getY()+1);
    }

    private void moveUp() {
        bodyParts.get(0).setDim(bodyParts.get(1).getX(),bodyParts.get(1).getY()-1);
    }

    private void moveRight() {
        bodyParts.get(0).setDim(bodyParts.get(1).getX()+1,bodyParts.get(1).getY());
    }

    private void moveLeft() {
        bodyParts.get(0).setDim(bodyParts.get(1).getX()-1,bodyParts.get(1).getY());
    }

    public void changeDir(String up) {
        switch (up){
            case "UP":
                if((bodyParts.size()>=2 && bodyParts.get(0).getY()-1!=bodyParts.get(1).getY())||bodyParts.size()==1){
                    d=Dir.UP;
                }
                break;
            case "DOWN":
                if((bodyParts.size()>=2 && bodyParts.get(0).getY()+1!=bodyParts.get(1).getY())||bodyParts.size()==1){
                    d=Dir.DOWN;
                }
                break;
            case "LEFT":
                if((bodyParts.size()>=2 && bodyParts.get(0).getX()-1!=bodyParts.get(1).getX())||bodyParts.size()==1){
                    d=Dir.LEFT;
                }
                break;
            case "RIGHT":
                if((bodyParts.size()>=2 && bodyParts.get(0).getX()+1!=bodyParts.get(1).getX())||bodyParts.size()==1){
                    d=Dir.RIGHT;
                }
                break;
        }
    }
}
