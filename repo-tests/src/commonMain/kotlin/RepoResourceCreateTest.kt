package com.crowdproj.resources.repo.tests

import com.crowdproj.resources.common.model.*
import com.crowdproj.resources.common.repo.DbResourceRequest
import com.crowdproj.resources.common.repo.IResourceRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoResourceCreateTest {
    abstract val repo: IResourceRepository

    protected open val lockNew: CwpResourceLock = CwpResourceLock("20000000-0000-0000-0000-000000000002")

    private val createObj = CwpResource(
        otherResourceId = CwpOtherResourceId("1"),
        scheduleId = CwpScheduleId("11"),
        ownerId = CwpResourceUserId("owner-123"),
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createResource(DbResourceRequest(createObj))
        val expected = createObj.copy(id = result.data?.id ?: CwpResourceId.NONE)

        assertEquals(true, result.isSuccess)
        assertEquals(expected.otherResourceId, result.data?.otherResourceId)
        assertEquals(expected.scheduleId, result.data?.scheduleId)
        assertNotEquals(CwpResourceId.NONE, result.data?.id)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockNew, result.data?.lock)
    }

    companion object : BaseInitResources("create") {
        override val initObjects: List<CwpResource> = emptyList()
    }
}