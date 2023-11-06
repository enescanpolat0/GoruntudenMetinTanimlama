package com.enescanpolat.goruntudenmetintanmlama.data.dependencyinjection

import android.app.Application
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import com.enescanpolat.goruntudenmetintanmlama.data.repository.MainRepositoryImpl
import com.enescanpolat.goruntudenmetintanmlama.domain.repository.MainRepository
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object appModule {

    @Singleton
    @Provides
    fun injectContext(application: Application):Context{
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun injectTextRecognizer():TextRecognizer{
        return TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    }

    @Singleton
    @Provides
    fun injectClipBoarManager(context: Context):ClipboardManager{
        return context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    }

    @Singleton
    @Provides
    fun injectMainRepository(context: Context,recognizer: TextRecognizer,clipboardManager: ClipboardManager):MainRepository{
        return MainRepositoryImpl(context, recognizer, clipboardManager)
    }


}