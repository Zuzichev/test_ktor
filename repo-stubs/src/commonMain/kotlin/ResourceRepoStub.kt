package com.crowdproj.resources.repo.stubs

import com.crowdproj.resources.common.repo.*
import com.crowdproj.resources.stubs.CwpResourceStub

class ResourceRepoStub : IResourceRepository {
    override suspend fun createResource(rq: DbResourceRequest): DbResourceResponse {
        return DbResourceResponse(
            data = CwpResourceStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun readResource(rq: DbResourceIdRequest): DbResourceResponse {
        return DbResourceResponse(
            data = CwpResourceStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun updateResource(rq: DbResourceRequest): DbResourceResponse {
        return DbResourceResponse(
            data = CwpResourceStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun deleteResource(rq: DbResourceIdRequest): DbResourceResponse {
        return DbResourceResponse(
            data = CwpResourceStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun searchResource(rq: DbResourceFilterRequest): DbResourcesResponse {
        return DbResourcesResponse(
            data = CwpResourceStub.prepareSearchList(filter = ""),
            isSuccess = true,
        )
    }
}