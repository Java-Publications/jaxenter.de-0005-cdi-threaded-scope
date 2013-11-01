package junit.org.rapidpm.demo.jaxenter.blog0005.threadsave;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import junit.org.rapidpm.demo.jaxenter.blog0005.logic.DemoLogicThreadSave;
import junit.org.rapidpm.demo.jaxenter.blog0005.logic.DemoLogicThreadScoped;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rapidpm.demo.jaxenter.blog0005.ManagedInstanceCreator;

/**
 * Created by Sven Ruppert on 01.11.13.
 */
@RunWith(Arquillian.class)
public class ContextResolver001Test {

    private static final int MAX_ROUNDS = 100;
    private static final String DATE_STRING = "2013.11.12";
    private static final Date DATE = new Date(2013 - 1900, 10, 12);

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.rapidpm.demo.jaxenter")
                .addPackages(true, "junit.org.rapidpm.demo.jaxenter")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject Instance<DemoLogicThreadSave> demoLogicInstance;

    @Inject ManagedInstanceCreator instanceCreator;


    @Test
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

    @Test
    public void test006a() throws Exception {
        final String format = demoLogicInstance.get().doIt(DATE);
        Assert.assertEquals(format, DATE_STRING);

        final List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < MAX_ROUNDS; i++) {
            final Thread thread = new Thread(){
                @Inject DemoLogicThreadScoped demoLogic;
                public void run() {
                    final String expected = demoLogic.doIt(DATE);
                    System.out.println("expected = " + expected);
                    Assert.assertEquals(expected, DATE_STRING);
                }
            };
            instanceCreator.activateCDI(thread);
            threads.add(thread);
        }
        for (final Thread thread : threads) {
            thread.start();
        }
        for (final Thread thread : threads) {
            thread.join();
        }

    }

    @Test
    public void test006b() throws Exception {
        final String format = demoLogicInstance.get().doIt(DATE);
        Assert.assertEquals(format, DATE_STRING);

        final List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < MAX_ROUNDS; i++) {
            final Thread thread = new Thread(){
                @Inject DemoLogicThreadScoped demoLogic;
                public void run() {
                    final String expected = demoLogic.doIt(DATE);
                    System.out.println("expected = " + expected);
                    Assert.assertEquals(expected, DATE_STRING);
                }
            };
            instanceCreator.activateCDI(thread);
            threads.add(thread);
        }
        for (final Thread thread : threads) {
            thread.start();
        }
        for (final Thread thread : threads) {
            thread.join();
        }

    }


    @Test
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
