package types;

public class Pair<F, L> {
  private F f;
  private L l;

  public Pair() {
  }

  public Pair(F f, L l) {
    this.f = f;
    this.l = l;
  }

  public F get_f() {
    return f;
  }

  public void set_f(F f) {
    this.f = f;
  }

  public L get_l() {
    return l;
  }

  public void set_l(L l) {
    this.l = l;
  }

  @Override
  public String toString() {
    return "Pair [f=" + f + ", l=" + l + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((f == null) ? 0 : f.hashCode());
    result = prime * result + ((l == null) ? 0 : l.hashCode());
    return result;
  }

}
