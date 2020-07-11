## BaseJavaApp
This repository contains the libraries, classes, and infrastructure that I use a lot for my Android app projects at work, so I decided to put them in one project so that I can have a way to jumpstart Android app development quickly, and also to serve as a reference point for me in the future. I intend to update this app in the future, since Android development is a constantly evolving thing.

## Libraries used
[Android Jetpack(Room, Databinding/View Binding, ViewModels, and Navigation Component)](https://developer.android.com/jetpack)  
[Dagger2](https://github.com/google/dagger)  
[RxJava2](https://github.com/ReactiveX/RxJava)  
[Moshi](https://github.com/square/moshi)  
[Retrofit2](https://github.com/square/retrofit)  
[OkHttp's Logging interceptor](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor)  

## A word about app architecture
I was tempted to describe this app as a "base MVVM with repository pattern app", since I have  ```ViewModels``` that are scoped to ```Fragments``` and have one ```MainViewModel``` that is scoped to the ```MainActivity```, and I use classes named "repositories". However, even if Microsoft's [definition](https://docs.microsoft.com/en-us/xamarin/xamarin-forms/enterprise-application-patterns/mvvm#the-mvvm-pattern) of MVVM is very similar to the way Android ViewModels are used, it seems that using Android ViewModels does not automatically mean you are implementing MVVM pattern. It's still very confusing for me. Read Yigit Boyar's(Android/Google developer that designed Android ViewModels) comments [here](https://www.reddit.com/r/androiddev/comments/b908fr/can_someone_explain_to_me_why_aac_is_trying_to/ek2cm50/) regarding ViewModels.

## Contribution
Feel free to open bug reports or PRs.

