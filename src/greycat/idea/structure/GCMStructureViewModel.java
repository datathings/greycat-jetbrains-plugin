package greycat.idea.structure;

import com.intellij.icons.AllIcons;
import com.intellij.ide.structureView.StructureViewModelBase;
import com.intellij.ide.util.treeView.smartTree.ActionPresentation;
import com.intellij.ide.util.treeView.smartTree.ActionPresentationData;
import com.intellij.ide.util.treeView.smartTree.Sorter;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.DocumentAdapter;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GCMStructureViewModel extends StructureViewModelBase {

    private Sorter typeSorter = new Sorter() {
        @Override
        public Comparator getComparator() {
            return new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {

                    if (o1.getClass() == o2.getClass()) {
                        if (o1 instanceof GCMStructureViewReferenceElement) {
                            if (((GCMStructureViewReferenceElement) o1).isAttribute() && !((GCMStructureViewReferenceElement) o2).isAttribute()) { //atr <-> !attr
                                return -1;
                            } else if (!((GCMStructureViewReferenceElement) o1).isAttribute() && ((GCMStructureViewReferenceElement) o2).isAttribute()) {// !atr <-> attr
                                return 1;
                            }
                        }
                        return Sorter.ALPHA_SORTER.getComparator().compare(o1, o2);
                    }

                    if (o1 instanceof GCMStructureViewClassElement) {
                        return 1;
                    } else if (o1 instanceof GCMStructureViewPackageElement) {
                        return -1;
                    } else if (o1 instanceof GCMStructureViewReferenceElement) {
                        return 1;
                    } else if (o1 instanceof GCMStructureViewParentElement) {
                        return -1;
                    }
                    return 0;
                }
            };
        }

        @Override
        public boolean isVisible() {
            return true;
        }

        @NotNull
        @Override
        public ActionPresentation getPresentation() {
            return new ActionPresentationData("Sort by type", "Sorts the elements by type", AllIcons.ObjectBrowser.SortByType);
        }

        @NotNull
        @Override
        public String getName() {
            return "METAMODEL_TYPE_SORTER";
        }
    };
    private Boolean needRefresh = false;
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    public GCMStructureViewModel(@NotNull PsiFile psiFile, @NotNull final GCMStructureViewRootElement root, Editor editor) {
        super(psiFile, root);
        withSorters(typeSorter, Sorter.ALPHA_SORTER);
        editor.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            public void documentChanged(DocumentEvent event) {
                synchronized (needRefresh) {
                    needRefresh = true;
                }
            }
        });
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                GCMStructureViewModel.this.refreshView();
            }
        }, 15, 5, TimeUnit.SECONDS);

    }

    public void refreshView() {
        boolean doRefresh;
        synchronized (needRefresh) {
            doRefresh = needRefresh;
            needRefresh = false;
        }
        if (doRefresh) {
            ((GCMStructureViewRootElement) GCMStructureViewModel.this.getRoot()).refresh();
        }
    }


}
