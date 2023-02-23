import acm.program.ConsoleProgram;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class test extends ConsoleProgram {
    public void run(){
        d("25_25.json");
    }

    private void d(String filename){
        try{
            JSONObject o = (JSONObject)new JSONParser().parse(new FileReader("C:/Users/Archil/Desktop/SnakeTerrains/"+filename));
            //JSONArray barr = o.get()
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}