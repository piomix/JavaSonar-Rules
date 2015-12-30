package org.sonar.samples.java;

import java.util.List;

import org.sonar.plugins.java.api.JavaCheck;
import org.sonar.samples.java.checks.AV001nombradoClasesJava;
import com.google.common.collect.ImmutableList;
import org.sonar.samples.java.checks.AV002nombradoMetodosVariablesJava;
import org.sonar.samples.java.checks.AV003nombradoVariablesJava;
import org.sonar.samples.java.checks.AV004nombradoParametrosMetodoJava;
import org.sonar.samples.java.checks.AV005nombradoInterfacesJava;
import org.sonar.samples.java.checks.AV006nombradoVariableMiembroJava;
import org.sonar.samples.java.checks.AV007nombradoVariableBoolJava;
import org.sonar.samples.java.checks.AV008nombradoImportJava;
import org.sonar.samples.java.checks.AV009indentacionLlaveAbreJava;



public final class RulesList {

  public static final String REPOSITORY_KEY = "Avianca";

  private RulesList() {
  }

  public static List<Class> getChecks() {
    return ImmutableList.<Class>builder().addAll(getJavaChecks()).addAll(getJavaTestChecks()).build();
  }

  public static List<Class<? extends JavaCheck>> getJavaChecks() {
    return ImmutableList.<Class<? extends JavaCheck>>builder()
      .add(AV001nombradoClasesJava.class)
      .add(AV002nombradoMetodosVariablesJava.class)
      .add(AV003nombradoVariablesJava.class)
      .add(AV004nombradoParametrosMetodoJava.class)
      .add(AV005nombradoInterfacesJava.class)
      .add(AV006nombradoVariableMiembroJava.class)
      .add(AV007nombradoVariableBoolJava.class)
      .add(AV008nombradoImportJava.class)
      .add(AV009indentacionLlaveAbreJava.class)
      .build();
  }

  public static List<Class<? extends JavaCheck>> getJavaTestChecks() {
    return ImmutableList.<Class<? extends JavaCheck>>builder()
      .build();
  }
}
