package ru.nightmare;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Main extends JFrame {
    public static Random r = new Random(System.currentTimeMillis());
    public static int size;
    public static int scale;
    public static int ENERGY;
    public static int NEIGHBORS;


    public static void loadSettings() {
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("settings.properties");
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            // Файл не найден, используем значения по умолчанию
            size = 500;
            scale = 1;
            ENERGY = 250;
            NEIGHBORS = 2;
            saveSettings();
            return;
        }

        size = Integer.parseInt(properties.getProperty("size", "500"));
        scale = Integer.parseInt(properties.getProperty("scale", "1"));
        ENERGY = Integer.parseInt(properties.getProperty("ENERGY", "250"));
        NEIGHBORS = Integer.parseInt(properties.getProperty("NEIGHBORS", "2"));
    }
    static {
        loadSettings();
    }
    public static void saveSettings() {
        Properties properties = new Properties();
        properties.setProperty("size", String.valueOf(size));
        properties.setProperty("scale", String.valueOf(scale));
        properties.setProperty("ENERGY", String.valueOf(ENERGY));
        properties.setProperty("NEIGHBORS", String.valueOf(NEIGHBORS));

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("settings.properties");
            properties.store(fileOutputStream, null);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Main main = boot();
        Timer timer = new Timer(2, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.repaint();
                main.step();
            }
        });
        timer.start();
    }

    public static Main boot() {
        return new Main();
    }

    class MyPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            //int bricks = 0;
            for (int i = 0; i < size/scale-1; i++) {
                for (int o = 0; o < size/scale-1; o++) {
                    if (matrix[i][o]==null) g.setColor(Color.black);
                    else g.setColor(new Color(matrix[i][o].energy+NEIGHBORS, 0, 100));
                    //g.fillArc(i*scale, o*scale, scale, scale, 0, 360);
                    g.fillRect(i*scale, o*scale, scale, scale);
                    //if (matrix[i][o]!=null)
                        //if (matrix[i][o].energy<=0) matrix[i][o]=null;
                        //else {matrix[i][o].energy--; bricks++;}

                }
            }
            //if (bricks<size*size/10) {
            //    int x = r.nextInt(size/scale-1);
            //    int y = r.nextInt(size/scale-1);
            //    Brick b = new Brick(ENERGY, NEIGHBORS, x, y);
            //    matrix[x][y] = b;
            //    next.add(b);
            //}
            //System.out.println(current.size());

        }
        {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int x = e.getX()/scale;
                    int y = e.getY()/scale;
                    Brick b = new Brick(ENERGY, NEIGHBORS, x, y);
                    matrix[x][y] = b;
                    next.add(b);
                }
            });
        }
    }

    {
        setSize(size, size);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(new MyPanel());
        addWindowStateListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveSettings();
            }
        });
    }



    ArrayList<Brick> current = new ArrayList<>();
    ArrayList<Brick> next = new ArrayList<>();

    Brick[][] matrix = new Brick[size/scale][size/scale];
    boolean first = true;

    public void step() {

        if (first) {
            Brick root = Brick.getRoot();
            matrix[size/scale/2][size/scale/2] = root;
            current.add(root);
            first = false;
        } else {
            current = next;
            next = new ArrayList<>();
        }
        for (Brick brick : current) {
            brick.generateNeighbors(matrix, next);
        }
    }

    
}
