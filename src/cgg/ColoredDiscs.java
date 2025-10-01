package cgg;

import tools.*;
import java.util.ArrayList;
import java.util.List;

public class ColoredDiscs {

    
    private List <ColorDisc> discs;
    

    public ColoredDiscs(int width, int height) {
        discs = new ArrayList<>();
        double radius = 0.5;
        Color color = new Color(0.0, 0.0, 0.0);
        
    
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x % 20==0 && y % 20 == 0) {
                    Vec2 mitte = new Vec2(x,y);
                    
                    
                    if (x%60==0||y%60==0) {
                        discs.add(new ColorDisc(mitte, Color.cyan, radius));
                    }
                    else if (x%40==0||y%40==0) {
                        discs.add(new ColorDisc(mitte, Color.blue, radius));
                    }
                    discs.add(new ColorDisc(mitte, Color.magenta, radius));
                    if (x<200&&y<200){
                        radius=radius+0.05;

                    }
                    if (x>200&&y<200){
                        radius=radius-0.05;

                    }
                    if(y>200&&x<200){
                        radius=radius+0.05;

                    }
                    if(y>200&&x>200){
                        radius=radius-0.05;

                    }
                    //radius=radius + 0.1;
                    
                    
                }
            }
        }
    }
    public Color getColor (Vec2 point) {
        for (ColorDisc disc : discs) {
            Color color = disc.getColor(point);
            if (!color.equals(color.black)){
                return color;
            }  
        }return Color.black;
    }
}