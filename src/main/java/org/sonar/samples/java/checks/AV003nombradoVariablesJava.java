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

@Rule(key = "AV003",
  name = "Nombre de variables con notacion Lower Camel Case",
  description = "El nombre de las variables de cumplir la notacion Pascal Case",
  priority = Priority.MINOR,
  tags = {Tag.CONVENTION})
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("10min")
public class AV003nombradoVariablesJava  extends BaseTreeVisitor implements JavaFileScanner {

  //Lower Camel Case: Tomado de http://stackoverflow.com/questions/19021873/upper-and-lower-camel-case
  private static final String DEFAULT_FORMAT = "[a-z]+[A-Z0-9][a-z0-9]+[A-Za-z0-9]*";

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
    if (!pattern.matcher(tree.simpleName().name()).matches()) {
      context.addIssue(tree, this, "Renombre el identificador del parametro o variable ya que no cumple la notacion Lower Camel Case");
    }
    super.visitVariable(tree);
  }

}
