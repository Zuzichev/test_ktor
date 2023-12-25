package com.crowdproj.resources.repo.tests

import com.crowdproj.resources.common.model.CwpResource
import com.crowdproj.resources.common.model.CwpResourceId
import com.crowdproj.resources.common.repo.DbResourceIdRequest
import com.crowdproj.resources.common.repo.IResourceRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoResourceReadTest {
    abstract val repo: IResourceRepository
    protected open val readSucc = initObjects[0]

    @Test
    fun readSuccess() = runRepoTest {
        val result = repo.readResource(DbResourceIdRequest(readSucc.id))

        assertEquals(true, result.isSuccess)
        assertEquals(readSucc, result.data)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun readNotFound() = runRepoTest {
        val result = repo.readResource(DbResourceIdRequest(notFoundId))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitResources("read") {
        override val initObjects: List<CwpResource> = listOf(
            createInitTestModel("read")
        )

        val notFoundId = CwpResourceId("resource-repo-read-notFound")
    }
}