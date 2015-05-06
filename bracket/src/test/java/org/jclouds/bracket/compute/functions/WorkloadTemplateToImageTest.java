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
package org.jclouds.bracket.compute.functions;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.testng.Assert.assertEquals;

import org.easymock.EasyMock;
import org.jclouds.bracket.domain.WorkloadTemplate;
import org.jclouds.compute.domain.Image;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit tests for the {@link org.jclouds.bracket.compute.functions.WorkloadTemplateToImage} class.
 */
@Test(groups = "unit", testName = "WorkloadTemplateToImageTest")
public class WorkloadTemplateToImageTest {

   private WorkloadTemplateToImage function;

   private WorkloadTemplate image;

   @BeforeMethod
   public void setup() {
      image = null;
      /*
      WorkloadTemplate.create(
              "id", // id
              "author",
              "comment",
              Config.builder()
                      .image("imageId")
                      .build(),
              Config.builder()
                      .image("imageId")
                      .build(),
              "parent", // parent
              new Date(), // created
              "containerId", // container
              "1.3.1", // dockerVersion
              "x86_64", // architecture
              "os", // os
              0l, // size
              0l, // virtualSize
              ImmutableList.of("repoTag1:version") // repoTags
      );
      */
      function = new WorkloadTemplateToImage();
   }

   public void testImageToImage() {
      WorkloadTemplate mockImage = mockImage();

      Image image = function.apply(mockImage);

      verify(mockImage);

      assertEquals(mockImage.id(), image.getId().toString());
   }

   private WorkloadTemplate mockImage() {
      WorkloadTemplate mockImage = EasyMock.createMock(WorkloadTemplate.class);

      expect(mockImage.id()).andReturn(image.id()).anyTimes();
      replay(mockImage);

      return mockImage;
   }
}
