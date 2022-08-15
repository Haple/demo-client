package com.example

import io.micronaut.runtime.Micronaut.*

suspend fun main(args: Array<String>) {

	println("Hello World!")
	val demoService = DemoService()
	//demoService.saveUser()
	demoService.saveUserStream()

	build()
		.args(*args)
		.packages("com.example")
		.start()
}

