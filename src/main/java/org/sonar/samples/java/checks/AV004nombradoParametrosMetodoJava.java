/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sonar.samples.java.checks;

import com.google.common.collect.ImmutableList;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import java.util.List;
import java.util.regex.Pattern;
import org.sonar.java.tag.Tag;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.VariableTree;

@Rule(
  key = "AV004",
  name = "Parametro de un metodo de cumplir la notacion Lower Camel Case",
  description = "El nombre de los parametros debe cumplir notacion Lower Pascal Case",
  priority = Priority.MINOR,
  tags = {Tag.CONVENTION})
@ActivatedByDefault
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("2min")
public class AV004nombradoParametrosMetodoJava extends IssuableSubscriptionVisitor{

 //Lower Camel Case: Tomado de http://stackoverflow.com/questions/19021873/upper-and-lower-camel-case
  private static final String DEFAULT_FORMAT = "[a-z]+[A-Z0-9][a-z0-9]+[A-Za-z0-9]*";
  
  public String format = DEFAULT_FORMAT;
  private Pattern pattern = null;

  @Override
  public List<Kind> nodesToVisit() {
    return ImmutableList.of(Tree.Kind.METHOD, Tree.Kind.CONSTRUCTOR);
  }
  
   @Override
   public void scanFile(JavaFileScannerContext context) {
    pattern = Pattern.compile(format, Pattern.DOTALL);
    super.scanFile(context);
  }

  @Override
  public void visitNode(Tree tree) {
	MethodTree methodTree = (MethodTree) tree;
    for (VariableTree var : methodTree.parameters()) {
		if(!pattern.matcher(var.simpleName().toString()).matches())
		addIssue(tree, "Parametro no cumple notacion Lower Camel Case");
    }
  }
}