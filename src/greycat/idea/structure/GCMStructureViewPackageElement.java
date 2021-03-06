package greycat.idea.structure;

import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.util.PlatformIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GCMStructureViewPackageElement implements StructureViewTreeElement {

    public List<StructureViewTreeElement> innerClasses = new ArrayList<StructureViewTreeElement>();
    public HashMap<String, GCMStructureViewPackageElement> packages = new HashMap<String, GCMStructureViewPackageElement>();

    private String packageName;

    public GCMStructureViewPackageElement(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public Object getValue() {
        return packageName;
    }

    @Override
    public void navigate(boolean b) {
    }

    @Override
    public boolean canNavigate() {
        return false;
    }

    @Override
    public boolean canNavigateToSource() {
        return false;
    }

    @NotNull
    @Override
    public ItemPresentation getPresentation() {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return packageName;
            }

            @Nullable
            @Override
            public String getLocationString() {
                return "";
            }

            @Nullable
            @Override
            public Icon getIcon(boolean b) {
                return PlatformIcons.PACKAGE_ICON;
            }
        };
    }

    @Override
    public TreeElement[] getChildren() {
        List<TreeElement> res = new ArrayList<TreeElement>();
        res.addAll(packages.values());
        res.addAll(innerClasses);
        return res.toArray(new TreeElement[res.size()]);
    }
}
