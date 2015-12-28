

class A {
    
  //No soy miembro validado
  int abc; // Noncompliant {{Nombrado de la variable miembro de clase debe cumplir la convencion iniciar con underscore}}
    
  void foo() {
  }
}

class MyList {
    
 
// Soy miembro valido
  int _miVariable;
  public MyList() {
  }
}
