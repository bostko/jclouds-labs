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
package org.jclouds.bracket.compute.options;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Preconditions.checkNotNull;
import java.util.List;
import java.util.Map;

import org.jclouds.compute.options.TemplateOptions;
import org.jclouds.domain.LoginCredentials;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.scriptbuilder.domain.Statement;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/**
 * Contains options supported in the {@code ComputeService#runNode} operation on the
 * "docker" provider. <h2>Usage</h2> The recommended way to instantiate a
 * BracketTemplateOptions object is to statically import BracketTemplateOptions.* and invoke a static
 * creation method followed by an instance mutator (if needed):
 * <p/>
 * <code>
 * import static BracketTemplateOptions.Builder.*;
 * <p/>
 * ComputeService api = // get connection
 * templateBuilder.options(inboundPorts(22, 80, 8080, 443));
 * Set<? extends NodeMetadata> set = api.createNodesInGroup(tag, 2, templateBuilder.build());
 * <code>
 */
public class BracketTemplateOptions extends TemplateOptions implements Cloneable {

   protected Optional<String> dns = Optional.absent();
   protected Optional<String> hostname = Optional.absent();
   protected Optional<Integer> memory = Optional.absent();
   protected Optional<Integer> cpuShares = Optional.absent();
   protected Optional<List<String>> commands = Optional.absent();
   protected Optional<Map<String, String>> volumes = Optional.absent();
   protected Optional<List<String>> env = Optional.absent();
   protected Optional<Map<Integer, Integer>> portBindings = Optional.absent();

   @Override
   public BracketTemplateOptions clone() {
      BracketTemplateOptions options = new BracketTemplateOptions();
      copyTo(options);
      return options;
   }

   @Override
   public void copyTo(TemplateOptions to) {
      super.copyTo(to);
      if (to instanceof BracketTemplateOptions) {
         BracketTemplateOptions eTo = BracketTemplateOptions.class.cast(to);
         if (volumes.isPresent()) {
            eTo.volumes(getVolumes().get());
         }
         if (hostname.isPresent()) {
            eTo.hostname(hostname.get());
         }
         if (dns.isPresent()) {
            eTo.dns(dns.get());
         }
         if (memory.isPresent()) {
            eTo.memory(memory.get());
         }
         if (commands.isPresent()) {
            eTo.commands(commands.get());
         }
         if (cpuShares.isPresent()) {
             eTo.cpuShares(cpuShares.get());
         }
         if (env.isPresent()) {
             eTo.env(env.get());
         }
         if (portBindings.isPresent()) {
             eTo.portBindings(portBindings.get());
         }
      }
   }

   @Override
   public boolean equals(Object o) {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;
      BracketTemplateOptions that = BracketTemplateOptions.class.cast(o);
      return super.equals(that) && equal(this.volumes, that.volumes) &&
              equal(this.hostname, that.hostname) &&
              equal(this.dns, that.dns) &&
              equal(this.memory, that.memory) &&
              equal(this.commands, that.commands) &&
              equal(this.cpuShares, that.cpuShares) &&
              equal(this.env, that.env) &&
              equal(this.portBindings, that.portBindings);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(super.hashCode(), volumes, hostname, dns, memory, commands, cpuShares, env, portBindings);
   }

   @Override
   public String toString() {
      return Objects.toStringHelper(this)
              .add("dns", dns)
              .add("hostname", hostname)
              .add("memory", memory)
              .add("cpuShares", cpuShares)
              .add("commands", commands)
              .add("volumes", volumes)
              .add("env", env)
              .add("portBindings", portBindings)
              .toString();
   }

   public BracketTemplateOptions volumes(Map<String, String> volumes) {
      this.volumes = Optional.<Map<String, String>>of(ImmutableMap.copyOf(checkNotNull(volumes, "volumes")));
      return this;
   }

   public BracketTemplateOptions dns(@Nullable String dns) {
      this.dns = Optional.fromNullable(dns);
      return this;
   }

   public BracketTemplateOptions hostname(@Nullable String hostname) {
      this.hostname = Optional.fromNullable(hostname);
      return this;
   }

   public BracketTemplateOptions memory(@Nullable Integer memory) {
      this.memory = Optional.fromNullable(memory);
      return this;
   }

   public BracketTemplateOptions commands(Iterable<String> commands) {
      this.commands = Optional.<List<String>>of(ImmutableList.copyOf(checkNotNull(commands, "commands")));
      return this;
   }

   public BracketTemplateOptions commands(String...commands) {
      return commands(ImmutableList.copyOf(checkNotNull(commands, "commands")));
   }

   public BracketTemplateOptions cpuShares(@Nullable Integer cpuShares) {
      this.cpuShares = Optional.fromNullable(cpuShares);
      return this;
   }

   public BracketTemplateOptions env(Iterable<String> env) {
      this.env = Optional.<List<String>>of(ImmutableList.copyOf(checkNotNull(env, "env")));
      return this;
   }

   public BracketTemplateOptions env(String...env) {
      return env(ImmutableList.copyOf(checkNotNull(env, "env")));
   }

   /**
    * Set port bindings between the Bracket host and a container.
    * <p>
    * The {@link java.util.Map} keys are host ports number, and the value for an entry is the
    * container port number. This is the same order as the arguments for the
    * {@code --publish} command-line option to {@code docker run} which is
    * {@code hostPort:containerPort}.
    *
    * @param portBindings the map of host to container port bindings
    */
   public BracketTemplateOptions portBindings(Map<Integer, Integer> portBindings) {
      this.portBindings = Optional.<Map<Integer, Integer>>of(ImmutableMap.copyOf(checkNotNull(portBindings, "portBindings")));
      return this;
   }

   public Optional<Map<String, String>> getVolumes() { return volumes; }

   public Optional<String> getDns() { return dns; }

   public Optional<String> getHostname() { return hostname; }

   public Optional<Integer> getMemory() { return memory; }

   public Optional<List<String>> getCommands() { return commands; }

   public Optional<Integer> getCpuShares() { return cpuShares; }

   public Optional<List<String>> getEnv() { return env; }

   public Optional<Map<Integer, Integer>> getPortBindings() { return portBindings; }

   public static class Builder {

      /**
       * @see BracketTemplateOptions#volumes(java.util.Map)
       */
      public static BracketTemplateOptions volumes(Map<String, String> volumes) {
         BracketTemplateOptions options = new BracketTemplateOptions();
         return options.volumes(volumes);
      }

      /**
       * @see BracketTemplateOptions#dns(String)
       */
      public static BracketTemplateOptions dns(String dns) {
         BracketTemplateOptions options = new BracketTemplateOptions();
         return options.dns(dns);
      }

      /**
       * @see BracketTemplateOptions#hostname(String)
       */
      public static BracketTemplateOptions hostname(String hostname) {
         BracketTemplateOptions options = new BracketTemplateOptions();
         return options.hostname(hostname);
      }

      /**
       * @see BracketTemplateOptions#memory
       */
      public static BracketTemplateOptions memory(int memory) {
         BracketTemplateOptions options = new BracketTemplateOptions();
         return options.memory(memory);
      }

      /**
       * @see BracketTemplateOptions#commands(String[])
       */
      public static BracketTemplateOptions commands(String...commands) {
         BracketTemplateOptions options = new BracketTemplateOptions();
         return options.commands(commands);
      }

      /**
       * @see BracketTemplateOptions#commands(Iterable)
       */
      public static BracketTemplateOptions commands(Iterable<String> commands) {
         BracketTemplateOptions options = new BracketTemplateOptions();
         return BracketTemplateOptions.class.cast(options.commands(commands));
      }

      /**
       * @see BracketTemplateOptions#cpuShares
       */
      public static BracketTemplateOptions cpuShares(int cpuShares) {
         BracketTemplateOptions options = new BracketTemplateOptions();
         return options.cpuShares(cpuShares);
      }

      /**
       * @see BracketTemplateOptions#env(String[])
       */
      public static BracketTemplateOptions env(String...env) {
         BracketTemplateOptions options = new BracketTemplateOptions();
         return BracketTemplateOptions.class.cast(options.env(env));
      }

      /**
       * @see BracketTemplateOptions#env(Iterable)
       */
      public static BracketTemplateOptions env(Iterable<String> env) {
         BracketTemplateOptions options = new BracketTemplateOptions();
         return options.env(env);
      }

      /**
       * @see BracketTemplateOptions#portBindings(java.util.Map)
       */
      public static BracketTemplateOptions portBindings(Map<Integer, Integer> portBindings) {
         BracketTemplateOptions options = new BracketTemplateOptions();
         return options.portBindings(portBindings);
      }

      /**
       * @see TemplateOptions#inboundPorts
       */
      public static BracketTemplateOptions inboundPorts(int... ports) {
         BracketTemplateOptions options = new BracketTemplateOptions();
         return options.inboundPorts(ports);
      }

      /**
       * @see TemplateOptions#port
       */
      public static BracketTemplateOptions blockOnPort(int port, int seconds) {
         BracketTemplateOptions options = new BracketTemplateOptions();
         return options.blockOnPort(port, seconds);
      }

      /**
       * @see TemplateOptions#installPrivateKey
       */
      public static BracketTemplateOptions installPrivateKey(String rsaKey) {
         BracketTemplateOptions options = new BracketTemplateOptions();
         return options.installPrivateKey(rsaKey);
      }

      /**
       * @see TemplateOptions#authorizePublicKey
       */
      public static BracketTemplateOptions authorizePublicKey(String rsaKey) {
         BracketTemplateOptions options = new BracketTemplateOptions();
         return options.authorizePublicKey(rsaKey);
      }

      /**
       * @see TemplateOptions#userMetadata
       */
      public static BracketTemplateOptions userMetadata(Map<String, String> userMetadata) {
         BracketTemplateOptions options = new BracketTemplateOptions();
         return options.userMetadata(userMetadata);
      }

      /**
       * @see TemplateOptions#nodeNames(Iterable)
       */
      public static BracketTemplateOptions nodeNames(Iterable<String> nodeNames) {
         BracketTemplateOptions options = new BracketTemplateOptions();
         return options.nodeNames(nodeNames);
      }

      /**
       * @see TemplateOptions#networks(Iterable)
       */
      public static BracketTemplateOptions networks(Iterable<String> networks) {
         BracketTemplateOptions options = new BracketTemplateOptions();
         return options.networks(networks);
      }

      /**
       * @see TemplateOptions#overrideLoginUser
       */
      public static BracketTemplateOptions overrideLoginUser(String user) {
         BracketTemplateOptions options = new BracketTemplateOptions();
         return options.overrideLoginUser(user);
      }

      /**
       * @see TemplateOptions#overrideLoginPassword
       */
      public static BracketTemplateOptions overrideLoginPassword(String password) {
         BracketTemplateOptions options = new BracketTemplateOptions();
         return options.overrideLoginPassword(password);
      }

      /**
       * @see TemplateOptions#overrideLoginPrivateKey
       */
      public static BracketTemplateOptions overrideLoginPrivateKey(String privateKey) {
         BracketTemplateOptions options = new BracketTemplateOptions();
         return options.overrideLoginPrivateKey(privateKey);
      }

      /**
       * @see TemplateOptions#overrideAuthenticateSudo
       */
      public static BracketTemplateOptions overrideAuthenticateSudo(boolean authenticateSudo) {
         BracketTemplateOptions options = new BracketTemplateOptions();
         return options.overrideAuthenticateSudo(authenticateSudo);
      }

      /**
       * @see TemplateOptions#overrideLoginCredentials
       */
      public static BracketTemplateOptions overrideLoginCredentials(LoginCredentials credentials) {
         BracketTemplateOptions options = new BracketTemplateOptions();
         return options.overrideLoginCredentials(credentials);
      }

      /**
       * @see TemplateOptions#blockUntilRunning
       */
      public static BracketTemplateOptions blockUntilRunning(boolean blockUntilRunning) {
         BracketTemplateOptions options = new BracketTemplateOptions();
         return options.blockUntilRunning(blockUntilRunning);
      }

   }

   // methods that only facilitate returning the correct object type

   /**
    * {@inheritDoc}
    */
   @Override
   public BracketTemplateOptions blockOnPort(int port, int seconds) {
      return BracketTemplateOptions.class.cast(super.blockOnPort(port, seconds));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public BracketTemplateOptions inboundPorts(int... ports) {
      return BracketTemplateOptions.class.cast(super.inboundPorts(ports));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public BracketTemplateOptions authorizePublicKey(String publicKey) {
      return BracketTemplateOptions.class.cast(super.authorizePublicKey(publicKey));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public BracketTemplateOptions installPrivateKey(String privateKey) {
      return BracketTemplateOptions.class.cast(super.installPrivateKey(privateKey));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public BracketTemplateOptions blockUntilRunning(boolean blockUntilRunning) {
      return BracketTemplateOptions.class.cast(super.blockUntilRunning(blockUntilRunning));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public BracketTemplateOptions dontAuthorizePublicKey() {
      return BracketTemplateOptions.class.cast(super.dontAuthorizePublicKey());
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public BracketTemplateOptions nameTask(String name) {
      return BracketTemplateOptions.class.cast(super.nameTask(name));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public BracketTemplateOptions runAsRoot(boolean runAsRoot) {
      return BracketTemplateOptions.class.cast(super.runAsRoot(runAsRoot));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public BracketTemplateOptions runScript(Statement script) {
      return BracketTemplateOptions.class.cast(super.runScript(script));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public BracketTemplateOptions overrideLoginCredentials(LoginCredentials overridingCredentials) {
      return BracketTemplateOptions.class.cast(super.overrideLoginCredentials(overridingCredentials));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public BracketTemplateOptions overrideLoginPassword(String password) {
      return BracketTemplateOptions.class.cast(super.overrideLoginPassword(password));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public BracketTemplateOptions overrideLoginPrivateKey(String privateKey) {
      return BracketTemplateOptions.class.cast(super.overrideLoginPrivateKey(privateKey));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public BracketTemplateOptions overrideLoginUser(String loginUser) {
      return BracketTemplateOptions.class.cast(super.overrideLoginUser(loginUser));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public BracketTemplateOptions overrideAuthenticateSudo(boolean authenticateSudo) {
      return BracketTemplateOptions.class.cast(super.overrideAuthenticateSudo(authenticateSudo));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public BracketTemplateOptions userMetadata(Map<String, String> userMetadata) {
      return BracketTemplateOptions.class.cast(super.userMetadata(userMetadata));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public BracketTemplateOptions userMetadata(String key, String value) {
      return BracketTemplateOptions.class.cast(super.userMetadata(key, value));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public BracketTemplateOptions nodeNames(Iterable<String> nodeNames) {
      return BracketTemplateOptions.class.cast(super.nodeNames(nodeNames));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public BracketTemplateOptions networks(Iterable<String> networks) {
      return BracketTemplateOptions.class.cast(super.networks(networks));
   }

}
