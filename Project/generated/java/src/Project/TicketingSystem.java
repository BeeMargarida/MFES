package Project;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class TicketingSystem {
  private VDMSet users = SetUtil.set();
  private TransportGraph transportMap;

  public void cg_init_TicketingSystem_1(final TransportGraph t) {

    transportMap = t;
    createUserDatabase();
  }

  public TicketingSystem(final TransportGraph t) {

    cg_init_TicketingSystem_1(t);
  }

  public void addUser(final Number id, final Number passwd, final Number amount) {

    users = SetUtil.union(Utils.copy(users), SetUtil.set(new User(id, passwd, amount)));
  }

  public void createUserDatabase() {

    addUser(12L, 1234L, 1000L);
    addUser(13L, 5555L, 9273L);
    addUser(14L, 8790L, 7834L);
  }

  private User getUserById(final Number ID) {

    for (Iterator iterator_25 = users.iterator(); iterator_25.hasNext(); ) {
      User u = (User) iterator_25.next();
      if (Utils.equals(u.userID, ID)) {
        return Utils.copy(u);
      }
    }
    return new User(0L, 0L, 0L);
  }

  public VDMSet getUsersDatabase() {

    return Utils.copy(users);
  }

  private void discountMoney(final Number userID, final Number tripPrice, final Number nrTickets) {

    for (Iterator iterator_26 = users.iterator(); iterator_26.hasNext(); ) {
      User u = (User) iterator_26.next();
      if (Utils.equals(u.userID, userID)) {
        updateDatabase(
            Utils.copy(u),
            u.moneyAmount.doubleValue() - tripPrice.doubleValue() * nrTickets.longValue());
      }
    }
  }

  public void updateDatabase(final User u, final Number newAmount) {

    Number userID = u.userID;
    Number passwd = u.passwd;
    users = SetUtil.diff(Utils.copy(users), SetUtil.set(Utils.copy(u)));
    users = SetUtil.union(Utils.copy(users), SetUtil.set(new User(userID, passwd, newAmount)));
  }

  public Boolean buyTickets(
      final Number userID,
      final Number passwd,
      final Trip selectedTrip,
      final Number nrSeatsToBuy) {

    IO.println(selectedTrip);
    if (Utils.equals(passwd, getUserById(userID).passwd)) {
      if (getUserById(userID).moneyAmount.doubleValue()
          >= selectedTrip.totalPrice().doubleValue() * nrSeatsToBuy.longValue()) {
        Number nrAvailableSeats = selectedTrip.getAvailableSeats();
        Boolean andResult_25 = false;

        if (!(Utils.equals(nrAvailableSeats, Utilities.MAX_INT))) {
          if (nrAvailableSeats.doubleValue() >= nrSeatsToBuy.longValue()) {
            andResult_25 = true;
          }
        }

        if (andResult_25) {
          selectedTrip.discountAvailableSeats(nrSeatsToBuy, transportMap);
          discountMoney(userID, selectedTrip.totalPrice(), nrSeatsToBuy);
          IO.println("***");
          IO.println(nrAvailableSeats);
          IO.println("***");
          return true;

        } else {
          IO.print("Not enough seats available for purchase");
        }

        return false;

      } else {
        IO.print("User does not have enough money to make the purchase");
      }

      return false;

    } else {
      IO.print("Password incorrect");
    }

    return false;
  }

  public TicketingSystem() {}

  public static Boolean uniqueID(final VDMSet users) {

    throw new UnsupportedOperationException();
  }

  private static VDMSeq getFinalResults(final Trip trip) {

    throw new UnsupportedOperationException();
  }

  public static Boolean possibleTransaction(
      final VDMSet users, final Number userID, final Trip selectedTrip, final Number nrSeatsToBuy) {

    throw new UnsupportedOperationException();
  }

  public String toString() {

    return "TicketingSystem{"
        + "users := "
        + Utils.toString(users)
        + ", transportMap := "
        + Utils.toString(transportMap)
        + "}";
  }

  public static class User implements Record {
    public Number userID;
    public Number passwd;
    public Number moneyAmount;

    public User(final Number _userID, final Number _passwd, final Number _moneyAmount) {

      userID = _userID;
      passwd = _passwd;
      moneyAmount = _moneyAmount;
    }

    public boolean equals(final Object obj) {

      if (!(obj instanceof User)) {
        return false;
      }

      User other = ((User) obj);

      return (Utils.equals(userID, other.userID))
          && (Utils.equals(passwd, other.passwd))
          && (Utils.equals(moneyAmount, other.moneyAmount));
    }

    public int hashCode() {

      return Utils.hashCode(userID, passwd, moneyAmount);
    }

    public User copy() {

      return new User(userID, passwd, moneyAmount);
    }

    public String toString() {

      return "mk_TicketingSystem`User" + Utils.formatFields(userID, passwd, moneyAmount);
    }
  }
}
