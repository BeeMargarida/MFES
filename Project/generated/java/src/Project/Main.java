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
	public static void main(String[] args) {
		TransportGraph t = null;
		SearchEngine s = null;
		TicketingSystem ticketS = null;
		VDMSeq trips = null;
		boolean procede = false;
		boolean sourceChosen = false;
		boolean destinationChosen = false;
		String sourceCity = null;
		String destinationCity = null;
		int weightFactor = -1;
		VDMSet meansOfTranportation = null;
		int maxDuration = -2;
		
		System.out.println("Welcome to Rome2Rio!");
		System.out.println("Press 1 to load our database. We will travel the world in the most efficient time!");
		
		Scanner sc = new Scanner(System.in);
		int input = sc.nextInt();
		
		while (!procede ) {
			if (input == 1) {
				System.out.println("Loading database...");
				t = new TransportGraph();
				procede = true;
			} else {
				System.out.println("We can not procede without the database.");
			}
		}
		
		System.out.println("Loading done! Now let's find the best trip for you...\n");
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
		System.out.println("\nSource city: " + sourceCity);
		System.out.println("Destination city: " + destinationCity + "\n");
		
		System.out.println("Please insert one of the following options:");
		System.out.println("*************************************");
		System.out.println("1 - choose means of transportation");
		System.out.println("2 - all combinations of transportation");
		System.out.println("*************************************");
		
		while (meansOfTranportation == null) {
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
						meansOfTranportation = JavaUtils.checkValidOptionsAndProcess(listOptions);
						if(meansOfTranportation == null) {
							System.out.println("Invalid input, please try again");
						}
						done = true;
					}
					break;
				case 2:
					meansOfTranportation = SetUtil.set();
					break;
				default:
					System.out.println("Invalid input, please try again");
			}
		}
		
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
					s = new SearchEngine(t);
					trips = s.rome2Rio(sourceCity, destinationCity, meansOfTranportation, weightFactor, maxDuration);
					break;
				case 2:
					System.out.println("Searching path with best price...");
					s = new SearchEngine(t);
					trips = s.rome2Rio(sourceCity, destinationCity, meansOfTranportation, weightFactor, maxDuration);
					break;
				case 3:
					System.out.println("Searching path with the shortest duration in time...");
					s = new SearchEngine(t);
					trips = s.rome2Rio(sourceCity, destinationCity, meansOfTranportation, weightFactor, maxDuration);
					break;
				default:
					System.out.println("Invalid input, please try again");
			}
			
			System.out.println(trips.toString());
		}
		
	}
	
	
}
