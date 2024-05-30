package backend.tasks;

import backend.utils.IntKey;

import java.util.Arrays;

/**
 * ComponentIndex
 *
 * @author Santiago Barreiro
 */
public class ComponentIndex {

    // CONSTANTS
    public static final int MAX_COMPONENTS = 1 << 8;

    // ATTRIBUTES
    // Starting value for the index
    private int index = 1;
    // Used to map each class to an Integer value
    private final ClassValue<Integer> classIndex = new ClassValue<>() {
        @Override
        protected Integer computeValue(Class<?> type) {
            return index++;
        }
    };

    // METHODS
    public int get(Class<?> type) {
        return classIndex.get(type);
    }

    public int[] getIndexArray(Class<?>[] types) {
        int[] index = new int[MAX_COMPONENTS];
        Arrays.fill(index, -1);
        for (int i = 0; i < types.length; i++) {
            index[classIndex.get(types[i])] = i;
        }
        return index;
    }

    public Class<?>[] getComponentClasses(Object[] components) {
        Class<?>[] componentTypes = new Class<?>[components.length];
        for (int i = 0; i < components.length; i++) componentTypes[i] = components[i].getClass();
        return componentTypes;
    }

    public IntKey getCompositionKey(Class<?>[] components) {
        int length = components.length;
        boolean[] classSlots = new boolean[index + length + 1];
        int begin = Integer.MAX_VALUE;
        int end = 0;
        for (Class<?> component : components) {
            int value = classIndex.get(component);
            if (classSlots[value]) {
                throw new IllegalArgumentException("Component types can't repeat within one composition is not allowed");
            } else {
                classSlots[value] = true;
            }
            begin = Math.min(value, begin);
            end = Math.max(value, end);
        }
        return new IntKey(classSlots, begin, end, length);
    }

    public IntKey getCompositionKey(Object[] components) {
        return getCompositionKey(getComponentClasses(components));
    }
}
