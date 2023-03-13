
public class Main {

	public static void main(String[] args) {
		try {

            Graph testGraph = new Graph("facebook_social_network.txt");
           
            Graph testGraphTwo = new Graph("karate_club_network.txt");
            Centrality c4 = new Centrality(testGraph);
            Centrality c5 = new Centrality(testGraphTwo);    
            
            System.out.println("2019510066 ENES SALIK");
           
            System.out.print("Zachary Karate Club Network - The Highest Node For Betweeness : ");
            System.out.print(c5.getBetweenessCentrality());
            System.out.println("  Value : "+Math.round(c5.getBetweennessCentralitiesw()));        
            System.out.print("Zachary Karate Club Network - The Highest Node For Closeness : ");
            System.out.print(c5.getClosenessCentrality());
            System.out.println("  Value : "+c5.getClosenessCentralitiesw());  
         
            
            System.out.println();
            System.out.print("Facebook Social Network - The Highest Node For Betweeness : ");
            System.out.print(c4.getBetweenessCentrality());
            System.out.println("  Value : "+c4.getBetweennessCentralitiesw());  
            System.out.print("Facebook Social Network - The Highest Node For Closeness : ");
            System.out.print(c4.getClosenessCentrality());
            System.out.println("  Value : "+c4.getClosenessCentralitiesw());            

            
        
        } catch (Exception e) {
            System.out.println(e);
        }

	}

}
