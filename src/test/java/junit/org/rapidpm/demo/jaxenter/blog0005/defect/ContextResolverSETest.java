package junit.org.rapidpm.demo.jaxenter.blog0005.defect;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import junit.org.rapidpm.demo.jaxenter.blog0005.logic.DemoLogic;
import org.junit.Assert;
import org.rapidpm.demo.jaxenter.blog0005.CDIContainerSingleton;

/**
 * User: Sven Ruppert
 * Date: 31.10.13
 * Time: 16:57
 */
public class ContextResolverSETest {

    private static final int MAX_ROUNDS = 100;
    private static final String DATE_STRING = "2013.11.12";
    private static final Date DATE = new Date(2013 - 1900, 10, 12);


    public static void main(String[] args) {
        final CDIContainerSingleton cdi = CDIContainerSingleton.getInstance();

        final ContextResolverSETest main = cdi.getManagedInstance(ContextResolverSETest.class);
        for (int i = 0; i < 100; i++) {
            try {
                main.test006();
                main.test008();
                System.out.println("i = " + i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Inject DemoLogic demoLogic;

    public void test006() throws Exception {
        final String format = demoLogic.doIt(DATE);
        Assert.assertEquals(format, DATE_STRING);

        final List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < MAX_ROUNDS; i++) {
            final Thread thread = new Thread(() -> Assert.assertEquals(demoLogic.doIt(DATE), DATE_STRING));
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
        final String format = demoLogic.doIt(DATE);
        Assert.assertEquals(format, DATE_STRING);

        final ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < MAX_ROUNDS; i++) {
            executorService.execute(() -> Assert.assertEquals(demoLogic.doIt(DATE), DATE_STRING));
        }
        executorService.shutdown();
        Assert.assertTrue(executorService.isShutdown());

    }


}
