package com.kotlin.learn.api.auth.domain.get

import com.kotlin.learn.api.auth.data.repository.AuthRepository
import javax.inject.Inject

class GetAuthRefreshTokenIdentityUseCase @Inject constructor(private val repo: AuthRepository) {
    operator fun invoke() = repo.getRefreshTokenIdentity()
}