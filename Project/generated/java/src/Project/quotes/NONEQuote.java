package Project.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class NONEQuote {
  private static int hc = 0;
  private static NONEQuote instance = null;

  public NONEQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static NONEQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new NONEQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof NONEQuote;
  }

  public String toString() {

    return "<NONE>";
  }
}
