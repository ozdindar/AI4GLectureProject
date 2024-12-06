package live.boardgames.ai;

import java.util.HashMap;

public class TranspositionTable<TableEntry> {

    HashMap<Long,TableEntry> table= new HashMap<>();

    public void put(long key, TableEntry entry)
    {
        table.put(key,entry);
    }


    public boolean contains(long key)
    {
        return table.containsKey(key);
    }

    public TableEntry get(long key)
    {
        return table.get(key);
    }

    public void remove(long key) {
        table.remove(key);
    }
}
