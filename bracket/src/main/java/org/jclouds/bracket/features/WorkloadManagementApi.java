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
package org.jclouds.bracket.features;

import java.util.List;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.jclouds.Fallbacks;
import org.jclouds.bracket.domain.InstanceTemplate;
import org.jclouds.bracket.domain.Workload;
import org.jclouds.bracket.filters.AuthenticationFilter;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.RequestFilters;

@Consumes(MediaType.APPLICATION_JSON)
@Path("/v{jclouds.api-version}/api")
@RequestFilters(AuthenticationFilter.class)
public interface WorkloadManagementApi {

   @Named("workload:list")
   @GET
   @Path("/config/workload")
   @Fallback(Fallbacks.EmptyListOnNotFoundOr404.class)
   List<Workload> list();

   @Named("workload:get")
   @GET
   @Path("/config/workload/{workloadUUID}")
   Workload get(@PathParam("modelId") String modelId);

}
