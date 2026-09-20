import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleFunction;

import javax.swing.*;

public class Board extends JPanel implements ActionListener {
    public static int bWidth; // Board width
    public static int bHeight; // Board height
    private int tic; // Simulation tic
    private Timer timer;
    public static List<List<Entity>> entities;
    public static List<Animal> babyAnimals;

    public Board(int bWidth, int bHeight) {
        Board.bWidth = bWidth;
        Board.bHeight = bHeight;
        
        init();
    }

    //Initialise the board
    private void init() {
        addKeyListener(new KeyInput());
        setLayout(null);
        setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(bWidth, bHeight + 100));

        resetSimulation();
    }

    private void resetSimulation() {

        babyAnimals = new ArrayList<>();
        entities = new ArrayList<>();
        entities.add(new ArrayList<>());
        entities.add(new ArrayList<>());
        entities.add(new ArrayList<>());
        entities.add(new ArrayList<>());
        entities.get(EntityType.sheep.get()).add(new Sheep("Mary", 1, 100, 5, new Animal[]{null, null}));
        entities.get(EntityType.sheep.get()).add(new Sheep("Franky", 2, 70, 5, new Animal[]{null, null}));
        entities.get(EntityType.sheep.get()).add(new Sheep("Bert", 1, 100, 5, new Animal[]{null, null}));
        entities.get(EntityType.sheep.get()).add(new Sheep("Henry VII", 2, 70, 5, new Animal[]{null, null}));
        //entities.get(EntityType.sheep.get()).add(new Sheep("Mary3", 1, 60, 5, new Animal[]{null, null}));

        entities.get(EntityType.wolf.get()).add(new Wolf("Fido", 1, 120, 5, new Animal[]{null, null}));
        entities.get(EntityType.wolf.get()).add(new Wolf("Scar", 1, 120, 5, new Animal[]{null, null}));
        for(int i =0; i < 20; i ++)
        {
            entities.get(EntityType.flower.get()).add(new Flower());
        }
        entities.get(EntityType.flower.get()).add(new Flower());
        entities.get(EntityType.grass.get()).add(new Grass());
        for (List<Entity> list : entities) {
            for (Entity ent : list) {
                ent.pos = Position.genRand(bWidth, bHeight, 0, 100, 40);
                System.out.println(ent);
            }
        }

        tic = 1;
        nextSpawn = 300;

        timer = new Timer(10, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.gray);
        g.fillRect(0, 0, bWidth, 100);
        drawEntities(g);
        drawGrid(g);
        drawCounts(g);
        drawStats(g);
    }

private void drawEntities(Graphics g) {
    for (List<Entity> list : entities) {
        for (Entity ent : list) {
            ent.drawEntity(g);
        }
    }
}

private void updateAnimals() {
    for (List<Entity> list : entities) {
        for (Entity ent : list) {
            if (ent instanceof Animal) {
                Animal animal = (Animal) ent;
                animal.AnimalBehaviour();
                if (tic % 100 == 0) {
                    System.out.println(animal + " " + animal.name + " Hunger " + animal.GetHunger() + " State: " + animal.GetState().toString());
                }
            }
        }
    }
}

    private void drawGrid(Graphics g) {
        g.setColor(Color.white);
        for (int i = 40; i < bWidth; i += 40) {
            g.drawLine(i, 100, i, bHeight + 100);
        }
        for (int i = 100; i < bHeight + 100; i += 40) {
            g.drawLine(0, i, bWidth, i);
        }
    }

    Font medium = new Font("Helvetica", Font.PLAIN, 16);
        Font small = new Font("Helvetica", Font.PLAIN, 12);

    // Averages any trait over one species. The lambda passed in says which trait to read.
    private double averageTrait(EntityType type, ToDoubleFunction<Animal> trait)
    {
        List<Entity> group = entities.get(type.get());
        double total = 0;
        for (int i = 0; i < group.size(); i++)
        {
            total += trait.applyAsDouble((Animal) group.get(i));
        }
        return total / group.size();
    }

    private String statsLine(String label, EntityType type)
    {
        if (entities.get(type.get()).isEmpty())
        {
            return label + " extinct";
        }
        double speed = averageTrait(type, a -> a.getSpeed());
        double perception = averageTrait(type, a -> a.getPerception());
        return String.format("%s avg speed %.2f, perception %.0f", label, speed, perception);
    }

    private void drawStats(Graphics g)
    {
        g.setFont(small);
        g.setColor(Color.white);
        g.drawString(statsLine("Sheep", EntityType.sheep), 10, 90);
        g.drawString(statsLine("Wolves", EntityType.wolf), bWidth / 2, 90);
    }

    private void drawCounts(Graphics g) {
        g.setFont(medium);
        FontMetrics metrics = g.getFontMetrics();
        int yPos =  ((100 - metrics.getHeight()) / 2) + metrics.getAscent();
        int nextX = bWidth / 16;
        g.drawString("Sheep: " + entities.get(EntityType.sheep.get()).size(), nextX, yPos);
        nextX += bWidth / 4;
        g.drawString("Wolves: " + entities.get(EntityType.wolf.get()).size(), nextX, yPos);
        nextX += bWidth / 4;
        g.drawString("Flowers: " + entities.get(EntityType.flower.get()).size(), nextX, yPos);
        nextX += bWidth / 4;
        g.drawString("Grass: " + entities.get(EntityType.grass.get()).size(), nextX, yPos);
    }
    private int nextSpawn = 300;
  
    // Flower growth sttings to adjust these to balance the food supply
    private static final int SPAWN_MIN_TICKS = 120;
    private static final int SPAWN_RANGE_TICKS = 130;
    private static final int MAX_FLOWERS = 40;

    private void spawnFlowers() {
        if (tic < nextSpawn) {
            return;
        }
        List<Entity> flowers = entities.get(EntityType.flower.get());
        if (flowers.size() < MAX_FLOWERS) {
            Flower newFlower = new Flower();
            newFlower.pos = Position.genRand(bWidth, bHeight, 0, 100, 40);
            flowers.add(newFlower);
        }
        nextSpawn = tic + SPAWN_MIN_TICKS + (int)(Math.random() * SPAWN_RANGE_TICKS);
    }

// After the timer finishes do this
@Override
public void actionPerformed(ActionEvent e) {
    spawnFlowers();
    updateAnimals();
    CreateChildren();
    CleanUp();
    repaint();
    tic++;
}

       private void CreateChildren()
    {
        for(int i = 0; i < babyAnimals.size(); i++)
        {
            Animal baby = babyAnimals.get(i);
            try {
                checkOnBoard(baby);
                entities.get(baby.GetType().get()).add(baby);
            } catch (InvalidPositionException e) {
                System.out.println("Baby discarded. " + e.getMessage());
            }
        }

        babyAnimals.clear();
    }

    // throws if the entity is outside the playable area 
    private void checkOnBoard(Entity ent) throws InvalidPositionException
    {
        int x = ent.pos.getX();
        int y = ent.pos.getY();
        if (x < 0 || x > bWidth || y < 100 || y > bHeight + 100)
        {
            throw new InvalidPositionException("Position is outside the board", ent.pos);
        }
    }

    private void CleanUp()
    {
        
        for(List<Entity> subList : entities)
        {
            for(int i = subList.size() -1; i >= 0; i--)
            {
                if(!subList.get(i).IsAlive())
                {
                    subList.remove(i);
                }
            }
        }
    }


    private class KeyInput extends KeyAdapter {
        @Override 
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_R) {
                entities.clear();
                timer.stop();
                resetSimulation();
            }
        }
    }
}
