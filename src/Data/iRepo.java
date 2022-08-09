package Data;

public interface iRepo<E, ID> {
    E[] findAll();
    E find(ID id);
    E add(E item);
    E update(E item);
    E delete(ID id);
}
