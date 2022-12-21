import java.util.*;

public class City{

    //Determine the City Grid based on the size of the Plotter
    public static final int WIDTH = 80;
    public static final int HEIGHT = 80;

    
    // Grid World
    //
    //       (x)
    //        0 1 2 3 4 5 ... WIDTH
    //       .----------------...
    //  (y) 0|           ,--y
    //      1|      * (3,1) 
    //      2|         ^    
    //      3|         '-x
    //      .|
    //      .|
    //      .|       
    //HEIGHT :

    public List<Creature> creatures; 
    public Queue<Creature> creaturesToAdd;
    private Random rand;
    
    public City(Random rand, int numMice, int numCats, int numZombieCats, int numZombieMice) {
        this.rand = rand;

        this.creatures = new LinkedList<Creature>();
        this.creaturesToAdd = new LinkedList<Creature>();
        
        // populate 
        for (int i=0; i<numMice; i++) addMouse();
        for (int i=0; i<numCats; i++) addCat();
        for (int i=0; i<numZombieCats; i++) addZombieCat();
        for (int i=0; i<numZombieMice; i++) addZombieMouse();
        addNewCreatures();
      
    }

    //Return the current number of creatures in the simulation
    public int numCreatures(){
        return creatures.size();
    }
    
    public void addMouse(){
        creaturesToAdd.add(new Mouse(rand.nextInt(HEIGHT),rand.nextInt(WIDTH),this,rand));
    }
    
    public void addCat(){
        creaturesToAdd.add(new Cat(rand.nextInt(HEIGHT),rand.nextInt(WIDTH),this,rand));
    }
    
    public void addZombieCat(){
        creaturesToAdd.add(new ZombieCat(rand.nextInt(HEIGHT),rand.nextInt(WIDTH),this,rand));
    }
    public void addZombieMouse(){
        creaturesToAdd.add(new ZombieMouse(rand.nextInt(HEIGHT),rand.nextInt(WIDTH),this,rand));
    }

    //queue up a create to be added
    public void addNewCreatures(){
        while(!creaturesToAdd.isEmpty()){
            creatures.add(creaturesToAdd.remove());
        }
    }

    // for all creatures - step, takeaction, find and remove dead, add new, display
    public void simulate() {

        for(Creature c : creatures){
            c.step(); 
        } 
        
        for(Creature c : creatures){
            c.takeAction(); 
        }

        LinkedList<Creature> deadCreatures = new LinkedList<Creature>();
        for(Creature c: creatures){
            if(c.isDead()) deadCreatures.add(c);
        }

        for(Creature c: deadCreatures){
            creatures.remove(c);
        }

        addNewCreatures();

        for(Creature c : creatures){
            System.out.println(c);
        }

    }
}