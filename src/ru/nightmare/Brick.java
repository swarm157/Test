package ru.nightmare;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static ru.nightmare.Main.*;

class Brick {
    int energy;
    int neighbors;
    int x, y;

    public Brick(int energy, int neighbors, int x, int y) {
        this.energy = energy;
        this.neighbors = neighbors;
        this.x = x;
        this.y = y;
        //System.out.println(toString());
    }

    public static Brick getRoot() {return new Brick(ENERGY, NEIGHBORS, size/scale/2, size/scale/2);}

    private static ArrayList<Point> genVectors = new ArrayList<>();
    static {
        //genVectors.add(new Point(-2, -2));
        //genVectors.add(new Point(2, -2));
        genVectors.add(new Point(-1, -1));
        genVectors.add(new Point(-1, 0));
        genVectors.add(new Point(-1, 1));
        genVectors.add(new Point(0, -1));
        genVectors.add(new Point(0, 0));
        genVectors.add(new Point(0, 1));
        genVectors.add(new Point(1, -1));
        genVectors.add(new Point(1, 0));
        genVectors.add(new Point(1, 1));
        //genVectors.add(new Point(2, 2));
        //genVectors.add(new Point(-2, 2));
    }
    public void generateNeighbors(Brick[][] matrix, ArrayList<Brick> next) {
        if (energy<1)
            return;
        for (float i = 0; i < neighbors; i++) {
            int e = r.nextInt(genVectors.size());//%genVectors.size();
            //System.out.println("e "+e);
            //System.out.println("v "+genVectors.size());
            Point point = genVectors.get(e);
            boolean generated = false;
            if (x+point.x>0&&x+point.x<size/scale-1&&y+point.y>0&&y+point.y<size/scale-1) {
                    if (matrix[x+point.x][y+point.y]==null) {
                        energy--;
                        matrix[x+point.x][y+point.y] = new Brick(energy, r.nextInt(NEIGHBORS)+1, x+point.x, y+point.y);
                        generated=true;
                        next.add(matrix[x+point.x][y+point.y]);
                    }
            } else {
                generated = true;
            }
            if (!generated)i-=0.5;
        }
        /*if (Main.r.nextInt()%2==1) {
            if (x-1>0&&matrix[x-1][y]==null) {
                energy--;
                matrix[x-1][y] = new Brick(energy, 2, x-1, y);
                energy/=2;
                next.add(matrix[x-1][y]);
            }
        } else {
            if (x+1<size/scale-1&&matrix[x+1][y]==null) {
                energy--;
                matrix[x+1][y] = new Brick(energy, 2, x+1, y);
                energy/=2;
                next.add(matrix[x+1][y]);
            }
        }

        if (Main.r.nextInt()%2==1) {
            if (y-1>0&&matrix[x][y-1]==null) {
                energy--;
                matrix[x][y-1] = new Brick(energy, 2, x, y-1);
                energy/=2;
                next.add(matrix[x][y-1]);
            }
        } else {
            if (y+1<size/scale-1&&matrix[x][y+1]==null) {
                energy--;
                matrix[x][y+1] = new Brick(energy, 2, x, y+1);
                energy/=2;
                next.add(matrix[x][y+1]);
            }
        }
*/
    }

    @Override
    public String toString() {
        return "Brick{" +
                "energy=" + energy +
                ", neighbors=" + neighbors +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}