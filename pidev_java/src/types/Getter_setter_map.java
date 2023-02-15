package types;

import java.util.HashMap;

public class Getter_setter_map extends HashMap<String, Pair<String, String>> {
  public Getter_setter_map() {
    super();
  }

  public void put(Getter_setter_pair pair) {
    super.put(pair.getKey(), pair.getValue());
  }

}
