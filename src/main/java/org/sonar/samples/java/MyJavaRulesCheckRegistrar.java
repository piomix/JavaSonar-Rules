/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sonar.samples.java;

import java.util.Arrays;

import org.sonar.plugins.java.api.CheckRegistrar;
import org.sonar.plugins.java.api.JavaCheck;
import org.sonar.samples.java.checks.AV001nombradoClasesJava;
import org.sonar.samples.java.checks.AV002nombradoMetodosVariablesJava;
import org.sonar.samples.java.checks.AV003nombradoVariablesJava;
import org.sonar.samples.java.checks.AV004nombradoParametrosMetodoJava;
import org.sonar.samples.java.checks.AV005nombradoInterfacesJava;
import org.sonar.samples.java.checks.AV006nombradoVariableMiembroJava;
import org.sonar.samples.java.checks.AV007nombradoVariableBoolJava;
import org.sonar.samples.java.checks.AV008nombradoImportJava;
import org.sonar.samples.java.checks.AV009indentacionLlaveAbreJava;


/**
 * Provide the "checks" (implementations of rules) classes that are gonna be executed during
 * source code analysis.
 *
 * This class is a batch extension by implementing the {@link org.sonar.plugins.java.api.CheckRegistrar} interface.
 */
public class MyJavaRulesCheckRegistrar implements CheckRegistrar {

  /**
   * Register the classes that will be used to instantiate checks during analysis.
   */
  @Override
  public void register(RegistrarContext registrarContext) {
    // Call to registerClassesForRepository to associate the classes with the correct repository key
    registrarContext.registerClassesForRepository(RulesList.REPOSITORY_KEY, Arrays.asList(checkClasses()), Arrays.asList(testCheckClasses()));
  }

  /**
   * Lists all the checks provided by the plugin
   */
  public static Class<? extends JavaCheck>[] checkClasses() {
    return new Class[] {
      AV001nombradoClasesJava.class, 
      AV002nombradoMetodosVariablesJava.class,
      AV003nombradoVariablesJava.class,
      AV004nombradoParametrosMetodoJava.class,
      AV005nombradoInterfacesJava.class,
      AV006nombradoVariableMiembroJava.class,
      AV007nombradoVariableBoolJava.class,
      AV008nombradoImportJava.class,
      AV009indentacionLlaveAbreJava.class
      };
  }

  /**
   * Lists all the test checks provided by the plugin
   */
  public static Class<? extends JavaCheck>[] testCheckClasses() {
    return new Class[] {};
  }
}