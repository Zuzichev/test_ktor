package com.crowdproj.resources.biz

import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.kotlin.cor.rootChain
import com.crowdproj.resources.biz.general.*
import com.crowdproj.resources.biz.permission.accessValidation
import com.crowdproj.resources.biz.permission.chainPermissions
import com.crowdproj.resources.biz.permission.frontPermissions
import com.crowdproj.resources.biz.permission.searchTypes
import com.crowdproj.resources.biz.repo.*
import com.crowdproj.resources.biz.validation.*
import com.crowdproj.resources.biz.worker.*
import com.crowdproj.resources.common.CwpResourceContext
import com.crowdproj.resources.common.CwpResourceCorSettings
import com.crowdproj.resources.common.model.*

class CwpResourceProcessor(private val settings: CwpResourceCorSettings = CwpResourceCorSettings()) {
    suspend fun exec(ctx: CwpResourceContext) =
        BusinessChain.exec(ctx.apply { settings = this@CwpResourceProcessor.settings })

    companion object {
        private val BusinessChain = rootChain<CwpResourceContext> {
            initStatus("Инициализация статуса")
            initRepo("Инициализация репозитория")

            operation("Создание рейтинга", CwpResourceCommand.CREATE) {
                stubs("Обработка стабов") {
                    stubCreateSuccess("Имитация успешной обработки")
                    stubValidationBadOtherResourceId("Имитация ошибки валидации ресурса")
                    stubValidationBadScheduleId("Имитация ошибки валидации расписания")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в resourceValidating") { resourceValidating = resourceRequest.deepCopy() }

                    worker("Очистка поля id рейтинга") { resourceValidating.id = CwpResourceId.NONE }
                    worker("Очистка поля ресурса") {
                        resourceValidating.otherResourceId =
                            CwpOtherResourceId(resourceValidating.otherResourceId.asString().trim())
                    }
                    worker("Очистка поля расписания") {
                        resourceValidating.scheduleId = CwpScheduleId(resourceValidating.scheduleId.asString().trim())
                    }

                    validateOtherResourceIdNotEmpty("Проверка, что поле ресурса не пустое")
                    validateScheduleIdNotEmpty("Проверка, что поле расписания не пустое")
                    validateOtherResourceIdProperFormat("Проверка формата поля ресурса")
                    validateScheduleIdProperFormat("Проверка формата поля расписания")
                    finishRatingValidation("Завершение проверок")
                }
                chainPermissions("Проверка разрешений для пользователя")
                chain {
                    title = "Логика сохранения"
                    repoPrepareCreate("Подготовка объекта для сохранения")
                    accessValidation("Проверка прав доступа")
                    repoCreate("Создание рейтинга в БД")
                }
                frontPermissions("Вычисление пользовательских разрешений для фронтенда") // удобно для работы с UI приложения
                prepareResult("Подготовка ответа")
            }

            operation("Получить рейтинг", CwpResourceCommand.READ) {
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в resourceValidating") { resourceValidating = resourceRequest.deepCopy() }

                    worker("Очистка поля id рейтинга") {
                        resourceValidating.id = CwpResourceId(resourceValidating.id.asString().trim())
                    }
                    validateIdNotEmpty("Проверка, что поле id не пустое")
                    validateIdProperFormat("Проверка формата поля id")

                    finishRatingValidation("Завершение проверок")
                }
                chainPermissions("Проверка разрешений для пользователя")
                chain {
                    title = "Логика чтения"
                    repoRead("Чтение рейтинга в БД")
                    accessValidation("Проверка прав доступа")
                    worker {
                        title = "Подготовка ответа на Read"
                        on { state == CwpResourceState.RUNNING }
                        handle { resourceRepoDone = resourceRepoRead }
                    }
                }
                frontPermissions("Вычисление пользовательских разрешений для фронтенда")
                prepareResult("Подготовка ответа")
            }

            operation("Изменить рейтинг", CwpResourceCommand.UPDATE) {
                stubs("Обработка стабов") {
                    stubUpdateSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubValidationBadOtherResourceId("Имитация ошибки валидации ресурса")
                    stubValidationBadScheduleId("Имитация ошибки валидации расписания")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в ratingValidating") { resourceValidating = resourceRequest.deepCopy() }

                    worker("Очистка поля id рейтинга") {
                        resourceValidating.id = CwpResourceId(resourceValidating.id.asString().trim())
                    }
                    worker("Очистка поля ресурса") {
                        resourceValidating.otherResourceId =
                            CwpOtherResourceId(resourceValidating.otherResourceId.asString().trim())
                    }
                    worker("Очистка поля расписания") {
                        resourceValidating.scheduleId = CwpScheduleId(resourceValidating.scheduleId.asString().trim())
                    }

                    validateIdNotEmpty("Проверка, что поле id рейтинга не пустое")
                    validateOtherResourceIdNotEmpty("Проверка, что поле ресурса не пустое")
                    validateScheduleIdNotEmpty("Проверка, что поле расписания не пустое")

                    validateIdProperFormat("Проверка формата поля id рейтинга")
                    validateOtherResourceIdProperFormat("Проверка формата поля ресурса")
                    validateScheduleIdProperFormat("Проверка формата поля расписания")

                    finishRatingValidation("Завершение проверок")
                }
                chainPermissions("Проверка разрешений для пользователя")
                chain {
                    title = "Логика сохранения"
                    repoRead("Чтение рейтинга из БД")
                    accessValidation("Проверка прав доступа")
                    repoPrepareUpdate("Подготовка объекта для обновления")
                    repoUpdate("Обновление рейтинга в БД")
                }
                frontPermissions("Вычисление пользовательских разрешений для фронтенда")
                prepareResult("Подготовка ответа")
            }

            operation("Удалить рейтинг", CwpResourceCommand.DELETE) {
                stubs("Обработка стабов") {
                    stubDeleteSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в resourceValidating") { resourceValidating = resourceRequest.deepCopy() }

                    worker("Очистка поля id рейтинга") {
                        resourceValidating.id = CwpResourceId(resourceValidating.id.asString().trim())
                    }
                    validateIdNotEmpty("Проверка, что поле id не пустое")
                    validateIdProperFormat("Проверка формата поля id")

                    finishRatingValidation("Завершение проверок")
                }
                chainPermissions("Проверка разрешений для пользователя")
                chain {
                    title = "Логика удаления"
                    repoRead("Чтение рейтинга из БД")
                    accessValidation("Проверка прав доступа")
                    repoPrepareDelete("Подготовка объекта для удаления")
                    repoDelete("Удаление рейтинга в БД")
                }
                frontPermissions("Вычисление пользовательских разрешений для фронтенда")
                prepareResult("Подготовка ответа")
            }

            operation("Поиск рейтингов", CwpResourceCommand.SEARCH) {
                stubs("Обработка стабов") {
                    stubSearchSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в resourceFilterValidating") {
                        resourceFilterValidating = resourceFilterRequest.copy()
                    }
                    finishRatingFilterValidation("Завершение проверок")
                }
                chainPermissions("Проверка разрешений для пользователя")
                searchTypes("Подготовка поискового запроса")
                repoSearch("Поиск рейтинга в БД по фильтру")
                frontPermissions("Вычисление пользовательских разрешений для фронтенда")
                prepareResult("Подготовка ответа")
            }
        }.build()
    }
}