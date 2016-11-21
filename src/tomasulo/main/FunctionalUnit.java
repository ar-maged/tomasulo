package tomasulo.main;

public class FunctionalUnit { 
	
	//public Object Instruction;
	public String OP;//assigned according to the incoming instruction type
	public String Name=this.OP; //name of the FU corresponds to the operation and Qj/Qk
	
	public int Vk; //assigned according to the incoming operands values from scoreboard/instruction
	public int Vj; 
	public int A;   
	public int offset;
	public static long addressCalculated;
	public int imm; 
	public int PCvalue;
	public static int result; //static variable to pass the result
	public int numberOfcycles; //main counter passed to every class
	
	boolean writtenToROB=false;
	boolean free=true;
	
   
     public void execute() 
     {
	    		switch(OP) //switch on the incoming instruction's operation instance 
	    		            //from the instruction object instead of string
	    		{
	    		case "ADD" : add(); break;  
	    		case "ADDI": add(); break;
	    		case "SUB" : subtract(); break;
	    		case "MULT" : multiply(); break; 
	    		case "LW": add(); break;
	    		case "SW":add(); break; 
	    		case "NAND": nand(); break; 
	    		case "JMP": add(); break;
	    		}
	    	
	  }  
	    
    
     public void add() 
     { 
    	 if(OP=="ADD")
    	 {
    	this.result = this.Vj + this.Vk; 
    	numberOfcycles++; 
    	free=false;  
    	  writetoROB();
    	//updateScoreBoard(Qk,Qj,Vj,Vk,free); 
    	 } 
    	 if(OP=="JMP")
    	 {
    		 this.result = PCvalue+1+Vj+imm;
        	 numberOfcycles++;
        	 free=false;
        	  writetoROB();
        	//updateScoreBoard(Qk,Qj,Vj,Vk,free);
    	 }
    	 if(OP=="ADDI")
    	 {
    		 this.result = this.Vj + this.imm; 
    	    	numberOfcycles++; 
    	    	free=false;  
    	    	  writetoROB();
    	    	//updateScoreBoard(Qk,Qk,Vj,Vk,free);
    	 }
         if((OP=="LW")||(OP=="SW"))
         {
        	 addressCalculated=this.offset+this.A; 
         	//load data from memory to Vk
         	numberOfcycles+=2; 
         	free=false;  
         	  writetoROB(); 
         	//updateScoreBoard(Qk,Qj,Vj,Vk,free);
         }
 
     } 
    
     public void nand() 
     {  
    	if((this.Vj==1)&&(this.Vk==1))
    	     this.result=0; 
    	else 
    		this.result=1;
    	
    	numberOfcycles++; 
    	free=false;  
    	  writetoROB();
    	//updateScoreBoard(Qk,Qj,Vj,Vk,free);
     }
     
     public void subtract()
     {
    	this.result = this.Vj - this.Vk; 
     	numberOfcycles++; 
     	free=false;  
    	   writetoROB();
     	//updateScoreBoard(Qk,Qj,Vj,Vk,free);
     } 
     
     public void multiply() 
     {
    	this.result = this.Vj * this.Vk; 
      	numberOfcycles++; 
      	free=false;  
    	   writetoROB();
      	//updateScoreBoard(Qk,Qj,Vj,Vk,free);
     } 
    
     public void writetoROB() //writes results to ROB 
     {
    	     //call ROB function for writing
    		  writtenToROB=true;
    	
     }
     
   


} 


