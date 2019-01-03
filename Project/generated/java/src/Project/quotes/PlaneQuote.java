package Project.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class PlaneQuote {
  private static int hc = 0;
  private static PlaneQuote instance = null;

  public PlaneQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static PlaneQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new PlaneQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof PlaneQuote;
  }

  public String toString() {

    return "<Plane>";
  }
}
