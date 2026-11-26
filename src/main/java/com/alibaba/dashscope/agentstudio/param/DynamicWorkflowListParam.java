// Copyright (c) Alibaba, Inc. and its affiliates.
package com.alibaba.dashscope.agentstudio.param;

import com.alibaba.dashscope.agentstudio.AgentStudioConstants;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/** List latest published definitions; archived definitions are excluded by default. */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class DynamicWorkflowListParam extends DynamicWorkflowVersionListParam {
  @Override
  public String toQueryString() {
    StringBuilder sb = new StringBuilder(super.toQueryString());
    AgentStudioConstants.appendParam(sb, "include_archived", includeArchived);
    return sb.toString();
  }

  @Default private Boolean includeArchived = null;
}
