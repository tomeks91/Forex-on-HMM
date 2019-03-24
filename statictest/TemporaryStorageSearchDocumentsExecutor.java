package statictest;

import java.util.function.Supplier;

public class TemporaryStorageSearchDocumentsExecutor {

    public <R> R execute(Supplier<R> executor, Integer dockindDocumentId) {
        try (TemporaryStorageSearchDocumentsHolder permissionPolicyHolder = new TemporaryStorageSearchDocumentsHolder(dockindDocumentId)) {
            return executor.get();
        }
    }

}
