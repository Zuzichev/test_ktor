package com.crowdproj.resources.common.repo

/**
 * @author  Nik Zyuzichev
 * @version 1.0
 * @date  21.12.2023 12:04
 */

interface IResourceRepository {
    suspend fun createResource(rq: DbResourceRequest): DbResourceResponse
    suspend fun readResource(rq: DbResourceIdRequest): DbResourceResponse
    suspend fun updateResource(rq: DbResourceRequest): DbResourceResponse
    suspend fun deleteResource(rq: DbResourceIdRequest): DbResourceResponse
    suspend fun searchResource(rq: DbResourceFilterRequest): DbResourcesResponse

    companion object {
        val NONE = object: IResourceRepository {
            override suspend fun createResource(rq: DbResourceRequest): DbResourceResponse {
                TODO("Not yet implemented")
            }

            override suspend fun readResource(rq: DbResourceIdRequest): DbResourceResponse {
                TODO("Not yet implemented")
            }

            override suspend fun updateResource(rq: DbResourceRequest): DbResourceResponse {
                TODO("Not yet implemented")
            }

            override suspend fun deleteResource(rq: DbResourceIdRequest): DbResourceResponse {
                TODO("Not yet implemented")
            }

            override suspend fun searchResource(rq: DbResourceFilterRequest): DbResourcesResponse {
                TODO("Not yet implemented")
            }

        }
    }
}