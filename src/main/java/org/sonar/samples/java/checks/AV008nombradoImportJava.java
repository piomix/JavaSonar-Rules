package org.sonar.samples.java.checks;


import java.util.regex.Pattern;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Rule;
import org.sonar.java.tag.Tag;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.CompilationUnitTree;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.samples.java.utils.PackageUtils;

@Rule(
  key = "AV008",
  name = "Nombrado de package de acuerdo al estandar",
  description = "Los paquetes deben nombrarse de acuerdo al estandar aviancataca.<nombreproyecto>.<componenteArquitectura>.<paquete>",
  tags = {Tag.CONVENTION})
@SqaleConstantRemediation("3min")
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
public class AV008nombradoImportJava extends BaseTreeVisitor implements JavaFileScanner {

  //String que cumple la notacion aviancataca.<nombreproyecyo>.<componentearquitectura>.<paquete>
  private static final String DEFAULT_FORMAT = "^aviancataca\\.[a-z]+\\.[a-z]+\\.[a-z]+";

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
  public void visitCompilationUnit(CompilationUnitTree tree) {
    if (tree.packageDeclaration() != null) {
      String name = PackageUtils.packageName(tree.packageDeclaration(), ".");
      System.out.println("HAB: Package? " + name );
      if (!pattern.matcher(name).matches()) {
        context.reportIssue(this, tree.packageDeclaration().packageName(), "Los paquetes deben nombrarse de acuerdo al estandar aviancataca.<nombreproyecto>.<componenteArquitectura>.<paquete>");
      }
    }
  }
}
