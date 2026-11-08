import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.util.List;
public class StatScreen extends JPanel{
    public int width;
    public int height;
    public JPanel statPanel;
    private List<Stat> stats;
    public int ticOffset;
    public boolean showTraits;
    private int yOffset = 50;
    private int xOffset = 25;

    public StatScreen(int width, int height) {
        this.width = width + xOffset;
        this.height = height + yOffset;
        ticOffset = 0;
        System.out.println(width + " " + height);
        init();
    }

    public void init() {
        setLayout(null);
        setBackground(Color.BLACK);
        repaint();
        setFocusable(true);
        setPreferredSize(new Dimension(width, height));
    }

    public void drawStats(List<Stat> stats, float flowerColourR, float flowerColourB) {
        this.stats = stats;
        this.flowerColourR = flowerColourR;
        this.flowerColourB = flowerColourB;
        repaint();
    }

    private float flowerColourR;
    private float flowerColourB;
    @Override 
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
            if (stats == null) {
            return;
        }

        if (showTraits) {
            drawTraits(g);
            return;
        }

        drawDots(g);
        drawLines(g);

        drawAxes(g);
        drawKey(g);
    }

    private void drawDots(Graphics g) {
        for (Stat stat : stats) {
            int xPos = scaleXPos(stat.tic / 100 - ticOffset) + xOffset;
            g.setColor(Color.white);
            int yPos = getHeight() - stat.numSheep - yOffset;
            g.fillOval(xPos, yPos - 4, 4, 4);

            g.setColor(Color.gray);
            yPos = getHeight() - stat.numWolves - yOffset;
            g.fillOval(xPos, yPos - 4, 4, 4);

            g.setColor(new Color(flowerColourR, 0, flowerColourB));
            yPos = getHeight() - stat.numFlowers - yOffset;
            g.fillOval(xPos, yPos - 4, 4, 4);

            g.setColor(Color.green);
            yPos = getHeight() - stat.numGrass - yOffset;
            g.fillOval(xPos, yPos - 4, 4, 4);
        }
    }

    private void drawLines(Graphics g) {
        for (int i = 0; i < stats.size() - 1; i++) {
            Stat stat1 = stats.get(i);
            Stat stat2 = stats.get(i + 1);

            int xPos1 = scaleXPos(stat1.tic / 100 - ticOffset) + 2 + xOffset;
            int xPos2 = scaleXPos(stat2.tic / 100 - ticOffset) + 2 + xOffset;

            g.setColor(Color.white);
            int yPos1 = getHeight() - stat1.numSheep - 2 - yOffset;
            int yPos2 = getHeight() - stat2.numSheep - 2 - yOffset;
            g.drawLine(xPos1, yPos1, xPos2, yPos2);

            g.setColor(Color.gray);
            yPos1 = getHeight() - stat1.numWolves - 2 - yOffset;
            yPos2 = getHeight() - stat2.numWolves - 2 - yOffset;
            g.drawLine(xPos1, yPos1, xPos2, yPos2);

            g.setColor(new Color(flowerColourR, 0, flowerColourB));
            yPos1 = getHeight() - stat1.numFlowers - 2 - yOffset;
            yPos2 = getHeight() - stat2.numFlowers - 2 - yOffset;
            g.drawLine(xPos1, yPos1, xPos2, yPos2);

            g.setColor(Color.green);
            yPos1 = getHeight() - stat1.numGrass - 2 - yOffset;
            yPos2 = getHeight() - stat2.numGrass - 2 - yOffset;
            g.drawLine(xPos1, yPos1, xPos2, yPos2);

            
        }
    }

    private void drawAxes(Graphics g) {
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.setColor(Color.white);

        g.drawLine(xOffset,  0, xOffset, height - yOffset);
        for (int i = 0; i < height - yOffset; i += 100) {
            g.drawLine(xOffset, i, xOffset + 10, i);
            g.drawString(height - i - yOffset + "", xOffset + 5, i + metrics.getHeight());
        }


        g.drawLine(xOffset - 10, height - yOffset, width, height - yOffset);
        int ratio = getRatio();
        int nextI = Math.max(ratio, 82);
        int nextJ = (int)Math.floor((double)nextI / ratio) * 100;
        for (int i = xOffset, j = ticOffset * 100; i < width; j += nextJ, i += nextI) {
            g.drawLine(i, height - yOffset, i, height - yOffset + 10);
            String str = j + "";
            int strWidth = metrics.stringWidth(str);
            int xPos = (i > xOffset) ? i - strWidth : i;
            g.drawString(str, xPos, height - yOffset + 10 + metrics.getHeight());
        }
        String str = "0";
        int strWidth = metrics.stringWidth(str);
        g.drawString(str, xOffset - 10 - strWidth / 2, height - yOffset + metrics.getHeight());

        str = "Time(tics)";
        strWidth = metrics.stringWidth(str);
        g.drawString(str, (width + xOffset) / 2 - strWidth / 2 , height - metrics.getHeight() / 2);

        Graphics2D g2d = (Graphics2D)g;
        int angle = -90;
        g2d.rotate(Math.toRadians(angle));
        str = "Number of entities";
        strWidth = metrics.stringWidth(str);
        g.drawString(str, -(height - yOffset) / 2 - strWidth, metrics.getHeight());
        g2d.rotate(Math.toRadians(-angle));
    }
    Font medium = new Font("Helvetica", Font.PLAIN, 16);
    Font small = new Font("Helvetica", Font.PLAIN, 13);
    private void drawKey(Graphics g) {
        g.setColor(Color.white);
        g.setFont(medium);
        String str = "Key:";
        FontMetrics metrics = g.getFontMetrics();
        int strWidth = metrics.stringWidth(str);
        int strHeight = metrics.getHeight() / 2;
        int xPos = width - (strWidth + Math.max(90 - strWidth, 0));
        int yPos = strHeight + 25;
        g.drawString(str, xPos, yPos);

        g.setFont(small);
        metrics = g.getFontMetrics();
        strHeight = metrics.getHeight() / 2;

        str = " Sheep";
        strWidth = metrics.stringWidth(str);
        xPos = width - (strWidth + Math.max(80 - strWidth, 0));
        yPos += strHeight + 15;
        g.drawString(str, xPos, yPos);
        g.drawLine(xPos - 20, yPos - strHeight / 2, xPos - 5, yPos - strHeight / 2);

        str = " Wolf";
        strWidth = metrics.stringWidth(str);
        xPos = width - (strWidth + Math.max(80 - strWidth, 0));
        yPos += strHeight + 15;
        g.drawString(str, xPos, yPos);
        g.setColor(Color.gray);
        g.drawLine(xPos - 20, yPos - strHeight / 2, xPos - 5, yPos - strHeight / 2);

        g.setColor(Color.white);
        str = " Flower";
        strWidth = metrics.stringWidth(str);
        xPos = width - (strWidth + Math.max(80 - strWidth, 0));
        yPos += strHeight + 15;
        g.drawString(str, xPos, yPos);
        g.setColor(new Color(flowerColourR, 0, flowerColourB));
        g.drawLine(xPos - 20, yPos - strHeight / 2, xPos - 5, yPos - strHeight / 2);

        g.setColor(Color.white);
        str = " Grass";
        strWidth = metrics.stringWidth(str);
        xPos = width - (strWidth + Math.max(80 - strWidth, 0));
        yPos += strHeight + 15;
        g.drawString(str, xPos, yPos);
        g.setColor(Color.green);
        g.drawLine(xPos - 20, yPos - strHeight / 2, xPos - 5, yPos - strHeight / 2);

    }

    //average speed and perception of each species as a percentage of the first sample
    private void drawTraits(Graphics g) {
        int plotHeight = height - yOffset;
        Stat first = stats.get(0);

        g.setColor(Color.darkGray);
        g.drawLine(xOffset, plotHeight / 2, width, plotHeight / 2);

        for (int i = 0; i < stats.size() - 1; i++) {
            Stat stat1 = stats.get(i);
            Stat stat2 = stats.get(i + 1);
            int xPos1 = scaleXPos(stat1.tic / 100 - ticOffset) + 2 + xOffset;
            int xPos2 = scaleXPos(stat2.tic / 100 - ticOffset) + 2 + xOffset;

            g.setColor(Color.white);
            g.drawLine(xPos1, traitY(stat1.sheepTraits.speed, first.sheepTraits.speed), xPos2, traitY(stat2.sheepTraits.speed, first.sheepTraits.speed));
            g.setColor(Color.cyan);
            g.drawLine(xPos1, traitY(stat1.sheepTraits.perception, first.sheepTraits.perception), xPos2, traitY(stat2.sheepTraits.perception, first.sheepTraits.perception));
            g.setColor(Color.gray);
            g.drawLine(xPos1, traitY(stat1.wolfTraits.speed, first.wolfTraits.speed), xPos2, traitY(stat2.wolfTraits.speed, first.wolfTraits.speed));
            g.setColor(Color.orange);
            g.drawLine(xPos1, traitY(stat1.wolfTraits.perception, first.wolfTraits.perception), xPos2, traitY(stat2.wolfTraits.perception, first.wolfTraits.perception));
        }

        g.setColor(Color.white);
        g.drawLine(xOffset, 0, xOffset, plotHeight);
        g.drawLine(xOffset, plotHeight, width, plotHeight);
        g.drawString("200%", xOffset + 5, 15);
        g.drawString("100% (first sample)", xOffset + 5, plotHeight / 2 - 5);
        g.drawString("0%", xOffset + 5, plotHeight - 5);
        g.drawString("Average traits (press E to switch back)", xOffset + 5, plotHeight + 25);

        g.drawString("Sheep speed", width - 140, 20);
        g.setColor(Color.cyan);
        g.drawString("Sheep perception", width - 140, 40);
        g.setColor(Color.gray);
        g.drawString("Wolf speed", width - 140, 60);
        g.setColor(Color.orange);
        g.drawString("Wolf perception", width - 140, 80);
    }

    //converts a trait value into a y position on the graph
    private int traitY(double value, double start) {
        int plotHeight = height - yOffset;
        if (start == 0) {
            return plotHeight;
        }
        int y = plotHeight - (int)(value / start * plotHeight / 2);
        return Math.max(0, Math.min(plotHeight, y));
    }





    private int scaleXPos(int xPos) {
        return xPos * getRatio();
    }

    private int getRatio() {
        int ratio = (getWidth() - xOffset) / stats.size();
        return ratio;
    }
}
