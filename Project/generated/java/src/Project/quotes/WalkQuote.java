package Project.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class WalkQuote {
  private static int hc = 0;
  private static WalkQuote instance = null;

  public WalkQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static WalkQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new WalkQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof WalkQuote;
  }

  public String toString() {

    return "<Walk>";
  }
}
