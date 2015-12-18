/**
 *This file is the sample code against we run our unit test.
 *It is placed src/test/files in order to not be part of the maven compilation.
 **/
class AvoidAnnotationCheck {

  int AField; // Noncompliant {{Renombre el identificador de la variable ya que no cumple la notacion Lower Camel Case}}
  
  public void aMethod() {
     int aCa; //Compliant
     float NotCompliant; // Noncompliant {{Renombre el identificador de la variable ya que no cumple la notacion Lower Camel Case}}
            
  }
}
