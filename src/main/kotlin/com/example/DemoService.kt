package com.example

import io.grpc.Channel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.collect

class DemoService {

    suspend fun saveUser() {
        val demoServerStub = createStub()

        val saveUserRequest = SaveUserRequest.newBuilder()
            .setName("Jhon")
            .setLastName("Doe")
            .setDocument("12345678909")
            .build()

        val saveUserResponse = demoServerStub.saveUser(saveUserRequest)

        println("Usuário registrado com id = " + saveUserResponse.id)
    }

    suspend fun saveUserStream() {
        val demoServerStub = createStub()

        val requests = generateOutgoingRequests()

        demoServerStub.saveUserStream(requests).collect { response ->
            println("Resposta: " + response.id)
        }
    }


    private fun generateOutgoingRequests(): Flow<SaveUserRequest> = flow {

        val request1 = SaveUserRequest.newBuilder()
            .setName("Eduardo")
            .setLastName("Silva")
            .setDocument("05262438594")
            .build()

        val request2 = SaveUserRequest.newBuilder()
            .setName("Carol")
            .setLastName("Souza")
            .setDocument("07262438594")
            .build()

        val request3 = SaveUserRequest.newBuilder()
            .setName("Murilo")
            .setLastName("Oliveira")
            .setDocument("09262438594")
            .build()

        val requests = listOf(request1, request2, request3)

        for (request in requests) {
            println("Requisição: " + request.name)
            emit(request)
            delay(5000)
        }
    }

    private fun createStub(): DemoServerServiceGrpcKt.DemoServerServiceCoroutineStub {
        val channel: Channel = ManagedChannelBuilder.forAddress("localhost", 50051)
            .usePlaintext()
            .build()

        return DemoServerServiceGrpcKt.DemoServerServiceCoroutineStub(channel)
    }
}