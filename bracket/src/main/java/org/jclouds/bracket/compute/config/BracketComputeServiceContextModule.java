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
package org.jclouds.bracket.compute.config;

import org.jclouds.bracket.compute.functions.ComputeInstanceToNodeMetadata;
import org.jclouds.bracket.compute.functions.WorkloadTemplateToImage;
import org.jclouds.bracket.compute.options.BracketTemplateOptions;
import org.jclouds.bracket.compute.strategy.BracketComputeServiceAdapter;
import org.jclouds.bracket.domain.ComputeInstance;
import org.jclouds.bracket.domain.InstanceTemplate;
import org.jclouds.bracket.domain.WorkloadTemplate;
import org.jclouds.compute.ComputeServiceAdapter;
import org.jclouds.compute.config.ComputeServiceAdapterContextModule;
import org.jclouds.compute.domain.Hardware;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.options.TemplateOptions;
import org.jclouds.domain.Location;
import org.jclouds.functions.IdentityFunction;

import com.google.common.base.Function;
import com.google.inject.TypeLiteral;

public class BracketComputeServiceContextModule extends ComputeServiceAdapterContextModule<ComputeInstance, Hardware, WorkloadTemplate, Location> {

   @Override
   protected void configure() {
      super.configure();
      bind(new TypeLiteral<ComputeServiceAdapter<ComputeInstance, Hardware, InstanceTemplate, Location>>() {
      }).to(BracketComputeServiceAdapter.class);
      bind(new TypeLiteral<Function<ComputeInstance, NodeMetadata>>() {
      }).to(ComputeInstanceToNodeMetadata.class);
      bind(new TypeLiteral<Function<WorkloadTemplate, Image>>() {
      }).to(WorkloadTemplateToImage.class);
      bind(new TypeLiteral<Function<Hardware, Hardware>>() {
      }).to(Class.class.cast(IdentityFunction.class));
      bind(new TypeLiteral<Function<Location, Location>>() {
      }).to(Class.class.cast(IdentityFunction.class));
      bind(TemplateOptions.class).to(BracketTemplateOptions.class);
   }

}
