package com.example.myapplication.di

interface Provider<T> {
    fun get(): T
}