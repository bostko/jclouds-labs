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
package org.jclouds.bracket.compute.strategy;

import static com.google.common.base.Preconditions.checkNotNull;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.jclouds.bracket.BracketApi;
import org.jclouds.bracket.compute.options.BracketTemplateOptions;
import org.jclouds.bracket.domain.ComputeInstance;
import org.jclouds.bracket.domain.InstanceTemplate;
import org.jclouds.bracket.domain.WorkloadTemplate;
import org.jclouds.compute.ComputeServiceAdapter;
import org.jclouds.compute.domain.Hardware;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.reference.ComputeServiceConstants;
import org.jclouds.domain.Location;
import org.jclouds.logging.Logger;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

/**
 * defines the connection between the {@link org.jclouds.bracket.BracketApi} implementation and
 * the jclouds {@link org.jclouds.compute.ComputeService}
 */
@Singleton
public class BracketComputeServiceAdapter implements ComputeServiceAdapter<ComputeInstance, Hardware, InstanceTemplate, Location> {

   @Resource
   @Named(ComputeServiceConstants.COMPUTE_LOGGER)
   protected Logger logger = Logger.NULL;

   private final BracketApi api;

   @Inject
   public BracketComputeServiceAdapter(BracketApi api) {
      this.api = checkNotNull(api, "api");
   }

   @Override
   public NodeAndInitialCredentials<ComputeInstance> createNodeWithGroupEncodedIntoName(String group, String name,
                                                                                  Template template) {
      checkNotNull(template, "template was null");
      checkNotNull(template.getOptions(), "template options was null");

      BracketTemplateOptions templateOptions = BracketTemplateOptions.class.cast(template.getOptions());
      // TODO
      return null;
   }

   @Override
   public Iterable<Hardware> listHardwareProfiles() {
      // TODO
      return ImmutableList.of();
   }

   @Override
   public List<InstanceTemplate> listImages() {
      return FluentIterable.from(api.getTemplateManagementApi().list()).transformAndConcat(new Function<WorkloadTemplate, Iterable<InstanceTemplate>>() {
         @Override
         public Iterable<InstanceTemplate> apply(WorkloadTemplate input) {
            return api.getTemplateManagementApi().listInstancesInWorkloadTemplate(input.id());
         }
      }).toList();
   }

   @Override
   public InstanceTemplate getImage(final String imageId) {
      return null;
   }

   @Override
   public Iterable<ComputeInstance> listNodes() {
      // TODO
      return ImmutableList.of();
   }

   @Override
   public Iterable<ComputeInstance> listNodesByIds(final Iterable<String> ids) {
      // TODO
      return ImmutableList.of();
   }

   @Override
   public Iterable<Location> listLocations() {
      return ImmutableSet.of();
   }

   @Override
   public ComputeInstance getNode(String id) {
      // TODO
      return null;
   }

   @Override
   public void destroyNode(String id) {
   }

   @Override
   public void rebootNode(String id) {
   }

   @Override
   public void resumeNode(String id) {
   }

   @Override
   public void suspendNode(String id) {
   }

}
