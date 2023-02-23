public class Dim {
    private int x;
    private int y;
    Dim(int x,int y){
        this.x=x;
        this.y=y;
    }

    int getX(){
        return x;
    }

    int getY(){
        return y;
    }

    void setDim(int x,int y){
        this.x=x;
        this.y=y;
    }

    public boolean equals(Object o){
        if(o instanceof Dim){
            return ((Dim)o).getX()==x && ((Dim)o).getY()==y;
        }
        return false;
    }
}
