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
package org.jclouds.bracket;

import static org.jclouds.compute.config.ComputeServiceProperties.TEMPLATE;
import static org.jclouds.reflect.Reflection2.typeToken;
import java.net.URI;
import java.util.Properties;

import org.jclouds.Constants;
import org.jclouds.apis.ApiMetadata;
import org.jclouds.bracket.compute.config.BracketComputeServiceContextModule;
import org.jclouds.bracket.config.BracketHttpApiModule;
import org.jclouds.bracket.config.BracketParserModule;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.config.ComputeServiceProperties;
import org.jclouds.rest.internal.BaseHttpApiMetadata;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;

@AutoService(ApiMetadata.class)
public class BracketApiMetadata extends BaseHttpApiMetadata<BracketApi> {

   @Override
   public Builder toBuilder() {
      return new Builder().fromApiMetadata(this);
   }

   public BracketApiMetadata() {
      this(new Builder());
   }

   protected BracketApiMetadata(Builder builder) {
      super(builder);
   }

   public static Properties defaultProperties() {
      Properties properties = BaseHttpApiMetadata.defaultProperties();
      properties.setProperty(Constants.PROPERTY_CONNECTION_TIMEOUT, "1200000"); // 15 minutes
      properties.setProperty(ComputeServiceProperties.IMAGE_LOGIN_USER, "root:password");
      properties.setProperty(TEMPLATE, "osFamily=UBUNTU,os64Bit=true");
      return properties;
   }

   public static class Builder extends BaseHttpApiMetadata.Builder<BracketApi, Builder> {

      protected Builder() {
         super(BracketApi.class);
         id("bracket")
                 .name("Bracket API")
                 .identityName("Path to certificate .pem file")
                 .credentialName("Password to key .pem file")
                 .documentation(URI.create("https://support.brkt.com/entries/102152043-Bracket-Computing-Cell-API-Guide-V1-5-BETA-"))
                 .version("1")
                 .defaultEndpoint("https://portal.brkt.com")
                 .defaultProperties(BracketApiMetadata.defaultProperties())
                 .view(typeToken(ComputeServiceContext.class))
                 .defaultModules(ImmutableSet.<Class<? extends Module>>builder()
                         .add(BracketHttpApiModule.class)
                         .add(BracketParserModule.class)
                         .add(BracketComputeServiceContextModule.class)
                         .build());
      }

      @Override
      public BracketApiMetadata build() {
         return new BracketApiMetadata(this);
      }

      @Override
      protected Builder self() {
         return this;
      }

      @Override
      public Builder fromApiMetadata(ApiMetadata in) {
         return this;
      }
   }
}
