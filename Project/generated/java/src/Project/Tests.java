package Project;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Tests {
  public Rome2RioTest rome2Rio = new Rome2RioTest();
  private TicketingSystemTest ticketingSystem = new TicketingSystemTest();

  public void create() {

    rome2Rio.main();
    ticketingSystem.main();
  }

  public static void main() {

    new Tests().create();
  }

  public Tests() {}

  public String toString() {

    return "Tests{"
        + "rome2Rio := "
        + Utils.toString(rome2Rio)
        + ", ticketingSystem := "
        + Utils.toString(ticketingSystem)
        + "}";
  }
}
