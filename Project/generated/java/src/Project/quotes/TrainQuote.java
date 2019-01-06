package Project.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class TrainQuote {
  private static int hc = 0;
  private static TrainQuote instance = null;

  public TrainQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static TrainQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new TrainQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof TrainQuote;
  }

  public String toString() {

    return "<Train>";
  }
}
