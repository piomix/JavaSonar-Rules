
import org.apache.commons.lang.NotImplementedException;

public class AV01NombradoClasesJava { 

    public String IntToEnglishValue001(int i) { 
        if (i == 1) {
            return "One";
        }
        if (i == 2) {
            return "Two";
        }
        if (i == 3) {
            return "Three";
        }
        if (i == 4) {
            return "Four";
        }
        if (i == 5) {
            return "Five";
        }
        if (i == 6) {
            return "Six";
        }
        if (i > 6) {
            throw new NotImplementedException();
        }
        return null;
    }
	
	public void miMetodo001(int a, int b){ // Noncompliant {{Renombre el identificador del metodo ya que no cumple la notacion Pascal Case}}
		
	}
        
        public static void main(){ //Cumple
         return;
        }

}