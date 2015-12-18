package org.sonar.samples.java.checks;

import java.util.regex.Pattern;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.java.tag.Tag;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

@Rule(key = "AV002",
  name = "Nombre del metodo cumple la notación Camel Case",
  description = "Nombre del metodo cumple la notación Camel Case",
  priority = Priority.MINOR,
  tags = {Tag.CONVENTION}
  )
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("10min")
public class AV002nombradoMetodosJava extends BaseTreeVisitor implements JavaFileScanner {

  private JavaFileScannerContext context;

  protected static final String COMPANY_NAME = "Avianca";
  private static final String DEFAULT_FORMAT = "[A-Z]([A-Z0-9]*[a-z][a-z0-9]*[A-Z]|[a-z0-9]*[A-Z][A-Z0-9]*[a-z])[A-Za-z0-9]*";
  
  public String format = DEFAULT_FORMAT;
  private Pattern pattern = null;
  
  @Override
  public void scanFile(JavaFileScannerContext context) {
      
    //Inicializar Patron
    if (pattern == null) {
      pattern = Pattern.compile(format);
    }
      
    this.context = context;

    // The call to the scan method on the root of the tree triggers the visit of the AST by this visitor
    scan(context.getTree());

    // For debugging purpose, you can print out the entire AST of the analyzed file
    System.out.println(PrinterVisitor.print(context.getTree()));
  }

  /**
   * Overriding the visitor method to implement the logic of the rule.
   * @param tree AST of the visited method.
   */
  @Override
  public void visitMethod(MethodTree tree) {
    // Si el nombre del mètodo no es vacio
    if (tree.is(Tree.Kind.METHOD) &&  tree.simpleName().name() != null) {
        // Adds an issue by attaching it with the tree and the rule 
        if (!pattern.matcher(tree.simpleName().name()).matches()) {
            context.addIssue(tree, this, "Renombre el identificador del metodo ya que no cumple la notacion Pascal Case");
      } 

    }
    // The call to the super implementation allows to continue the visit of the AST.
    // Be careful to always call this method to visit every node of the tree.
    super.visitMethod(tree);

    // All the code located after the call to the overridden method is executed when leaving the node
  }

}
