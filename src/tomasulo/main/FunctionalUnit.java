package tomasulo.main;

public class FunctionalUnit { 
	
	
	public String OP;//assigned according to the incoming instruction type
	public String Qk;//assigned according to the final state of the scoreboard
	public String Qj;  
	public String Name=this.OP; //name of the FU corresponds to the operation
	
	public int Vk;
	public int Vj; 
	public int A;   
	public static int result; //static variable to pass the result
	public int numberOfcycles; //main counter passed to every class
	
	boolean writtenToROB=false;
	boolean free=true;  
	boolean pendingInstruction=false;
	
   
     public void execute() 
     {
	    if(free) //if FU is free
	    {
	    	if((Qk==null)&&(Qj==null)) //if both operands are ready => execute the operation
	    	{
	    		switch(OP) //switch on the incoming instruction as an enum instead of string
	    		{
	    		case "ADD" : add(); break; 
	    		case "SUB" : subtract(); break;
	    		case "MULT" : multiply(); break;
	    		}
	    	} 
	    	
	    	else //if one of the operands are not ready => stall and save operands on hold
	    	{
	    		pendingInstruction=true; 
	    		
	    	}
	    	
	    }  
	    
	    else //if FU is busy
	    {
	    	
	    }
         
     }
  
     
     public void add() 
     { 
    	this.result = this.Vj + this.Vk; 
    	numberOfcycles++; 
    	free=false;  
    	this.Qj=this.Name;
    	this.Qk=this.Name; 
    	  writetoROB();
    	//updateScoreBoard(Qk,Qj,Vj,Vk,free);
     } 
     
     public void subtract()
     {
    	this.result = this.Vj - this.Vk; 
     	numberOfcycles++; 
     	free=false;  
     	this.Qj=this.Name;
    	this.Qk=this.Name; 
    	   writetoROB();
     	//updateScoreBoard(Qk,Qj,Vj,Vk,free);
     } 
     
     public void multiply() 
     {
    	this.result = this.Vj * this.Vk; 
      	numberOfcycles++; 
      	free=false;  
      	this.Qj=this.Name;
    	this.Qk=this.Name;
    	   writetoROB();
      	//updateScoreBoard(Qk,Qj,Vj,Vk,free);
     } 
     
     public void writetoROB() //writes results to ROB 
     {
    	     //call ROB function for writing
    		  writtenToROB=true;
    	
     }
     
     public void clear() //clears FU after execusion
     {
    	 this.Qj=null;
    	 this.Qk=null; 
    	 this.Name=null;
    	 free=true; 
    	 writtenToROB=false;
    	//updateScoreBoard(Qk,Qj,Vj,Vk,free);
    	 
    			 
     }



    




} 


