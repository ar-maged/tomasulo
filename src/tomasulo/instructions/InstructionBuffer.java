package tomasulo.instructions;
import java.util.ArrayList;
import java.util.Arrays;



public class InstructionBuffer {
 
			
	

	 private ArrayList<Instruction>instBuffer;
	int size;

	public  InstructionBuffer(){

	}
	
	public  InstructionBuffer(int size){
	 instBuffer=new ArrayList <Instruction>();
	 this.size = size;
	}

	
	public void Insert(Instruction [] instArray){
		
		for(int i=0; i<instArray.length ; i++){
			if(instBuffer.size() < this.size)
				instBuffer.add(instArray[i]);
			else
				break;
		}
		
	}

	
	public  Instruction remove(){
		
		Instruction inst=instBuffer.remove(0);
		
		return inst;
		
	}
	
	
	

}
