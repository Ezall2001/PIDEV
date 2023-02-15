package types;

public class Getter_setter_pair extends javafx.util.Pair<String, Pair<String, String>> {

  public Getter_setter_pair(String nom_col, String nom_getter, String nom_setter) {
    super(nom_col, new Pair<String, String>(nom_getter, nom_setter));
  }

}
