package scene;


import dataLayer.LiveState;
import dataLayer.Member;
import dataLayer.Tree;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Calendar;

/***
 * @author Justin
 */
public class TreeDrawing {
    private static ArrayList<Integer> generationCount;
    private static double canvasWidth;
    private static double canvasHeight;
    private static int gridWidth = 40;
    private static int gridHeight = 20;
    private static int yGap = 50;//vertical gap between grids
    private static int[] xGap;//horizontal gap
    private static int[] drawCount;//count nodes for each layer
    private static int startY = 10;
    private static int startX = 10;
    private static Calendar now;
    private static int month;
    private static int date;

    public static void draw(Canvas canvas,Tree tree){
        now = Calendar.getInstance();
        month = now.get(Calendar.MONTH)+1;
        date = now.get(Calendar.DATE);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());//clear canvas
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
        generationCount = tree.generationStatistic();//for space distribution
        yGap = ((int)canvasHeight-2*startY)/(generationCount.size()+1)-gridHeight;
        xGap = new int[generationCount.size()];

        for (int i = 0; i < xGap.length; i++) {
            xGap[i] = (((int)canvasWidth-2*startX))/(generationCount.get(i))-gridWidth;
        }
        drawCount = new int[generationCount.size()];
        draw(gc,tree.getRoot());
    }

    private static void draw(GraphicsContext gc, Member member){
        int lay = member.getGeneration();
        int y = startY+lay*(yGap +gridHeight);//grid location
        int StringY = y + gridHeight/2+5;//text location

        //mark alive as blue
        if (member.getIsAlive()== LiveState.alive && member.getBirthday()[1]==month && member.getBirthday()[2] == date){
            gc.setStroke(Color.BLUE);
        }else{
            gc.setStroke(Color.BLACK);
        }

        int x = startX + drawCount[lay]*(xGap[lay]+gridWidth);
        gc.strokeRect(x,y,gridWidth,gridHeight);
        gc.fillText(member.getName(),x+5,StringY);
        drawCount[lay]=drawCount[lay]+1;

        gc.setStroke(Color.BLACK);//set line color
        for (int i = 0; i < member.getDescendents().size(); i++) {
            gc.strokeLine(x+gridWidth/2,y+gridHeight,startX + drawCount[lay+1]*(xGap[lay+1]+gridWidth)+gridWidth/2,y+gridHeight+yGap);
            draw(gc,member.getDescendents().get(i));
        }

    }


    public static void drawExamples(Canvas canvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //shape examples
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.strokeLine(40, 10, 10, 40);
        gc.fillOval(10, 60, 30, 30);
        gc.strokeOval(60, 60, 30, 30);
        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
        gc.fillPolygon(new double[]{10, 40, 10, 40},
                new double[]{210, 210, 240, 240}, 4);
        gc.strokePolygon(new double[]{60, 90, 60, 90},
                new double[]{210, 210, 240, 240}, 4);
        gc.strokePolyline(new double[]{110, 140, 110, 140},
                new double[]{210, 210, 240, 240}, 4);
    }

}
