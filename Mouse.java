import java.util.Random;

/*After 20 rounds of simulation time, a mouse produces a new baby mouse at the same location
A mouse dies after 30 rounds simulation time
A mouse randomly turns, changes directions 20% of the time
A mouse is displayed as a blue dot. */

public class Mouse extends Creature {

    public Mouse(int x, int y, City cty, Random rnd) {
        super(x, y, cty, rnd);
        this.lab = LAB_BLUE;
    }

    //change direction 20% of the time
    @Override
    public void randomTurn() {
        if (rand.nextInt(5) == 0) {
            setDir(rand.nextInt(4));
        }
    }

    //after 20 rounds produce a new baby mouse at same location; die after 30 
    @Override
    public void takeAction() {
        if (this.roundsNum % 20 == 0) {
            Mouse baby = new Mouse(this.getX(), this.getY(), this.city, this.rand);
            city.creaturesToAdd.add(baby);
        }
        //Every 100 rounds, add a mouse
        if(roundsNum % 100 == 0) {
            city.addMouse();
        }
        //become a zombiemouse
        if (this.roundsNum == 30) {
            this.dead = true;
            ZombieMouse zM = new ZombieMouse(this.getX(), this.getY(), this.city, this.rand);
            city.creaturesToAdd.add(zM);
        }
        this.roundsNum++;
    }
}