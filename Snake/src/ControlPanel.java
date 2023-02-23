import acm.graphics.GRect;
import acm.program.GraphicsProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class ControlPanel extends GraphicsProgram implements SnakeConstants {

    private final JButton createNew = new JButton("new Terrain");
    private final JTextField width = new JTextField(10);
    private final JTextField height = new JTextField(10);
    private final JButton saveAsJSON = new JButton("save");

    private int widthVal;
    private int heightVal;
    private ArrayList<Dim> barriers;

    public void init(){
        add(createNew,SOUTH);
        add(width,SOUTH);
        add(height,SOUTH);
        add(saveAsJSON,SOUTH);
        addActionListeners();
    }

    public void run(){

    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==createNew && !width.getText().equals("") && !height.getText().equals("")){
            barriers = new ArrayList<>();
            int widthInt = Integer.parseInt(width.getText());
            int heightInt = Integer.parseInt(height.getText());
            widthVal=widthInt;
            heightVal=heightInt;
            removeAll();
            drawNewTerrain(widthInt,heightInt);
            width.setText("");
            height.setText("");
        }else if(e.getSource()==saveAsJSON && barriers!=null){
            saveTerrain();
        }
    }

    private void saveTerrain() {
        try{
            JSONObject obj = new JSONObject();
            int [][] Barr = fillArr(barriers);
            obj.put("width",widthVal);
            obj.put("height",heightVal);
            obj.put("barriers", new JSONParser().parse(Arrays.deepToString(Barr)));
            String json = obj.toJSONString();
            saveFile(widthVal+"_"+heightVal+".json",json);
        }catch (Exception e){
            println(e.getMessage());
        }

    }

    private void saveFile(String fileName,String json) {
        try {
            FileWriter myWriter = new FileWriter("C:/Users/Archil/Desktop/SnakeTerrains/"+fileName);
            myWriter.write(json);
            myWriter.close();
        }catch (Exception e){
            println(e.getMessage());
        }
    }

    private int[][] fillArr(ArrayList<Dim> dimArrayList) {
        int [][] arr = new int[dimArrayList.size()][2];
        for(int i=0;i<dimArrayList.size();i++){
            arr[i][0] = dimArrayList.get(i).getX();
            arr[i][1] = dimArrayList.get(i).getY();
        }
        return arr;
    }

    private void drawNewTerrain(int width,int height){
        for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
                GRect rect = new GRect(initialBodySize,initialBodySize);
                rect.setFilled(true);
                if(y==0 || x==0 || y == height-1 || x==width-1){
                    rect.setFillColor(Color.red);
                }else {
                    rect.setFillColor(Color.white);
                    rect.addMouseListener(this);
                }
                add(rect,x*initialBodySize,y*initialBodySize);
            }
        }
    }

    public void mouseClicked(MouseEvent e){
        GRect rect = (GRect) e.getSource();
        if(rect!=null){
            Dim d = new Dim((int) rect.getX()/initialBodySize,(int) rect.getX()/initialBodySize);
            if(rect.getFillColor().equals(Color.white)){
                barriers.add(d);
                rect.setFillColor(Color.red);
            }else {
                barriers.remove(d);
                rect.setFillColor(Color.white);
            }
        }
    }

    private File openFile(){
        JFileChooser fileChooser = new JFileChooser("C:/Users/Archil/Desktop/SnakeTerrains");
        int r = fileChooser.showOpenDialog(this);
        if(r==JFileChooser.APPROVE_OPTION){
            return fileChooser.getSelectedFile();
        }
        return null;
    }

}
