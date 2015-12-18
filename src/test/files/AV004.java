package company.demo;

/**
 *This file is the sample code against we run our unit test.
 *It is placed src/test/files in order to not be part of the maven compilation.
 **/
class BusinessClassDelegate {

  int aField;

  public void aMethod() {

  }

  public void aMethod(int miParam, int MiParametro) { // Noncompliant {{Parametro no cumple notacion Lower Camel Case}}

  }

}
