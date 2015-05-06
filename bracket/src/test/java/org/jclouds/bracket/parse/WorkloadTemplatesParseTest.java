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

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import org.jclouds.bracket.domain.WorkloadTemplate;
import org.jclouds.bracket.internal.BaseBracketParseTest;
import org.jclouds.date.internal.SimpleDateFormatDateService;
import org.testng.annotations.Test;

import autovalue.shaded.com.google.common.common.collect.ImmutableList;

@Test(groups = "unit")
public class WorkloadTemplatesParseTest extends BaseBracketParseTest<List<WorkloadTemplate>> {

   @Override
   public String resource() {
      return "/workloadtemplates.json";
   }

   @Override
   @Consumes(MediaType.APPLICATION_JSON)
   public List<WorkloadTemplate> expected() {
      return ImmutableList.of(
              WorkloadTemplate.create(
                      "0e443646dc2d4e688f4ab0b27b6c2d5a", // id
                      "test", // name
                      "", // description
                      "701d8871cccc41a99ee8fd01ae5828c5", // customer
                      "PUBLISHED", // state
                      null, // max_cost
                      "andrea.turli@cloudsoftcorp.com", // created_by
                      "0.00", // cost
                      "0.13", // hourly_cost
                      "2.94", // daily_cost
                      "88.20", // monthly_cost
                      "0.00", // fixed_charge
                      "0.12250000", // base_hourly_rate
                      false, // enable_service_domain
                      "/v1/api/config/workloadtemplate/0e443646dc2d4e688f4ab0b27b6c2d5a/workloads", // workloads
                      ImmutableList.of("9b04cfbb07d64ddd924932f6b878095b"), // assigned_groups
                      new SimpleDateFormatDateService().iso8601DateParse("2015-05-12T16:35:53.407597+00:00"),  // created_time
                      null)  // last_deployed_time
              );
   }

}
