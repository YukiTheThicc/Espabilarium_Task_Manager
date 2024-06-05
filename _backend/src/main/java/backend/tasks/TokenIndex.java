package backend.tasks;

import java.util.concurrent.ConcurrentHashMap;

/**
 * TokenIndex
 *
 * @author Santiago Barreiro
 */
public class TokenIndex {

    // CONSTANTS


    // ATTRIBUTES
    private final ConcurrentHashMap<String, IndexNode> tokenMap;

    // CONSTRUCTORS
    public TokenIndex(ConcurrentHashMap<String, IndexNode> tokenMap) {
        this.tokenMap = tokenMap;
    }

    // GETTERS & SETTERS


    // METHODS
    public void updateIndex(String text, Object obj) {

    }

    private static class IndexNode {

        private ConcurrentHashMap<String, IndexNode> nodes;
    }
}
