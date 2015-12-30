/*
 * SonarQube Java
 * Copyright (C) 2012-2016 SonarSource SA
 * mailto:contact AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
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
import org.sonar.plugins.java.api.tree.ForEachStatement;
import org.sonar.plugins.java.api.tree.ForStatementTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import java.util.regex.Pattern;

@Rule(key = "AV007",
  name = "Nombre de variables booleana",
  description = "El nombre de las variables boleana debe iniciar con is",
  priority = Priority.MINOR,
  tags = {Tag.CONVENTION})
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("2min")
public class AV007nombradoVariableBoolJava  extends BaseTreeVisitor implements JavaFileScanner {

  //String que inicia con is seguido de Upper Camel Case
  private static final String DEFAULT_FORMAT = "^is[A-Z0-9][a-z0-9]+[A-Za-z0-9]*";

  public String format = DEFAULT_FORMAT;

  private Pattern pattern = null;
  private JavaFileScannerContext context;

  @Override
  public void scanFile(JavaFileScannerContext context) {
    if (pattern == null) {
      pattern = Pattern.compile(format, Pattern.DOTALL);
    }
    this.context = context;
    // For debugging purpose, you can print out the entire AST of the analyzed file
    System.out.println(PrinterVisitor.print(context.getTree()));
    scan(context.getTree());
  }

  @Override
  public void visitClass(ClassTree tree) {
    for (Tree member : tree.members()) {
      //if (member.is(Tree.Kind.VARIABLE)) {
        // skip check of field
      //  scan(((VariableTree) member).initializer());
      //} else {
        scan(member);
      //}
    }
  }

  @Override
  public void visitForStatement(ForStatementTree tree) {
    scan(tree.statement());
    
  }

  @Override
  public void visitForEachStatement(ForEachStatement tree) {
    scan(tree.statement());
  }

  @Override
  public void visitVariable(VariableTree tree) {
    //System.out.println("HAB: Bool? " + tree.type().symbolType() + tree.type().symbolType().is("java.lang.Boolean") );
    if ((tree.type().symbolType().is("boolean") || tree.type().symbolType().is("java.lang.Boolean")) && !pattern.matcher(tree.simpleName().name()).matches()) {
      context.addIssue(tree, this, "El nombre de las variables boleana debe iniciar con is seguido de Upper Pascal Case");
    }
    super.visitVariable(tree);
  }

}