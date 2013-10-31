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
import java.util.Date;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.weld.environment.se.contexts.ThreadScoped;
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


    @Inject  @ThreadScoped  @CDISimpleDateFormatter("rechnungsdatum")
    private SimpleDateFormat sdf;

    @Test
    public void test001() throws Exception {
        Assert.assertNotNull(sdf);
        final String format = sdf.format(new Date());
        System.out.println("format = " + format);
    }


}
