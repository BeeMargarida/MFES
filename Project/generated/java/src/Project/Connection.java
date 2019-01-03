package Project;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Connection {
  public Object type;
  public Station source;
  public Station destination;
  public Number distance;
  public Number price;
  public Number duration;
  public VDMSeq timetable;
  public Number seatsAvailable = 0L;
  public VDMSeq calculatedVariables;

  public void cg_init_Connection_1(
      final Object t,
      final Station s,
      final Station d,
      final Number dist,
      final VDMSeq ttbl,
      final Number seats) {

    type = t;
    source = s;
    destination = d;
    distance = dist;
    price = getPrice(((Object) t), dist);
    duration = getDuration(((Object) t), dist);
    timetable = Utils.copy(ttbl);
    calculatedVariables = SeqUtil.seq(distance, price, duration);
    seatsAvailable = seats;
    return;
  }

  public Connection(
      final Object t,
      final Station s,
      final Station d,
      final Number dist,
      final VDMSeq ttbl,
      final Number seats) {

    cg_init_Connection_1(t, s, d, dist, Utils.copy(ttbl), seats);
  }

  private Number getPrice(final Object t, final Number dist) {

    Number priceKm = 0.0;
    Object quotePattern_1 = t;
    Boolean success_1 = Utils.equals(quotePattern_1, Project.quotes.PlaneQuote.getInstance());

    if (!(success_1)) {
      Object quotePattern_2 = t;
      success_1 = Utils.equals(quotePattern_2, Project.quotes.BusQuote.getInstance());

      if (!(success_1)) {
        Object quotePattern_3 = t;
        success_1 = Utils.equals(quotePattern_3, Project.quotes.TrainQuote.getInstance());

        if (!(success_1)) {
          Object quotePattern_4 = t;
          success_1 = Utils.equals(quotePattern_4, Project.quotes.WalkQuote.getInstance());

          if (success_1) {
            priceKm = 0L;
          }

        } else {
          priceKm = 0.07;
        }

      } else {
        priceKm = 0.1;
      }

    } else {
      priceKm = 0.06;
    }

    return priceKm.doubleValue() * dist.doubleValue();
  }

  private Number getDuration(final Object t, final Number dist) {

    Number speed = 0.0;
    Object quotePattern_5 = t;
    Boolean success_2 = Utils.equals(quotePattern_5, Project.quotes.PlaneQuote.getInstance());

    if (!(success_2)) {
      Object quotePattern_6 = t;
      success_2 = Utils.equals(quotePattern_6, Project.quotes.BusQuote.getInstance());

      if (!(success_2)) {
        Object quotePattern_7 = t;
        success_2 = Utils.equals(quotePattern_7, Project.quotes.TrainQuote.getInstance());

        if (!(success_2)) {
          Object quotePattern_8 = t;
          success_2 = Utils.equals(quotePattern_8, Project.quotes.WalkQuote.getInstance());

          if (success_2) {
            speed = 4L;
          }

        } else {
          speed = 100L;
        }

      } else {
        speed = 80L;
      }

    } else {
      speed = 760L;
    }

    return Utils.divide(dist.doubleValue(), speed.doubleValue());
  }

  public Number getAvailableSeats() {

    return seatsAvailable;
  }

  public void decreaseNumberOfSeats(final Number numSeats) {

    seatsAvailable = seatsAvailable.longValue() - numSeats.longValue();
  }

  public Connection() {}

  public String toString() {

    return "Connection{"
        + "type := "
        + Utils.toString(type)
        + ", source := "
        + Utils.toString(source)
        + ", destination := "
        + Utils.toString(destination)
        + ", distance := "
        + Utils.toString(distance)
        + ", price := "
        + Utils.toString(price)
        + ", duration := "
        + Utils.toString(duration)
        + ", timetable := "
        + Utils.toString(timetable)
        + ", seatsAvailable := "
        + Utils.toString(seatsAvailable)
        + ", calculatedVariables := "
        + Utils.toString(calculatedVariables)
        + "}";
  }
}
