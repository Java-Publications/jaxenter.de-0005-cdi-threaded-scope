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

package junit.org.rapidpm.demo.jaxenter.blog0005.defect;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import junit.org.rapidpm.demo.jaxenter.blog0005.logic.DemoLogic;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
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


    @Inject
    @CDISimpleDateFormatter("rechnungsdatum")
    private SimpleDateFormat sdf;

    @Inject DemoLogic demoLogic;


    @Test
    public void test001() throws Exception {
        Assert.assertNotNull(sdf);

        System.out.println("target -> DATE_STRING = " + DATE_STRING);
        final String format1 = sdf.format(DATE);
        System.out.println("format1 = " + format1);
        final Date parse = sdf.parse(format1);
        System.out.println("parse = " + parse);
        final String format2 = sdf.format(parse);
        System.out.println("format2 = " + format2);
        Assert.assertEquals(format2, DATE_STRING);

    }


    @Test
    public void test002() throws Exception {
        final String format = demoLogic.doIt(DATE);
        Assert.assertEquals(format, DATE_STRING);

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final String format;
                    format = demoLogic.doIt(DATE);
                    Assert.assertEquals(format, DATE_STRING);
            }
        };
        runnable.run();
    }


    @Test
    public void test003() throws Exception {
        final String format = demoLogic.doIt(DATE);
        Assert.assertEquals(format, DATE_STRING);

        final Runnable runnable = () -> Assert.assertEquals(demoLogic.doIt(DATE), DATE_STRING);
        runnable.run();

    }

    @Test
    public void test004() throws Exception {
        final String format = demoLogic.doIt(DATE);
        Assert.assertEquals(format, DATE_STRING);

        final Runnable runnable = () -> Assert.assertEquals(demoLogic.doIt(DATE), DATE_STRING);

        final Thread thread = new Thread(runnable);
        thread.start();

        while (thread.isAlive()) {
            thread.join();
        }

    }

    @Test
    public void test005() throws Exception {
        final String format = demoLogic.doIt(DATE);
        Assert.assertEquals(format, DATE_STRING);

        final Thread thread = new Thread(() -> Assert.assertEquals(demoLogic.doIt(DATE), DATE_STRING));
        thread.start();

        while (thread.isAlive()) {
            thread.join();
        }

    }

    @Test
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

    @Test
    public void test007() throws Exception {

        final String format = demoLogic.doIt(DATE);
        Assert.assertEquals(format, DATE_STRING);

        final ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < MAX_ROUNDS; i++) {
            final Thread thread = new Thread(() -> Assert.assertEquals(demoLogic.doIt(DATE), DATE_STRING));
            executorService.execute(thread);
        }
        executorService.shutdown();
        Assert.assertTrue(executorService.isShutdown());

    }

    @Test
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
