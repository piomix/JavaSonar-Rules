/*
 * Creation : 20 avr. 2015
 */
package org.sonar.samples.java;

import org.apache.log4j.Logger;

/**
 * A class with extends another class outside the JVM but in classpath
 */
public class AvoidSuperClassCheck extends Logger { 

  boolean  myBoolean; // Noncompliant {{El nombre de las variables boleana debe iniciar con is seguido de Upper Pascal Case}}
  Boolean isBool;
  Boolean myBool; // Noncompliant {{El nombre de las variables boleana debe iniciar con is seguido de Upper Pascal Case}}
  protected AvoidSuperClassCheck(String name, boolean isBoolean) {
    super(name);
  }

}
