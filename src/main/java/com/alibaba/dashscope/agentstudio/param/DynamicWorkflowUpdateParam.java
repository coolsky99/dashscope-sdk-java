// Copyright (c) Alibaba, Inc. and its affiliates.
package com.alibaba.dashscope.agentstudio.param;

import com.alibaba.dashscope.base.FlattenHalfDuplexParamBase;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/** Partial update that publishes a new version; an empty body leaves the version unchanged. */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class DynamicWorkflowUpdateParam extends FlattenHalfDuplexParamBase {
  @Override
  public boolean shouldSerializeExplicitNulls() {
    return Boolean.TRUE.equals(clearDescription) || Boolean.TRUE.equals(clearInputSchema);
  }

  @Override
  public JsonObject getHttpBody() {
    if (description != null && Boolean.TRUE.equals(clearDescription)) {
      throw new IllegalArgumentException("description and clearDescription cannot both be set");
    }
    if (inputSchema != null && Boolean.TRUE.equals(clearInputSchema)) {
      throw new IllegalArgumentException("inputSchema and clearInputSchema cannot both be set");
    }
    JsonObject body = new JsonObject();
    if (name != null) {
      body.addProperty("name", name);
    }
    if (script != null) {
      body.addProperty("script", script);
    }
    if (description != null) {
      body.addProperty("description", description);
    } else if (Boolean.TRUE.equals(clearDescription)) {
      body.add("description", JsonNull.INSTANCE);
    }
    if (inputSchema != null) {
      body.add("input_schema", inputSchema);
    } else if (Boolean.TRUE.equals(clearInputSchema)) {
      body.add("input_schema", JsonNull.INSTANCE);
    }
    addExtraBody(body);
    return body;
  }

  @Default private String name = null;
  @Default private String script = null;
  @Default private String description = null;
  @Default private JsonObject inputSchema = null;
  /** Emit description=null instead of omitting the field. */
  @Default private Boolean clearDescription = false;
  /** Emit input_schema=null to clear the input constraint. */
  @Default private Boolean clearInputSchema = false;
}
