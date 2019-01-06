package Project;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class TicketingSystemTest {
  private TransportGraph t = new TransportGraph();
  private TicketingSystem ticket = new TicketingSystem(t);
  private SearchEngine s = new SearchEngine(t);

  private void assertTrue(final Boolean cond) {

    return;
  }

  private void assertFalse(final Boolean cond) {

    return;
  }

  private void testUserCreation() {

    assertTrue(ticket.getUsersDatabase().size() > 0L);
  }

  private void testBuyTicket() {

    VDMSeq answer =
        s.rome2Rio(
            new String(new char[] {'P', 'o', 'r', 't', 'o'}),
            new String(new char[] {'M', 'a', 'd', 'r', 'i', 'd'}),
            SetUtil.set(1L, 2L),
            3L,
            -1L);
    Boolean resBuy = ticket.buyTickets(12L, 1234L, ((Trip) Utils.get(answer, 1L)), 1L);
    assertTrue(resBuy);
  }

  private void testBuyTicketNoValidUser() {

    VDMSeq answer =
        s.rome2Rio(
            new String(new char[] {'P', 'o', 'r', 't', 'o'}),
            new String(new char[] {'M', 'a', 'd', 'r', 'i', 'd'}),
            SetUtil.set(1L, 2L),
            3L,
            -1L);
    Boolean resBuy = ticket.buyTickets(12123123L, 1234L, ((Trip) Utils.get(answer, 1L)), 1L);
    assertFalse(resBuy);
  }

  private void testBuyTicketNotEnoughMoney() {

    VDMSeq answer =
        s.rome2Rio(
            new String(new char[] {'P', 'o', 'r', 't', 'o'}),
            new String(new char[] {'M', 'a', 'd', 'r', 'i', 'd'}),
            SetUtil.set(1L, 2L),
            3L,
            -1L);
    Boolean resBuy = false;
    ticket.addUser(12345L, 1234L, 10L);
    resBuy = ticket.buyTickets(12345L, 1234L, ((Trip) Utils.get(answer, 1L)), 1L);
    assertFalse(resBuy);
  }

  private void testBuyTicketNotEnoughSeats() {

    VDMSeq answer =
        s.rome2Rio(
            new String(new char[] {'P', 'o', 'r', 't', 'o'}),
            new String(new char[] {'M', 'a', 'd', 'r', 'i', 'd'}),
            SetUtil.set(1L, 2L),
            3L,
            -1L);
    Boolean resBuy = false;
    ticket.addUser(12345L, 1234L, 10000000L);
    resBuy = ticket.buyTickets(12345L, 1234L, ((Trip) Utils.get(answer, 1L)), 20L);
    assertFalse(resBuy);
  }

  public static void main() {

    new TicketingSystemTest().testUserCreation();
    new TicketingSystemTest().testBuyTicket();
    new TicketingSystemTest().testBuyTicketNoValidUser();
    new TicketingSystemTest().testBuyTicketNotEnoughMoney();
    new TicketingSystemTest().testBuyTicketNotEnoughSeats();
  }

  public TicketingSystemTest() {}

  public String toString() {

    return "TicketingSystemTest{"
        + "t := "
        + Utils.toString(t)
        + ", ticket := "
        + Utils.toString(ticket)
        + ", s := "
        + Utils.toString(s)
        + "}";
  }
}
