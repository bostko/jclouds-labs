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

import java.util.Date;

import org.jclouds.json.SerializedNames;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Workload {

   public abstract String id();
   public abstract String name();
   public abstract String description();
   public abstract String customer();
   public abstract String state();
   public abstract String billingGroup();
   public abstract String cost();
   public abstract String createdBy();
   public abstract Date createdTime();
   public abstract String hourlyCost();
   public abstract String dailyCost();
   public abstract String monthlyCost();
   public abstract String fixedCharge();
   public abstract boolean deleted();
   public abstract boolean expired();
   public abstract String instances();
   public abstract Date leaseExpireTime();
   public abstract Date leaseModifiedTime();
   public abstract String modifiedBy();
   public abstract Date modifiedTime();
   public abstract String requestedState();
   public abstract String workloadTemplate();
   public abstract String zone();

   Workload() {
   }

   @SerializedNames({"id", "name", "description", "customer", "state", "billing_group", "cost", "created_by", "created_time", "hourly_cost", "daily_cost",
           "monthly_cost", "fixed_charge", "deleted", "expired", "instances", "lease_expire_time", "lease_modified_time", "modified_by", "modified_time",
           "requested_state", "workload_template", "zone" })
   public static Workload create(String id, String name, String description, String customer, String state, String billingGroup, String cost, String createdBy, Date createdTime,
                                 String hourlyCost, String dailyCost, String monthlyCost, String fixedCharge, boolean deleted, boolean expired, String instances,
                                 Date leaseExpireTime, Date leaseModifiedTime, String modifiedBy, Date modifiedTime, String requestedState, String workloadTemplate, String zone) {
      return new AutoValue_Workload(
              id, name, description, customer, state, billingGroup, cost, createdBy, createdTime,
              hourlyCost, dailyCost, monthlyCost, fixedCharge, deleted, expired, instances,
              leaseExpireTime, leaseModifiedTime, modifiedBy, modifiedTime, requestedState, workloadTemplate, zone);
   }
}
