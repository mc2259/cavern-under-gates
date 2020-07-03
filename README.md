# cavern-under-gates

Cavern under gates is a game that calculates the shortest path that allows a player to scram from the start to the end of a maze and allows the player

A game that finds the shortest path from the start to the end so that the player to scram (get away from) a collapsing cavern 

## Find-the-Orb Phase

On the way to the Orb (see Fig. 1 below), the layout of the cavern is unknown. Pres Pollack
knows only the status of the tile on which she is standing and the immediately surrounding ones (and
perhaps those that she remembers). Her goal is to make it to the Orb in as few steps as possible.
This is not a blind search, however. For each tile, the distance to the Orb is known, and this can be used
greedily to optimize the find algorithm, but the greediness does not always work.

<img width="336" alt="Screenshot 2020-07-03 at 1 32 05 PM" src="https://user-images.githubusercontent.com/57819870/86446591-c8e94780-bd31-11ea-8292-ddaef4686fc4.png">

##  Scram Phase

After picking up the Orb, the walls of the cavern shift and a new layout is generated. Additionally, piles of
gold fall onto the ground. Luckily, underneath the Orb is a map, which reveals the full cavern. However,
2
the stress of the moving walls has compromised the integrity of the cavern, beginning a step limit after
which the ceiling will collapse. Additionally, picking up the Orb activated the traps and puzzles of the
cavern, causing different edges of the graph to have different weights.

<img width="397" alt="Screenshot 2020-07-03 at 3 01 29 PM" src="https://user-images.githubusercontent.com/57819870/86456432-aeb66600-bd3f-11ea-8b76-c3147db91cc7.png">

The goal of the scram phase is to run to the exit from the cavern before it collapses. A score component
is based on two additional factors:
1. The amount of gold that Pres Pollack picks up during the scram phase, and
2. The score multiplier from the find-the-Orb phase.

Pres Pollack’s score will be the amount of gold picked up times the score multiplier from the findthe-Orb phase. 
The person with the greatest score wins.

In order to get out of the cavern,  we need to return while standing on the exit. Returning while at any
other position, or failing to return within the required number of steps, causes the application to end with
a score of 0.

The cavern ceiling will collapse after Pres Pollack has taken a fixed number of steps.
The steps remaining is decremented by the weight of the edge traversed when making a move, regardless
of how long Pres Pollack spent deciding which move to make. You are guaranteed that Pres Pollack can get
out of the cavern if she takes the shortest path out.
## How to play 
Run GUI.java

## Running in headless Mode

Running from GameState.java runs the program in headless
mode (without a GUI); running it from GUI.java runs it with an accompanying display, which may be
helpful for debugging. By default, each of these runs a single map on a random seed.

Two optional flags can be used to run the program in different ways.

1. -n ( count )runs the program multiple times. This option is available only in headless mode; it is
ignored if run with the GUI. Output will still be written to the console for each map so you know
how well you did, and an average score will be provided at the end. This is helpful for running your
solution many times and comparing different solutions on a large number of maps.
  
  
2. -s ( seed) : runs the program with a predefined seed. This allows you to test your solutions on
particular maps that can be challenging or that you might be failing on. It is helpful for debugging.
This can be used both with the GUI and in headless mode.

For example:

<img width="506" alt="Screenshot 2020-07-03 at 3 31 45 PM" src="https://user-images.githubusercontent.com/57819870/86458212-8a0fbd80-bd42-11ea-85e2-baff24530e27.png">

<img width="495" alt="Screenshot 2020-07-03 at 3 31 40 PM" src="https://user-images.githubusercontent.com/57819870/86458223-8da34480-bd42-11ea-8e27-fba02b1ac647.png">

