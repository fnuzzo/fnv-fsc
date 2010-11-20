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
    public int source;
    public int target;
    public double frequency;

    public InteractionElement(int source, int target, double frequency) {
        this.source = source;
        this.target = target;
        this.frequency = frequency;
    }

    public int getTarget() {
        return target;
    }

    public double getFrequency() {
        return frequency;
    }

    public int getSource() {
        return source;
    }
}
