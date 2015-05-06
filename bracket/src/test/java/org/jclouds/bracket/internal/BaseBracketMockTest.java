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
package org.jclouds.bracket.internal;

import static com.google.common.util.concurrent.MoreExecutors.sameThreadExecutor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jclouds.Constants.PROPERTY_API_VERSION;
import static org.jclouds.Constants.PROPERTY_CREDENTIAL;
import static org.jclouds.Constants.PROPERTY_IDENTITY;
import static org.jclouds.util.Strings2.toStringAndClose;
import java.io.IOException;
import java.util.Properties;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.jclouds.bracket.BracketApiMetadata;
import org.jclouds.bracket.config.BracketParserModule;
import org.jclouds.concurrent.config.ExecutorServiceModule;
import org.jclouds.http.BaseMockWebServerTest;
import org.jclouds.http.okhttp.config.OkHttpCommandExecutorServiceModule;

import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.inject.Module;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

/**
 * Base class for all Bracket mock tests.
 */
public class BaseBracketMockTest extends BaseMockWebServerTest {

   protected static final String API_VERSION = new BracketApiMetadata().getVersion();

   @Override
   protected void addOverrideProperties(Properties properties) {
      properties.setProperty(PROPERTY_IDENTITY, "uuid");
      properties.setProperty(PROPERTY_CREDENTIAL, "apikey");
      properties.setProperty(PROPERTY_API_VERSION, API_VERSION);
   }

   @Override
   protected Module createConnectionModule() {
      return new OkHttpCommandExecutorServiceModule();
   }

   public byte[] payloadFromResource(String resource) {
      try {
         return toStringAndClose(getClass().getResourceAsStream(resource)).getBytes(Charsets.UTF_8);
      } catch (IOException e) {
         throw Throwables.propagate(e);
      }
   }

   protected RecordedRequest assertSent(MockWebServer server, String method, String path) throws InterruptedException {
      RecordedRequest request = server.takeRequest();
      assertThat(request.getMethod()).isEqualTo(method);
      assertThat(request.getPath()).isEqualTo("/v" + API_VERSION + path);
      assertThat(request.getHeader(HttpHeaders.ACCEPT)).isEqualTo(MediaType.APPLICATION_JSON);
      return request;
   }

   protected Module[] getModules() {
      return new Module[] { new ExecutorServiceModule(sameThreadExecutor()), BracketTestModule.INSTANCE, new OkHttpCommandExecutorServiceModule(), new
              BracketParserModule() };
   }

}
