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
public abstract class WorkloadTemplate {
   public abstract String id();
   public abstract String name();
   public abstract String description();
   public abstract String customer();
   public abstract String state();
   @Nullable public abstract String maxCost();
   public abstract String createdBy();
   public abstract String cost();
   public abstract String hourlyCost();
   public abstract String dailyCost();
   public abstract String monthlyCost();
   public abstract String fixedCharge();
   public abstract String baseHourlyRate();
   public abstract boolean enableServiceDomain();
   public abstract String workloads();
   public abstract List<String> assignedGroups();
   @Nullable public abstract Date createdTime();
   @Nullable public abstract Date lastDeployedTime();

   WorkloadTemplate() {
   }

   @SerializedNames({ "id", "name", "description", "customer", "state", "max_cost", "created_by", "cost", "hourly_cost", "daily_cost",
                 "monthly_cost", "fixed_charge", "base_hourly_rate", "enable_service_domain", "workloads", "assigned_groups", "created_time",
                 "last_deployed_time" })
   public static WorkloadTemplate create(String id, String name, String description, String customer, String state, String maxCost, String createdBy,
                                         String cost, String hourlyCost, String dailyCost, String monthlyCost, String fixedCharge, String baseHourlyRate, boolean enableServiceDomain,
                                         String workloads, List<String> assignedGroups, Date createdTime, Date lastDeployedTime) {
      return new AutoValue_WorkloadTemplate(id, name, description, customer, state, maxCost, createdBy, cost, hourlyCost, dailyCost, monthlyCost,
              fixedCharge, baseHourlyRate, enableServiceDomain, workloads, copyOf(assignedGroups), createdTime, lastDeployedTime);
   }

}
