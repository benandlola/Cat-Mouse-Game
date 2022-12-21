import java.util.Random;

/*A cat eats a mouse if they end up on the same location. That is, the mouse should die and be removed from the simulation.
If a cat doesn’t eat a mouse within 50 moves, the cat dies.
Cats jump two spaces at a time. They do not traverse the grid point they jump over. That is, if they are on space (1,2) they would move to (1,4).
Cats randomly turn, change direction, 5% of the time.
Cats are displayed as a yellow dot.
If Cat does not eat within 50 rounds, they instead turn into a Zombie Cat.*/

public class Cat extends Creature{

    public Cat(int x, int y, City cty, Random rnd) {
        super(x, y, cty, rnd);
        this.lab = LAB_YELLOW;
        this.stepLen = 2; //jump 2 spaces at a time
    }

    //change direction 5% of the time
    @Override
    public void randomTurn() {
        if (rand.nextInt(20) == 0) {
            setDir(rand.nextInt(4));
        }
    }

    //eat a mouse if on the same location
    public void eat(Creature creature) {
        if (this.getX() == creature.getX() && this.getY() == creature.getY() && creature instanceof Mouse) {
            this.roundsAction = 0;
            creature.dead = true;

            if (creature instanceof ZombieMouse) {
                this.dead = true;
                ZombieCat zCat = new ZombieCat(this.getX(), this.getY(), this.city, this.rand);
                city.creaturesToAdd.add(zCat);
                city.addMouse();
            }
        }
    }

    //if a cat doesn't eat a mouse in 50 rounds it dies
    @Override
    public void takeAction() {
        if (this.roundsAction == 50) {
            this.dead = true;
            ZombieCat cat = new ZombieCat(this.getX(), this.getY(), this.city, this.rand);
            city.creaturesToAdd.add(cat);
        }
        if(roundsNum % 25 == 0) {
            city.addCat(); 
        }
        this.roundsNum++;
        this.roundsAction++;

        search();
    }

    //A cat searches up to 20 grid points (as measured by the GridPoint.distance() method) for a mouse to chase.
    //If the cat finds a mouse, it moves towards the mouse and is displayed using the color cyan. (This is to make it easier for you to debug, and for us to grade).
    //If the cat cannot find a mouse, it moves normally and is displayed in yellow.
    public boolean search() {
        //loop through all creatures and get their distance
        int distance = 20;
        Creature chased = null;
        for (Creature c : city.creatures) {
            int d = this.dist(c);
            if (c instanceof Mouse && d <= distance) {
                chased = c;
                distance = d;
                this.lab = LAB_CYAN;
            }
        }

        if (chased != null) {
            //chasing - find the longest distance to the chased and set direction to it
            if (Math.abs(this.getX() - chased.getX()) > Math.abs(this.getY() - chased.getY())) {
                //if creature is right of chased turn west, otherwise turn east
                if (this.getX() > chased.getX()) {
                    this.setDir(WEST);
                }
                else {
                    this.setDir(EAST);
                }
            }
            else {
                //if crerature is below chased turn north, otherwise turn south
                if (this.getY() > chased.getY()) {
                    this.setDir(NORTH);
                }
                else {
                    this.setDir(SOUTH);
                }
            }
            eat(chased);
            return true;
        }

        //if it didn't find a creature to chase
        this.lab = LAB_YELLOW;
        return false;
    }
}

