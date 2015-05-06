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
package org.jclouds.bracket.compute.functions;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.testng.Assert.assertEquals;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.easymock.EasyMock;
import org.jclouds.bracket.domain.ComputeInstance;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.ImageBuilder;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.OperatingSystem;
import org.jclouds.compute.domain.OsFamily;
import org.jclouds.compute.functions.GroupNamingConvention;
import org.jclouds.date.internal.SimpleDateFormatDateService;
import org.jclouds.domain.Location;
import org.jclouds.domain.LocationBuilder;
import org.jclouds.domain.LocationScope;
import org.jclouds.domain.LoginCredentials;
import org.jclouds.providers.ProviderMetadata;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Guice;

/**
 * Unit tests for the {@link org.jclouds.bracket.compute.functions.ComputeInstanceToNodeMetadata} class.
 */
@Test(groups = "unit", testName = "ContainerToNodeMetadataTest")
public class ContainerToNodeMetadataTest {

   private LoginCredentials credentials;

   private ComputeInstanceToNodeMetadata function;

   private ComputeInstance computeInstance;

   @BeforeMethod
   public void setup() {
      computeInstance = ComputeInstance.builder()
              .id("6d35806c1bd2b25cd92bba2d2c2c5169dc2156f53ab45c2b62d76e2d2fee14a9")
              .name("/hopeful_mclean")
              .created(new SimpleDateFormatDateService().iso8601DateParse("2014-03-22T07:16:45.784120972Z"))
              .path("/usr/sbin/sshd")
              .args(Arrays.asList("-D"))
              .image("af0f59f1c19eef9471c3b8c8d587c39b8f130560b54f3766931b37d76d5de4b6")
              .driver("aufs")
              .execDriver("native-0.1")
              .volumes(ImmutableMap.<String, String>of())
              .volumesRW(ImmutableMap.<String, Boolean>of())
              .command("")
              .status("")
              .build();
      ProviderMetadata providerMetadata = EasyMock.createMock(ProviderMetadata.class);
      expect(providerMetadata.getEndpoint()).andReturn("http://127.0.0.1:4243");
      replay(providerMetadata);

      GroupNamingConvention.Factory namingConvention = Guice.createInjector().getInstance(GroupNamingConvention.Factory.class);

      Supplier<Map<String, ? extends Image>> images = new Supplier<Map<String, ? extends Image>>() {
         @Override
         public Map<String, ? extends Image> get() {
            OperatingSystem os = OperatingSystem.builder()
                    .description("Ubuntu 12.04 64bit")
                    .family(OsFamily.UBUNTU)
                    .version("12.04")
                    .is64Bit(true)
                    .build();

            return ImmutableMap.of("af0f59f1c19eef9471c3b8c8d587c39b8f130560b54f3766931b37d76d5de4b6",
                    new ImageBuilder()
                            .ids("af0f59f1c19eef9471c3b8c8d587c39b8f130560b54f3766931b37d76d5de4b6")
                            .name("ubuntu")
                            .description("Ubuntu 12.04 64bit")
                            .operatingSystem(os)
                            .status(Image.Status.AVAILABLE)
                            .build());
         }
      };

      Supplier<Set<? extends Location>> locations = new Supplier<Set< ? extends Location>>() {
         @Override
         public Set<? extends Location> get() {

            return ImmutableSet.of(
                    new LocationBuilder()
                            .id("docker")
                            .description("http://localhost:2375")
                            .scope(LocationScope.PROVIDER)
                            .build()
            );
         }
      };

      credentials = LoginCredentials.builder().user("foo").password("bar").build();

      function = new ComputeInstanceToNodeMetadata(providerMetadata, namingConvention, images, locations);
   }

   public void testVirtualMachineToNodeMetadata() {
      ComputeInstance mockContainer = mockContainer();

      NodeMetadata node = function.apply(mockContainer);

      verify(mockContainer);

      assertEquals(node.getId(), "6d35806c1bd2b25cd92bba2d2c2c5169dc2156f53ab45c2b62d76e2d2fee14a9");
      assertEquals(node.getGroup(), "hopeful_mclean");
      assertEquals(node.getImageId(), "af0f59f1c19eef9471c3b8c8d587c39b8f130560b54f3766931b37d76d5de4b6");
      assertEquals(node.getLoginPort(), 49199);
      assertEquals(node.getPrivateAddresses().size(), 1);
      assertEquals(node.getPublicAddresses().size(), 1);
   }

   private ComputeInstance mockContainer() {
      ComputeInstance mockContainer = EasyMock.createMock(ComputeInstance.class);

      expect(mockContainer.id()).andReturn(computeInstance.id());
      expect(mockContainer.name()).andReturn(computeInstance.name());
      replay(mockContainer);

      return mockContainer;
   }
}
