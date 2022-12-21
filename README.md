# Cat-Mouse-Game
This game involves cat chasing mice, and they all extend from the Creature class which holds methods like getters, setters, step, and distance.

Mice are displayed as blue dots that travel the plot and give birth to new mice every 20 rounds, dying after 30.
Cats are displayed as yellow dots that search for the closest mouse and chases it, turning cyan. If it ends up on the same tile as a mouse it eats it, but it dies if it hasn't eaten in a certain amount of time, turning into a ZombieCat
ZombieCats chase both cats and mice and are displayed as a red dot when not chasing, and a black one when chasing. 
ZombieMice are are dead mice that search out regular mice eating them. If they are eaten by a ZombieCat they turn into a regular Cat and if eaten by a regular Cat it will turn into a ZombieCat

Running the program requires this format:
java Simulator Mice Cats ZombieCats ZombieMice rounds seed | java -jar Plotter.jar milliseconds
Mice, Cats, ZombieCats, and ZombieMice are all integers, representing the starting number of them on the plot. 