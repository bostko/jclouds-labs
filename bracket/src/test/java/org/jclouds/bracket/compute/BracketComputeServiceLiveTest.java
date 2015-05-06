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

import org.jclouds.compute.ComputeService;
import org.jclouds.compute.internal.BaseComputeServiceContextLiveTest;
import org.jclouds.sshj.config.SshjSshClientModule;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.google.inject.Module;

/**
 * Live tests for the {@link org.jclouds.compute.ComputeService} integration.
 */
@Test(groups = "live", singleThreaded = true, testName = "BracketComputeServiceLiveTest")
public class BracketComputeServiceLiveTest extends BaseComputeServiceContextLiveTest {

   protected ComputeService client;

   public BracketComputeServiceLiveTest() {
      provider = "bracket";
   }

   @Override
   protected Module getSshModule() {
      return new SshjSshClientModule();
   }

   @Override
   protected void initializeContext() {
      super.initializeContext();
      client = view.getComputeService();
   }

   @AfterClass
   @Override
   protected void tearDownContext() {
      super.tearDownContext();
   }

}
