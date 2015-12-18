package org.sonar.samples.java.checks;

import org.junit.Rule;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.squidbridge.checks.CheckMessagesVerifierRule;

public class AV004nombradoParametrosMetodoCheckTest {



  @Test
  public void detected() {

    // Use an instance of the check under test to raise the issue.
    AV004nombradoParametrosMetodoJava check = new AV004nombradoParametrosMetodoJava();
    

    // Verifies that the check will raise the adequate issues with the expected message.
    // In the test file, lines which should raise an issue have been commented out
    // by using the following syntax: "// Noncompliant {{EXPECTED_MESSAGE}}"
    JavaCheckVerifier.verify("src/test/files/AV004.java", check);
  }
}
