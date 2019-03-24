package tests;

import org.junit.Test;
import statictest.TemporaryStorageSearchDocumentsExecutor;
import statictest.TemporaryStorageSearchDocumentsHolder;

public class StaticThreadHolder {

    @Test
    public void test(){
        new TemporaryStorageSearchDocumentsExecutor().execute(
                () -> execute(), 5);
        new TemporaryStorageSearchDocumentsExecutor().execute(
                () -> execute(), 5);
        new TemporaryStorageSearchDocumentsExecutor().execute(
                () -> execute(), 5);
        new TemporaryStorageSearchDocumentsExecutor().execute(
                () -> execute(), 5);
    }

    public static int execute(){
        TemporaryStorageSearchDocumentsHolder.addDocument(new Integer(3));
        return 0;
    }
}
