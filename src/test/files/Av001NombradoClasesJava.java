
import org.apache.commons.lang.NotImplementedException;

public class AV001NombradoClasesJava { // Noncompliant {{Renombre el identificador de la clase ya que no cumple la notacion Pascal Case}}

    public String intToEnglishValue(int i) {
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
	
	public void miMetodo(int a, int b){ 
		
	}

}