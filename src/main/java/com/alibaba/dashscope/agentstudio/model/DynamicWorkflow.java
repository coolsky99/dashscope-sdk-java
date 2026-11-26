// Copyright (c) Alibaba, Inc. and its affiliates.
package com.alibaba.dashscope.agentstudio.model;

import com.alibaba.dashscope.common.FlattenResultBase;
import com.google.gson.annotations.SerializedName;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;

/** A published Workflow version, including its script and input schema in list responses. */
@Data
@EqualsAndHashCode(callSuper = true)
public class DynamicWorkflow extends FlattenResultBase {
  /** Public Workflow resource type. */
  public enum Type {
    /** A reusable JavaScript Workflow definition. */
    @SerializedName("dynamic_workflow")
    DYNAMIC_WORKFLOW
  }

  /** Publication status, independent of whether the definition is archived. */
  public enum Status {
    /** The version is published and immutable. */
    @SerializedName("published")
    PUBLISHED
  }

  @SerializedName("id")
  private String id;

  @SerializedName("type")
  private Type type;

  @SerializedName("name")
  private String name;

  @SerializedName("description")
  private String description;

  @SerializedName("status")
  private Status status;

  @SerializedName("archived")
  private Boolean archived;

  @SerializedName("version")
  private Integer version;

  @SerializedName("script")
  private String script;

  @SerializedName("input_schema")
  private Map<String, Object> inputSchema;

  @SerializedName("created_at")
  private String createdAt;

  @SerializedName("updated_at")
  private String updatedAt;
}
