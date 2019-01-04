package Project;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class TransportGraph {
  public VDMSet stations;
  public VDMSet connections;
  public HashSet<String> sourceCities;
  public HashSet<String> destinationCities;

  public void cg_init_TransportGraph_1() {

    stations = SetUtil.set();
    connections = SetUtil.set();
    createDatabase();
  }

  public TransportGraph() {
	  
	sourceCities = new HashSet<String>();
	destinationCities = new HashSet<String>();
    cg_init_TransportGraph_1();
  }

  private void addConnection(
      final Object t,
      final String s,
      final String d,
      final Number dist,
      final VDMSeq ttbl,
      final Number seats) {

    Connection tempConnection = null;
    Station originStation = getStationWithCreation(s);
    Station destinationStation = getStationWithCreation(d);
    tempConnection =
        new Connection(
            ((Object) t), originStation, destinationStation, dist, Utils.copy(ttbl), seats);
    connections = SetUtil.union(Utils.copy(connections), SetUtil.set(tempConnection));
    stations = SetUtil.union(Utils.copy(stations), SetUtil.set(originStation, destinationStation));
    sourceCities.add(s);
    destinationCities.add(d);
  }

  public void createDatabase() {

    addConnection(
        Project.quotes.BusQuote.getInstance(), "Porto", "Lisbon", 300L, SeqUtil.seq(6L, 12L), 12L);
    addConnection(
        Project.quotes.WalkQuote.getInstance(),
        "Porto",
        "Gaia",
        10L,
        SeqUtil.seq(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L),
        10L);
    addConnection(
        Project.quotes.PlaneQuote.getInstance(), "Porto", "Lisbon", 300L, SeqUtil.seq(8L, 22L), 5L);
    addConnection(
        Project.quotes.TrainQuote.getInstance(),
        "Porto",
        "Lisbon",
        350L,
        SeqUtil.seq(10L, 12L, 16L, 20L, 22L),
        10L);
    addConnection(
        Project.quotes.BusQuote.getInstance(), "Porto", "Madrid", 1500L, SeqUtil.seq(6L, 12L), 10L);
    addConnection(
        Project.quotes.PlaneQuote.getInstance(),
        "Porto",
        "Paris",
        1300L,
        SeqUtil.seq(8L, 20L),
        15L);
    addConnection(
        Project.quotes.PlaneQuote.getInstance(),
        "Amsterdam",
        "Bologna",
        300L,
        SeqUtil.seq(6L, 12L),
        5L);
    addConnection(
        Project.quotes.PlaneQuote.getInstance(),
        "Bologna",
        "Paris",
        2900L,
        SeqUtil.seq(9L, 21L),
        6L);
    addConnection(
        Project.quotes.BusQuote.getInstance(),
        "Macedo de Cavaleiros",
        "Porto",
        350L,
        SeqUtil.seq(10L, 12L, 20L),
        4L);
    addConnection(
        Project.quotes.TrainQuote.getInstance(),
        "Porto",
        "Madrid",
        100L,
        SeqUtil.seq(10L, 12L, 16L, 20L, 22L),
        19L);
    addConnection(
        Project.quotes.TrainQuote.getInstance(),
        "Lisbon",
        "Faro",
        280L,
        SeqUtil.seq(9L, 11L, 15L, 19L),
        20L);
    addConnection(
        Project.quotes.PlaneQuote.getInstance(),
        "Lisbon",
        "Faro",
        250L,
        SeqUtil.seq(8L, 12L, 20L),
        90L);
    addConnection(
        Project.quotes.BusQuote.getInstance(), "Lisbon", "Faro", 285L, SeqUtil.seq(8L, 12L), 54L);
    addConnection(
        Project.quotes.BusQuote.getInstance(),
        "Lisbon",
        "Madrid",
        750L,
        SeqUtil.seq(8L, 12L, 15L, 17L),
        12L);
    addConnection(
        Project.quotes.PlaneQuote.getInstance(),
        "Lisbon",
        "Madrid",
        650L,
        SeqUtil.seq(8L, 10L, 12L, 15L, 19L, 22L),
        3L);
    addConnection(
        Project.quotes.TrainQuote.getInstance(),
        "Lisbon",
        "Madrid",
        680L,
        SeqUtil.seq(8L, 10L, 12L, 15L, 17L, 19L, 22L),
        5L);
    addConnection(
        Project.quotes.PlaneQuote.getInstance(),
        "Lisbon",
        "Barcelona",
        1347L,
        SeqUtil.seq(8L, 12L, 15L, 19L, 22L),
        2L);
    addConnection(
        Project.quotes.TrainQuote.getInstance(),
        "Madrid",
        "Barcelona",
        625L,
        SeqUtil.seq(8L, 10L, 12L, 15L, 17L, 19L, 22L),
        10L);
    addConnection(
        Project.quotes.PlaneQuote.getInstance(),
        "Madrid",
        "Barcelona",
        625L,
        SeqUtil.seq(8L, 12L, 15L, 19L, 22L),
        5L);
    addConnection(
        Project.quotes.TrainQuote.getInstance(), "Madrid", "Krakow", 1342L, SeqUtil.seq(8L), 10L);
    addConnection(
        Project.quotes.PlaneQuote.getInstance(), "Krakow", "Moscow", 2789L, SeqUtil.seq(9L), 5L);
  }

  public VDMSet listConnections() {

    return Utils.copy(connections);
  }

  public VDMSet listStations() {

    return Utils.copy(stations);
  }

  public Boolean stringEqual(final String s1, final String s2) {

    if (!(Utils.equals(s1.length(), s2.length()))) {
      return false;
    }

    long toVar_4 = s1.length();

    for (Long idx = 1L; idx <= toVar_4; idx++) {
      if (!(Utils.equals(s1.charAt(Utils.index(idx)), s2.charAt(Utils.index(idx))))) {
        return false;
      }
    }
    return true;
  }

  public VDMSet getConnectionsWithSource(final String s) {

    VDMSet result = null;
    result = SetUtil.set();
    for (Iterator iterator_20 = connections.iterator(); iterator_20.hasNext(); ) {
      Connection e = (Connection) iterator_20.next();
      if (stringEqual(e.source.name, s)) {
        result = SetUtil.union(Utils.copy(result), SetUtil.set(e));
      }
    }
    return Utils.copy(result);
  }

  public VDMSet getNeighborsOfNode(final String name) {

    VDMSet result = SetUtil.set();
    for (Iterator iterator_21 = connections.iterator(); iterator_21.hasNext(); ) {
      Connection e = (Connection) iterator_21.next();
      if (stringEqual(e.source.name, name)) {
        result = SetUtil.union(Utils.copy(result), SetUtil.set(e.destination));
      }
    }
    return Utils.copy(result);
  }

  public VDMSet getConnectionsWithDestination(final String d) {

    VDMSet result = null;
    result = SetUtil.set();
    for (Iterator iterator_22 = connections.iterator(); iterator_22.hasNext(); ) {
      Connection e = (Connection) iterator_22.next();
      if (stringEqual(e.destination.name, d)) {
        result = SetUtil.union(Utils.copy(result), SetUtil.set(e));
      }
    }
    return Utils.copy(result);
  }

  public Station getStation(final String stationName) {

    for (Iterator iterator_23 = stations.iterator(); iterator_23.hasNext(); ) {
      Station station = (Station) iterator_23.next();
      if (stringEqual(station.name, stationName)) {
        return station;
      }
    }
    return new Station("Error");
  }

  public Station getStationWithCreation(final String stationName) {

    Station stationRes = new Station("");
    for (Iterator iterator_24 = stations.iterator(); iterator_24.hasNext(); ) {
      Station station = (Station) iterator_24.next();
      if (stringEqual(station.name, stationName)) {
        stationRes = station;
      }
    }
    if (stringEqual(stationRes.name, "")) {
      stationRes = new Station(stationName);
    }

    return stationRes;
  }

  public static Boolean checkValidConnections(final VDMSet connections, final VDMSet stations) {

    throw new UnsupportedOperationException();
  }

  public String toString() {

    return "TransportGraph{"
        + "stations := "
        + Utils.toString(stations)
        + ", connections := "
        + Utils.toString(connections)
        + "}";
  }
}
