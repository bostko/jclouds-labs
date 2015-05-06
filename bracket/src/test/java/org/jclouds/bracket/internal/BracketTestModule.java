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

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.easymock.EasyMock;
import org.jclouds.crypto.Crypto;

import com.google.common.base.Supplier;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;

import autovalue.shaded.com.google.common.common.base.Throwables;

enum BracketTestModule implements Module {
   INSTANCE;

   BracketTestModule() {
   }

   @Override
   public void configure(Binder binder) {
      Mac mac = null;
      try {
         mac = Mac.getInstance("HMACSHA256");
      } catch (NoSuchAlgorithmException e) {
         e.printStackTrace();
      }
      try {
         SecretKeySpec secretKey = new SecretKeySpec("password".getBytes(Charset.defaultCharset()), "HmacSHA256");
         mac.init(secretKey);
      } catch (Exception e) {
         throw Throwables.propagate(e);
      }

      Crypto crypto = createMock(Crypto.class);
      try {
         expect(crypto.hmacSHA256((byte[]) EasyMock.anyObject())).andReturn(mac).anyTimes();
      } catch (InvalidKeyException e) {
         e.printStackTrace();
      }
      replay(crypto);
      binder.bind(Crypto.class).toInstance(crypto);
      binder.bind(Mac.class).toInstance(mac);

      //  predictable node names
      final AtomicInteger suffix = new AtomicInteger();
      binder.bind(new TypeLiteral<Supplier<String>>() {
      }).toInstance(new Supplier<String>() {
         @Override
         public String get() {
            return suffix.getAndIncrement() + "";
         }
      });
   }
}
