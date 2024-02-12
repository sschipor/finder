package com.example.myapplication.data.repo

import com.example.myapplication.data.local.LocalSource
import com.example.myapplication.data.remote.RemoteSource
import com.example.myapplication.domain.rest.dto.LoginUserDto
import com.example.myapplication.extensions.RxImmediateSchedulerExtension
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verify
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TheRepositoryImplTest {
    @get:Rule
    val rxSchedulerRule = RxImmediateSchedulerExtension()

    @MockK
    private lateinit var mockRemote: RemoteSource

    @MockK
    private lateinit var mockLocal: LocalSource

    private lateinit var objectUnderTest: TheRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        objectUnderTest = TheRepositoryImpl(
            localSource = mockLocal,
            remoteSource = mockRemote,
        )
    }

    @Test
    fun `when calling is user logged in shoud return value from local source`() {
        //given
        every { mockLocal.isUserLoggedIn() } returns Flowable.just(true)
        //when
        val testObserver = objectUnderTest.isUserLoggedIn().test()
        //then
        testObserver.assertValueAt(0, true).cancel()
    }

    @Test
    fun `when calling login user should store in local the token in case of success`() {
        //given
        val mockLoginResponse = LoginUserDto("Bearer", accessToken = "the access token")
        every { mockRemote.loginUser() } returns Single.just(mockLoginResponse)
        every { mockLocal.updateToken(any(), any()) } just Runs
        //when
        val testObserverLogIn = objectUnderTest.loginUser().test()
        //then
        testObserverLogIn.assertComplete().dispose()
        verify { mockLocal.updateToken(mockLoginResponse.tokenType, mockLoginResponse.accessToken) }
    }

}