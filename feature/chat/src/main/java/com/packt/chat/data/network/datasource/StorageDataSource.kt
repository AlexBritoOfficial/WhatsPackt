package com.packt.chat.data.network.datasource

import androidx.core.net.toUri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.File
import javax.inject.Inject

class StorageDataSource @Inject constructor(private val firebaseStorage: FirebaseStorage) {

    suspend fun uploadFile(localFile: File, remotePath: String){

        val storageRef = firebaseStorage.reference.child(remotePath)
        storageRef.putFile(localFile.toUri()).await();
    }

    suspend fun downloadFile(localFile: File, remotePath: String){
        val storageRef = firebaseStorage.reference.child(remotePath)
        storageRef.getFile(localFile).await();

    }

}