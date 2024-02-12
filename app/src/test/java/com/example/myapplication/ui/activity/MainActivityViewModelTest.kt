package com.example.myapplication.ui.activity

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myapplication.data.model.AnimalData
import com.example.myapplication.data.model.AnimalsList
import com.example.myapplication.extensions.RxImmediateSchedulerExtension
import com.example.myapplication.data.repo.TheRepository
import com.example.myapplication.extensions.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityViewModelTest {

    @get:Rule
    val rxSchedulerRule = RxImmediateSchedulerExtension()
    @get:Rule var instantTaskRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var mockRepo: TheRepository

    private lateinit var objectUnderTest: MainActivityViewModel
    
    @Before
    fun setup() {
       MockKAnnotations.init(this)
    }

    @Test
    fun `when init should check if user is logged in`() {
        //given
        every { mockRepo.isUserLoggedIn() } returns Flowable.just(true)
        //when
        objectUnderTest = MainActivityViewModel(repository = mockRepo)
        //then
        verify(exactly = 1) { mockRepo.isUserLoggedIn() }
    }

    @Test
    fun `when init if logged in should fetch list`() {
        //given
        val mockResponse = AnimalsList()
        every { mockRepo.isUserLoggedIn() } returns Flowable.just(true)
        every { mockRepo.getAnimals(any(), any()) } returns Single.just(mockResponse)
        //when
        objectUnderTest = MainActivityViewModel(repository = mockRepo)
        //then
        verify(exactly = 1) { mockRepo.getAnimals(pageNo = 1, userLocation = null)}
    }

    @Test
    fun `when init if not logged in should notify UI to show login screen`() {
        //given
        val mockResponse = AnimalsList()
        every { mockRepo.isUserLoggedIn() } returns Flowable.just(false)
        //when
        objectUnderTest = MainActivityViewModel(repository = mockRepo)
        //then
        assertTrue(objectUnderTest.openLoginScreen.getOrAwaitValue())
    }

    @Test
    fun `when get list returns error should notify UI to show error`() {
        //given
        val mockError = Throwable("Mock error")
        every { mockRepo.isUserLoggedIn() } returns Flowable.just(true)
        every { mockRepo.getAnimals(any(), any()) } returns Single.error(mockError)
        //when
        objectUnderTest = MainActivityViewModel(repository = mockRepo)
        //then
        assertEquals(objectUnderTest.onError.getOrAwaitValue(), mockError)
    }

    @Test
    fun `when list item is clicked should notify UI to launch details page`() {
        //given
        val animalData = AnimalData()
        every { mockRepo.isUserLoggedIn() } returns Flowable.just(true)
        //when
        objectUnderTest = MainActivityViewModel(repository = mockRepo)
        objectUnderTest.onPetItemClick(animalData = animalData)
        //then
        assertEquals(objectUnderTest.openDetailsScreen.getOrAwaitValue(), animalData)
    }

    @Test
    fun `when user scrolled to the bottom should fetch new page`() {
        //given
        val mockResponse = AnimalsList(hasNextPage = true)
        every { mockRepo.isUserLoggedIn() } returns Flowable.just(true)
        every { mockRepo.getAnimals(any(), any()) } returns Single.just(mockResponse)
        //when
        objectUnderTest = MainActivityViewModel(repository = mockRepo)
        //initial page
        verify(exactly = 1) { mockRepo.getAnimals(pageNo = 1, userLocation = null)}
        //when load next page
        objectUnderTest.onLoadNextPage()
        //then
        verify(exactly = 1) { mockRepo.getAnimals(pageNo = 2, userLocation = null)}
    }

    @Test
    fun `when user input location valid should fetch list based on location`() {
        //given
        val mockResponse = AnimalsList()
        val mockLocationInput = "LA"
        every { mockRepo.isUserLoggedIn() } returns Flowable.just(true)
        every { mockRepo.getAnimals(any(), any()) } returns Single.just(mockResponse)
        //when
        objectUnderTest = MainActivityViewModel(repository = mockRepo)
        //initial page
        verify(exactly = 1) { mockRepo.getAnimals(pageNo = 1, userLocation = null)}
        //when load next page
        objectUnderTest.onLocationInputChange(mockLocationInput, 0 ,0, 0)
        //then
        verify(exactly = 1) { mockRepo.getAnimals(pageNo = 1, userLocation = mockLocationInput)}
    }

    @Test
    fun `when user input location invalid should fetch list based on location`() {
        //given
        val mockResponse = AnimalsList()
        val mockLocationInput = "M"
        every { mockRepo.isUserLoggedIn() } returns Flowable.just(true)
        every { mockRepo.getAnimals(any(), any()) } returns Single.just(mockResponse)
        //when
        objectUnderTest = MainActivityViewModel(repository = mockRepo)
        //initial page
        verify(exactly = 1) { mockRepo.getAnimals(pageNo = 1, userLocation = null)}
        //when load next page
        objectUnderTest.onLocationInputChange(mockLocationInput, 0 ,0, 0)
        //then
        verify(exactly = 0) { mockRepo.getAnimals(pageNo = 1, userLocation = mockLocationInput)}
    }
}