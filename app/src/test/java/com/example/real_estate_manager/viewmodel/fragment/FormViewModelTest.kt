package com.example.real_estate_manager.viewmodel.fragment

import android.app.Application
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.real_estate_manager.viewmodel.FormPictureViewModel
import com.example.real_estate_manager.viewmodel.FormViewModel
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import pl.aprilapps.easyphotopicker.MediaFile
import utils.getOrAwaitValue
import java.io.File


@RunWith(AndroidJUnit4::class)
@Config(application = Application::class)
class FormViewModelTest {

    private val application by lazy { ApplicationProvider.getApplicationContext<Application>() }

    private val formViewModel by lazy { FormViewModel(application) }

    @Test
    fun isLiveDataEmitting() {
        formViewModel.formPictures.postValue(
            mutableListOf(
                FormPictureViewModel(
                    "titi",
                    MutableLiveData(null)
                )
            )
        )
        Assert.assertEquals(1, formViewModel.itemList.getOrAwaitValue().size)
        Assert.assertEquals("titi", formViewModel.formPictures.value?.get(0)?.base64)


    }

    @Test
    fun testFormPicture(){
        formViewModel.addPhoto(listOf(MediaFile(Uri.parse("test"), File("toto"))))
        Assert.assertEquals(1, formViewModel.itemList.getOrAwaitValue().size)

        formViewModel.removePictures(0)
        Assert.assertEquals(0, formViewModel.itemList.getOrAwaitValue().size)
    }

}