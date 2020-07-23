# Project Starter
MVVM + ViewBinding + Rx + Fast Android Networking + Koin + Coil with example

### Sample
- [View](https://github.com/abehbatre/Project-Starter-Kotlin-MVVM/blob/ff58a05602fedf14fd355dc19614e3889d3c5e08/app/src/main/java/id/web/adit/starter/ui/dashboard/HomeFragment.kt#L52)
- [ViewModel](https://github.com/abehbatre/Project-Starter-Kotlin-MVVM/blob/ff58a05602fedf14fd355dc19614e3889d3c5e08/app/src/main/java/id/web/adit/starter/ui/dashboard/HomeViewModel.kt)
- [Repository](https://github.com/abehbatre/Project-Starter-Kotlin-MVVM/blob/master/app/src/main/java/id/web/adit/starter/datasource/AppRepository.kt)


### Screenshot
![Screenshot](https://raw.githubusercontent.com/abehbatre/Project-Starter-Kotlin-MVVM/SS.png)

### Packages Tree
```
├── datasource
│   ├── AppRepository.kt
│   ├── model
│   │   └── ReposPojo.kt
│   ├── persistance
│   └── remote
│       └── Endpoint.kt
├── di
│   ├── AppModule.kt
│   ├── RepoModule.kt
│   └── VMModule.kt
├── networking
│   ├── HttpConfig.kt
│   ├── HttpExt.kt
│   ├── Http.kt
│   ├── _.md
│   ├── Outcome.kt
│   ├── Resource.kt
│   ├── SslUtils.kt
│   └── Status.kt
├── ui
│   ├── __base
│   │   ├── BaseActivity.kt
│   │   ├── BaseFragment.kt
│   │   └── BaseViewModel.kt
│   ├── dashboard
│   │   ├── HomeAdapter.kt
│   │   ├── HomeFragment.kt
│   │   └── HomeViewModel.kt
│   ├── MainActivity.kt
│   └── SplashScreen.kt
└── utils
|   ├── MyExt.kt
|   └── ViewBindingExt.kt
├── AppController.kt
├── Constant.kt

```

