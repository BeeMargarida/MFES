package Project;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Station {
  public String name;
  public Number arrivalTime = 0L;
  private VDMSeq calculatedVariables = SeqUtil.seq(0L, 0L, 0L);
  public Object meanOfTransportationUsed = Project.quotes.NONEQuote.getInstance();
  private Number seatsAvailable = 0L;

  public void cg_init_Station_1(final String n) {

    name = n;
    return;
  }

  public Station(final String n) {

    cg_init_Station_1(n);
  }

  public void setCalculatedVariables(final VDMSeq vars) {

    calculatedVariables = Utils.copy(vars);
  }

  public void setArrivalTime(final Number time) {

    arrivalTime = time;
  }

  public VDMSeq getCalculatedVariables() {

    return Utils.copy(calculatedVariables);
  }

  public void setMeanOfTransportationUsed(final Object type) {

    meanOfTransportationUsed = type;
  }

  public Object getMeanOfTransportationUsed() {

    return meanOfTransportationUsed;
  }

  public Number getAvailableSeats(final VDMSet connections, final Station prevStation) {

    Number seats = 0L;
    for (Iterator iterator_16 = connections.iterator(); iterator_16.hasNext(); ) {
      Connection con = (Connection) iterator_16.next();
      Boolean andResult_15 = false;

      if (stringEqual(prevStation.name, con.source.name)) {
        Boolean andResult_16 = false;

        if (stringEqual(name, con.destination.name)) {
          if (Utils.equals(con.type, meanOfTransportationUsed)) {
            andResult_16 = true;
          }
        }

        if (andResult_16) {
          andResult_15 = true;
        }
      }

      if (andResult_15) {
        seats = con.getAvailableSeats();
      }
    }
    return seats;
  }

  public void decreaseAvailableSeats(
      final VDMSet connections, final Station prevStation, final Number seatsToBuy) {

    Number seats = 0L;
    for (Iterator iterator_17 = connections.iterator(); iterator_17.hasNext(); ) {
      Connection con = (Connection) iterator_17.next();
      Boolean andResult_17 = false;

      if (stringEqual(prevStation.name, con.source.name)) {
        Boolean andResult_18 = false;

        if (stringEqual(name, con.destination.name)) {
          if (Utils.equals(con.type, meanOfTransportationUsed)) {
            andResult_18 = true;
          }
        }

        if (andResult_18) {
          andResult_17 = true;
        }
      }

      if (andResult_17) {
        con.decreaseNumberOfSeats(seatsToBuy);
      }
    }
  }

  private Boolean stringEqual(final String s1, final String s2) {

    if (!(Utils.equals(s1.length(), s2.length()))) {
      return false;
    }

    long toVar_3 = s1.length();

    for (Long idx = 1L; idx <= toVar_3; idx++) {
      if (!(Utils.equals(s1.charAt(Utils.index(idx)), s2.charAt(Utils.index(idx))))) {
        return false;
      }
    }
    return true;
  }

  public Station() {}

  public String toString() {

    return "Station{"
        + "name := "
        + Utils.toString(name)
        + ", arrivalTime := "
        + Utils.toString(arrivalTime)
        + ", calculatedVariables := "
        + Utils.toString(calculatedVariables)
        + ", meanOfTransportationUsed := "
        + Utils.toString(meanOfTransportationUsed)
        + ", seatsAvailable := "
        + Utils.toString(seatsAvailable)
        + "}";
  }
}
