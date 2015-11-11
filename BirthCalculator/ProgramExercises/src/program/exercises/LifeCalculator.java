package program.exercises;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LifeCalculator {

	private final int MIN_YEAR = 1900;
	private final int MAX_YEAR = 2000;
	
	//bucket for years with values representing number of living people in data

	private int[] years;
	private int mostAliveYear;
	private int maxAlive;
	
	public LifeCalculator(ResultSet data){
		try{
			years = new int[MAX_YEAR - MIN_YEAR + 1];
			int[] born;
			int[] died;
			born = new int[MAX_YEAR - MIN_YEAR + 1];
			died = new int[MAX_YEAR - MIN_YEAR + 1];
			while(data.next()){
				int birthYear = data.getInt("Birth_Year");
				int deathYear = data.getInt("Death_Year");
				born[birthYear - MIN_YEAR]++;
				died[deathYear - MIN_YEAR]++;
				
			}
			//now we have the full set of births and deaths by year
			int living = 0;
			
			maxAlive = 0;
			for(int i = 0; i < MAX_YEAR - MIN_YEAR; i++){
				//determine the living for this year
				living = living + born[i] - died[i];
				years[i] = living;
				if(living > maxAlive){
					maxAlive = living;
					mostAliveYear = MIN_YEAR + i;
				}
			}
		}
		catch(SQLException ex){
			System.out.println("Error: "  + ex.getMessage().toString());
		}

	}
	
	public int maxYear(){
		return mostAliveYear;
	}
	
	public int[] getData(){
		return years;
	}
	public List<Integer> getListData(){
		List<Integer> list = new ArrayList<Integer>();
		for(int i = 0; i < years.length; i++){
			Integer num = new Integer(i);
			list.add(num);
		}
		return list;
	}
	
	public int maxAlive(){
		return maxAlive;
	}
	
	
	
}
