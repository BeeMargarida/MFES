package Project;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Rome2RioTest {
  private TransportGraph t = new TransportGraph();
  private SearchEngine s = new SearchEngine(t);

  private void assertTrue(final Boolean cond) {

    return;
  }

  private void testGraphCreation() {

    assertTrue(s.getTransportGraph().listConnections().size() > 0L);
  }

  private void testPricePath() {

    VDMSeq answer =
        s.rome2Rio(
            new String(new char[] {'P', 'o', 'r', 't', 'o'}),
            new String(new char[] {'M', 'a', 'd', 'r', 'i', 'd'}),
            SetUtil.set(1L, 2L),
            2L,
            -1L);
    VDMSeq segments = ((Trip) Utils.get(answer, 1L)).getSegments();
    Number price = ((Trip.Segment) Utils.get(segments, 3L)).price;
    assertTrue(Utils.equals(price, 57L));
    assertTrue(Utils.equals(segments.size(), 3L));
    assertTrue(
        Utils.equals(
            ((Trip.Segment) Utils.get(segments, 1L)).startCity,
            new String(new char[] {'P', 'o', 'r', 't', 'o'})));
    assertTrue(
        Utils.equals(
            ((Trip.Segment) Utils.get(segments, 2L)).startCity,
            new String(new char[] {'L', 'i', 's', 'b', 'o', 'n'})));
    assertTrue(
        Utils.equals(
            ((Trip.Segment) Utils.get(segments, 3L)).startCity,
            new String(new char[] {'M', 'a', 'd', 'r', 'i', 'd'})));
  }

  private void testDistancePath() {

    VDMSeq answer =
        s.rome2Rio(
            new String(new char[] {'P', 'o', 'r', 't', 'o'}),
            new String(new char[] {'M', 'a', 'd', 'r', 'i', 'd'}),
            SetUtil.set(1L, 2L),
            1L,
            -1L);
    VDMSeq segments = ((Trip) Utils.get(answer, 1L)).getSegments();
    Number distance = ((Trip.Segment) Utils.get(segments, 3L)).distance;
    assertTrue(Utils.equals(distance, 950L));
    assertTrue(Utils.equals(segments.size(), 3L));
    assertTrue(
        Utils.equals(
            ((Trip.Segment) Utils.get(segments, 1L)).startCity,
            new String(new char[] {'P', 'o', 'r', 't', 'o'})));
    assertTrue(
        Utils.equals(
            ((Trip.Segment) Utils.get(segments, 2L)).startCity,
            new String(new char[] {'L', 'i', 's', 'b', 'o', 'n'})));
    assertTrue(
        Utils.equals(
            ((Trip.Segment) Utils.get(segments, 3L)).startCity,
            new String(new char[] {'M', 'a', 'd', 'r', 'i', 'd'})));
  }

  private void testDurationPath() {

    VDMSeq answer =
        s.rome2Rio(
            new String(new char[] {'P', 'o', 'r', 't', 'o'}),
            new String(new char[] {'M', 'a', 'd', 'r', 'i', 'd'}),
            SetUtil.set(1L, 2L),
            3L,
            -1L);
    VDMSeq segments = ((Trip) Utils.get(answer, 1L)).getSegments();
    Number duration = ((Trip.Segment) Utils.get(segments, 3L)).timeDuration;
    assertTrue(Utils.equals(duration, 1.25));
    assertTrue(Utils.equals(segments.size(), 3L));
    assertTrue(
        Utils.equals(
            ((Trip.Segment) Utils.get(segments, 1L)).startCity,
            new String(new char[] {'P', 'o', 'r', 't', 'o'})));
    assertTrue(
        Utils.equals(
            ((Trip.Segment) Utils.get(segments, 2L)).startCity,
            new String(new char[] {'L', 'i', 's', 'b', 'o', 'n'})));
    assertTrue(
        Utils.equals(
            ((Trip.Segment) Utils.get(segments, 3L)).startCity,
            new String(new char[] {'M', 'a', 'd', 'r', 'i', 'd'})));
  }

  private void testTrainPath() {

    VDMSeq answer =
        s.rome2Rio(
            new String(new char[] {'P', 'o', 'r', 't', 'o'}),
            new String(new char[] {'M', 'a', 'd', 'r', 'i', 'd'}),
            SetUtil.set(3L),
            3L,
            -1L);
    VDMSeq segments = ((Trip) Utils.get(answer, 1L)).getSegments();
    Number duration = ((Trip.Segment) Utils.get(segments, 2L)).timeDuration;
    assertTrue(Utils.equals(duration, 1L));
    assertTrue(Utils.equals(segments.size(), 2L));
    assertTrue(
        Utils.equals(
            ((Trip.Segment) Utils.get(segments, 1L)).startCity,
            new String(new char[] {'P', 'o', 'r', 't', 'o'})));
    assertTrue(
        Utils.equals(
            ((Trip.Segment) Utils.get(segments, 2L)).startCity,
            new String(new char[] {'M', 'a', 'd', 'r', 'i', 'd'})));
  }

  private void testMaxDurationOnePath() {

    VDMSeq answer =
        s.rome2Rio(
            new String(new char[] {'P', 'o', 'r', 't', 'o'}),
            new String(new char[] {'M', 'a', 'd', 'r', 'i', 'd'}),
            SetUtil.set(1L, 2L),
            3L,
            1L);
    assertTrue(Utils.empty(answer));
  }

  private void testCombinationPath() {

    VDMSeq answer =
        s.rome2Rio(
            new String(new char[] {'P', 'o', 'r', 't', 'o'}),
            new String(new char[] {'M', 'a', 'd', 'r', 'i', 'd'}),
            SetUtil.set(),
            3L,
            -1L);
    VDMSeq segments1 = ((Trip) Utils.get(answer, 1L)).getSegments();
    VDMSeq segments2 = ((Trip) Utils.get(answer, 2L)).getSegments();
    VDMSeq segments3 = ((Trip) Utils.get(answer, 3L)).getSegments();
    assertTrue(
        Utils.equals(
            ((Trip.Segment) Utils.get(segments1, 1L)).startCity,
            new String(new char[] {'P', 'o', 'r', 't', 'o'})));
    assertTrue(
        Utils.equals(
            ((Trip.Segment) Utils.get(segments1, 2L)).startCity,
            new String(new char[] {'M', 'a', 'd', 'r', 'i', 'd'})));
    assertTrue(Utils.equals(((Trip.Segment) Utils.get(segments1, 2L)).timeDuration, 1L));
    assertTrue(
        Utils.equals(
            ((Trip.Segment) Utils.get(segments2, 1L)).startCity,
            new String(new char[] {'P', 'o', 'r', 't', 'o'})));
    assertTrue(
        Utils.equals(
            ((Trip.Segment) Utils.get(segments2, 2L)).startCity,
            new String(new char[] {'L', 'i', 's', 'b', 'o', 'n'})));
    assertTrue(
        Utils.equals(
            ((Trip.Segment) Utils.get(segments2, 3L)).startCity,
            new String(new char[] {'M', 'a', 'd', 'r', 'i', 'd'})));
    assertTrue(Utils.equals(((Trip.Segment) Utils.get(segments2, 3L)).timeDuration, 1.25));
    assertTrue(
        Utils.equals(
            ((Trip.Segment) Utils.get(segments3, 1L)).startCity,
            new String(new char[] {'P', 'o', 'r', 't', 'o'})));
    assertTrue(
        Utils.equals(
            ((Trip.Segment) Utils.get(segments3, 2L)).startCity,
            new String(new char[] {'L', 'i', 's', 'b', 'o', 'n'})));
    assertTrue(
        Utils.equals(
            ((Trip.Segment) Utils.get(segments3, 3L)).startCity,
            new String(new char[] {'M', 'a', 'd', 'r', 'i', 'd'})));
    assertTrue(Utils.equals(((Trip.Segment) Utils.get(segments3, 3L)).timeDuration, 13.125));
  }

  private void testCombinationPathWithMaximumDuration() {

    VDMSeq answer =
        s.rome2Rio(
            new String(new char[] {'P', 'o', 'r', 't', 'o'}),
            new String(new char[] {'B', 'a', 'r', 'c', 'e', 'l', 'o', 'n', 'a'}),
            SetUtil.set(),
            2L,
            13L);
    VDMSeq segments1 = ((Trip) Utils.get(answer, 1L)).getSegments();
    VDMSeq segments2 = ((Trip) Utils.get(answer, 2L)).getSegments();
    assertTrue(
        Utils.equals(
            ((Trip.Segment) Utils.get(segments1, 1L)).startCity,
            new String(new char[] {'P', 'o', 'r', 't', 'o'})));
    assertTrue(
        Utils.equals(
            ((Trip.Segment) Utils.get(segments1, 2L)).startCity,
            new String(new char[] {'M', 'a', 'd', 'r', 'i', 'd'})));
    assertTrue(
        Utils.equals(
            ((Trip.Segment) Utils.get(segments1, 3L)).startCity,
            new String(new char[] {'B', 'a', 'r', 'c', 'e', 'l', 'o', 'n', 'a'})));
    assertTrue(
        Utils.equals(
            ((Number) Utils.get(((Trip) Utils.get(answer, 1L)).finalResults, 3L)),
            10.167105263157895));
    assertTrue(
        Utils.equals(
            ((Trip.Segment) Utils.get(segments2, 1L)).startCity,
            new String(new char[] {'P', 'o', 'r', 't', 'o'})));
    assertTrue(
        Utils.equals(
            ((Trip.Segment) Utils.get(segments2, 2L)).startCity,
            new String(new char[] {'L', 'i', 's', 'b', 'o', 'n'})));
    assertTrue(
        Utils.equals(
            ((Trip.Segment) Utils.get(segments2, 3L)).startCity,
            new String(new char[] {'M', 'a', 'd', 'r', 'i', 'd'})));
    assertTrue(
        Utils.equals(
            ((Trip.Segment) Utils.get(segments2, 4L)).startCity,
            new String(new char[] {'B', 'a', 'r', 'c', 'e', 'l', 'o', 'n', 'a'})));
    assertTrue(
        Utils.equals(
            ((Number) Utils.get(((Trip) Utils.get(answer, 2L)).finalResults, 3L)),
            12.822368421052632));
  }

  private void testNoPath() {

    VDMSeq answer =
        s.rome2Rio(
            new String(new char[] {'P', 'o', 'r', 't', 'o'}),
            new String(new char[] {'M', 'a', 'd', 'r', 'i', 'd'}),
            SetUtil.set(4L),
            3L,
            -1L);
    assertTrue(Utils.empty(answer));
    answer =
        s.rome2Rio(
            new String(new char[] {'G', 'a', 'i', 'a'}),
            new String(new char[] {'K', 'r', 'a', 'k', 'o', 'w'}),
            SetUtil.set(),
            3L,
            -1L);
    assertTrue(Utils.empty(answer));
  }

  private void testWrongStations() {

    VDMSeq answer =
        s.rome2Rio(
            new String(new char[] {'P', 'e', 'r', 't', 'o'}),
            new String(new char[] {'M', 'a', 'd', 'r', 'i', 'd'}),
            SetUtil.set(1L, 2L),
            3L,
            -1L);
    assertTrue(Utils.empty(answer));
    answer =
        s.rome2Rio(
            new String(new char[] {'P', 'o', 'r', 't', 'o'}),
            new String(new char[] {'M', 'e', 'd', 'r', 'i', 'd'}),
            SetUtil.set(1L, 2L),
            3L,
            -1L);
    assertTrue(Utils.empty(answer));
  }

  private void testArrivalTimeProblem() {

    VDMSeq answer =
        s.rome2Rio(
            new String(new char[] {'M', 'a', 'd', 'r', 'i', 'd'}),
            new String(new char[] {'M', 'o', 's', 'c', 'o', 'w'}),
            SetUtil.set(1L, 2L, 3L, 4L),
            2L,
            -1L);
    assertTrue(Utils.empty(answer));
  }

  private void testGetConnectionWithDestination() {

    VDMSet answer = t.getConnectionsWithDestination("Lisbon");
    assertTrue(Utils.equals(answer.size(), 3L));
  }

  public static void main() {

    new Rome2RioTest().testGraphCreation();
    new Rome2RioTest().testPricePath();
    new Rome2RioTest().testDistancePath();
    new Rome2RioTest().testTrainPath();
    new Rome2RioTest().testDurationPath();
    new Rome2RioTest().testMaxDurationOnePath();
    new Rome2RioTest().testCombinationPath();
    new Rome2RioTest().testCombinationPathWithMaximumDuration();
    new Rome2RioTest().testNoPath();
    new Rome2RioTest().testWrongStations();
    new Rome2RioTest().testArrivalTimeProblem();
    new Rome2RioTest().testGetConnectionWithDestination();
  }

  public Rome2RioTest() {}

  public String toString() {

    return "Rome2RioTest{" + "t := " + Utils.toString(t) + ", s := " + Utils.toString(s) + "}";
  }
}
