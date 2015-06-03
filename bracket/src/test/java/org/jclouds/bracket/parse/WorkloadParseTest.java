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

import org.jclouds.bracket.domain.Workload;
import org.jclouds.bracket.internal.BaseBracketParseTest;
import org.jclouds.date.internal.SimpleDateFormatDateService;
import org.testng.annotations.Test;

@Test(groups = "unit")
public class WorkloadParseTest extends BaseBracketParseTest<Workload> {

   @Override
   public String resource() {
      return "/workload.json";
   }

   @Override
   @Consumes(MediaType.APPLICATION_JSON)
   public Workload expected() {
      return Workload.create(
              "35c43fc788db466883be2cfd592c5f01", // id
              "Example Workload Name", // name
              "", // description
              "2fedd0d2760545169120f53c5c036ff9", // customer
              "READY", // state
              "90a389a05e904bf5b44079205d92144a", // billing_group
              "0.00", // cost
              "user@example.com", // created_by
              new SimpleDateFormatDateService().iso8601DateParse("2014-07-02T22:16:06.635654+00:00"),  // created_time
              "0.13", // hourly_cost
              "2.94", // daily_cost
              "88.20", // monthly_cost
              "0.00", // fixed_charge
              false, // deleted
              false, // expired
              "/v1/api/config/workload/35c43fc788db466883be2cfd592c5f01/instances", // instances
              new SimpleDateFormatDateService().iso8601DateParse("2014-07-05T00:34:03.134000+00:00"), // lease_expire_time
              new SimpleDateFormatDateService().iso8601DateParse("2014-07-02T22:16:06.638958+00:00"), // lease_modified_time
              "user@example.com", // modified_by
              new SimpleDateFormatDateService().iso8601DateParse("2014-07-02T22:16:06.635681+00:00"), // modified_time
              "AVAILABLE",  // requested_state
              "f9af7f2116cc486188ef0cbf18b11775", // workload_template
              "f3372c4a73b04928bd8b73687fcef69c"// zone
              );
   }
}
