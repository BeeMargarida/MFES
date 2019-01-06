package Project.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class BusQuote {
  private static int hc = 0;
  private static BusQuote instance = null;

  public BusQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static BusQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new BusQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof BusQuote;
  }

  public String toString() {

    return "<Bus>";
  }
}
