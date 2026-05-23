package basic1;
import java.util.PriorityQueue;
import java.util.Collections;
 class PatientPriority {
	 		String name;
	 		int priority;
	 		public PatientPriority(String name,int priority) {
			this.name=name;
			this.priority=priority;
	 		}
public static void main(String[] args) {
	PriorityQueue<Integer>pq=new PriorityQueue<Integer>();
	pq.offer(30);
	pq.offer(20);
	pq.offer(50);
	pq.offer(40);
	pq.offer(10);
	System.out.println(pq);
	while(!pq.isEmpty()) {
		System.out.println("\nAscending Order: "+pq.poll());
	}
PriorityQueue<Integer>pq1=new PriorityQueue<Integer>(Collections.reverseOrder());
    pq1.offer(10);	
    pq1.offer(20);
    pq1.offer(70);
    pq1.offer(40);
    pq1.offer(30);
               System.out.println("\n"+pq1);
          while(!pq1.isEmpty()) {
	                  System.out.println("\nDescending Order: "+pq1.poll());
              }	
          PriorityQueue<PatientPriority>patients=new PriorityQueue<>((a,b)->a.priority-b.priority);
          patients.offer(new PatientPriority("John", 3));
          patients.offer(new PatientPriority("Bob", 1));
          patients.offer(new PatientPriority("Ravi", 2));
          System.out.println("Treatment Order");
          while(!patients.isEmpty()) {
        	  PatientPriority p=patients.poll();
        	  System.out.println(p.name+"->Priority"+p.priority);
          }
}
	
}
