import java.util.Random;

/*Zombie cats chase both mice and other non-zombie cats
Zombie cats can search up to 40 gird squares away (as measured by GridPoint.distance()
Zombie cats eating a mouse is the same as a normal cat. The mouse dies and is removed from the simulation.
When a zombie cat eats a cat, that cat becomes a zombie cat placed at the same location in the grid square
A zombie cat when not chasing another creature is displayed as red dot.
A zombie cat chasing another creature is displayed as a black dot
A zombie cat jumps 3 spaces at time. It does not move through the intervening space. That is, if it is at (5,10) it moves directly to (5,13).
A zombie cat that doesn’t eat anything within 100 rounds dies.*/

public class ZombieCat extends Cat{
    
    public ZombieCat(int x, int y, City city, Random rand) {
        super(x, y, city, rand);
        this.lab = LAB_RED;
        this.stepLen = 3;
    }

    @Override
    public void eat(Creature creature) {
        if (this.getX() == creature.getX() && this.getY() == creature.getY()) {
            this.roundsAction = 0;
            creature.dead = true;

            //if it eats a cat
            if (creature instanceof Cat && !(creature instanceof ZombieCat)) {
                ZombieCat zCat = new ZombieCat(this.getX(), this.getY(), this.city, this.rand);
                city.creaturesToAdd.add(zCat);
            }

            //if it eats a zombiemouse
            if (creature instanceof ZombieMouse) {
                this.dead = true;
                Cat cat = new Cat(this.getX(), this.getY(), this.city, this.rand);
                city.creaturesToAdd.add(cat);
            }
        }
    }

    @Override
    public void takeAction() {
    //A zombie cat that doesn’t eat anything within 100 rounds dies.    
        if (this.roundsAction == 100) {
            this.dead = true;
        }

        this.roundsAction++;
        this.roundsNum++;

        search();
    }

    @Override 
    public boolean search() {
        //loop through all creatures and get their distance
        int distance = 40;
        Creature chased = null;
        for (Creature c : city.creatures) {
            int d = this.dist(c);
            if (c instanceof Mouse || (c instanceof Cat && !(c instanceof ZombieCat)) && d <= distance) {
                chased = c;
                distance = d;
                this.lab = LAB_BLACK;
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
        this.lab = LAB_RED;
        return false;
    }

}