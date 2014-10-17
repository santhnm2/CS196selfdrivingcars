package Car;


import java.util.ArrayList;

import Constants.Directions;
import Map.Map;
import Map.Tile;
import Map.Road.Road;
import Map.Road.TrafficLight;

public class Car {
    private int speed=0;
    private int dir=0;
    private int xPos=0;
    private int yPos=0;
    private int previousDir=0;
    //	final private Tile destination=new Tile();   waiting to be implemented
    ArrayList<Integer> path=new ArrayList<Integer>();
    Map map; //Grid that the car is in
    private int destX;
    private int destY;
    
    public Car(int x, int y, int destX, int destY, Map map) {
        xPos = x;
        yPos = y;
        this.destX = destX;
        this.destY = destY;
        Road road = (Road)map.get(xPos, yPos);
        road.setCar(true);
        this.map = map;
        path = genPath();
    }
    
    public boolean move() // plug off the first item of the arraylist and execute that
    {
        Tile nextTile=getNextTile(xPos, yPos, path.get(0));
        if(nextTile instanceof TrafficLight) {
            TrafficLight l = (TrafficLight)nextTile;
            if(l.isRed() && !(map.get(xPos,yPos) instanceof TrafficLight))
                return false;
        }
        if(nextTile.getX()>=0 && nextTile.getY()>=0)
        {
            Road road = (Road)map.get(xPos, yPos);
            road.setCar(false);
            xPos=nextTile.getX();
            yPos=nextTile.getY();
            road = (Road) map.get(xPos, yPos);
            road.setCar(true);
            path.remove(0);
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean turn()
    {
        dir++;
        return true;
    }
    public boolean exists()
    {
        return true;
    }
    /*
     * Given a direction, will find the tile (if it exists) in that direction and return it
     * @param Direction to check in
     * @return Tile in the given direction, or null if it doesn't exist.
     */
    public Tile getNextTile(int x, int y, int dir)
    {
        switch(dir) {
            case Directions.UP:
                y--;
                break;
            case Directions.DOWN:
                y++;
                break;
            case Directions.RIGHT:
                x++;
                break;
            case Directions.LEFT:
                x--;
                break;
        }
        if(map.pointIsValid(x, y)) //Ensure tile is in grid
            return map.get(x, y);
        else
            return null; //If not in grid, return null
    }
    public int getSpeed()
    {
        return speed;
    }
    public void setSpeed(int speed)
    {
        speed=this.speed;
    }
    public int getDir()
    {
        return dir;
    }
    public void seDir(int dir)
    {
        dir=this.dir;
    }
    // unsafe, do not use.
    public ArrayList<Integer> genPath() {
        int curX = xPos;
        int curY = yPos;
        ArrayList<Integer> path = new ArrayList<Integer>();
        boolean finding = true;
        while(finding) {
            Road curRoad = (Road)map.get(curX, curY);
            
            int dir = curRoad.getDirection();
            if(curRoad instanceof TrafficLight)
                dir=previousDir; // giving traffic light a temp direction.
            
            //Hit a Traffic Light
            if(getNextTile(curX, curY, dir) instanceof Road &&!(getNextTile(curX, curY, dir) instanceof TrafficLight)) {
                Road roadInDir = (Road)getNextTile(curX, curY, dir);
                if(roadInDir.getDirection()==dir) {
                    path.add(dir);
                    curX = getNextTile(curX, curY, dir).getX();
                    curY = getNextTile(curX, curY, dir).getY();
                }
            }
            else if(getNextTile(curX, curY, dir) instanceof TrafficLight &&(curX!=destX && curY!=destY) ) {
                //Left Turn
                if(curX>destX && dir == Directions.UP) {
                    path.addAll(leftTurn(dir));
                    curX-=2; curY-=2;
                }
                else if(curX<destX && dir == Directions.DOWN) {
                    path.addAll(leftTurn(dir));
                    curX+=2; curY+=2;
                }
                else if(curY<destY && dir == Directions.LEFT) {
                    path.addAll(leftTurn(dir));
                    curX-=2; curY+=2;
                }
                else if(curY>destY && dir == Directions.RIGHT) {
                    path.addAll(leftTurn(dir));
                    curX+=2; curY-=2;
                }
                //Right Turn
                else if(curX<destX && dir == Directions.UP) {
                    path.addAll(rightTurn(dir));
                    curX++; curY--;
                }
                else if(curX>destX && dir == Directions.DOWN) {
                    path.addAll(rightTurn(dir));
                    curX--; curY++;
                }
                else if(curY>destY && dir == Directions.LEFT) {
                    path.addAll(rightTurn(dir));
                    curX--; curY--;
                }
                else if(curY<destY && dir == Directions.RIGHT) {
                    path.addAll(rightTurn(dir));
                    curX++; curY++;
                }
            }
            else if(getNextTile(curX, curY, dir) instanceof TrafficLight && (curX==destX || curY==destY))
            {
                
                path.add(dir);
                curX = getNextTile(curX, curY, dir).getX();
                curY = getNextTile(curX, curY, dir).getY();
                
            }
            
            //Continue on road
            
            previousDir=dir;
            //Check to see if found destination
            if((dir==Directions.UP||dir==Directions.DOWN)&&Math.abs(destX-curX)<=1&&destY==curY) finding = false;
            else if((dir==Directions.RIGHT||dir==Directions.LEFT)&&Math.abs(destY-curY)<=1&&destX==curX) finding = false;
        }
        
        System.out.println(path);
        return path;
        
        /*while(curX!=destX && curY!=destY) {
         if(curX<destX && map.get(curX+1,curY) instanceof Road && ((Road)map.get(curX+1,curY)).getDirection()==Directions.RIGHT) {
         curX++;
         path.add(Directions.RIGHT);
         }
         else if(curX>destX && map.get(curX-1,curY) instanceof Road && ((Road)map.get(curX-1,curY)).getDirection()==Directions.LEFT) {
         curX--;
         path.add(Directions.LEFT);
         }
         else if(curY<destY && map.get(curX,curY+1) instanceof Road && ((Road)map.get(curX,curY+1)).getDirection()==Directions.DOWN) {
         curY++;
         path.add(Directions.DOWN);
         }
         else if(curY>destY && map.get(curX,curY-1) instanceof Road && ((Road)map.get(curX,curY-1)).getDirection()==Directions.UP) {
         curY--;
         path.add(Directions.UP);
         }
         else {
         //May throw an error in certain start conditions
         int nextDir = ((Road)map.get(curX,curY)).getDirection();
         path.add(nextDir);
         curX = getNextTile(nextDir).getX();
         curY = getNextTile(nextDir).getY();
         }
         }
         return path;*/
    }
    private ArrayList<Integer> leftTurn(int dir) {
        ArrayList<Integer> dirs = new ArrayList<Integer>();
        int leftDir = dir-1;
        if(leftDir<0) leftDir+=4;
        dirs.add(dir);
        dirs.add(dir);
        dirs.add(leftDir);
        dirs.add(leftDir);
        return dirs;
    }
    private ArrayList<Integer> rightTurn(int dir) {
        ArrayList<Integer> dirs = new ArrayList<Integer>();
        int rightDir = dir+1;
        if(rightDir>3) rightDir-=4;
        dirs.add(dir);
        dirs.add(rightDir);
        return dirs;
    }
    public ArrayList<Integer> getPath(){ return path; }
    
    public String toString() {
        return (xPos+","+yPos);
    }
}
