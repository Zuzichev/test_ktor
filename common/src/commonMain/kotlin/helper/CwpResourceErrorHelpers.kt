package com.crowdproj.resources.common.helper

import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.exception.RepoConcurrencyException
import com.crowdproj.resources.common.model.CwpResourceError
import com.crowdproj.resources.common.model.CwpResourceLock
import com.crowdproj.resources.common.model.CwpResourceState

fun Throwable.asCwpResourceError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = CwpResourceError(
    code = code,
    group = group,
    field = "",
    title = "Error",
    description = message,
    exception = this,
)

fun CwpResourceContext.addError(vararg error: CwpResourceError) = errors.addAll(error)

fun CwpResourceContext.fail(error: CwpResourceError) {
    addError(error)
    state = CwpResourceState.FINISHING
}

fun errorValidation(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: CwpResourceError.Level = CwpResourceError.Level.ERROR,
) = CwpResourceError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    title = "error",
    description = "Validation error for field $field: $description",
    level = level,
)

fun errorAdministration(
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    field: String = "",
    violationCode: String,
    description: String,
    exception: Exception? = null,
    level: CwpResourceError.Level = CwpResourceError.Level.ERROR,
) = CwpResourceError(
    field = field,
    code = "administration-$violationCode",
    group = "administration",
    title = "administration-$violationCode",
    description = "Microservice management error: $description",
    level = level,
    exception = exception,
)

fun errorRepoConcurrency(
    expectedLock: CwpResourceLock,
    actualLock: CwpResourceLock?,
    exception: Exception? = null,
) = CwpResourceError(
    field = "lock",
    code = "concurrency",
    group = "repo",
    title = "concurrency",
    description = "The object has been changed concurrently by another user or process",
    exception = exception ?: RepoConcurrencyException(expectedLock, actualLock),
)

val errorEmptyId = CwpResourceError(
    code = "id-empty",
    field = "id",
    group = "cruds",
    description = "id must not be null or blank"
)

val errorNotFound = CwpResourceError(
    code = "not-found",
    field = "id",
    group = "cruds",
    title = "error",
    description = "not found",
)

val errorSave = CwpResourceError(
    code = "not-save",
    field = "row",
    group = "cruds",
    title = "error",
    description = "not save a new item",
)
