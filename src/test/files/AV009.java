class A 
{

  int foo() 
  
  {
   if(true){ // Noncompliant {{Una llave que abre un bloque debe localizar en la linea siguiente de manera exclusiva}}
  }
   for(int i=0;i<1;i++ )
   {
   }
   return 0;} 
 
}