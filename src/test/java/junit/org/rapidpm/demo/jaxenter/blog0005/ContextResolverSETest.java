package junit.org.rapidpm.demo.jaxenter.blog0005;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.enterprise.inject.Instance;
import javax.enterprise.util.AnnotationLiteral;

import org.junit.Assert;
import org.rapidpm.demo.jaxenter.blog0005.CDIContainerSingleton;
import org.rapidpm.demo.jaxenter.blog0005.format.CDISimpleDateFormatter;
import org.rapidpm.demo.jaxenter.blog0005.format.CDISimpleDateFormatterQualifier;

/**
 * User: Sven Ruppert
 * Date: 31.10.13
 * Time: 16:57
 */
public class ContextResolverSETest {

    private static final int INTMAX_ROUNDS = 1000;
    private Instance<SimpleDateFormat> simpleDateFormatInstance;





    public static void main(String[] args) {
        final CDIContainerSingleton cdi = CDIContainerSingleton.getInstance();
        final ContextResolverSETest main = new ContextResolverSETest();

        main.simpleDateFormatInstance = cdi.getInstanceReference( new CDISimpleDateFormatterQualifier(), SimpleDateFormat.class);
        for(int i = 0; i< 100;i++){
            try {
                main.test002();
                main.test003();
                main.test004();
                main.test005();
                main.test006();
                main.test007();
                main.test008();
                System.out.println("i = " + i);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }



    public void test002() throws Exception {
        Assert.assertNotNull(simpleDateFormatInstance);
        final Date date = new Date(2013 - 1900, 10, 12);
        String format = simpleDateFormatInstance.get().format(date);
        Assert.assertEquals(format, "2013.11.12");  //:-) why 11 ? :-)

        final Runnable runnable = new Runnable() {
            @Override public void run() {
                final String format = simpleDateFormatInstance.get().format(date);
                Assert.assertEquals(format, "2013.11.12");
            }
        };
        runnable.run();
    }


    public void test003() throws Exception {
        Assert.assertNotNull(simpleDateFormatInstance);
        final Date date = new Date(2013 - 1900, 10, 12);
        String format = simpleDateFormatInstance.get().format(date);
        Assert.assertEquals(format, "2013.11.12");  //:-) why 11 ? :-)

        final Runnable runnable = () -> Assert.assertEquals(simpleDateFormatInstance.get().format(date), "2013.11.12");
        runnable.run();

    }

    public void test004() throws Exception {
        Assert.assertNotNull(simpleDateFormatInstance);
        final Date date = new Date(2013 - 1900, 10, 12);
        String format = simpleDateFormatInstance.get().format(date);
        Assert.assertEquals(format, "2013.11.12");  //:-) why 11 ? :-)

        final Runnable runnable = () -> Assert.assertEquals(simpleDateFormatInstance.get().format(date), "2013.11.12");

        final Thread thread = new Thread(runnable);
        thread.start();
        thread.join();
    }

    public void test005() throws Exception {
        Assert.assertNotNull(simpleDateFormatInstance);
        final Date date = new Date(2013 - 1900, 10, 12);
        String format = simpleDateFormatInstance.get().format(date);
        Assert.assertEquals(format, "2013.11.12");  //:-) why 11 ? :-)

        final Thread thread = new Thread(() -> Assert.assertEquals(simpleDateFormatInstance.get().format(date), "2013.11.12"));
        thread.start();
        thread.join();

    }

    public void test006() throws Exception {
        Assert.assertNotNull(simpleDateFormatInstance);
        final Date date = new Date(2013 - 1900, 10, 12);
        String format = simpleDateFormatInstance.get().format(date);
        Assert.assertEquals(format, "2013.11.12");  //:-) why 11 ? :-)


        final List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < INTMAX_ROUNDS; i++) {
            final Thread thread = new Thread(() -> Assert.assertEquals(simpleDateFormatInstance.get().format(date), "2013.11.12"));
            threads.add(thread);
        }
        for (final Thread thread : threads) {
            thread.start();
        }
        for (final Thread thread : threads) {
            thread.join();
        }

    }

    public void test007() throws Exception {
        Assert.assertNotNull(simpleDateFormatInstance);
        final Date date = new Date(2013 - 1900, 10, 12);
        String format = simpleDateFormatInstance.get().format(date);
        Assert.assertEquals(format, "2013.11.12");  //:-) why 11 ? :-)

        final ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < INTMAX_ROUNDS; i++) {
            final Thread thread = new Thread(() -> Assert.assertEquals(simpleDateFormatInstance.get().format(date), "2013.11.12"));
            executorService.execute(thread);
        }
        executorService.shutdown();
        Assert.assertTrue(executorService.isShutdown());

    }


    public void test008() throws Exception {
        Assert.assertNotNull(simpleDateFormatInstance);
        final Date date = new Date(2013 - 1900, 10, 12);
        String format = simpleDateFormatInstance.get().format(date);
        Assert.assertEquals(format, "2013.11.12");  //:-) why 11 ? :-)

        final ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < INTMAX_ROUNDS; i++) {
            executorService.execute(() -> Assert.assertEquals(simpleDateFormatInstance.get().format(date), "2013.11.12"));
        }
        executorService.shutdown();
        Assert.assertTrue(executorService.isShutdown());

    }


}
