/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

/**
 *
 * @author hbuitrago
 */
public class AV009indetancionLlavesJavaCheckTest {
      @Test
  public void check() {
    // Verifies that the check will raise the adequate issues with the expected message.
    // In the test file, lines which should raise an issue have been commented out
    // by using the following syntax: "// Noncompliant {{EXPECTED_MESSAGE}}"
    JavaCheckVerifier.verify("src/test/files/AV009.java", new AV009indentacionLlaveAbreJava());
    //avaCheckVerifier.verify("src/test/files/AV009.java", new AV009indentacionLlaveIzquierdaJava());
  }
}
