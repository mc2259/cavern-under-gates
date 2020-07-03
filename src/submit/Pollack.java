/** NetId(s): djg17, rm725, mc2259
 * Name(s): David Gries, Rishi Malhotra, Maitreyi Chatterjee
 *
 *
 *
 */
package submit;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import game.FindState;
import game.Finder;
import game.Node;
import game.NodeStatus;
import game.ScramState;

/** Student solution for two methods. */
public class Pollack extends Finder {

    /** Get to the orb in as few steps as possible. <br>
     * Once you get there, you must return from the function in order to pick it up. <br>
     * If you continue to move after finding the orb rather than returning, it will not count.<br>
     * If you return from this function while not standing on top of the orb, it will count as <br>
     * a failure.
     *
     * There is no limit to how many steps you can take, but you will receive<br>
     * a score bonus multiplier for finding the orb in fewer steps.
     *
     * At every step, you know only your current tile's ID and the ID of all<br>
     * open neighbor tiles, as well as the distance to the orb at each of <br>
     * these tiles (ignoring walls and obstacles).
     *
     * In order to get information about the current state, use functions<br>
     * state.currentLoc(), state.neighbors(), and state.distanceToOrb() in FindState.<br>
     * You know you are standing on the orb when distanceToOrb() is 0.
     *
     * Use function state.moveTo(long id) in FindState to move to a neighboring<br>
     * tile by its ID. Doing this will change state to reflect your new position.
     *
     * A suggested first implementation that will always find the orb, but <br>
     * likely won't receive a large bonus multiplier, is a depth-first walk. <br>
     * Some modification is necessary to make the search better, in general. */

    /** Whether each node of the graph has been visited during a search. */

    HashMap<Long, Boolean> visited= new HashMap<>();

    /** Calls helper methods to keep on walking till we find the orb and stop immediately. */
    @Override
    public void findOrb(FindState state) {
        // TODO 1: Get the orb

        dfswalk(state);

    }

    /** implements a dfs walk recursively by using the id of the current location and the id of its
     * neighbors w. Visit every node reachable along a path of unvisited nodes from node u */
    public void dfswalk(FindState state) {
        int distToOrb= state.distanceToOrb();
        if (distToOrb == 0)
            return;
        long u= state.currentLoc();
        visit(u);
        Collection<NodeStatus> nbrs= sort(state);
        for (NodeStatus w : nbrs) {
            if (visited.get(w.getId()) == null) {

                state.moveTo(w.getId());

                dfswalk(state);

                if (state.distanceToOrb() == 0) { return; }

                state.moveTo(u);
            }
        }

    }

    /** sorts the neighbors in ascending order of distance to optimise greedily */
    private Collection<NodeStatus> sort(FindState state) {
        Collection<NodeStatus> nbrs= state.neighbors();
        NodeStatus[] n= nbrs.toArray(new NodeStatus[nbrs.size()]);
        Arrays.sort(n, (b, c) -> b.compareTo(c));
        return Arrays.asList(n);

    }

    /** Change the status of an id to visited in the visited Hashmap */
    private void visit(long n) {

        visited.put(n, true);

    }

    /** Pres Pollack is standing at a node given by parameter state.<br>
     *
     * Get out of the cavern before the ceiling collapses, trying to collect as <br>
     * much gold as possible along the way. Your solution must ALWAYS get out <br>
     * before time runs out, and this should be prioritized above collecting gold.
     *
     * You now have access to the entire underlying graph, which can be accessed <br>
     * through parameter state. <br>
     * state.currentNode() and state.getExit() will return Node objects of interest, and <br>
     * state.allNodes() will return a collection of all nodes on the graph.
     *
     * The cavern will collapse in the number of steps given by <br>
     * state.stepsLeft(), and for each step this number is decremented by the <br>
     * weight of the edge taken. <br>
     * Use state.stepsLeft() to get the time still remaining, <br>
     * Use state.moveTo() to move to a destination node adjacent to your current node.<br>
     * Do not call state.grabGold(). Gold on a node is automatically picked up <br>
     * when the node is reached.<br>
     * s The method must return from this function while standing at the exit. <br>
     * Failing to do so before time runs out or returning from the wrong <br>
     * location will be considered a failed run.
     *
     * You will always have enough time to scram using the shortest path from the <br>
     * starting position to the exit, although this will not collect much gold. <br>
     * For this reason, using the shortest path method to calculate the shortest <br>
     * path to the exit is a good starting solution */
    @Override
    public void scram(ScramState state) {
        // TODO 2: scram

        Node start= state.currentNode();
        Node end= state.getExit();
        int stepsLeft= state.stepsLeft(); // the number of steps left

        List<Node> route= Path.pathToNearestGold(start, end, state, stepsLeft);
        Node n1= start;

////    Invariant pt 1: route contains the shortest path from start to the closest node
////    containing gold
//
////   Invariant pt2 : n1 is the last node in path or start if path is empty
////
////   Invariant pt2 : stepsLeft is the number of steps PresPollack can take
////     from the last node in the route
        while (route != null) { // route never the empty list
            // Visiting all nodes except the first in route.
            stepsLeft-= Path.pathSum(route);
            traverse(route, state);
            n1= route.get(route.size() - 1); // get the last node in the nodes added
            route= Path.pathToNearestGold(n1, end, state, stepsLeft);
        }
        List<Node> routeToEnd= Path.shortest(n1, end);// n1 is start if route is null
        traverse(routeToEnd, state);
    }

    /** visits all nodes except the first in the route */
    public void traverse(List<Node> route, ScramState state) {
        route.remove(0);
        for (Node a : route) {
            // System.out.println(a.getId() + " " + a.getTile().gold());
            state.moveTo(a);
        }
    }

}
