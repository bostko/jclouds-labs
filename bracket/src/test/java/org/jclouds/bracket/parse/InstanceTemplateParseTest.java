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
package org.jclouds.bracket.parse;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import org.jclouds.bracket.domain.InstanceTemplate;
import org.jclouds.bracket.internal.BaseBracketParseTest;
import org.jclouds.date.internal.SimpleDateFormatDateService;
import org.testng.annotations.Test;

import autovalue.shaded.com.google.common.common.collect.ImmutableList;

@Test(groups = "unit")
public class InstanceTemplateParseTest extends BaseBracketParseTest<InstanceTemplate> {

   @Override
   public String resource() {
      return "/instancetemplate.json";
   }

   @Override
   @Consumes(MediaType.APPLICATION_JSON)
   public InstanceTemplate expected() {
      return InstanceTemplate.create(
              "0e19f9450b8e4f6ca0d30029d611e6d7", // id
              "Splunk Master", // name
              "", // description
              "2fedd0d2760545169120f53c5c036ff9", // customer
              "PUBLISHED", // state
              "amd64", // cpu_arch
              1, // cpu_cores_minimum
              1, // ram_minimum
              "ami-bb4f69fe", // os_image
              "0.13", // hourly_cost
              "2.94", // daily_cost
              "88.20", // monthly_cost
              false, // internet_accessible
              "f5a645f4b20e49bf9109cc06201d058b", // workload_template
              ImmutableList.of("90a389a05e904bf5b44079205d92144a"), // assigned_groups
              "user@example.com", // created_by
              new SimpleDateFormatDateService().iso8601DateParse("2014-07-21T22:55:27.182477+00:00"),  // created_time
              "d9e4c45678eb40718fb515b57371132d", // cloudinit_id
              "", // cloudinit_script
              "CONFIGURED", // cloudinit_type
              "", // cloudinit_config
              null, // cloudinit_data
              1); // min_quantity
   }

}
