package org.sonar.samples.java;

import java.util.List;

import org.sonar.plugins.java.api.JavaCheck;
import org.sonar.samples.java.checks.AV001nombradoClasesJava;
import com.google.common.collect.ImmutableList;
import org.sonar.samples.java.checks.AV002nombradoMetodosJava;


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
      .add(AV002nombradoMetodosJava.class)
      .build();
  }

  public static List<Class<? extends JavaCheck>> getJavaTestChecks() {
    return ImmutableList.<Class<? extends JavaCheck>>builder()
      .build();
  }
}
