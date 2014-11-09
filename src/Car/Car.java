package Car;


import java.util.ArrayList;

import Constants.Directions;
import Map.Map;
import Map.Tile;
import Map.Road.Road;
import Map.Road.TrafficLight;
import Optimization.PathGenerator;

public class Car {
    private int speed = 0;
    private int dir = 0;
    private int xPos = 0;
    private int yPos = 0;
    private int previousDir = 0;
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
        road.carIncrement(this);
        this.dir = road.getDirection();
        this.map = map;

        genPath();
    }


    public int getXPos()
    {
       return xPos;
    }
    public int getYPos()
    {
       return yPos;
    }
    public int getDestX()
    {
       return destX;
    }
    public int getDestY()
    {
       return destY;
    }
    
    public boolean move() // plug off the first item of the arraylist and execute that
    {

        int dir = path.remove(0);
        Tile nextTile = map.getInDir(map.get(getXPos(), getYPos()), dir);

        Road r2 = (Road) map.get(xPos, yPos);
        r2.carDecrement(this);

        xPos = nextTile.getX();
        yPos = nextTile.getY();

        Road r = (Road) map.get(xPos, yPos);


        r.carIncrement(this);

        return true;
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
        Road curr = (Road) map.get(xPos, yPos);

        for (int i = 0; i < 100; i++) {
            int[] dx = { 0, 1, 0, -1 };
            int[] dy = { 1, 0, -1, 0 };

            int altX, altY;
            int rand;
            do {
                rand = (int) (Math.random() * 4);

                altX = curr.getX() + dx[rand];
                altY = curr.getY() + dy[rand];
            } while (!(map.get(altX, altY) instanceof Road));

            path.add(rand);

            if (!(map.getInDir(curr, rand) instanceof Road) && map.getInDir(curr, rand) == map.get(altX, altY)) {
                throw new RuntimeException("FUCK FUCK FUCK FUCK FUCK FUCK FUCK");
            }

            curr = (Road) map.get(altX, altY);
        }

        return path;
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
