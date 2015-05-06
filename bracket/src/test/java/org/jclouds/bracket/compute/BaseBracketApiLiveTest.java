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

import static org.jclouds.Constants.PROPERTY_RELAX_HOSTNAME;
import static org.jclouds.Constants.PROPERTY_TRUST_ALL_CERTS;
import java.util.Properties;

import org.jclouds.apis.BaseApiLiveTest;
import org.jclouds.bracket.BracketApi;
import org.jclouds.compute.config.ComputeServiceProperties;
import org.jclouds.sshj.config.SshjSshClientModule;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Injector;
import com.google.inject.Module;

@Test(groups = "live")
public class BaseBracketApiLiveTest extends BaseApiLiveTest<BracketApi> {

   public BaseBracketApiLiveTest() {
      provider = "bracket";
   }

   @Override
   protected Iterable<Module> setupModules() {
      return ImmutableSet.<Module>of(getLoggingModule(), new SshjSshClientModule());
   }

   @Override protected BracketApi create(Properties props, Iterable<Module> modules) {
      Injector injector = newBuilder().modules(modules).overrides(props).buildInjector();
      return injector.getInstance(BracketApi.class);
   }

   @Override
   protected Properties setupProperties() {
      Properties overrides = super.setupProperties();
      overrides.setProperty(ComputeServiceProperties.IMAGE_LOGIN_USER, "root:password");
      overrides.setProperty(PROPERTY_TRUST_ALL_CERTS, "true");
      overrides.setProperty(PROPERTY_RELAX_HOSTNAME, "true");
      return overrides;
   }

}
