package services;

import java.util.List;

public interface InterfaceService<T> {

    public void delete(int id);

    public List<T> get_All();

    public List<T> get_by_id(int id);

}
