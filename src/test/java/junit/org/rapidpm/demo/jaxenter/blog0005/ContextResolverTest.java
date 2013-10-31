/*
 * Copyright [2013] [www.rapidpm.org / Sven Ruppert (sven.ruppert@rapidpm.org)]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package junit.org.rapidpm.demo.jaxenter.blog0005;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.weld.environment.se.contexts.ThreadScoped;
import org.jboss.weld.executor.FixedThreadPoolExecutorServices;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rapidpm.demo.jaxenter.blog0005.format.CDISimpleDateFormatter;

/**
 * User: Sven Ruppert
 * Date: 21.10.13
 * Time: 11:14
 */
@RunWith(Arquillian.class)
public class ContextResolverTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.rapidpm.demo.jaxenter")
                .addPackages(true, "junit.org.rapidpm.demo.jaxenter")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }


    @Inject @CDISimpleDateFormatter("rechnungsdatum")
    private SimpleDateFormat sdf;

    @Inject @CDISimpleDateFormatter("rechnungsdatum")
    private Instance<SimpleDateFormat> simpleDateFormatInstance;

    @Test
    public void test001() throws Exception {
        Assert.assertNotNull(sdf);
        final String format = sdf.format(new Date());
        System.out.println("format = " + format);
    }


    @Test
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


    @Test
    public void test003() throws Exception {
        Assert.assertNotNull(simpleDateFormatInstance);
        final Date date = new Date(2013 - 1900, 10, 12);
        String format = simpleDateFormatInstance.get().format(date);
        Assert.assertEquals(format, "2013.11.12");  //:-) why 11 ? :-)

        final Runnable runnable = () -> Assert.assertEquals(simpleDateFormatInstance.get().format(date), "2013.11.12");
        runnable.run();

    }

    @Test
    public void test004() throws Exception {
        Assert.assertNotNull(simpleDateFormatInstance);
        final Date date = new Date(2013 - 1900, 10, 12);
        String format = simpleDateFormatInstance.get().format(date);
        Assert.assertEquals(format, "2013.11.12");  //:-) why 11 ? :-)

        final Runnable runnable = () -> Assert.assertEquals(simpleDateFormatInstance.get().format(date), "2013.11.12");

        final Thread thread = new Thread(runnable);
        thread.start();

        while (thread.isAlive()){
            thread.join(1000);
        }

    }

    @Test
    public void test005() throws Exception {
        Assert.assertNotNull(simpleDateFormatInstance);
        final Date date = new Date(2013 - 1900, 10, 12);
        String format = simpleDateFormatInstance.get().format(date);
        Assert.assertEquals(format, "2013.11.12");  //:-) why 11 ? :-)


        final Thread thread = new Thread(() -> Assert.assertEquals(simpleDateFormatInstance.get().format(date), "2013.11.12"));
        thread.start();

        while (thread.isAlive()){
            thread.join(1000);
        }

    }
    @Test
    public void test006() throws Exception {
        Assert.assertNotNull(simpleDateFormatInstance);
        final Date date = new Date(2013 - 1900, 10, 12);
        String format = simpleDateFormatInstance.get().format(date);
        Assert.assertEquals(format, "2013.11.12");  //:-) why 11 ? :-)


        final List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
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

    @Test
    public void test007() throws Exception {
        Assert.assertNotNull(simpleDateFormatInstance);
        final Date date = new Date(2013 - 1900, 10, 12);
        String format = simpleDateFormatInstance.get().format(date);
        Assert.assertEquals(format, "2013.11.12");  //:-) why 11 ? :-)

        final ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            final Thread thread = new Thread(() -> Assert.assertEquals(simpleDateFormatInstance.get().format(date), "2013.11.12"));
            executorService.execute(thread);
        }
        executorService.shutdown();
        Assert.assertTrue(executorService.isShutdown());

    }@Test
    public void test008() throws Exception {
        Assert.assertNotNull(simpleDateFormatInstance);
        final Date date = new Date(2013 - 1900, 10, 12);
        String format = simpleDateFormatInstance.get().format(date);
        Assert.assertEquals(format, "2013.11.12");  //:-) why 11 ? :-)

        final ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> Assert.assertEquals(simpleDateFormatInstance.get().format(date), "2013.11.12"));
        }
        executorService.shutdown();
        Assert.assertTrue(executorService.isShutdown());

    }


}
