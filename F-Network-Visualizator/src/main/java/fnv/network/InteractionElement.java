/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fnv.network;

/**
 *
 * @author enrico
 */
public class InteractionElement {
    private int source;
    private int destination;
    private double frequency;

    public InteractionElement(int source, int destination, double frequency) {
        this.source = source;
        this.destination = destination;
        this.frequency = frequency;
    }

    public int getDestination() {
        return destination;
    }

    public double getFrequency() {
        return frequency;
    }

    public int getSource() {
        return source;
    }
}
