package junit.org.rapidpm.demo.jaxenter.blog0005.threadsave;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import junit.org.rapidpm.demo.jaxenter.blog0005.logic.DemoLogic;
import junit.org.rapidpm.demo.jaxenter.blog0005.logic.DemoLogicThreadSave;
import org.junit.Assert;
import org.rapidpm.demo.jaxenter.blog0005.CDIContainerSingleton;

/**
 * Created by Sven Ruppert on 01.11.13.
 */
public class ContextResolver001SETest {

    private static final int MAX_ROUNDS = 100;
    private static final String DATE_STRING = "2013.11.12";
    private static final Date DATE = new Date(2013 - 1900, 10, 12);

    public static void main(String[] args) {
        final CDIContainerSingleton cdi = CDIContainerSingleton.getInstance();

        final ContextResolver001SETest main = cdi.getManagedInstance(ContextResolver001SETest.class);
        for(int i = 0; i< 100;i++){
            try {
                main.test006();
                main.test008();
                System.out.println("i = " + i);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @Inject
    Instance<DemoLogicThreadSave> demoLogicInstance; //Version 1, jeder Thread eigene Instanz


    public void test006() throws Exception {
        final String format = demoLogicInstance.get().doIt(DATE);
        Assert.assertEquals(format, DATE_STRING);

        final List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < MAX_ROUNDS; i++) {
            final Thread thread = new Thread(() -> Assert.assertEquals(demoLogicInstance.get().doIt(DATE), DATE_STRING));
            threads.add(thread);
        }
        for (final Thread thread : threads) {
            thread.start();
        }
        for (final Thread thread : threads) {
            thread.join();
        }

    }


    public void test008() throws Exception {
        final String format = demoLogicInstance.get().doIt(DATE);
        Assert.assertEquals(format, DATE_STRING);

        final ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < MAX_ROUNDS; i++) {
            executorService.execute(() -> Assert.assertEquals(demoLogicInstance.get().doIt(DATE), DATE_STRING));
        }
        executorService.shutdown();
        Assert.assertTrue(executorService.isShutdown());

    }


}
