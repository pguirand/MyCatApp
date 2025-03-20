package com.example.mycatapp.domain

import com.example.mycatapp.data.models.CatsItemModel
import com.example.mycatapp.data.repository.CatsRepository
import com.example.mycatapp.data.utils.ApiResponse
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class GetCatsUseCaseTest {

    private lateinit var getCatsUseCase: GetCatsUseCase
    private val repository: CatsRepository = mockk()

    @Before
    fun setUp() {
        getCatsUseCase = GetCatsUseCase(repository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when API returns success, use case emits loading then success`() = runTest {
        // Arrange (Given)
        val mockCats: List<CatsItemModel> = listOf(mockk())
        val response = mockk<Response<List<CatsItemModel>>> {
            every { isSuccessful } returns true
            every { body() } returns mockCats
        }
        coEvery { repository.getCats(any(), any()) } returns response

        // Act (When)
        val emissions = getCatsUseCase(10, 1).toList()

        // Assert (Then)
        assertEquals(ApiResponse.Loading, emissions[0]) // First emission should be Loading
        assertEquals(
            ApiResponse.Success(mockCats),
            emissions[1]
        ) // Second emission should be Success

        coVerify(exactly = 1) { repository.getCats(10, 1) } // Ensure repository is called once
    }

    @Test
    fun `when API returns error, use case emits loading then error`() = runTest {
        // Arrange (Given)
        val errorMessage = "Server Error"
        val response = mockk<Response<List<CatsItemModel>>> {
            every { isSuccessful } returns false
            every { errorBody() } returns mockk {
                every { string() } returns errorMessage
            }
        }
        coEvery { repository.getCats(any(), any()) } returns response

        // Act (When)
        val emissions = getCatsUseCase(10, 1).toList()

        // Assert (Then)
        assertEquals(ApiResponse.Loading, emissions[0]) // First emission should be Loading
        assert(emissions[1] is ApiResponse.Error) // Second emission should be Error
        assertEquals("java.lang.Exception: $errorMessage", (emissions[1] as ApiResponse.Error).errorMessage)

        coVerify(exactly = 1) { repository.getCats(10, 1) } // Ensure repository is called once
    }
}
