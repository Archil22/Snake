import acm.graphics.GCanvas;
import acm.graphics.GCompound;
import acm.graphics.GRect;
import acm.util.RandomGenerator;

import java.awt.*;
import java.util.ArrayList;

public class SceneHandler extends GCompound implements SnakeConstants{
    protected ArrayList<ArrayList<GRect>> cells = new ArrayList<>();
    private final ArrayList<Dim> Barriers = new ArrayList<>();
    private final ArrayList<Dim> whiteRects = new ArrayList<>();
    private final static RandomGenerator rg = RandomGenerator.getInstance();
    protected void drawCells(int width,int height,boolean dotted){
        for(int y = 0; y< height; y++){
            cells.add(new ArrayList<>());
            for(int x = 0; x< width; x++){
                GRect rect = new GRect(initialBodySize,initialBodySize);
                rect.setFilled(true);
                if(y==0 || x==0 || y == height -1 || x== width -1){
                    if(dotted){
                        rect.setFillColor(Color.red);
                    }else {
                        rect.setColor(Color.red);
                    }
                }else {
                    if(dotted){
                        rect.setFillColor(Color.WHITE);
                    }else{
                        rect.setColor(Color.WHITE);
                    }
                }
                add(rect,x*initialBodySize,y*initialBodySize);
                cells.get(y).add(rect);
            }
        }
    }

    protected void scanScene(){
        for(int y=0;y<Height();y++){
            for(int x=0;x<Width();x++){
                GRect rect = cells.get(y).get(x);
                if(rect.getColor().equals(Color.red)){
                    Barriers.add(new Dim(x,y));
                }else {
                    whiteRects.add(new Dim(x,y));
                }
            }
        }
    }

    int Height(){
        return cells.size();
    }

    int Width(){
        return cells.get(0).size();
    }

    void setColor(Dim d,Color c,boolean dotted){
        if(dotted){
            cells.get(d.getY()).get(d.getX()).setFillColor(c);
        }else {
            cells.get(d.getY()).get(d.getX()).setColor(c);
        }
    }

    public boolean containsBarrier(Dim d){
        return Barriers.contains(d);
    }

    public Dim generateMeal() {
        return whiteRects.get(rg.nextInt(whiteRects.size()));
    }
}