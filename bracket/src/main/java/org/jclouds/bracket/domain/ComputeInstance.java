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
package org.jclouds.bracket.domain;

import static org.jclouds.bracket.internal.NullSafeCopies.copyOf;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jclouds.javax.annotation.Nullable;
import org.jclouds.json.SerializedNames;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableMap;

@AutoValue
public abstract class ComputeInstance {

   public abstract String id();

   @Nullable public abstract Date created();

   @Nullable public abstract String path();

   @Nullable public abstract String name();

   public abstract List<String> args();

   @Nullable public abstract String sysInitPath();

   @Nullable public abstract String resolvConfPath();

   public abstract Map<String, String> volumes();

   @Nullable public abstract String driver();

   @Nullable public abstract String execDriver();

   public abstract Map<String, Boolean> volumesRW();

   @Nullable public abstract String command();

   @Nullable public abstract String status();

   @Nullable public abstract String hostnamePath();

   @Nullable public abstract String hostsPath();

   @Nullable public abstract String mountLabel();

   @Nullable public abstract String processLabel();

   ComputeInstance() {
   }

   @SerializedNames(
         {
                 "Id", "Created", "Path", "Name", "Args", "Config", "State", "Image", "NetworkSettings", "SysInitPath",
                 "ResolvConfPath", "Volumes", "HostConfig", "Driver", "ExecDriver", "VolumesRW", "Command", "Status",
                 "Ports", "HostnamePath", "HostsPath", "MountLabel", "ProcessLabel"
         })
   public static ComputeInstance create(String id, Date created, String path, String name, List<String> args, String sysInitPath,
                                  String resolvConfPath, Map<String, String> volumes,
                                  String driver, String execDriver, Map<String, Boolean> volumesRW, String command,
                                  String status, String hostnamePath, String hostsPath,
                                  String mountLabel, String processLabel) {
      return new AutoValue_ComputeInstance(id, created, path, name, copyOf(args), sysInitPath, resolvConfPath, copyOf(volumes), driver, execDriver, copyOf(volumesRW), command,
              status, hostnamePath, hostsPath, mountLabel, processLabel);
   }

   public static Builder builder() {
      return new Builder();
   }

   public Builder toBuilder() {
      return builder().fromComputeInstance(this);
   }

   public static final class Builder {

      private String id;
      private Date created;
      private String path;
      private String name;
      private List<String> args;
      private String image;
      private String sysInitPath;
      private String resolvConfPath;
      private Map<String, String> volumes = ImmutableMap.of();
      private String driver;
      private String execDriver;
      private Map<String, Boolean> volumesRW = ImmutableMap.of();
      private String command;
      private String status;
      private String hostnamePath;
      private String hostsPath;
      private String mountLabel;
      private String processLabel;

      public Builder id(String id) {
         this.id = id;
         return this;
      }

      public Builder created(Date created) {
         this.created = created;
         return this;
      }

      public Builder path(String path) {
         this.path = path;
         return this;
      }

      public Builder name(String name) {
         this.name = name;
         return this;
      }

      public Builder args(List<String> args) {
         this.args = args;
         return this;
      }

      public Builder image(String imageName) {
         this.image = imageName;
         return this;
      }

      public Builder sysInitPath(String sysInitPath) {
         this.sysInitPath = sysInitPath;
         return this;
      }

      public Builder resolvConfPath(String resolvConfPath) {
         this.resolvConfPath = resolvConfPath;
         return this;
      }

      public Builder volumes(Map<String, String> volumes) {
         this.volumes = volumes;
         return this;
      }

      public Builder driver(String driver) {
         this.driver = driver;
         return this;
      }

      public Builder execDriver(String execDriver) {
         this.execDriver = execDriver;
         return this;
      }

      public Builder volumesRW(Map<String, Boolean> volumesRW) {
         this.volumesRW = volumesRW;
         return this;
      }

      public Builder command(String command) {
         this.command = command;
         return this;
      }

      public Builder status(String status) {
         this.status = status;
         return this;
      }

      public Builder hostnamePath(String hostnamePath) {
         this.hostnamePath = hostnamePath;
         return this;
      }

      public Builder hostsPath(String hostsPath) {
         this.hostsPath = hostsPath;
         return this;
      }

      public Builder mountLabel(String mountLabel) {
         this.mountLabel = mountLabel;
         return this;
      }

      public Builder processLabel(String processLabel) {
         this.processLabel = processLabel;
         return this;
      }

      public ComputeInstance build() {
         return ComputeInstance.create(id, created, path, name, args,
                 sysInitPath, resolvConfPath, volumes, driver, execDriver, volumesRW, command, status,
                 hostnamePath, hostsPath, mountLabel, processLabel);
      }

      public Builder fromComputeInstance(ComputeInstance in) {
         return this.id(in.id()).name(in.name()).created(in.created()).path(in.path()).args(in.args())
                 .sysInitPath(in.sysInitPath()).resolvConfPath(in.resolvConfPath()).driver(in.driver())
                 .execDriver(in.execDriver()).volumes(in.volumes()).volumesRW(in.volumesRW())
                 .command(in.command()).status(in.status()).hostnamePath(in.hostnamePath())
                 .hostsPath(in.hostsPath()).mountLabel(in.mountLabel()).processLabel(in.processLabel());
      }
   }
}
