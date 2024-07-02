package com.gscapin.conexanewsapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gscapin.conexanewsapp.domain.model.Address
import com.gscapin.conexanewsapp.domain.model.Company
import com.gscapin.conexanewsapp.domain.model.Geo
import com.gscapin.conexanewsapp.domain.model.Login
import com.gscapin.conexanewsapp.domain.model.User
import com.gscapin.conexanewsapp.domain.useCases.GetUsersUseCase
import com.gscapin.conexanewsapp.presentation.home.NewsUiState
import com.gscapin.conexanewsapp.presentation.user.UserUiState
import com.gscapin.conexanewsapp.presentation.user.UserViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest {

    private lateinit var userViewModel: UserViewModel

    @RelaxedMockK
    private lateinit var getUsersUseCase: GetUsersUseCase

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)

        // probarlo sin init
        userViewModel = UserViewModel(getUsersUseCase = getUsersUseCase)

        Dispatchers.setMain(Dispatchers.Unconfined)

    }

    @After
    fun onAfter(){
        Dispatchers.resetMain()
    }

    @Test
    fun `when randomQuote is executed, it does a postValue on quoteModel`() = runTest {
        val usersList = listOf(
            User(
                id = 1,
                firstname = "John",
                lastname = "Doe",
                email = "johndoe@example.com",
                birthDate = "1973-01-22",
                login = Login(
                    uuid = "1a0eed01-9430-4d68-901f-c0d4c1c3bf22",
                    username = "johndoe",
                    password = "jsonplaceholder.org",
                    md5 = "c1328472c5794a25723600f71c1b4586",
                    sha1 = "35544a31cc19bd6520af116554873167117f4d94",
                    registered = "2023-01-10T10:03:20.022Z"
                ),
                address = Address(
                    city = "Anytown",
                    street = "123 Main Street",
                    suite = "Apt. 4",
                    zipcode = "12345-6789",
                    geo = Geo(lat = "42.1234", lng = "-71.2345")
                ),
                phone = "(555) 555-1234",
                website = "www.johndoe.com",
                company = Company(
                    name = "ABC Company",
                    catchPhrase = "Innovative solutions for all your needs",
                    bs = "Marketing"
                )
            ),
            User(
                id = 2,
                firstname = "Jane",
                lastname = "Smith",
                email = "janesmith@example.com",
                birthDate = "1985-05-15",
                login = Login(
                    uuid = "2b1eefd2-8541-4c79-812g-d1e5d2d4cf33",
                    username = "janesmith",
                    password = "securepassword",
                    md5 = "d2439587c69a4b35737611e822c2b697",
                    sha1 = "46655b42dd2abee631bf227665984278228e5e05",
                    registered = "2022-08-22T15:45:30.123Z"
                ),
                address = Address(
                    city = "Otherville",
                    street = "456 Elm Street",
                    suite = "Suite 101",
                    zipcode = "67890-1234",
                    geo = Geo(lat = "34.5678", lng = "-65.4321")
                ),
                phone = "(555) 555-5678",
                website = "www.janesmith.net",
                company = Company(
                    name = "XYZ Corporation",
                    catchPhrase = "Empowering businesses through technology",
                    bs = "Software Development"
                )
            ),
            User(
                id = 3,
                firstname = "Robert",
                lastname = "Johnson",
                email = "robertjohnson@example.com",
                birthDate = "1990-07-30",
                login = Login(
                    uuid = "3c2eed03-9430-4d68-903f-e0d4c1c3bf44",
                    username = "robertjohnson",
                    password = "securepassword",
                    md5 = "e6e6e6e6e6e6e6e6e6e6e6e6e6e6e6e6",
                    sha1 = "b6e6e6e6e6e6e6e6e6e6e6e6e6e6e6e6e6e6",
                    registered = "2021-09-25T14:22:45.022Z"
                ),
                address = Address(
                    city = "Anycity",
                    street = "789 Oak Street",
                    suite = "Floor 2",
                    zipcode = "67890-1234",
                    geo = Geo(lat = "41.1234", lng = "-73.4567")
                ),
                phone = "(555) 555-9012",
                website = "www.robertjohnson.com",
                company = Company(
                    name = "DEF Inc",
                    catchPhrase = "Your trusted partner",
                    bs = "Consulting"
                )
            ),
            User(
                id = 4,
                firstname = "Emily",
                lastname = "Davis",
                email = "emilydavis@example.com",
                birthDate = "2000-11-11",
                login = Login(
                    uuid = "4d3eed04-9430-4d68-904f-f0d4c1c3bf55",
                    username = "emilydavis",
                    password = "mypassword",
                    md5 = "f7f7f7f7f7f7f7f7f7f7f7f7f7f7f7f7",
                    sha1 = "c7f7f7f7f7f7f7f7f7f7f7f7f7f7f7f7f7f7",
                    registered = "2020-11-30T19:55:10.022Z"
                ),
                address = Address(
                    city = "Newtown",
                    street = "101 Maple Avenue",
                    suite = "Apt. 5",
                    zipcode = "11223-4455",
                    geo = Geo(lat = "39.5678", lng = "-74.5678")
                ),
                phone = "(555) 555-3456",
                website = "www.emilydavis.com",
                company = Company(
                    name = "GHI LLC",
                    catchPhrase = "Quality above all",
                    bs = "Manufacturing"
                )
            )

        )

        coEvery { getUsersUseCase.invoke() } returns usersList

        // When
        userViewModel.getUsers()

        // Then
        val firstValue = (userViewModel.uiState.value as? UserUiState.Success)?.users?.first()
        assert(firstValue == usersList.first())
    }
}