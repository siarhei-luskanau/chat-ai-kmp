package shared.network.api

import shared.common.GenericResult

interface NetworkService {
    suspend fun getLlmUrl(): GenericResult<String>
}
