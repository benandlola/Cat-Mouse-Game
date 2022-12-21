import java.util.Random;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Simulator {
	
    public static void main(String[] args) {

        final String USAGE = "java Simulator numMice numCats numZombieCats numZombieMice rounds [randSeed]";

        boolean DEBUG = false;
        
        //parse arguments
        if(args.length < 5){
            System.out.println("ERROR: missing arguments");
            System.out.println(USAGE);
            System.exit(1);
        }
        int numMice = Integer.parseInt(args[0]);
        int numCats = Integer.parseInt(args[1]);
        int numZombieCats = Integer.parseInt(args[2]);
        int numZombieMice = Integer.parseInt(args[3]);
        int rounds = Integer.parseInt(args[4]);

        Random rand;
        if(args.length > 5)
            rand = new Random(Integer.parseInt(args[5]));
        else
            rand = new Random(100);

        if(args.length > 6 && args[6].equals("--DEBUG")){
            DEBUG=true;
        }

        // Populate city with mice, cats, zombiecats, and zombiemice
        City city= new City(rand, numMice, numCats, numZombieCats, numZombieMice);
        int count = 0;

        while (count < rounds) {
            count++;
            
            city.simulate();
            System.out.println("done "+ count);
            System.out.flush();

            if(DEBUG){
                System.err.print("Enter anything to continue: ");
                try{
                    (new BufferedReader(new InputStreamReader(System.in))).readLine();
                }catch(Exception e){
                    System.exit(1);
                }
            }
        }
    }
}