package Project;

import java.util.ArrayList;
import java.util.List;

import org.overture.codegen.runtime.SetUtil;
import org.overture.codegen.runtime.VDMSeq;
import org.overture.codegen.runtime.VDMSet;

public class JavaUtils {
	
	public static VDMSet checkValidOptionsAndProcess(List<String> list) {
		
		VDMSet answer = SetUtil.set();
		for(int i = 0; i < list.size(); i++) {
			if(Integer.parseInt(list.get(i)) < 1 || Integer.parseInt(list.get(i)) > 4) {
				return null;
			}
			answer = SetUtil.union(answer,SetUtil.set(Integer.parseInt(list.get(i))));
		}
		
		
		return answer;
		
	}
	
	public static boolean printTrips(VDMSeq trips) {
		
		System.out.println("**************RESULTS****************");
		System.out.println("*************************************");
		
		if(trips.size() == 0) {
			System.out.println("There are no possible paths");
			return false;
		}
		
		for(int i = 0; i < trips.size(); i++) {
			
			System.out.println("\n***************  " + (i + 1) +"  *****************");
			System.out.println(trips.get(i).toString());
			
		}
		
		return true;
		
	}
	
}
