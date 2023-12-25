package com.crowdproj.resources.repo.tests

import com.crowdproj.resources.common.model.CwpResource
import com.crowdproj.resources.common.model.CwpResourceUserId
import com.crowdproj.resources.common.repo.DbResourceFilterRequest
import com.crowdproj.resources.common.repo.IResourceRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoRatingSearchTest {
    abstract val repo: IResourceRepository

    protected open val initializedObjects: List<CwpResource> = initObjects

    @Test
    fun searchOwner() = runRepoTest {
        val result = repo.searchResource(DbResourceFilterRequest(ownerId = searchOwnerId))
        assertEquals(true, result.isSuccess)
        val expected = listOf(initializedObjects[1], initializedObjects[3]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data?.sortedBy { it.id.asString() })
        assertEquals(emptyList(), result.errors)
    }

    companion object : BaseInitResources("search") {

        val searchOwnerId = CwpResourceUserId("owner-124")

        override val initObjects: List<CwpResource> = listOf(
            createInitTestModel("rating1"),
            createInitTestModel("rating2", ownerId = searchOwnerId),
            createInitTestModel("rating4", ownerId = searchOwnerId),
        )
    }
}