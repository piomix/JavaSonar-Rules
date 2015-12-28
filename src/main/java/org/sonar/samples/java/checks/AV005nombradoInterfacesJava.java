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
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import java.util.regex.Pattern;

@Rule(
  key = "AV005",
  name = "Los nombres de Interface deben cumplir la convencion de avianca",
  description = "El nombre de la interfaz debe iniciar con la letra I seguido del nombre de interfaz",
  priority = Priority.MINOR,
  tags = {Tag.CONVENTION})
@ActivatedByDefault
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("2min")
public class AV005nombradoInterfacesJava extends BaseTreeVisitor implements JavaFileScanner {

  //HAB: Expresion regular para que el nombre de la interfaz inicie con I.  
  private static final String DEFAULT_FORMAT = "^[I][a-zA-Z0-9]*$";

  public String format = DEFAULT_FORMAT;

  private Pattern pattern = null;
  private JavaFileScannerContext context;

  @Override
  public void scanFile(JavaFileScannerContext context) {
    if (pattern == null) {
      pattern = Pattern.compile(format);
    }
    this.context = context;
    // For debugging purpose, you can print out the entire AST of the analyzed file
    //System.out.println(PrinterVisitor.print(context.getTree()));
    scan(context.getTree());
  }

  @Override
  public void visitClass(ClassTree tree) {
    //System.out.println("HAB: No match? " + tree.is(Tree.Kind.INTERFACE) + !pattern.matcher(tree.simpleName().name()).matches());
    if (tree.is(Tree.Kind.INTERFACE) && !pattern.matcher(tree.simpleName().name()).matches()) {
      context.addIssue(tree, this, "El nombre de la interfaz debe iniciar con la letra I seguido del nombre de interfaz");
    }

    super.visitClass(tree);
  }

}