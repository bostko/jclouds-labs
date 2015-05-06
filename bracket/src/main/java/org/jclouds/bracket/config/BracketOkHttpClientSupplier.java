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
package org.jclouds.bracket.config;

import java.lang.reflect.Field;

import javax.inject.Singleton;

import org.jclouds.http.okhttp.OkHttpClientSupplier;

import com.google.common.collect.ImmutableList;
import com.squareup.okhttp.CipherSuite;
import com.squareup.okhttp.ConnectionSpec;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.TlsVersion;

@Singleton
public class BracketOkHttpClientSupplier implements OkHttpClientSupplier {

   // TODO remove it, useful during development
   static {
      try {
         Field field = Class.forName("javax.crypto.JceSecurity").getDeclaredField("isRestricted");
         field.setAccessible(true);
         field.set(null, java.lang.Boolean.FALSE);
      } catch (Exception ex) {
      }
   }

   @Override
   public OkHttpClient get() {
      OkHttpClient client = new OkHttpClient();
      ConnectionSpec modernTLS = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
              .tlsVersions(TlsVersion.TLS_1_0, TlsVersion.TLS_1_1, TlsVersion.TLS_1_2)
              .cipherSuites(
                      CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
                      CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384,
                      CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384)
              .build();
      client.setConnectionSpecs(ImmutableList.of(modernTLS, ConnectionSpec.CLEARTEXT));
      return client;
   }

}
