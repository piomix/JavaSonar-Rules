package org.sonar.samples.java.checks;

import java.util.List;
import java.util.regex.Pattern;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.java.tag.Tag;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

@Rule(key = "AV003",
  name = "Nombre de variables con notacion Lower Camel Case",
  description = "El nombre de las variables de cumplir la notacion Pascal Case",
  priority = Priority.MINOR,
  tags = {Tag.CONVENTION})
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("10min")
public class AV003nombradoVariablesJava extends BaseTreeVisitor implements JavaFileScanner {

  private static final String DEFAULT_VALUE = "Inject";

  private JavaFileScannerContext context;

  //Lower Camel Case: Tomado de http://stackoverflow.com/questions/19021873/upper-and-lower-camel-case
  private static final String DEFAULT_FORMAT = "[a-z]+[A-Z0-9][a-z0-9]+[A-Za-z0-9]*";
  
  public String format = DEFAULT_FORMAT;
  private Pattern pattern = null;

  @Override
  public void scanFile(JavaFileScannerContext context) {
    if (pattern == null) {
      pattern = Pattern.compile(format);
    }
    this.context = context;

    scan(context.getTree());

    System.out.println(PrinterVisitor.print(context.getTree()));
  }

  @Override
  public void visitVariable(VariableTree tree) {
    System.out.println("HAB: Antes de validar...");
    // Si el nombre de la variable no es vacio
    if (tree.is(Tree.Kind.VARIABLE) &&  tree.simpleName().name() != null){ 
     // Adds an issue by attaching it with the tree and the rule 
     if (!pattern.matcher(tree.simpleName().name()).matches()) {
        System.out.println("HAB: No cumpli...");
        context.addIssue(tree, this, "Renombre el identificador de la variable ya que no cumple la notacion Lower Camel Case");
      } 
     }
    
    // The call to the super implementation allows to continue the visit of the AST.
    // Be careful to always call this method to visit every node of the tree.
    super.visitVariable(tree);
  }
}
