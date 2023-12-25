package com.crowdproj.resources.common.exception

import com.crowdproj.resources.common.model.CwpResourceLock

class RepoConcurrencyException(expectedLock: CwpResourceLock, actualLock: CwpResourceLock?): RuntimeException(
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)