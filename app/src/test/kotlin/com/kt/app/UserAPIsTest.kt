package com.kt.app

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.has
import com.natpryce.hamkrest.present
import com.natpryce.hamkrest.throws
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertFailsWith

class UserAPIsTest {

    @Test
    fun get_userById_returns_user_given_user_exists() {
        // given
        val mockUserRepository = mockk<UserRepository>()
        every {
            mockUserRepository.getById(any())
        } returns User(id = 1, userName = "Jian")

        // when
        val user = UserAPIs(mockUserRepository).getById(1)

        // verify
        assertThat(user, equalTo(User(
            id = 1,
            userName = "Jian"
        )))

        verify(exactly = 1) { mockUserRepository.getById(1) }
    }

    @Test
    fun get_userById_throws_not_found_error_given_user_not_found() {
        // given
        val mockUserRepository = mockk<UserRepository>()
        every {
            mockUserRepository.getById(any())
        } throws NotFoundException("User not found for id: 0")

        // when
        assertThat({
            UserAPIs(mockUserRepository).getById(0)
        }, throws<NotFoundException>(
            has(Exception::message,
                present(equalTo("User not found for id: 0")))
            )
        )


        // verify
        verify(exactly = 1) { mockUserRepository.getById(0) }
    }
}
