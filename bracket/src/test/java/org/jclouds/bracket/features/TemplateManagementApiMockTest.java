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

import static org.assertj.core.api.Assertions.assertThat;

import org.jclouds.bracket.BracketApi;
import org.jclouds.bracket.internal.BaseBracketMockTest;
import org.jclouds.bracket.parse.InstanceTemplatesParseTest;
import org.jclouds.bracket.parse.WorkloadTemplatesParseTest;
import org.testng.annotations.Test;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

/**
 * Mock tests for the {@link WorkloadTemplateManagementApi} class.
 */
@Test(groups = "unit", testName = "TemplateManagementApiMockTest")
public class TemplateManagementApiMockTest extends BaseBracketMockTest {


   public void testList() throws Exception {
      MockWebServer server = mockWebServer(new MockResponse().setResponseCode(200).setBody(payloadFromResource("/workloadtemplates.json")));
      WorkloadTemplateManagementApi api = api(BracketApi.class, server.getUrl("/").toString(), getModules()).getTemplateManagementApi();
      try {
         assertThat(api.list()).isEqualTo(new WorkloadTemplatesParseTest().expected());
         assertSent(server, "GET", "/api/config/workloadtemplate");
      } finally {
         server.shutdown();
      }
   }

   public void testListInstancesInWorkloadTemplate() throws Exception {
      MockWebServer server = mockWebServer(new MockResponse().setResponseCode(200).setBody(payloadFromResource("/instancetemplates.json")));
      WorkloadTemplateManagementApi api = api(BracketApi.class, server.getUrl("/").toString(), getModules()).getTemplateManagementApi();
      try {
         assertThat(api.listInstancesInWorkloadTemplate("0e443646dc2d4e688f4ab0b27b6c2d5a")).isEqualTo(new InstanceTemplatesParseTest().expected());
         assertSent(server, "GET", "/v1/api/config/workloadtemplate/0e443646dc2d4e688f4ab0b27b6c2d5a/instancetemplates");
      } finally {
         server.shutdown();
      }
   }

}
