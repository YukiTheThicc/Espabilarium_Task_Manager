package backend.utils;

import java.util.Arrays;

/**
 * IntKey
 *
 * @author Santiago Barreiro
 */
public class IntKey {

    // CONSTANTS
    public static final int HASH_FACTOR = 31;       // Hash factor to obtain the hash from

    // ATTRIBUTES
    private final int hashCode;                     // Hash code obtained for the composition
    private final byte[] compTypes;                 // Array of byte values for the component types of this composition

    // CONSTRUCTORS
    public IntKey(boolean[] classSlots, int begin, int end, int size) {
        long result = 1;
        int index = 0;
        compTypes = new byte[size];
        for (int i = begin; i <= end; i++) {
            if (classSlots[i]) {
                result = result * HASH_FACTOR + i;
                compTypes[index++] = (byte) (i ^ (i >>> 8));
            }
        }
        hashCode = (int) (result ^ (result >>> 32));
    }

    // METHODS
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Arrays.equals(((IntKey) o).compTypes, compTypes);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return "CompositionHash => { " +
                "hashCode = " + hashCode + ", " +
                "data = " + Arrays.toString(compTypes) +
                " }";
    }
}
