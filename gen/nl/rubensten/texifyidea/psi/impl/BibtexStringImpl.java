// This is a generated file. Not intended for manual editing.
package nl.rubensten.texifyidea.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static nl.rubensten.texifyidea.psi.BibtexTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import nl.rubensten.texifyidea.psi.*;

public class BibtexStringImpl extends ASTWrapperPsiElement implements BibtexString {

  public BibtexStringImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull BibtexVisitor visitor) {
    visitor.visitString(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof BibtexVisitor) accept((BibtexVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public BibtexBracedString getBracedString() {
    return findChildByClass(BibtexBracedString.class);
  }

  @Override
  @Nullable
  public BibtexDefinedString getDefinedString() {
    return findChildByClass(BibtexDefinedString.class);
  }

  @Override
  @Nullable
  public BibtexQuotedString getQuotedString() {
    return findChildByClass(BibtexQuotedString.class);
  }

}
