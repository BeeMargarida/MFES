package Project;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class SearchEngine {
  protected TransportGraph transportMap;
  protected VDMSet settledNodes = SetUtil.set();
  protected VDMSet unsettledNodes = SetUtil.set();
  protected VDMMap distances = MapUtil.map();
  protected VDMMap prev = MapUtil.map();
  protected Station stationOrigin;
  protected Station minimumNode;
  protected VDMSeq trips = SeqUtil.seq();

  public void cg_init_SearchEngine_1(final TransportGraph t) {

    transportMap = t;
    unsettledNodes = transportMap.listStations();
    return;
  }

  public SearchEngine(final TransportGraph t) {

    cg_init_SearchEngine_1(t);
  }

  public TransportGraph getTransportGraph() {

    return transportMap;
  }

  public void dijkstraAlgorithm(
      final Station origin, final VDMSet meansOfTransportation, final Number weightFactor) {

    distances =
        MapUtil.override(
            Utils.copy(distances), MapUtil.map(new Maplet(origin, SeqUtil.seq(0L, 0L, 0L))));
    origin.setArrivalTime(0L);
    settledNodes = SetUtil.union(Utils.copy(settledNodes), SetUtil.set(origin));
    Boolean whileCond_1 = true;
    while (whileCond_1) {
      whileCond_1 =
          !(Utils.empty(SetUtil.intersect(Utils.copy(settledNodes), Utils.copy(unsettledNodes))));
      if (!(whileCond_1)) {
        break;
      }

      {
        Station minimumNode_1 = getMinimumNode(weightFactor);
        settledNodes = SetUtil.union(Utils.copy(settledNodes), SetUtil.set(minimumNode_1));
        unsettledNodes = SetUtil.diff(Utils.copy(unsettledNodes), SetUtil.set(minimumNode_1));
        findMinimalDistances(minimumNode_1, Utils.copy(meansOfTransportation), weightFactor);
      }
    }
  }

  private Number findMinDuration(final VDMSet connectionsSet) {

    Number minDuration = 0.0;
    minDuration = Utilities.MAX_INT;
    for (Iterator iterator_1 = connectionsSet.iterator(); iterator_1.hasNext(); ) {
      Connection c = (Connection) iterator_1.next();
      if (c.duration.doubleValue() <= minDuration.doubleValue()) {
        minDuration = c.duration;
      }
    }
    return minDuration;
  }

  private Number getArrivalTime(
      final Number startTime,
      final Station startNode,
      final Station targetNode,
      final VDMSet meansOfTransportation) {

    VDMSet validConnections = null;
    VDMSet connectionsFromSource = null;
    Number minArrivalTime = 0.0;
    validConnections = SetUtil.set();
    connectionsFromSource = transportMap.getConnectionsWithSource(startNode.name);
    for (Iterator iterator_2 = connectionsFromSource.iterator(); iterator_2.hasNext(); ) {
      Connection c = (Connection) iterator_2.next();
      if (stringEqual(c.destination.name, targetNode.name)) {
        if (SetUtil.inSet(c.type, meansOfTransportation)) {
          validConnections = SetUtil.union(Utils.copy(validConnections), SetUtil.set(c));
        }
      }
    }
    minArrivalTime =
        startTime.doubleValue()
            + startNode.arrivalTime.doubleValue()
            + findMinDuration(Utils.copy(validConnections)).doubleValue();
    return minArrivalTime;
  }

  private Number waitingTime(final Connection connection, final Station node) {

    Number timeDiff = 0.0;
    Number minDiff = 0.0;
    minDiff = Utilities.MAX_INT;
    long toVar_1 = connection.timetable.size();

    for (Long idx = 1L; idx <= toVar_1; idx++) {
      timeDiff =
          ((Number) Utils.get(connection.timetable, idx)).doubleValue()
              - node.arrivalTime.doubleValue();
      Boolean andResult_7 = false;

      if (timeDiff.doubleValue() >= 0L) {
        if (timeDiff.doubleValue() < minDiff.doubleValue()) {
          andResult_7 = true;
        }
      }

      if (andResult_7) {
        minDiff = timeDiff;
      }
    }
    return minDiff;
  }

  private void findMinimalDistances(
      final Station node, final VDMSet meansOfTransportation, final Number weightFactor) {

    VDMSet adjacentNodes = transportMap.getNeighborsOfNode(node.name);
    Number neighborArrivalTime = 0.0;
    Number startTime = 0L;
    for (Iterator iterator_3 = adjacentNodes.iterator(); iterator_3.hasNext(); ) {
      Station neighbor = (Station) iterator_3.next();
      neighborArrivalTime = getArrivalTime(0L, node, neighbor, Utils.copy(meansOfTransportation));
      neighbor.setArrivalTime(neighborArrivalTime);
    }
    for (Iterator iterator_4 = adjacentNodes.iterator(); iterator_4.hasNext(); ) {
      Station target = (Station) iterator_4.next();
      VDMSet cons =
          getDistanceConnection(node, target, Utils.copy(meansOfTransportation), weightFactor);
      for (Iterator iterator_5 = cons.iterator(); iterator_5.hasNext(); ) {
        ConnectionInfo con = (ConnectionInfo) iterator_5.next();
        if (!(Utils.empty(con.con))) {
          if (getShortestDistance(target, weightFactor).doubleValue()
              > getShortestDistance(node, weightFactor).doubleValue() + con.weight.doubleValue()) {
            Number newArrivalTime = 0.0;
            Number waitT = waitingTime(((Connection) Utils.get(con.con, 1L)), node);
            Boolean andResult_8 = false;

            if (waitT.doubleValue() >= 0L) {
              if (!(Utils.equals(waitT, Utilities.MAX_INT))) {
                andResult_8 = true;
              }
            }

            if (andResult_8) {
              Number newPrice = 0.0;
              Number newDist = 0.0;
              Number newDuration = 0.0;
              VDMSeq newSeq = SeqUtil.seq();
              newArrivalTime =
                  startTime.doubleValue()
                      + node.arrivalTime.doubleValue()
                      + getDistanceFromConnection(((Connection) Utils.get(con.con, 1L)), 3L)
                          .doubleValue()
                      + waitT.doubleValue();
              newDist =
                  getShortestDistance(node, 1L).doubleValue()
                      + getDistanceFromConnection(((Connection) Utils.get(con.con, 1L)), 1L)
                          .doubleValue();
              newPrice =
                  getShortestDistance(node, 2L).doubleValue()
                      + getDistanceFromConnection(((Connection) Utils.get(con.con, 1L)), 2L)
                          .doubleValue();
              newDuration =
                  getShortestDistance(node, 3L).doubleValue()
                      + getDistanceFromConnection(((Connection) Utils.get(con.con, 1L)), 3L)
                          .doubleValue();
              newSeq = SeqUtil.seq(newDist, newPrice, newDuration);
              distances =
                  MapUtil.override(
                      Utils.copy(distances), MapUtil.map(new Maplet(target, Utils.copy(newSeq))));
              target.setCalculatedVariables(Utils.copy(newSeq));
              target.setArrivalTime(newArrivalTime);
              target.setMeanOfTransportationUsed(con.type);
              prev = MapUtil.override(Utils.copy(prev), MapUtil.map(new Maplet(target, node)));
              settledNodes = SetUtil.union(Utils.copy(settledNodes), SetUtil.set(target));
            }
          }
        }
      }
    }
  }

  private Station getMinimumNode(final Number weightFactor) {

    minimumNode_1 = new Station("");
    for (Iterator iterator_6 =
            SetUtil.intersect(Utils.copy(settledNodes), Utils.copy(unsettledNodes)).iterator();
        iterator_6.hasNext();
        ) {
      Station n = (Station) iterator_6.next();
      if (Utils.equals(minimumNode.name, "")) {
        minimumNode_1 = n;

      } else {
        if (getShortestDistance(n, weightFactor).doubleValue()
            < getShortestDistance(minimumNode, weightFactor).doubleValue()) {
          minimumNode_1 = n;
        }
      }
    }
    return minimumNode;
  }

  private Number getShortestDistance(final Station destination, final Number weightFactor) {

    VDMMap d = MapUtil.domResTo(SetUtil.set(destination), Utils.copy(distances));
    VDMSet d1 = MapUtil.rng(Utils.copy(d));
    VDMSeq dist = getFirstFromSet(Utils.copy(d1));
    return ((Number) Utils.get(dist, weightFactor));
  }

  private VDMSet getDistanceConnection(
      final Station node,
      final Station target,
      final VDMSet meansOfTransportation,
      final Number weightFactor) {

    Connection conTmp = null;
    VDMSet connectionInfo = SetUtil.set();
    for (Iterator iterator_7 = transportMap.listConnections().iterator(); iterator_7.hasNext(); ) {
      Connection con = (Connection) iterator_7.next();
      if (SetUtil.inSet(con.type, meansOfTransportation)) {
        Boolean andResult_9 = false;

        if (stringEqual(con.source.name, node.name)) {
          if (stringEqual(con.destination.name, target.name)) {
            andResult_9 = true;
          }
        }

        if (andResult_9) {
          if (Utils.equals(weightFactor, 1L)) {
            connectionInfo =
                SetUtil.union(
                    Utils.copy(connectionInfo),
                    SetUtil.set(
                        new ConnectionInfo(SeqUtil.seq(con), ((Object) con.type), con.distance)));

          } else {
            if (Utils.equals(weightFactor, 2L)) {
              connectionInfo =
                  SetUtil.union(
                      Utils.copy(connectionInfo),
                      SetUtil.set(
                          new ConnectionInfo(SeqUtil.seq(con), ((Object) con.type), con.price)));

            } else {
              if (Utils.equals(weightFactor, 3L)) {
                connectionInfo =
                    SetUtil.union(
                        Utils.copy(connectionInfo),
                        SetUtil.set(
                            new ConnectionInfo(
                                SeqUtil.seq(con), ((Object) con.type), con.duration)));
              }
            }
          }
        }
      }
    }
    return Utils.copy(connectionInfo);
  }

  private Number getDistanceFromConnection(final Connection con, final Number weightFactor) {

    Number ret = 0L;
    if (Utils.equals(weightFactor, 1L)) {
      ret = con.distance;

    } else {
      if (Utils.equals(weightFactor, 2L)) {
        ret = con.price;

      } else {
        if (Utils.equals(weightFactor, 3L)) {
          ret = con.duration;
        }
      }
    }

    return ret;
  }

  private VDMSeq getFirstFromSet(final VDMSet reals) {

    if (!(Utils.empty(reals))) {
      for (Iterator iterator_8 = reals.iterator(); iterator_8.hasNext(); ) {
        VDMSeq ds = (VDMSeq) iterator_8.next();
        return Utils.copy(ds);
      }

    } else {
      return SeqUtil.seq(1000000L, 1000000L, 1000000L);
    }
  }

  public VDMSeq getPath(final String destination) {

    VDMSeq path = SeqUtil.seq();
    VDMSeq revertedPath = SeqUtil.seq();
    Station stationDest = transportMap.getStation(destination);
    VDMMap tmp = MapUtil.domResTo(SetUtil.set(stationDest), Utils.copy(prev));
    VDMSet tmp2 = MapUtil.rng(Utils.copy(tmp));
    if (Utils.empty(tmp2)) {
      return SeqUtil.seq();
    }

    path = SeqUtil.conc(Utils.copy(path), SeqUtil.seq(stationDest));
    Boolean whileCond_2 = true;
    while (whileCond_2) {
      whileCond_2 = !(Utils.empty(tmp2));
      if (!(whileCond_2)) {
        break;
      }

      {
        stationDest = ((Station) Utils.get(prev, stationDest));
        path = SeqUtil.conc(Utils.copy(path), SeqUtil.seq(stationDest));
        tmp = MapUtil.domResTo(SetUtil.set(stationDest), Utils.copy(prev));
        tmp2 = MapUtil.rng(Utils.copy(tmp));
      }
    }

    revertedPath = revertSeq(Utils.copy(path));
    return Utils.copy(revertedPath);
  }

  private VDMSeq revertSeq(final VDMSeq stations) {

    VDMSeq result = Utils.copy(stations);
    Number i = 0L;
    for (Iterator iterator_9 = stations.iterator(); iterator_9.hasNext(); ) {
      Station sta = (Station) iterator_9.next();
      {
        Utils.mapSeqUpdate(result, stations.size() - i.longValue(), sta);
        i = i.longValue() + 1L;
      }
    }
    return Utils.copy(result);
  }

  public VDMSeq rome2Rio(
      final String origin,
      final String destination,
      final VDMSet meansOfTransportation,
      final Number weightFactor,
      final Number maxDuration) {

    Station stationDest = transportMap.getStation(destination);
    trips = SeqUtil.seq();
    if (stringEqual(stationDest.name, "Error")) {
      IO.println("There is no destination station with that name.");
      return SeqUtil.seq();
    }

    stationOrigin = transportMap.getStation(origin);
    if (stringEqual(stationOrigin.name, "Error")) {
      IO.println("There is no origin station with that name.");
      return SeqUtil.seq();
    }

    prev = MapUtil.map();
    distances = MapUtil.map();
    unsettledNodes = transportMap.listStations();
    settledNodes = SetUtil.set();
    if (Utils.empty(meansOfTransportation)) {
      VDMSeq answerOne = SeqUtil.seq();
      VDMSet means =
          SetUtil.set(
              SetUtil.set(Project.quotes.BusQuote.getInstance()),
              SetUtil.set(Project.quotes.PlaneQuote.getInstance()),
              SetUtil.set(Project.quotes.TrainQuote.getInstance()),
              SetUtil.set(Project.quotes.WalkQuote.getInstance()),
              SetUtil.set(
                  Project.quotes.BusQuote.getInstance(), Project.quotes.PlaneQuote.getInstance()),
              SetUtil.set(
                  Project.quotes.BusQuote.getInstance(), Project.quotes.TrainQuote.getInstance()),
              SetUtil.set(
                  Project.quotes.BusQuote.getInstance(), Project.quotes.WalkQuote.getInstance()),
              SetUtil.set(
                  Project.quotes.PlaneQuote.getInstance(), Project.quotes.TrainQuote.getInstance()),
              SetUtil.set(
                  Project.quotes.PlaneQuote.getInstance(), Project.quotes.WalkQuote.getInstance()),
              SetUtil.set(
                  Project.quotes.TrainQuote.getInstance(), Project.quotes.WalkQuote.getInstance()),
              SetUtil.set(
                  Project.quotes.BusQuote.getInstance(),
                  Project.quotes.TrainQuote.getInstance(),
                  Project.quotes.WalkQuote.getInstance()),
              SetUtil.set(
                  Project.quotes.BusQuote.getInstance(),
                  Project.quotes.TrainQuote.getInstance(),
                  Project.quotes.PlaneQuote.getInstance()),
              SetUtil.set(
                  Project.quotes.PlaneQuote.getInstance(),
                  Project.quotes.TrainQuote.getInstance(),
                  Project.quotes.WalkQuote.getInstance()),
              SetUtil.set(
                  Project.quotes.BusQuote.getInstance(),
                  Project.quotes.PlaneQuote.getInstance(),
                  Project.quotes.WalkQuote.getInstance()),
              SetUtil.set(
                  Project.quotes.BusQuote.getInstance(),
                  Project.quotes.PlaneQuote.getInstance(),
                  Project.quotes.TrainQuote.getInstance(),
                  Project.quotes.WalkQuote.getInstance()));
      for (Iterator iterator_10 = means.iterator(); iterator_10.hasNext(); ) {
        VDMSet mean = (VDMSet) iterator_10.next();
        Trip trip = new Trip(SeqUtil.seq());
        prev = MapUtil.map();
        distances = MapUtil.map();
        unsettledNodes = transportMap.listStations();
        settledNodes = SetUtil.set();
        answerOne = SeqUtil.seq();
        dijkstraAlgorithm(stationOrigin, Utils.copy(mean), weightFactor);
        answerOne = getPath(destination);
        IO.println(Utils.copy(mean));
        IO.println("\n\n");
        Boolean andResult_10 = false;

        if (!(Utils.empty(prev))) {
          Boolean andResult_11 = false;

          if (!(Utils.equals(((Number) Utils.get(stationDest.getCalculatedVariables(), 1L)), 0L))) {
            Boolean andResult_12 = false;

            if (!(Utils.equals(
                ((Number) Utils.get(stationDest.getCalculatedVariables(), 2L)), 0L))) {
              Boolean andResult_13 = false;

              if (!(Utils.equals(
                  ((Number) Utils.get(stationDest.getCalculatedVariables(), 3L)), 0L))) {
                Boolean orResult_12 = false;

                if (maxDuration.doubleValue() < 0L) {
                  orResult_12 = true;
                } else {
                  orResult_12 = stationDest.arrivalTime.doubleValue() <= maxDuration.doubleValue();
                }

                if (orResult_12) {
                  andResult_13 = true;
                }
              }

              if (andResult_13) {
                andResult_12 = true;
              }
            }

            if (andResult_12) {
              andResult_11 = true;
            }
          }

          if (andResult_11) {
            andResult_10 = true;
          }
        }

        if (andResult_10) {
          Number i = 0L;
          Station prevStation = null;
          for (Iterator iterator_11 = answerOne.iterator(); iterator_11.hasNext(); ) {
            Station el = (Station) iterator_11.next();
            {
              IO.println(el);
              if (Utils.equals(i, 0L)) {
                trip.addSegmentFirst(el.name, el.getCalculatedVariables(), 0L);
              } else {
                trip.addSegment(
                    el.name,
                    el.getCalculatedVariables(),
                    el.getMeanOfTransportationUsed(),
                    el.getAvailableSeats(transportMap.listConnections(), prevStation));
              }

              prevStation = el;
              i = i.longValue() + 1L;
            }
          }
          IO.println("\n\n");
          if (!(Utils.empty(trip.getSegments()))) {
            trip.setFinalResults(stationDest.getCalculatedVariables(), stationDest.arrivalTime);
            if (Utils.empty(trips)) {
              trips = SeqUtil.conc(Utils.copy(trips), SeqUtil.seq(trip));

            } else {
              Boolean isIn = false;
              for (Iterator iterator_12 = trips.iterator(); iterator_12.hasNext(); ) {
                Trip t = (Trip) iterator_12.next();
                {
                  if (Utils.equals(equalTrips(t, trip), true)) {
                    isIn = true;
                  }
                }
              }
              if (Utils.equals(isIn, false)) {
                trips = SeqUtil.conc(Utils.copy(trips), SeqUtil.seq(trip));
              }
            }
          }
        }
      }
      if (Utils.empty(trips)) {
        IO.println("There are no possible paths for your options.");
      }

      return Utils.copy(trips);

    } else {
      VDMSeq answerSeq = SeqUtil.seq();
      Trip trip = new Trip(SeqUtil.seq());
      VDMSet means = SetUtil.set();
      Number i = 0L;
      Station prevStation = null;
      for (Iterator iterator_13 = meansOfTransportation.iterator(); iterator_13.hasNext(); ) {
        Number m = (Number) iterator_13.next();
        if (Utils.equals(m, 1L)) {
          means =
              SetUtil.union(Utils.copy(means), SetUtil.set(Project.quotes.BusQuote.getInstance()));

        } else {
          if (Utils.equals(m, 2L)) {
            means =
                SetUtil.union(
                    Utils.copy(means), SetUtil.set(Project.quotes.PlaneQuote.getInstance()));

          } else {
            if (Utils.equals(m, 3L)) {
              means =
                  SetUtil.union(
                      Utils.copy(means), SetUtil.set(Project.quotes.TrainQuote.getInstance()));

            } else {
              if (Utils.equals(m, 4L)) {
                means =
                    SetUtil.union(
                        Utils.copy(means), SetUtil.set(Project.quotes.WalkQuote.getInstance()));
              }
            }
          }
        }
      }
      dijkstraAlgorithm(stationOrigin, Utils.copy(means), weightFactor);
      answerSeq = getPath(destination);
      for (Iterator iterator_14 = answerSeq.iterator(); iterator_14.hasNext(); ) {
        Station el = (Station) iterator_14.next();
        {
          if (Utils.equals(i, 0L)) {
            trip.addSegmentFirst(el.name, el.getCalculatedVariables(), 0L);
          } else {
            trip.addSegment(
                el.name,
                el.getCalculatedVariables(),
                el.getMeanOfTransportationUsed(),
                el.getAvailableSeats(transportMap.listConnections(), prevStation));
          }

          prevStation = el;
          i = i.longValue() + 1L;
        }
      }
      Boolean andResult_14 = false;

      if (maxDuration.doubleValue() > 0L) {
        if (stationDest.arrivalTime.doubleValue() > maxDuration.doubleValue()) {
          andResult_14 = true;
        }
      }

      if (andResult_14) {
        IO.println(
            "There is no path with the configurations given that has a smaller duration than the one given");
        return SeqUtil.seq();
      }

      if (!(Utils.empty(trip.getSegments()))) {
        trip.setFinalResults(stationDest.getCalculatedVariables(), stationDest.arrivalTime);
        trips = SeqUtil.conc(Utils.copy(trips), SeqUtil.seq(trip));
      }

      if (Utils.empty(trips)) {
        IO.println("There are no possible paths for your options.");
      }

      return Utils.copy(trips);
    }
  }

  private Boolean stringEqual(final String s1, final String s2) {

    if (!(Utils.equals(s1.length(), s2.length()))) {
      return false;
    }

    long toVar_2 = s1.length();

    for (Long idx = 1L; idx <= toVar_2; idx++) {
      if (!(Utils.equals(s1.charAt(Utils.index(idx)), s2.charAt(Utils.index(idx))))) {
        return false;
      }
    }
    return true;
  }

  public Boolean equalTrips(final Trip trip1, final Trip trip2) {

    Number i = 1L;
    VDMSeq seg2 = trip2.getSegments();
    for (Iterator iterator_15 = trip1.getSegments().iterator(); iterator_15.hasNext(); ) {
      Trip.Segment s1 = (Trip.Segment) iterator_15.next();
      {
        Boolean orResult_13 = false;

        if (!(Utils.equals(s1.startCity, ((Trip.Segment) Utils.get(seg2, i)).startCity))) {
          orResult_13 = true;
        } else {
          Boolean orResult_14 = false;

          if (!(Utils.equals(s1.timeDuration, ((Trip.Segment) Utils.get(seg2, i)).timeDuration))) {
            orResult_14 = true;
          } else {
            Boolean orResult_15 = false;

            if (!(Utils.equals(s1.distance, ((Trip.Segment) Utils.get(seg2, i)).distance))) {
              orResult_15 = true;
            } else {
              Boolean orResult_16 = false;

              if (!(Utils.equals(s1.price, ((Trip.Segment) Utils.get(seg2, i)).price))) {
                orResult_16 = true;
              } else {
                orResult_16 =
                    !(Utils.equals(
                        s1.meanOfTransport, ((Trip.Segment) Utils.get(seg2, i)).meanOfTransport));
              }

              orResult_15 = orResult_16;
            }

            orResult_14 = orResult_15;
          }

          orResult_13 = orResult_14;
        }

        if (orResult_13) {
          return false;
        }

        i = i.longValue() + 1L;
      }
    }
    return true;
  }

  public SearchEngine() {}

  private static Boolean validGraph(final TransportGraph g) {

    throw new UnsupportedOperationException();
  }

  private static Boolean validStart(final Station sta, final TransportGraph g) {

    throw new UnsupportedOperationException();
  }

  private static Boolean IsShortestPath(
      final VDMMap distances,
      final VDMMap prev,
      final VDMSet settledNodes,
      final Station stationOrigin,
      final TransportGraph transportMap,
      final VDMSet meansOfTransportation,
      final Number weightFactor) {

    throw new UnsupportedOperationException();
  }

  private static Boolean definesShortestDist(
      final VDMMap distances,
      final VDMMap prev,
      final VDMSet settledNodes,
      final Station stationOrigin,
      final TransportGraph transportMap,
      final VDMSet meansOfTransportation,
      final Number weightFactor) {

    throw new UnsupportedOperationException();
  }

  private static Number getConnectionWeight(
      final Connection connection, final Number weightFactor) {

    throw new UnsupportedOperationException();
  }

  private static VDMSeq getConnectionVars(final Connection connection) {

    throw new UnsupportedOperationException();
  }

  private static Boolean setOfLinkedVertices(
      final VDMSet settledNodes,
      final Station stationOrigin,
      final TransportGraph transportMap,
      final VDMSet meansOfTransportation) {

    throw new UnsupportedOperationException();
  }

  private static Boolean neighbour(
      final TransportGraph transportMap,
      final Station u,
      final Station v,
      final VDMSet meansOfTransportation) {

    throw new UnsupportedOperationException();
  }

  private static Boolean isMinimumNode(
      final Number weightFactor,
      final VDMMap distances,
      final Station minimumNode,
      final TransportGraph transportMap,
      final VDMSet settledNodes,
      final VDMSet unsettledNodes) {

    throw new UnsupportedOperationException();
  }

  private static VDMSeq getFinalResults(final Trip trip) {

    throw new UnsupportedOperationException();
  }

  private static Boolean checkMaximumDuration(final VDMSeq trips, final Number maxDuration) {

    throw new UnsupportedOperationException();
  }

  private static Boolean visitedImpliesConnected(
      final VDMSet settledNodes, final VDMSet unsettledNodes, final TransportGraph transportMap) {

    throw new UnsupportedOperationException();
  }

  public String toString() {

    return "SearchEngine{"
        + "transportMap := "
        + Utils.toString(transportMap)
        + ", settledNodes := "
        + Utils.toString(settledNodes)
        + ", unsettledNodes := "
        + Utils.toString(unsettledNodes)
        + ", distances := "
        + Utils.toString(distances)
        + ", prev := "
        + Utils.toString(prev)
        + ", stationOrigin := "
        + Utils.toString(stationOrigin)
        + ", minimumNode := "
        + Utils.toString(minimumNode)
        + ", trips := "
        + Utils.toString(trips)
        + "}";
  }

  public static class ConnectionInfo implements Record {
    public VDMSeq con;
    public Object type;
    public Number weight;

    public ConnectionInfo(final VDMSeq _con, final Object _type, final Number _weight) {

      con = _con != null ? Utils.copy(_con) : null;
      type = _type != null ? _type : null;
      weight = _weight;
    }

    public boolean equals(final Object obj) {

      if (!(obj instanceof ConnectionInfo)) {
        return false;
      }

      ConnectionInfo other = ((ConnectionInfo) obj);

      return (Utils.equals(con, other.con))
          && (Utils.equals(type, other.type))
          && (Utils.equals(weight, other.weight));
    }

    public int hashCode() {

      return Utils.hashCode(con, type, weight);
    }

    public ConnectionInfo copy() {

      return new ConnectionInfo(con, type, weight);
    }

    public String toString() {

      return "mk_SearchEngine`ConnectionInfo" + Utils.formatFields(con, type, weight);
    }
  }
}
