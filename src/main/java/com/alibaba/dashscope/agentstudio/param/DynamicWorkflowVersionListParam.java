// Copyright (c) Alibaba, Inc. and its affiliates.
package com.alibaba.dashscope.agentstudio.param;

import com.alibaba.dashscope.agentstudio.AgentStudioConstants;
import com.alibaba.dashscope.base.FlattenHalfDuplexParamBase;
import com.google.gson.JsonObject;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/** Cursor pagination for published versions; page is the opaque next_page value. */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class DynamicWorkflowVersionListParam extends FlattenHalfDuplexParamBase {
  @Override
  public JsonObject getHttpBody() {
    return new JsonObject();
  }

  public String toQueryString() {
    StringBuilder sb = new StringBuilder();
    AgentStudioConstants.appendParam(sb, "limit", limit);
    AgentStudioConstants.appendParam(sb, "page", page);
    return sb.toString();
  }

  @Default private Integer limit = null;
  @Default private String page = null;
}
