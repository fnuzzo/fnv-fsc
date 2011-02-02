/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fnv.network;

import java.io.Serializable;

/**
 *
 * @author enrico
 */
public class InteractionElement implements Serializable {
    public final int source;
    public final int target;
    public final int quantity;
    public final float frequency;

    public InteractionElement(int source, int target, int quantity, float frequency) {
        this.source = source;
        this.target = target;
	this.quantity = quantity;
        this.frequency = frequency;
    }
}
