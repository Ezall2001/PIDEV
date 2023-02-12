package types;

public class Tuple<P, D> {
  private P p;
  private D d;

  public Tuple() {
  }

  public Tuple(P p, D d) {
    this.p = p;
    this.d = d;
  }

  public P get_p() {
    return p;
  }

  public void set_p(P p) {
    this.p = p;
  }

  public D get_d() {
    return d;
  }

  public void set_d(D d) {
    this.d = d;
  }

  @Override
  public String toString() {
    return "Tuple [p=" + p + ", d=" + d + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((p == null) ? 0 : p.hashCode());
    result = prime * result + ((d == null) ? 0 : d.hashCode());
    return result;
  }

}
