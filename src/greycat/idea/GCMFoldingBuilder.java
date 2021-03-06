package greycat.idea;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilder;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import greycat.idea.psi.GCMTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GCMFoldingBuilder implements FoldingBuilder {
    @NotNull
    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull ASTNode node, @NotNull Document document) {
        List<FoldingDescriptor> descriptors = new ArrayList<FoldingDescriptor>();

        appendDescriptors(node, document, descriptors);
        return descriptors.toArray(new FoldingDescriptor[descriptors.size()]);
    }

    private void appendDescriptors(final ASTNode node, final Document document, final List<FoldingDescriptor> descriptors) {
        if (node.getElementType() == GCMTypes.CLASS_DECLARATION || node.getElementType() == GCMTypes.CUSTOM_TYPE_DECLARATION) {
            TextRange fullRange = node.getTextRange();
            if (fullRange.getEndOffset() - fullRange.getStartOffset() > 0) {

                try {
                    int startOffset = fullRange.getStartOffset() + document.getText(fullRange).indexOf("{") + 1;
                    int endOffset = fullRange.getEndOffset() - 1;
                    if (startOffset < endOffset) {
                        TextRange shortRange = new TextRange(startOffset, fullRange.getEndOffset() - 1);
                        if (shortRange.getEndOffset() - shortRange.getStartOffset() > 1) {
                            descriptors.add(new FoldingDescriptor(node, shortRange));
                        }
                    }
                } catch (Throwable e) {

                }
            }
        }
        ASTNode child = node.getFirstChildNode();
        while (child != null) {
            appendDescriptors(child, document, descriptors);
            child = child.getTreeNext();
        }
    }

    @Nullable
    @Override
    public String getPlaceholderText(@NotNull ASTNode node) {
        return "...";

    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode astNode) {
        return false;
    }
}

