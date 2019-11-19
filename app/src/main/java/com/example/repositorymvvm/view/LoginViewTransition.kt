package com.example.repositorymvvm.view

sealed class LoginViewTransition {
    object GoToRepositoryView : LoginViewTransition()
}