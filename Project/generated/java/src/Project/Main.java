package Project;

import java.util.Scanner;

import org.overture.codegen.runtime.SetUtil;
import org.overture.codegen.runtime.VDMSeq;
import org.overture.codegen.runtime.VDMSet;

import java.lang.System;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Main {
	
	public static TransportGraph loadDatabase(Scanner sc) {
		boolean procede = false;

		int input = sc.nextInt();
		
		while (!procede ) {
			if (input == 1) {
				System.out.println("Loading database...");
				System.out.println("Loading done! Now let's find the best trip for you...\n");
				return new TransportGraph();
			} else {
				System.out.println("We can not procede without the database.");
			}
		}
		
		return null;
	}
	
	public static int mainMenu(Scanner sc) {
		System.out.println("\n\n*****************************************");
		System.out.println("****************MAIN MENU****************");
		System.out.println("Please insert one of the following options:");
		System.out.println("*****************************************");
		System.out.println("1 - Add New Connection");
		System.out.println("2 - Add New User");
		System.out.println("3 - Search Paths");
		System.out.println("4 - Exit Program");
		System.out.println("*****************************************");
		
		boolean procede = false;
		int input = 0;
		while (!procede ) {
			System.out.print("Your option is: ");
			input = sc.nextInt();
			if (input < 1 || input > 4) {
				System.out.println("Please insert a valid option");
			
			} else {
				procede = true;
			}
		}
		
		return input;
	}
	
	public static String getSourceCity(TransportGraph t,  Scanner sc) {
		boolean sourceChosen = false;
		String sourceCity = null;
		
		System.out.println("You can travel from the following cities:");
		if (t != null) {
			System.out.println(t.sourceCities);
		}
		System.out.println("\nTo these cities: ");
		if (t != null) {
			System.out.println(t.destinationCities);
		}
		
		while (!sourceChosen) {
			System.out.print("\nInsert source city: ");
			sourceCity = sc.next();
			if (sourceCity != "" && t.sourceCities.contains(sourceCity)) {
				sourceChosen = true;
			} else if (sourceCity.equalsIgnoreCase("exit")) {
				System.out.println("Sorry to see you leaving... Good bye!");
				System.exit(0);
			} else {
				System.out.println("Please insert a valid source city");
			}
		}
		return sourceCity;

	}
	
	public static String getDestinationCity(TransportGraph t,  Scanner sc) {
		boolean destinationChosen = false;
		String destinationCity = null;
	
		while (!destinationChosen) {
			System.out.print("Insert destination city: ");
			destinationCity = sc.next();
			if (destinationCity != null && t.destinationCities.contains(destinationCity)) {
				destinationChosen = true;
			} else if (destinationCity.equalsIgnoreCase("exit")) {
				System.out.println("Sorry to see you leaving... Good bye!");
				System.exit(0);
			} else {
				System.out.println("Please insert a valid destination city");
			}
		}
		return destinationCity;
		
	}
	
	public static VDMSet getMeansOfTransportation(Scanner sc) {
		VDMSet meansOfTransportation = null;
		System.out.println("Please insert one of the following options:");
		System.out.println("*************************************");
		System.out.println("1 - choose means of transportation");
		System.out.println("2 - all combinations of transportation");
		System.out.println("*************************************");
		
		while (meansOfTransportation == null) {
			System.out.print("Your option is: ");
			int opM = sc.nextInt();
			
			switch (opM) {
				case 1:
					System.out.println("Please insert one or more options, separated by COMMAs:");
					System.out.println("*************************************");
					System.out.println("1 - Bus");
					System.out.println("2 - Plane");
					System.out.println("3 - Train");
					System.out.println("4 - Walk");
					System.out.println("*************************************");
					boolean done = false;
					while(!done) {
						System.out.print("Your option is: ");
						String answerMeans = sc.next();
						
						List<String> listOptions = Arrays.asList(answerMeans.split(","));
						meansOfTransportation = JavaUtils.checkValidOptionsAndProcess(listOptions);
						if(meansOfTransportation == null) {
							System.out.println("Invalid input, please try again");
						}
						done = true;
					}
					break;
				case 2:
					meansOfTransportation = SetUtil.set();
					break;
				default:
					System.out.println("Invalid input, please try again");
			}
		}
		
		return meansOfTransportation;
	}
	
	public static int getMaxDuration(Scanner sc) {
		int maxDuration = -2;
		System.out.println("Please insert one of the following options:");
		System.out.println("*************************************");
		System.out.println("1 - add maximum duration for the trip");
		System.out.println("2 - NOT add maximum duration for the trip");
		System.out.println("*************************************");
		
		while (maxDuration == -2) {
			System.out.print("Your option is: ");
			int opM = sc.nextInt();
			
			switch (opM) {
				case 1:
					System.out.println("Please insert the maximum duration:");
					while(maxDuration == -2) {
						System.out.print("Your option is: ");
						int answerDur = sc.nextInt();
						
						if(answerDur < 0) {
							System.out.println("Invalid input, please try again");
						}
						else {
							maxDuration = answerDur;
						}
					}
					break;
				case 2:
					maxDuration = -1;
					break;
				default:
					System.out.println("Invalid input, please try again");
			}
		}
		
		return maxDuration;
	}
	
	public static int getWeightFactor(Scanner sc) {
		int weightFactor = -1;
		System.out.println("Please insert one of the following options:");
		System.out.println("*************************************");
		System.out.println("1 - path with shortest distance");
		System.out.println("2 - path with best price");
		System.out.println("3 - path with shortest time duration");
		System.out.println("*************************************");
		
		
		while (weightFactor != 1 && weightFactor != 2 && weightFactor != 3) {
			System.out.print("Your option is: ");
			weightFactor = sc.nextInt();
			
			switch (weightFactor) {
				case 1:
					System.out.println("Searching path with the shortest distance...");
					break;
				case 2:
					System.out.println("Searching path with best price...");
					break;
				case 3:
					System.out.println("Searching path with the shortest duration in time...");
					break;
				default:
					System.out.println("Invalid input, please try again");
			}
		}
		
		return weightFactor;
	}
	
	public static int getUserID(Scanner sc) {
		int userID = -1;
		while (userID == -1) {
			System.out.print("\nPlease insert your user ID: ");
			userID = sc.nextInt();
			if(userID < 0) {
				System.out.println("Invalid option, please try again");
			}
		}
		return userID;
		
	}
	
	public static int getPassword(Scanner sc) {
		int password = -1;
		while (password == -1) {
			System.out.print("\nPlease insert your password: ");
			password = sc.nextInt();
			if(password < 0) {
				System.out.println("Invalid option, please try again");
			}
		}
		return password;
		
	}
	
	public static int getTripID(Scanner sc, int lengthTrips) {
		int tripID = -1;
		while (tripID == -1) {
			System.out.print("\nPlease insert the tripID: ");
			tripID = sc.nextInt();
			
			if(tripID < 1 || tripID > lengthTrips) {
				System.out.println("Invalid option, please try again");
			}
		}
		return tripID;
		
	}
	
	public static int getNrSeats(Scanner sc) {
		int nrSeats = -1;
		while (nrSeats == -1) {
			System.out.print("\nPlease insert the number of seats you wish to buy: ");
			nrSeats = sc.nextInt();
			
			if(nrSeats < 0) {
				System.out.println("Invalid option, please try again");
			}
		}
		return nrSeats;
		
	}
	
	public static void main(String[] args) {
		TransportGraph t = null;
		SearchEngine s = null;
		TicketingSystem ticketS = null;
		VDMSeq trips = null;
		
		int step = 0;
		String sourceCity = null;
		String destinationCity = null;
		int weightFactor = -1;
		VDMSet meansOfTransportation = null;
		int maxDuration = -2;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Welcome to Rome2Rio!");
		System.out.println("Press 1 to load our database. We will travel the world in the most efficient time!");
		
		t = loadDatabase(sc);
		
		
		while(step != -1) {
			switch(step) {
				case 0:
					step = mainMenu(sc);
				case 1: //new Connection
					
					break;
				case 2: //New User
					break;
					
				case 3: //Search Path
					sourceCity = getSourceCity(t, sc);
					destinationCity = getDestinationCity(t, sc);
					
					System.out.println("\nSource city: " + sourceCity);
					System.out.println("Destination city: " + destinationCity + "\n");
					
					meansOfTransportation = getMeansOfTransportation(sc);
					maxDuration = getMaxDuration(sc);
					
					weightFactor = getWeightFactor(sc);
					
					s = new SearchEngine(t);
					trips = s.rome2Rio(sourceCity, destinationCity, meansOfTransportation, weightFactor, maxDuration);
					
					boolean isEmpty = JavaUtils.printTrips(trips);
					
					if(!isEmpty) {
						step = 0; // Return to main menu
						break;
					}
					
					System.out.println("Please insert one of the following options:");
					System.out.println("*************************************");
					System.out.println("1 - Buy Tickets");
					System.out.println("2 - Go to Main Menu");
					System.out.println("*************************************");
					
					boolean procede = false;
					while (!procede ) {
						System.out.print("Your option is: ");
						int optionTicket = sc.nextInt();
						
						if (optionTicket == 2) {
							step = 0;
							procede = true;
						}
						else if (optionTicket == 1) {
							step = 5;
							procede = true;
						} else {
							System.out.println("Please insert a valid option");
						}
					}
					break;
				
				case 4:
					System.out.println("The program will shutdown...");
					System.exit(0);
				
				case 5: //Buy ticket
					Number userID = -1;
					Number password = -1;
					int tripID = -1;
					Number nrSeats = -1;
					
					userID = getUserID(sc);
					password = getPassword(sc);
					tripID = getTripID(sc, trips.size());
					nrSeats = getNrSeats(sc);
					
					System.out.println("\n\n");
					
					ticketS = new TicketingSystem(t);
					boolean successBuy = ticketS.buyTickets(userID, password, (Trip) trips.get(tripID - 1), nrSeats);
					
					if(successBuy) {
						System.out.println("You have successfully bought " + nrSeats + " tickets for the selected trip");
						step = 0;
					}
					else {
						System.out.println("\n\nDo you wish to try again? 1 - Yes | 0 - No");
						procede = false;
						while(!procede) {
							int in = sc.nextInt();
							if(in == 1) {
								procede = true;
							}
							else if(in == 0) {
								step = 0;
								procede = true;
							}
							else {
								System.out.println("Please insert a valid option");
							}
						}
					}
					break;
			}
		}
		
		
		
		
		
		
		
	}
	
	
}
