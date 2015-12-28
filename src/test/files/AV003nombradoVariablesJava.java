/**
 *This file is the sample code against we run our unit test.
 *It is placed src/test/files in order to not be part of the maven compilation.
 **/
class AvoidAnnotationCheck {

  int UnCampo =0; // Noncompliant {{Renombre el identificador del parametro o variable ya que no cumple la notacion Lower Camel Case}}
  
  public void aMethod(int aBc, int ABC) { // Noncompliant {{Renombre el identificador del parametro o variable ya que no cumple la notacion Lower Camel Case}}
     int aCa; //Compliant
     float NotCompliant; // Noncompliant {{Renombre el identificador del parametro o variable ya que no cumple la notacion Lower Camel Case}}
     for (int iOi=0; iOi != 1; iOi ++) aCa =0;
  }
}
