// This is a generated file. Not intended for manual editing.
package greycat.idea.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static greycat.idea.psi.GCMTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import greycat.idea.psi.*;
import greycat.idea.GCMUtil;

public class GCMSubConstDeclarationImpl extends ASTWrapperPsiElement implements GCMSubConstDeclaration {

  public GCMSubConstDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull GCMVisitor visitor) {
    visitor.visitSubConstDeclaration(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof GCMVisitor) accept((GCMVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public GCMConstValueDeclaration getConstValueDeclaration() {
    return findChildByClass(GCMConstValueDeclaration.class);
  }

  @Override
  @NotNull
  public GCMTypeDeclaration getTypeDeclaration() {
    return findNotNullChildByClass(GCMTypeDeclaration.class);
  }

  @Override
  @NotNull
  public PsiElement getIdent() {
    return findNotNullChildByType(IDENT);
  }

}
