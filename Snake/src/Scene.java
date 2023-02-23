import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.*;
import java.io.FileReader;

public class Scene extends SceneHandler{
    private int maxScore;
    Scene(int width,int height,int maxScore){
        this.maxScore=maxScore;
        drawCells(width,height,false);
        scanScene();
    }

    Scene(String fileName,int maxScore){
        this.maxScore=maxScore;
        try{
            JSONObject o = (JSONObject)(new JSONParser().parse(new FileReader("C:/Users/Archil/Desktop/SnakeTerrains/"+fileName)));
            drawCells(((Long)o.get("width")).intValue(),((Long)o.get("height")).intValue(),false);
            addBarriers((JSONArray) o.get("barriers"));
            scanScene();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public int getMaxScore(){
        return maxScore;
    }

    private void addBarriers(JSONArray barriers ){
        for (Object barrier : barriers) {
            setColor(new Dim(((Long) ((JSONArray) barrier).get(0)).intValue(), ((Long) ((JSONArray) barrier).get(1)).intValue()), Color.red, false);
        }
    }
}
