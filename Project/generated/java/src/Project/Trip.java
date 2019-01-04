package Project;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Trip {
  protected VDMSeq segments;
  public VDMSeq finalResults;
  protected Number availableSeatsForTrip = Utilities.MAX_INT;

  public void cg_init_Trip_1(final VDMSeq segs) {

    segments = Utils.copy(segs);
    finalResults = SeqUtil.seq();
    return;
  }

  public Trip(final VDMSeq segs) {

    cg_init_Trip_1(Utils.copy(segs));
  }

  public void addSegment(
      final String origin,
      final VDMSeq distValues,
      final Object meanType,
      final Number seatsAvailable) {

    Segment segment =
        new Segment(
            origin,
            ((Number) Utils.get(distValues, 3L)),
            ((Number) Utils.get(distValues, 1L)),
            ((Number) Utils.get(distValues, 2L)),
            ((Object) meanType),
            seatsAvailable);
    segments = SeqUtil.conc(Utils.copy(segments), SeqUtil.seq(Utils.copy(segment)));
  }

  public void addSegmentFirst(
      final String origin, final VDMSeq distValues, final Number seatsAvailable) {

    Segment segment =
        new Segment(
            origin,
            ((Number) Utils.get(distValues, 3L)),
            ((Number) Utils.get(distValues, 1L)),
            ((Number) Utils.get(distValues, 2L)),
            Project.quotes.NONEQuote.getInstance(),
            seatsAvailable);
    segments = SeqUtil.conc(Utils.copy(segments), SeqUtil.seq(Utils.copy(segment)));
  }

  public VDMSeq getSegments() {

    return Utils.copy(segments);
  }

  public void setFinalResults(final VDMSeq results, final Number arrivalTime) {

    finalResults =
        SeqUtil.seq(
            ((Number) Utils.get(results, 1L)), ((Number) Utils.get(results, 2L)), arrivalTime);
  }

  public Number getAvailableSeats() {

    availableSeatsForTrip = Utilities.MAX_INT;
    long toVar_5 = segments.size();

    for (Long idx = 2L; idx <= toVar_5; idx++) {
      Boolean andResult_21 = false;

      if (((Segment) Utils.get(segments, idx)).seatsAvailable.longValue() >= 0L) {
        if (((Segment) Utils.get(segments, idx)).seatsAvailable.longValue()
            <= availableSeatsForTrip.doubleValue()) {
          andResult_21 = true;
        }
      }

      if (andResult_21) {
        availableSeatsForTrip = ((Segment) Utils.get(segments, idx)).seatsAvailable;
      }
    }
    return availableSeatsForTrip;
  }

  public void discountAvailableSeats(final Number nrSeatsToBuy, final TransportGraph transportMap) {

    Number index = 1L;
    VDMSeq newSegments = SeqUtil.seq();
    for (Iterator iterator_23 = segments.iterator(); iterator_23.hasNext(); ) {
      Segment seg = (Segment) iterator_23.next();
      {
        if (!(Utils.equals(index, 1L))) {
          Station station = getSegmentStation(transportMap, seg.startCity);
          Station prevStation =
              getSegmentStation(
                  transportMap, ((Segment) Utils.get(segments, index.longValue() - 1L)).startCity);
          station.decreaseAvailableSeats(transportMap.listConnections(), prevStation, nrSeatsToBuy);
          newSegments =
              SeqUtil.conc(
                  Utils.copy(newSegments),
                  SeqUtil.seq(
                      new Segment(
                          seg.startCity,
                          seg.timeDuration,
                          seg.distance,
                          seg.price,
                          ((Object) seg.meanOfTransport),
                          seg.seatsAvailable.longValue() - nrSeatsToBuy.longValue())));

        } else {
          newSegments = SeqUtil.conc(Utils.copy(newSegments), SeqUtil.seq(Utils.copy(seg)));
        }

        index = index.longValue() + 1L;
      }
    }
    segments = Utils.copy(newSegments);
  }

  private Station getSegmentStation(final TransportGraph transportMap, final String stationName) {

    Station stationRes = null;
    for (Iterator iterator_24 = transportMap.listStations().iterator(); iterator_24.hasNext(); ) {
      Station station = (Station) iterator_24.next();
      if (stringEqual(station.name, stationName)) {
        stationRes = station;
      }
    }
    return stationRes;
  }

  public Number totalPrice() {

    return ((Number) Utils.get(finalResults, 2L));
  }

  private Boolean stringEqual(final String s1, final String s2) {

    if (!(Utils.equals(s1.length(), s2.length()))) {
      return false;
    }

    long toVar_6 = s1.length();

    for (Long idx = 1L; idx <= toVar_6; idx++) {
      if (!(Utils.equals(s1.charAt(Utils.index(idx)), s2.charAt(Utils.index(idx))))) {
        return false;
      }
    }
    return true;
  }

  public Trip() {}

  public static Boolean isMinSeatAvailable(
      final VDMSeq segments, final Number availableSeatsForTrip) {

    throw new UnsupportedOperationException();
  }

  public String toString() {
	String result = "";
	double duration;
	double distance;
	double price;
	int availableSeats;
	int availableSeatsTrip = Integer.MAX_VALUE;
	String prev = "";
	
	for (int i = 0; i < segments.size(); i++) {
		if (i == 0) {
			prev = ((Segment) segments.get(i)).startCity;
		} else {
			duration = (double) ((Segment) segments.get(i)).timeDuration;
			distance = (double) ((Segment) segments.get(i)).distance;
			price = (double) ((Segment) segments.get(i)).price;
			availableSeats = ((Segment) segments.get(i)).seatsAvailable.intValue();
			
			if (availableSeats <= availableSeatsTrip)
				availableSeatsTrip = availableSeats;
			
			result = result + prev + " -> ";
			result += ((Segment) segments.get(i)).startCity;
			result += " by " + ((Segment) segments.get(i)).meanOfTransport
//					+ "; duration: " + String.format("%.2f", duration)
//					+ "; distance: " + String.format("%.2f", distance)
//					+ "; price: " + String.format("%.2f", price)
					+ "; available seats: " + availableSeats
					+ "\n";
		}
		prev = ((Segment) segments.get(i)).startCity;
	}
	result += "\nTotal distance: " + String.format("%.2f", finalResults.get(0))
			+ "\nTotal price: " + String.format("%.2f", finalResults.get(1))
			+ "\nTotal duration: " + String.format("%.2f", finalResults.get(2))
			+ "\nAvailable seats: " + availableSeatsTrip;
	return result;
  }

  public static class Segment implements Record {
    public String startCity;
    public Number timeDuration;
    public Number distance;
    public Number price;
    public Object meanOfTransport;
    public Number seatsAvailable;

    public Segment(
        final String _startCity,
        final Number _timeDuration,
        final Number _distance,
        final Number _price,
        final Object _meanOfTransport,
        final Number _seatsAvailable) {

      startCity = _startCity != null ? _startCity : null;
      timeDuration = _timeDuration;
      distance = _distance;
      price = _price;
      meanOfTransport = _meanOfTransport != null ? _meanOfTransport : null;
      seatsAvailable = _seatsAvailable;
    }

    public boolean equals(final Object obj) {

      if (!(obj instanceof Segment)) {
        return false;
      }

      Segment other = ((Segment) obj);

      return (Utils.equals(startCity, other.startCity))
          && (Utils.equals(timeDuration, other.timeDuration))
          && (Utils.equals(distance, other.distance))
          && (Utils.equals(price, other.price))
          && (Utils.equals(meanOfTransport, other.meanOfTransport))
          && (Utils.equals(seatsAvailable, other.seatsAvailable));
    }

    public int hashCode() {

      return Utils.hashCode(
          startCity, timeDuration, distance, price, meanOfTransport, seatsAvailable);
    }

    public Segment copy() {

      return new Segment(startCity, timeDuration, distance, price, meanOfTransport, seatsAvailable);
    }

    public String toString() {

      return "mk_Trip`Segment"
          + Utils.formatFields(
              startCity, timeDuration, distance, price, meanOfTransport, seatsAvailable);
    }
  }
}
