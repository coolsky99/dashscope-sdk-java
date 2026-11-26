// Copyright (c) Alibaba, Inc. and its affiliates.
package com.alibaba.dashscope.agentstudio.resource;

import com.alibaba.dashscope.agentstudio.AgentStudioConstants;
import com.alibaba.dashscope.agentstudio.model.DynamicWorkflow;
import com.alibaba.dashscope.agentstudio.pagination.CursorPage;
import com.alibaba.dashscope.agentstudio.param.DynamicWorkflowCreateParam;
import com.alibaba.dashscope.agentstudio.param.DynamicWorkflowListParam;
import com.alibaba.dashscope.agentstudio.param.DynamicWorkflowUpdateParam;
import com.alibaba.dashscope.agentstudio.param.DynamicWorkflowVersionListParam;
import com.alibaba.dashscope.api.GeneralApi;
import com.alibaba.dashscope.base.HalfDuplexParamBase;
import com.alibaba.dashscope.common.FlattenResultBase;
import com.alibaba.dashscope.common.GeneralGetParam;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.protocol.ConnectionOptions;
import com.alibaba.dashscope.protocol.GeneralServiceOption;
import com.alibaba.dashscope.protocol.HttpMethod;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;

/** Published Workflow definitions. Execution is initiated by an Agent's Workflow tools. */
public final class DynamicWorkflows {
  public DynamicWorkflows(String baseUrl, ConnectionOptions connectionOptions, String apiKey) {
    this.baseUrl = baseUrl;
    this.apiKey = apiKey;
    this.api = connectionOptions != null ? new GeneralApi<>(connectionOptions) : new GeneralApi<>();
  }

  public DynamicWorkflow create(DynamicWorkflowCreateParam param) {
    return AsyncHelper.joinAndUnwrap(createAsync(param));
  }

  public DynamicWorkflow retrieve(String workflowId) {
    return AsyncHelper.joinAndUnwrap(retrieveAsync(workflowId));
  }

  public DynamicWorkflow update(String workflowId, DynamicWorkflowUpdateParam param) {
    return AsyncHelper.joinAndUnwrap(updateAsync(workflowId, param));
  }

  public DynamicWorkflow archive(String workflowId) {
    return AsyncHelper.joinAndUnwrap(archiveAsync(workflowId));
  }

  public CursorPage<DynamicWorkflow> list(DynamicWorkflowListParam param) {
    return AsyncHelper.joinAndUnwrap(listAsync(param));
  }

  public CursorPage<DynamicWorkflow> listVersions(
      String workflowId, DynamicWorkflowVersionListParam param) {
    return AsyncHelper.joinAndUnwrap(listVersionsAsync(workflowId, param));
  }

  public DynamicWorkflow retrieveVersion(String workflowId, Integer version) {
    return AsyncHelper.joinAndUnwrap(retrieveVersionAsync(workflowId, version));
  }

  public CompletableFuture<DynamicWorkflow> createAsync(DynamicWorkflowCreateParam param) {
    if (param == null) {
      return AsyncHelper.failedFuture(new InputRequiredException("param is required!"));
    }
    GeneralServiceOption opt =
        AgentStudioConstants.newServiceOption(HttpMethod.POST, "dynamic_workflows", baseUrl);
    return AsyncHelper.callAsync(api, AgentStudioConstants.withApiKey(apiKey, param), opt)
        .thenApply(r -> FlattenResultBase.fromDashScopeResult(r, DynamicWorkflow.class));
  }

  public CompletableFuture<DynamicWorkflow> retrieveAsync(String workflowId) {
    if (workflowId == null || workflowId.isEmpty()) {
      return AsyncHelper.failedFuture(new InputRequiredException("workflowId is required!"));
    }
    GeneralServiceOption opt =
        AgentStudioConstants.newServiceOption(
            HttpMethod.GET, "dynamic_workflows/" + workflowId, baseUrl);
    return AsyncHelper.callAsync(api, getRequestParam(), opt)
        .thenApply(r -> FlattenResultBase.fromDashScopeResult(r, DynamicWorkflow.class));
  }

  public CompletableFuture<DynamicWorkflow> updateAsync(
      String workflowId, DynamicWorkflowUpdateParam param) {
    if (workflowId == null || workflowId.isEmpty() || param == null) {
      return AsyncHelper.failedFuture(
          new InputRequiredException("workflowId and param are required!"));
    }
    GeneralServiceOption opt =
        AgentStudioConstants.newServiceOption(
            HttpMethod.POST, "dynamic_workflows/" + workflowId, baseUrl);
    return AsyncHelper.callAsync(api, AgentStudioConstants.withApiKey(apiKey, param), opt)
        .thenApply(r -> FlattenResultBase.fromDashScopeResult(r, DynamicWorkflow.class));
  }

  public CompletableFuture<DynamicWorkflow> archiveAsync(String workflowId) {
    if (workflowId == null || workflowId.isEmpty()) {
      return AsyncHelper.failedFuture(new InputRequiredException("workflowId is required!"));
    }
    GeneralServiceOption opt =
        AgentStudioConstants.newServiceOption(
            HttpMethod.POST, "dynamic_workflows/" + workflowId + "/archive", baseUrl);
    DynamicWorkflowUpdateParam.DynamicWorkflowUpdateParamBuilder<?, ?> builder =
        DynamicWorkflowUpdateParam.builder();
    return AsyncHelper.callAsync(api, AgentStudioConstants.withApiKey(apiKey, builder.build()), opt)
        .thenApply(r -> FlattenResultBase.fromDashScopeResult(r, DynamicWorkflow.class));
  }

  public CompletableFuture<CursorPage<DynamicWorkflow>> listAsync(DynamicWorkflowListParam param) {
    if (param == null) {
      return AsyncHelper.failedFuture(new InputRequiredException("param is required!"));
    }
    String query = param.toQueryString();
    String path = query.isEmpty() ? "dynamic_workflows" : "dynamic_workflows?" + query;
    GeneralServiceOption opt = AgentStudioConstants.newServiceOption(HttpMethod.GET, path, baseUrl);
    return AsyncHelper.callAsync(api, getRequestParam(), opt)
        .thenApply(
            r -> {
              Type type = new TypeToken<CursorPage<DynamicWorkflow>>() {}.getType();
              CursorPage<DynamicWorkflow> page = FlattenResultBase.fromDashScopeResult(r, type);
              page.setFetchNext(
                  cursor ->
                      listAsync(
                          DynamicWorkflowListParam.builder()
                              .limit(param.getLimit())
                              .includeArchived(param.getIncludeArchived())
                              .page(cursor)
                              .build()));
              return page;
            });
  }

  public CompletableFuture<CursorPage<DynamicWorkflow>> listVersionsAsync(
      String workflowId, DynamicWorkflowVersionListParam param) {
    if (workflowId == null || workflowId.isEmpty()) {
      return AsyncHelper.failedFuture(new InputRequiredException("workflowId is required!"));
    }
    String query = param != null ? param.toQueryString() : "";
    String path = "dynamic_workflows/" + workflowId + "/versions";
    GeneralServiceOption opt =
        AgentStudioConstants.newServiceOption(
            HttpMethod.GET, query.isEmpty() ? path : path + "?" + query, baseUrl);
    return AsyncHelper.callAsync(api, getRequestParam(), opt)
        .thenApply(
            r -> {
              Type type = new TypeToken<CursorPage<DynamicWorkflow>>() {}.getType();
              CursorPage<DynamicWorkflow> page = FlattenResultBase.fromDashScopeResult(r, type);
              page.setFetchNext(
                  cursor ->
                      listVersionsAsync(
                          workflowId,
                          DynamicWorkflowVersionListParam.builder()
                              .limit(param != null ? param.getLimit() : null)
                              .page(cursor)
                              .build()));
              return page;
            });
  }

  public CompletableFuture<DynamicWorkflow> retrieveVersionAsync(
      String workflowId, Integer version) {
    if (workflowId == null || workflowId.isEmpty() || version == null || version < 1) {
      return AsyncHelper.failedFuture(
          new InputRequiredException("workflowId and a positive version are required!"));
    }
    GeneralServiceOption opt =
        AgentStudioConstants.newServiceOption(
            HttpMethod.GET, "dynamic_workflows/" + workflowId + "/versions/" + version, baseUrl);
    return AsyncHelper.callAsync(api, getRequestParam(), opt)
        .thenApply(r -> FlattenResultBase.fromDashScopeResult(r, DynamicWorkflow.class));
  }

  private GeneralGetParam getRequestParam() {
    GeneralGetParam.GeneralGetParamBuilder<?, ?> builder = GeneralGetParam.builder();
    builder.apiKey(apiKey);
    return builder.build();
  }

  private final GeneralApi<HalfDuplexParamBase> api;
  private final String baseUrl;
  private final String apiKey;
}
