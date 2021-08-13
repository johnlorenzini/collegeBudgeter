/*
BudgetBudgeter v1.0
John Lorenzini
*/

import java.util.*;
import java.io.*;
import java.text.DecimalFormat;
public class Compute {
    private double budget; String college; Map<String, Double> cityIndex; Double[] budgetPercentages = new Double[6];
    
    public Compute(){
        budget = 0;
        college = "";
        cityIndex = new HashMap<String, Double>();
    }
    
    public Compute(double b, String c){
        budget = b;
        college = c;
        cityIndex = new HashMap<String, Double>();
    }

	public String[][] threeDArray(){ //Converts Map to 2D array for Swing purposes
		String[][] combined = new String[cityIndex.size()][2];
		int count = 0;
		for(String i : cityIndex.keySet()){
			combined[count][0] = i;
			combined[count][1] = "$" + Double.toString(cityIndex.get(i));
			count++;
		}
		for(int i = 0; i < combined.length; i++){
			System.out.println(Arrays.toString(combined[i]));
		}
		return combined;
		
	}


	public void defaultValues(){ //Updates balance chart with default "Recommended" budget values (To be utilized with updateBudgetPercentages in the future)
		for(int i = 0; i < budgetPercentages.length; i++){
			switch(i){
				case 0:
					budgetPercentages[i] = 18.75;
					break;
				case 1:
					budgetPercentages[i] = 7.50;
					break;
				case 2:
					budgetPercentages[i] = 40.00;
					break;
				case 3:
					budgetPercentages[i] = 12.50;
					break;
				case 4:
					budgetPercentages[i] = 8.75;
					break;
				case 5:
					budgetPercentages[i] = 12.50;
					break;
				default:
					break;
			}
		}
	}

	public void updateBudgetPercentages(String n){ //allows user to specify custom budget percentages (TO BE IMPLEMENTED)
		boolean execute = true;
		while(execute);
			if(n.equals("y")){
				System.out.println("Reminder: Custom %s MUST add to 100%!");
				int count = 0;
				Scanner scan = new Scanner(System.in);
				for(String i : cityIndex.keySet()){
					System.out.println("--------------------");
					System.out.print(i + ": ");
					double temp = scan.nextDouble();
					scan.nextLine();
					System.out.println("--------------------");
					budgetPercentages[count] = temp;
					count++;
				}
				double total = 0;
				for(int i = 0; i < budgetPercentages.length; i++){
					total+= budgetPercentages[i];
				}
				if(total != 100.0){
					System.out.println("ERROR: %s MUST add to 100%!");
					System.out.println("Would you like to use the default values? (Y/N)");
					String tempp = scan.next();
					if(tempp.equals("y") || tempp.equals("Y")){
						updateBudgetPercentages("n");
					}
					else{
						updateBudgetPercentages("y");
					}
				}
				else{
					execute = false;
				}
				scan.close();
			}
			else{
				defaultValues();
				execute = false;
			}
			
	}
    
	//Updates cityIndex with city-specific indexes to calculate budget with
    public void updateCityDifferences(){ 
		String templatePath = "Data/" + college.toUpperCase() + ".txt";
        File read = new File(templatePath);
        try{
            Scanner reader = new Scanner(read);
            while(reader.hasNextLine()){
                String c = reader.next();
                reader.nextLine();
                Double v = reader.nextDouble();
                cityIndex.put(c,v);
            } 
            reader.close(); 
        }
        catch(Exception e){
            System.out.println("Invalid College Option! Would you like to try a different option? (Y/N)");
        }
    }
	
    /*
	computes city-specific differences from natl avg, 
	multiplies against common budget %s, 
	and scales properly within budget.
	for cities with higher-than-average
	cost of living, the difference comes
	out of savings
	*/
    public void computeUpdatedAvgs(){ 
        int count = 0;
        for(String i : cityIndex.keySet()){
            if(count == cityIndex.size()){
                break;
            }
			else{
				cityIndex.put(i, (((cityIndex.get(i)/100)*(budgetPercentages[count]/100.00))*budget));
				count++;
			}
		}
		double total = 0.0;
		for(double i:cityIndex.values()){
			total+= i;
		}
		if(total > budget){
			total -= budget;
			double temp = cityIndex.get("Savings");
			temp -= total;
			if(temp < 0.0){
				temp = 0.0;
			}
			cityIndex.put("Savings", temp);
		}
    }
    
	//formats to two decimal places
	public void formatVals(){
		DecimalFormat f = new DecimalFormat("##.00");
		for(String i : cityIndex.keySet()){
			cityIndex.put(i, Double.parseDouble(f.format(cityIndex.get(i))));
		}
	}
	
}
