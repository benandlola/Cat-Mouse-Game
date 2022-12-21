import java.util.Random;

/*it will search out other mice and eat them, turning them into zombie mice. If it is eaten by a Cat it will turn that cat into a ZombieCat, and if eaten by the latter it will turn it into a regular cat and create a new mouse. It will also utilize polymorphism to have its own distinct actions and steps.
*/

public class ZombieMouse extends Mouse{

    public ZombieMouse(int x, int y, City cty, Random rnd) {
        super(x, y, cty, rnd);
        this.lab = LAB_ORANGE;
        this.stepLen = 2;
    }

    public void eat(Creature creature) {
        if ((this.getX() == creature.getX() && this.getY() == creature.getY()) && (creature instanceof Mouse && !(creature instanceof ZombieMouse))) {
            this.roundsAction = 0;
            creature.dead = true;
        }
    }

    public void takeAction() {
        //A zombie mouse that doesn’t eat anything within 40 rounds dies.    
            if (this.roundsAction == 40) {
                this.dead = true;
            }
    
            this.roundsAction++;
            this.roundsNum++;
    
            search();
        }

    public boolean search() {
        //loop through all creatures and get their distance
        int distance = 30;
        Creature chased = null;
        for (Creature c : city.creatures) {
            int d = this.dist(c);
            if ((c instanceof Mouse && !(c instanceof ZombieMouse)) && d <= distance) {
                chased = c;
                distance = d;
                this.lab = LAB_GREEN;
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
        this.lab = LAB_ORANGE;
        return false;
    }

}