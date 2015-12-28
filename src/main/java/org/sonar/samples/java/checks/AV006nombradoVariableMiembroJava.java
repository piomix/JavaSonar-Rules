/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sonar.samples.java.checks;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.java.tag.Tag;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import java.util.regex.Pattern;

@Rule(
  key = "AV006",
  name = "Nombrado de la variable miembro de clase debe cumplir la convencion Avianca",
  description = "El nombre de la variable miembro de iniciar con underscore",
  priority = Priority.MINOR,
  tags = {Tag.CONVENTION})

@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("2min")
public class AV006nombradoVariableMiembroJava  extends BaseTreeVisitor implements JavaFileScanner {

  private static final String DEFAULT_FORMAT = "^_[a-z]+[A-Z0-9][a-z0-9]+[A-Za-z0-9]*";

  public String format = DEFAULT_FORMAT;

  private Pattern pattern = null;
  private JavaFileScannerContext context;

  @Override
  public void scanFile(JavaFileScannerContext context) {
    if (pattern == null) {
      pattern = Pattern.compile(format, Pattern.DOTALL);
    }
    this.context = context;
    scan(context.getTree());
  }

  @Override
  public void visitClass(ClassTree tree) {
    for (Tree member : tree.members()) {
      if (member.is(Tree.Kind.VARIABLE) && !member.parent().is(Tree.Kind.METHOD)) {
        // skip variables inside a method
        //System.out.println("HAB: No soy variable de metodo...");
         scan(member);
      }
    }
  }

  @Override
  public void visitVariable(VariableTree tree) {
    if (!pattern.matcher(tree.simpleName().name()).matches()) {
      //System.out.println("HAB: No empiezo con _ ");
      context.reportIssue(this, tree.simpleName(), "Nombrado de la variable miembro de clase debe cumplir la convencion iniciar con underscore");
    }
    super.visitVariable(tree);
  }

}