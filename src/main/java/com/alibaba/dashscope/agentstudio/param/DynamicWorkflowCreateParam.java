// Copyright (c) Alibaba, Inc. and its affiliates.
package com.alibaba.dashscope.agentstudio.param;

import com.alibaba.dashscope.base.FlattenHalfDuplexParamBase;
import com.google.gson.JsonObject;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

/** Create and publish version 1; script and schema validation is performed by the server. */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class DynamicWorkflowCreateParam extends FlattenHalfDuplexParamBase {
  @Override
  public JsonObject getHttpBody() {
    JsonObject body = new JsonObject();
    body.addProperty("name", name);
    body.addProperty("script", script);
    if (description != null) {
      body.addProperty("description", description);
    }
    if (inputSchema != null) {
      body.add("input_schema", inputSchema);
    }
    addExtraBody(body);
    return body;
  }

  @NonNull private String name;
  @NonNull private String script;
  @Default private String description = null;
  @Default private JsonObject inputSchema = null;
}
