package com.dmitrylovin.aoc2024.days;

import com.dmitrylovin.aoc2024.models.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Day14 extends DayHandler {
    JFrame frame = new JFrame("DAY 14");
    PlayGround pg;

    public Day14() {
        super("14");
        testValues = new Object[]{12, 0};
    }

    @Override
    Object partOne(boolean isTestRun) {
        String[] input = isTestRun ? testInput : this.input;
        int width = isTestRun ? 11 : 101;
        int height = isTestRun ? 7 : 103;
        int middleX = (width - 1) / 2;
        int middleY = (height - 1) / 2;
        AtomicInteger lt = new AtomicInteger(0);
        AtomicInteger lb = new AtomicInteger(0);
        AtomicInteger rt = new AtomicInteger(0);
        AtomicInteger rb = new AtomicInteger(0);
        Arrays.stream(input).forEach((raw) -> {
            String[] rawData = raw.split(" ");
            String[] position = rawData[0].split("p=")[1].split(",");
            String[] velocity = rawData[1].split("v=")[1].split(",");
            int x = Integer.parseInt(position[0]);
            int y = Integer.parseInt(position[1]);
            int dx = Integer.parseInt(velocity[0]);
            int dy = Integer.parseInt(velocity[1]);
            int finalX = (x + (dx * 100)) % width;
            finalX = finalX < 0 ? width + finalX : finalX;
            int finalY = (y + (dy * 100)) % height;
            finalY = finalY < 0 ? height + finalY : finalY;

            if (finalX < middleX && finalY < middleY)
                lt.incrementAndGet();
            else if (finalX > middleX && finalY < middleY)
                rt.incrementAndGet();
            else if (finalX < middleX && finalY > middleY)
                lb.incrementAndGet();
            else if (finalX > middleX && finalY > middleY)
                rb.incrementAndGet();
        });
        return lt.get() * lb.get() * rt.get() * rb.get();
    }

    @Override
    Object partTwo(boolean isTestRun) {
        if(isTestRun)
            return 0;
        List<Vector> robots = Arrays.stream(input).map((raw) -> {
            String[] rawData = raw.split(" ");
            String[] position = rawData[0].split("p=")[1].split(",");
            String[] velocity = rawData[1].split("v=")[1].split(",");
            return new Vector(
                    Integer.parseInt(position[0]),
                    Integer.parseInt(position[1]),
                    Integer.parseInt(velocity[0]),
                    Integer.parseInt(velocity[1])
            );
        }).toList();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200,1200);
        frame.add(pg = new PlayGround(101, 103, robots));
        frame.setVisible(true);
        InputMap inputMap = pg.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = pg.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "moveUp");
        actionMap.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pg.index -= 10;
                pg.recalc();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "moveDown");
        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pg.index += 10;
                pg.recalc();
            }
        });

        pg.recalc();

        return 0;
    }

    class PlayGround extends JPanel {
        int width;
        int height;

        public List<Vector> robots;
        BufferedImage buffer;
        int index = 0;

        public PlayGround(int width, int height, List<Vector> robots) {
            super();
            this.width = width;
            this.height = height;
            this.robots = robots;
            this.buffer = new BufferedImage((width + 2) * 10, (height + 2) * 10, BufferedImage.TYPE_INT_ARGB);
        }

        public void recalc() {
            Graphics2D g = buffer.createGraphics();
            g.setBackground(Color.WHITE);
            g.clearRect(0, 0, (width + 2) * 10, (height + 2) * 10);
            g.setColor(Color.BLACK);
            g.setColor(Color.DARK_GRAY);
            for(Vector robot:robots) {
                for(int y = 0; y < 10; y++){
                    for(int x = 0; x < 10; x++){
                        int tIdx = index + y * 10 + x;
                        Vector tmp = robot.copy();
                        tmp.x = (tmp.x + tmp.dx * tIdx) % width;
                        tmp.x = tmp.x < 0 ? width + tmp.x : tmp.x;
                        tmp.y = (tmp.y + tmp.dy * tIdx) % height;
                        tmp.y = tmp.y < 0 ? height + tmp.y : tmp.y;
                        g.fillRect(x * width + tmp.x + 2, y * height + tmp.y + 2, 1, 1);
                        g.drawString(String.valueOf(tIdx), x * width, y * height + 10);
                    }
                }
            }

            g.dispose();
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(buffer, 10, 20, null);
        }
    }
}
