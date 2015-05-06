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
package org.jclouds.bracket.filters;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.io.BaseEncoding.base64;
import static com.google.common.io.ByteStreams.readBytes;
import static org.jclouds.crypto.Macs.asByteProcessor;
import static org.jclouds.util.Strings2.toInputStream;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jclouds.crypto.Crypto;
import org.jclouds.domain.Credentials;
import org.jclouds.http.HttpException;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpRequestFilter;
import org.jclouds.location.Provider;

import com.google.common.base.Charsets;
import com.google.common.base.Supplier;
import com.google.common.io.ByteProcessor;

/**
 * Adds the authentication query parameters to the requests.
 */
@Singleton
public class AuthenticationFilter implements HttpRequestFilter {

   private final Supplier<Credentials> credentialsSupplier;
   private final Crypto crypto;

   @Inject
   AuthenticationFilter(@Provider final Supplier<Credentials> credentials, Crypto crypto) {
      this.credentialsSupplier = checkNotNull(credentials, "credential supplier cannot be null");
      this.crypto = crypto;
   }

   /**
     The client has previously obtained a set of MAC credentials for
     accessing resources on the "http://example.com/" server.  The MAC
     credentials issued to the client include the following attributes:

     MAC key identifier:  h480djs93hd8
     MAC key:  489dks293j39
     MAC algorithm:  hmac-sha-1

     The client constructs the authentication header by calculating a
     timestamp (e.g. the number of seconds since January 1, 1970 00:00:00
     GMT) and generating a random string used as a nonce:

     Timestamp:  1336363200
     Nonce:  dj83hs9s

     The client constructs the normalized request string (the new line
     separator character is represented by "\n" for display purposes only;
     the trailing new line separator signify that no extension value is
     included with the request, explained below):


     1336363200\n
     dj83hs9s\n
     GET\n
       /resource/1?b=1&a=2\n
     example.com\n
     80\n
     \n
   */
   @Override public HttpRequest filter(HttpRequest request) throws HttpException {
      long timestamp = currentTimeSeconds();

      String nonce = generateNonce();
      String httpRequestMethod = request.getMethod().toUpperCase();
      String httpRequestURI = request.getEndpoint().getPath();
      String hostname = request.getEndpoint().getHost();

      String port;
      if (request.getEndpoint().getPort() != -1) {
         port = Integer.toString(request.getEndpoint().getPort());
      } else {
         port = request.getEndpoint().getScheme().equalsIgnoreCase("https") ? "443" : "80";
      }

      String normalizedRequest = String.format("%s\n%s\n%s\n%s\n%s\n%s\n\n", timestamp, nonce, httpRequestMethod, httpRequestURI, hostname, port);
      String authorizationHeader = null;
      try {
         final byte[] bytes = credentialsSupplier.get().credential.getBytes(Charsets.UTF_8);
         ByteProcessor<byte[]> hmacSHA256 = asByteProcessor(crypto.hmacSHA256(bytes));
         String mac = base64().encode(readBytes(toInputStream(normalizedRequest), hmacSHA256));
         authorizationHeader = String.format("MAC id=\"%s\", ts=\"%d\", nonce=\"%s\", mac=\"%s\"", credentialsSupplier.get().identity,
                 timestamp, nonce, mac);
      } catch (IOException e) {
         e.printStackTrace();
      } catch (InvalidKeyException e) {
         e.printStackTrace();
      }

      return request.toBuilder().addHeader("Authorization", authorizationHeader).build();
   }

   long currentTimeSeconds() {
      return System.currentTimeMillis() / 1000;
   }

   String generateNonce() {
      final byte[] buffer = new byte[12];
      new Random().nextBytes(buffer);
      return base64().encode(buffer);
   }

}
