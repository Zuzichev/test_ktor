package com.crowdproj.resources.stubs

import com.crowdproj.resources.common.model.*
import com.crowdproj.resources.stubs.CwpResourceStubProduct.RESOURCE_PRODUCT


object CwpResourceStub {
    fun get(): CwpResource = RESOURCE_PRODUCT.copy()

    fun prepareResult(block: CwpResource.() -> Unit): CwpResource = get().apply(block)

    fun prepareSearchList(filter: String) = listOf(
        cwpRating("pr-111-01", filter),
        cwpRating("pr-111-02", filter),
        cwpRating("pr-111-03", filter),
        cwpRating("pr-111-04", filter),
        cwpRating("pr-111-05", filter),
        cwpRating("pr-111-06", filter),
    )

    private fun cwpRating(id: String, filter: String) = resourceCopy(RESOURCE_PRODUCT, id = id, otherResourceId = filter)

    private fun resourceCopy(base: CwpResource, id: String, otherResourceId: String) = base.copy(
        id = CwpResourceId(id),
        otherResourceId = CwpOtherResourceId(otherResourceId),
    )

}