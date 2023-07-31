package com.example.myunittest

import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.mockito.Mockito.*

class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var cuboidModel: CuboidModel

    /**
     * value dari panjang, lebar, tinggi
     */
    private val dummyLength = 12.0
    private val dummyWidth = 7.0
    private val dummyHeight = 6.0

    /**
     * ekspetasi hasil volume ketika function testVolume dijalankan
     */
    private val dummyVolume = 504.0
    /**
     * ekspetasi hasil volume ketika function testCircumference dijalankan
     */
    private val dummyCircumference = 100.0
    /**
     * ekspetasi hasil volume ketika function testSurfaceArea dijalankan
     */
    private val dummySurfaceArea = 396.0

    /**
     * @Before
     * Fungsinya untuk menginisialisasi method sebelum melakukan test. Method yang diberi anotasi @Before
     * ini akan dijalankan sebelum menjalankan semua method dengan anotasi @Test. Selain anotasi @Before,
     * dalam melakukan Unit Test juga ada anotasi @After yang berfungsi sebaliknya dari anotasi @Before,
     * yaitu untuk menginisialisai method yang akan dijalankan setelah method dengan anotasi @Test.
     */
    @Before
    fun before() {
        cuboidModel = mock(CuboidModel::class.java)
        mainViewModel = MainViewModel(cuboidModel)
    }

    /**
     * @Test
     * Anotasi ini digunakan pada method yang akan dites.
     *
     * mock()
     * Fungsinya untuk membuat obyek mock yang akan menggantikan obyek yang asli.
     *
     * when()
     * Digunakan untuk menandakan event di mana Anda ingin memanipulasi behavior dari mock object.
     *
     * thenReturn()
     * Digunakan untuk memanipulasi output dari mock object.
     *
     * verify()
     * Digunakan untuk memeriksa metode dipanggil dengan arguman yang diberikan. Verify merupkan fungsi dari framework Mockito
     *
     * assertEquals()
     * Fungsi ini merupakan fungsi dari JUnit yang digunakan untuk memvalidasi output yang diharapkan dan output yang sebenarnya.
     */
    @Test
    fun testVolume() {
        cuboidModel = CuboidModel()
        mainViewModel = MainViewModel(cuboidModel)
        mainViewModel.save(dummyWidth, dummyLength, dummyHeight)
        val volume = mainViewModel.getVolume()
        /**
         * Angka 0.0001 pada parameter ketiga dalam assertEquals() adalah angka delta yang merupakan
         * selisih range di belakang koma bilangan double.
         */
        assertEquals(dummyVolume, volume, 0.0001)
    }

    @Test
    fun testCircumference() {
        cuboidModel = CuboidModel()
        mainViewModel = MainViewModel(cuboidModel)
        mainViewModel.save(dummyWidth, dummyLength, dummyHeight)
        val circumference = mainViewModel.getCircumference()
        assertEquals(dummyCircumference, circumference, 0.0001)
    }

    @Test
    fun testSurfaceArea() {
        cuboidModel = CuboidModel()
        mainViewModel = MainViewModel(cuboidModel)
        mainViewModel.save(dummyWidth, dummyLength, dummyHeight)
        val surfaceArea = mainViewModel.getSurfaceArea()
        assertEquals(dummySurfaceArea, surfaceArea, 0.0001)
    }

    @Test
    fun testMockVolume() {
        `when`(mainViewModel.getVolume()).thenReturn(dummyVolume)
        val volume = mainViewModel.getVolume()
        verify(cuboidModel).getVolume()
        assertEquals(dummyVolume, volume, 0.0001)
    }

    @Test
    fun testMockCircumference() {
        `when`(mainViewModel.getCircumference()).thenReturn(dummyCircumference)
        val circumference = mainViewModel.getCircumference()
        verify(cuboidModel).getCircumference()
        assertEquals(dummyCircumference, circumference, 0.0001)
    }

    @Test
    fun testMockSurfaceArea() {
        `when`(mainViewModel.getSurfaceArea()).thenReturn(dummySurfaceArea)
        val surfaceArea = mainViewModel.getSurfaceArea()
        verify(cuboidModel).getSurfaceArea()
        assertEquals(dummySurfaceArea, surfaceArea, 0.0001)
    }
}