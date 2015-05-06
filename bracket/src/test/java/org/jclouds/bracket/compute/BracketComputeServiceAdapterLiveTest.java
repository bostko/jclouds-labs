/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.bracket.compute;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import java.util.Properties;
import java.util.Random;

import org.jclouds.bracket.BracketApi;
import org.jclouds.bracket.compute.options.BracketTemplateOptions;
import org.jclouds.bracket.compute.strategy.BracketComputeServiceAdapter;
import org.jclouds.bracket.domain.ComputeInstance;
import org.jclouds.compute.ComputeServiceAdapter.NodeAndInitialCredentials;
import org.jclouds.compute.domain.Hardware;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.domain.TemplateBuilder;
import org.jclouds.sshj.config.SshjSshClientModule;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.inject.Injector;
import com.google.inject.Module;

@Test(groups = "live", singleThreaded = true, testName = "BracketComputeServiceAdapterLiveTest")
public class BracketComputeServiceAdapterLiveTest extends BaseBracketApiLiveTest {

   private BracketComputeServiceAdapter adapter;
   private TemplateBuilder templateBuilder;
   private NodeAndInitialCredentials<ComputeInstance> guest;

   @Override
   protected BracketApi create(Properties props, Iterable<Module> modules) {
      Injector injector = newBuilder().modules(modules).overrides(props).buildInjector();
      adapter = injector.getInstance(BracketComputeServiceAdapter.class);
      templateBuilder = injector.getInstance(TemplateBuilder.class);
      return injector.getInstance(BracketApi.class);
   }

   public void testCreateNodeWithGroupEncodedIntoNameThenStoreCredentials() {
      String group = "foo";
      String name = "container" + new Random().nextInt();

      Template template = templateBuilder.build();

      BracketTemplateOptions options = template.getOptions().as(BracketTemplateOptions.class);
      options.env(ImmutableList.of("ROOT_PASS=password"));
      guest = adapter.createNodeWithGroupEncodedIntoName(group, name, template);
      assertEquals(guest.getNodeId(), guest.getNode().id());
   }

   public void testListHardwareProfiles() {
      Iterable<Hardware> profiles = adapter.listHardwareProfiles();
      assertFalse(Iterables.isEmpty(profiles));

      for (Hardware profile : profiles) {
         assertNotNull(profile);
      }
   }

   @AfterGroups(groups = "live")
   protected void tearDown() {
      if (guest != null) {
         adapter.destroyNode(guest.getNode().id() + "");
      }
      super.tearDown();
   }

   @Override
   protected Iterable<Module> setupModules() {
      return ImmutableSet.<Module>of(getLoggingModule(), new SshjSshClientModule());
   }

}
