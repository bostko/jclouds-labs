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

import static com.google.common.base.Preconditions.checkNotNull;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.jclouds.bracket.domain.ComputeInstance;
import org.jclouds.collect.Memoized;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.NodeMetadataBuilder;
import org.jclouds.compute.functions.GroupNamingConvention;
import org.jclouds.domain.Location;
import org.jclouds.providers.ProviderMetadata;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.collect.Iterables;
import com.google.inject.Singleton;

@Singleton
public class ComputeInstanceToNodeMetadata implements Function<ComputeInstance, NodeMetadata> {

   /**
    * This value is used when a container does not have an accessible
    * login port (i.e. the SSH daemon is not running) due to being
    * started outside jclouds. Client code should check for this value
    * when accessing NodeMetadata from Bracket.
    */
   public static final Integer NO_LOGIN_PORT = Integer.valueOf(-1);

   private final ProviderMetadata providerMetadata;
   private final GroupNamingConvention nodeNamingConvention;
   private final Supplier<Map<String, ? extends Image>> images;
   private final Supplier<Set<? extends Location>> locations;

   @Inject
   public ComputeInstanceToNodeMetadata(ProviderMetadata providerMetadata, GroupNamingConvention.Factory namingConvention,
                                  Supplier<Map<String, ? extends Image>> images,
                                  @Memoized Supplier<Set<? extends Location>> locations) {
      this.providerMetadata = checkNotNull(providerMetadata, "providerMetadata");
      this.nodeNamingConvention = checkNotNull(namingConvention, "namingConvention").createWithoutPrefix();
      this.images = checkNotNull(images, "images cannot be null");
      this.locations = checkNotNull(locations, "locations");
   }

   @Override
   public NodeMetadata apply(ComputeInstance computeInstance) {
      String name = cleanUpName(computeInstance.name());
      String group = nodeNamingConvention.extractGroup(name);
      NodeMetadataBuilder builder = new NodeMetadataBuilder();
      builder.ids(computeInstance.id())
              .name(name)
              .group(group);
              //.hostname(computeInstance.config().hostname())
               // TODO Set up hardware
              //.hardware(new HardwareBuilder()
              //        .id("")
              //        .ram(computeInstance.config().memory())
              //        .processor(new Processor(computeInstance.config().cpuShares(), computeInstance.config().cpuShares()))
              //        .build());
      //builder.status(toPortableStatus.apply(computeInstance.state()));
      //builder.loginPort(getLoginPort(computeInstance));
      //builder.publicAddresses(getPublicIpAddresses());
      //builder.privateAddresses(getPrivateIpAddresses(computeInstance));
      builder.location(Iterables.getOnlyElement(locations.get()));
      /*
      String imageId = computeInstance.image();
      builder.imageId(imageId);
      if (images.get().containsKey(imageId)) {
          Image image = images.get().get(imageId);
          builder.operatingSystem(image.getOperatingSystem());
      }
      */
      return builder.build();
   }

   private String cleanUpName(String name) {
      return name.startsWith("/") ? name.substring(1) : name;
   }

}
