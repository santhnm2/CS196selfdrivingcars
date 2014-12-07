package Map;

import Map.NonRoad.House;
import Car.Car;
import Map.Road.Road;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.PriorityQueue;
/**
 * @author mchen14
 * @since 10 2014
 */

public class DensityBasedMapGenerator implements MapGenerator {
    private final Tile[][] grid;

    public DensityBasedMapGenerator(Tile[][] grid) {
        this.grid = grid;
    }

    @Override
    public Map generateMap() {
        Map map = new Map(grid, new ArrayList<Car>());

        int[][] visits = simulatePaths(1000, grid);

        convertPaths(visits, grid);

        return map;
    }


    private int[][] simulatePaths(int iterations, Tile[][] grid) {
        int[][] visits = new int[grid.length][grid[0].length];

        ArrayList<Tile> densities = new ArrayList<Tile>(); //assuming that the densities are in the format of rectangular areas of
        //one density
        int startPointX = 0;
        int startPointY = 0;
        for(int i = startPointX; i < grid.length; i++){
            for(int j = startPointY; j < grid[0].length; j++){

                if(grid[i][j].density > 0){

                    Tile[] t = getAdjTiles(grid, grid[i][j]);
                    for(int a = 0; a < 4; a++){
                        if(t[a].density == 0) {
                            visits[t[a].getX()][t[a].getY()]++;
                            densities.add(grid[i][j]);
                            a+=4;
                        }
                    }

                }

            }

        }
        for(int i = 0; i < iterations; i++){
            double threshold = Math.random();
            Tile[] endpoints = new Tile[2];
            //this for loop grabs the density endpoints for the path
            for(Tile t : endpoints){
                while(t == null){
                    int randomNum = (int)(Math.random() * densities.size());
                    if(densities.get(randomNum).density > threshold){
                        t = densities.get(randomNum);
                    }
                }
            }
            ArrayList<Tile> path = findPath(endpoints[0], endpoints[1], grid);
            for(Tile t : path){
                visits[t.getX()][t.getY()]++;
            }
        }
        return visits;

    }

    private static class Path implements Comparable<Path>{
        public ArrayList<Tile> tiles;
        public int priority;
        public Path(){
            priority = 0;
            tiles = new ArrayList<Tile>();
        }
        public Path(Path e){
            this.priority = e.priority;
            for(Tile t : e.tiles){
                this.tiles.add(t);
            }
        }
        public void add(Tile e){
            tiles.add(e);
        }
        public void remove(int i){
            tiles.remove(i);
        }
        public Tile get(int i){
            return tiles.get(i);
        }
        public int size(){
            return tiles.size();
        }
        public int compareTo(Path p){
            if(p.priority > this.priority)
                return -1;
            if(p.priority < this.priority)
                return 1;
            return 0;
        }
    }

    private static ArrayList<Tile> findPath(Tile x, Tile y, Tile[][] grid){
        int tgtY = y.getY();
        int tgtX = y.getX();
        Path p = new Path();
        PriorityQueue<Path> paths = new PriorityQueue<Path>();
        Tile headOfPath = new Tile(1,2);
        Tile[] adj = getAdjTiles(grid, x);
        int random = (Math.random() > 0.50)? 1 : -1;
        for(int i = ((random == 1)? 0 : 4); i < 4; i+=random){
            if(adj[i].density == 0)
                headOfPath = adj[i];
            break;
        }
        if(headOfPath == null) return p.tiles;

        p.add(headOfPath);
        paths.add(p);

        while(headOfPath.getX() != y.getX() || headOfPath.getY() != y.getY()){
            Path q = paths.poll();
            headOfPath = q.get(q.size() - 1);
            int currentX = headOfPath.getX();
            int currentY = headOfPath.getY();
            Tile[] potential = getAdjPotentialRoads(grid, headOfPath);
            int distance = Math.abs(currentX - y.getX()) + Math.abs(currentY - y.getX());
            if(distance == 0){
                p = q;
            }
            for(Tile t : potential){
                q.add(t);

                Path temp = new Path(q);
                temp.priority = Math.abs(temp.get(temp.size() - 1).getX() - y.getX()) + Math.abs(temp.get(temp.size() - 1).getY() - y.getY());;
                paths.add(temp);
                q.remove(q.size() - 1);
            }


       /*     Tile next = (Math.random() > 0.50)?((y.getX() > currentX)? grid[currentX + 1][currentY] : grid[currentX - 1][currentY]) :
                                                ((y.getY() > currentY)? grid[currentX][currentY + 1] : grid[currentX][currentY - 1]);
            if(contains(potential, next)) {
                headOfPath = next;
                p.add(next);
            } else {

            }*/
            //strategy for paths: find shortest paths with least number of turns (prioritize that)
            //to do this, find shortest paths and each time there's a turn, add a point to that path. then grab the path with least points
        }

        return p.tiles;
    }
    private static boolean contains(Tile[] t, Tile x){
        for(int i = 0; i < t.length; i++){
            if(t[i].getX() == x.getX() && t[i].getY() == x.getY())
                return true;
        }
        return false;
    }
    private static Tile[] getAdjPotentialRoads(Tile[][] grid, Tile x){
        Tile[] t = getAdjTiles(grid, x);

        ArrayList<Tile> potentialRoads = new ArrayList<Tile>();
        for(int i = 0; i < 4; i++){
            if(t[i].density == 0)
                potentialRoads.add(t[i]);
        }
        Tile[] potRoads = new Tile[potentialRoads.size()];
        for(int i = 0; i < potRoads.length; i++){
            potRoads[i] = potentialRoads.get(i);
        }
        return potRoads;
    }

    private static Tile[] getAdjTiles(Tile[][] grid, Tile x){
        Tile[] y = new Tile[4];
        y[0] = grid[x.getX()][x.getY() - 1];
        y[1] = grid[x.getX() + 1][x.getY()];
        y[2] = grid[x.getX()][x.getY() + 1];
        y[3] = grid[x.getX() - 1][x.getY()];

        return y;
    }
    private void convertPaths(int[][] visits, Tile[][] grid) {
        //more work on this later.
        for(int i = 0; i < visits.length; i++){
            for(int j = 0; j < visits[0].length; j++){
                if(visits[i][j] > 10)
                    turnIntoRoad(grid[i][j], grid, visits[i][j]/100);
            }
        }
    }
    //needs fixing
    private static void turnIntoRoad(Tile x, Tile[][] grid, int lanes){
        Tile[] t = getAdjTiles(grid, x);
        if(t[0] instanceof Road || t[2] instanceof Road){
            if(t[1].density > 0){
                t[3] = new Road(x.getX() - 1, x.getY(), 35, 2,  lanes);
                x = new Road(x.getX(), x.getY(), 35, 0, lanes);
            } else {
                t[1] = new Road(x.getX() + 1, x.getY(), 35, 0,  lanes);
                x = new Road(x.getX(), x.getY(), 35, 2, lanes);
            }
        } else if(t[1] instanceof Road || t[3] instanceof Road){
            if(t[0].density > 0){
                t[2] = new Road(x.getX(), x.getY() + 1, 35, 1,  lanes);
                x = new Road(x.getX(), x.getY(), 35, 3, lanes);
            } else {
                t[0] = new Road(x.getX(), x.getY() - 1, 35, 3,  lanes);
                x = new Road(x.getX(), x.getY(), 35, 1, lanes);
            }
        }
    }
    public static Tile[][] turnIntoGrid(double[][] density_map){
        Tile[][] grid = new Tile[density_map.length][density_map[0].length];
        for(int i = 0; i < density_map.length; i++){
            for(int j = 0; j < density_map[0].length; j++){
                grid[i][j] = new Tile(i,j);
                grid[i][j].density = density_map[i][j];
            }
        }
        return grid;
    }

    public static void main(String[] args) {
       /* double[][] density_map = RandomDensity.generateDensity(50);



        Tile[][] grid = turnIntoGrid(density_map);
        MapGenerator x = new DensityBasedMapGenerator(grid);
        Car[] cars = new Car[5];
        Map y = x.generateMap();
        System.out.println(y);*/

    }
}

