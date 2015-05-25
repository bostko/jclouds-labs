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

import org.jclouds.javax.annotation.Nullable;
import org.jclouds.json.SerializedNames;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class InstanceTemplate {
   public abstract String id();

   public abstract String name();

   public abstract String description();

   public abstract String customer();

   public abstract String state();

   public abstract String cpuArch();

   public abstract String cpuCoresMinimum();

   public abstract String ramMinimum();

   public abstract String osImage();

   public abstract String cost();

   public abstract String hourlyCost();

   public abstract String dailyCost();

   public abstract String monthlyCost();

   public abstract String baseHourlyRate();

   public abstract boolean internetAccessible();

   public abstract String workloadTemplate();

   public abstract List<String> assignedGroups();

   @Nullable public abstract Date createdTime();

   @Nullable public abstract Date lastDeployedTime();

   public abstract String cloudInitId();

   public abstract String cloudInitScript();

   public abstract String cloudInitType();

   public abstract String cloudInitConfig();

   public abstract String cloudInitData();

   InstanceTemplate() {
   }

   @SerializedNames({"id", "name", "description", "customer", "state", "cpu_arch", "cpu_cores_minimum", "ram_minimum ", "os_image",
           "cost", "hourly_cost", "daily_cost", "monthly_cost", "base_hourly_rate", "internet_accessible", "workload_template", "assigned_groups",
           "created_time", "last_deployed_time", "cloudinit_id", "cloudinit_script", "cloudinit_type", "cloudinit_config", "cloudinit_data"})
   public static InstanceTemplate create(String id, String name, String description, String customer, String state, String cpuArch, String cpuCoresMinimum, String ramMinum, String osImage,
                                         String cost, String hourlyCost, String dailyCost, String monthlyCost, String baseHourlyRate, boolean internetAccessible, String workloadTemplate, List<String> assignedGroups,
                                         Date createdTime, Date lastDeployedTime, String cloudInitId, String cloudInitScript, String cloudInitType, String cloudInitConfig, String cloudInitData) {
      return new AutoValue_InstanceTemplate(
              id, name, description, customer, state, cpuArch, cpuCoresMinimum, ramMinum, osImage,
              cost, hourlyCost, dailyCost, monthlyCost, baseHourlyRate, internetAccessible, workloadTemplate, copyOf(assignedGroups),
              createdTime, lastDeployedTime, cloudInitId, cloudInitScript, cloudInitType, cloudInitConfig, cloudInitData);
   }

}
