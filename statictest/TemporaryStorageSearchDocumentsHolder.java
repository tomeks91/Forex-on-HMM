package statictest;

import lombok.Builder;
import lombok.Value;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

public class TemporaryStorageSearchDocumentsHolder implements AutoCloseable {

    private static final ThreadLocal<Holder> HOLDER = ThreadLocal.withInitial(() -> new Holder(new HashSet<Integer>()));

    public static boolean hasDocument(Integer dockindDocumentId) {
        return HOLDER.get().getDockindDocumentIdSet().contains(dockindDocumentId);
    }

    public static void addDocument(Integer dockindDocumentId) {
        HOLDER.get().getDockindDocumentIdSet().add(dockindDocumentId);
    }

    TemporaryStorageSearchDocumentsHolder(Integer dockindDocumentId) {
        checkState(HOLDER.get().getDockindDocumentIdSet().isEmpty(), "Policy was set before!");
        addDocument(dockindDocumentId);
    }

    @Override
    public void close() {
        HOLDER.remove();
    }

    @Value
    @Builder
    static class Holder {
        Set<Integer> dockindDocumentIdSet;
    }
}
