/*
 * SonarQube Java
 * Copyright (C) 2012 SonarSource
 * sonarqube@googlegroups.com
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
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.samples.java.checks;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.java.tag.Tag;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import java.util.regex.Pattern;
import org.sonar.squidbridge.annotations.ActivatedByDefault;

@Rule(
  key = "Av001",
  name = "Nombrado de clases JAVA",
  description = "Nombre de clase debe cumplir la notacion Pascal Case",
  priority = Priority.MINOR,
  tags = {Tag.CONVENTION})
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("10min")
//@ActivatedByDefault
public class AV001nombradoClasesJava extends BaseTreeVisitor implements JavaFileScanner {

  /* Expresion regular tomada de http://stackoverflow.com/questions/1128305/regular-expression-to-identify-camelcased-words
  Okay, then: [A-Z]([A-Z0-9]*[a-z][a-z0-9]*[A-Z]|[a-z0-9]*[A-Z][A-Z0-9]*[a-z])[A-Za-z0-9]* 
  It matches strings that start with an uppercase letter, contain only letters and numbers, and contain at least one lowercase letter 
  and at least one other uppercase letter. â€“ Adam Crume Jul 15 '09 at 17:50
  */
  private static final String DEFAULT_FORMAT = "^[A-Z0-9][a-z0-9]+(?:[A-Z0-9][a-z0-9]+)*$";

  @RuleProperty(
    key = "format",
    description = "El nombramiento de las clases debe realizarse utilizando Pascal Casing",
    defaultValue = "" + DEFAULT_FORMAT)
  
  public String format = DEFAULT_FORMAT;

  private Pattern pattern = null;
  private JavaFileScannerContext context;

  @Override
  public void scanFile(JavaFileScannerContext context) {
    if (pattern == null) {
      pattern = Pattern.compile(format);
    }
    this.context = context;
    scan(context.getTree());
  }

  @Override
  public void visitClass(ClassTree tree) {
    System.out.println("HAB: Iniciando validacion Clase...");
    
    if (tree.is(Tree.Kind.CLASS) && tree.simpleName() != null) {
      if (!pattern.matcher(tree.simpleName().name()).matches()) {
           System.out.println("HAB: No Cumpli validacion...");
          // For debugging purpose, you can print out the entire AST of the analyzed file
          //System.out.println(PrinterVisitor.print(context.getTree()));
          context.addIssue(tree, this, "Renombre el identificador de la clase ya que no cumple la notacion Pascal Case");
      }
      else // For debugging purpose, you can print out the entire AST of the analyzed file
          System.out.println("Match con patrón definido para la clase: " + tree.simpleName().name());
    }
    super.visitClass(tree);
  }

}