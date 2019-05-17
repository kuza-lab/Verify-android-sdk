# Verifysdk

Shows how to use the the verifyke sdk  in your android project

## Authentication
Create a developer account at [Verify Developers Portal ](https://verify.ke/get-started)
When you sign up for an account, you create a team and an app within the team. 
The app is given a consumer key and a secret key. 
You can regenerate secret keys (as you may need to rotate your keys in the future for security reasons). 
The sdk will authenticate to the Verify  by providing a JWT token generated using your consumer key and secret key.

## Using the Library

#To use the sdk the following in the build.gradle(project)
```java
repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
    
```
    
    
#Then add the dependancy

```java
   
       implementation 'com.github.kuza-lab:Verifysdk:v0.0.2Alpha'
```


#In the activity you plan to use the sdk, initialize using the builder pattern and provide your consumer secret and secret key 

```kotlin
    v = Verify.Builder(this)
            .secretKey("secret key here")
            .consumerKey("consumer key here")
            .enviroment(Enviroment.PRODUCTION)
            .build()
     
```
     
     
#You can then proceed to use the created Verify object to perfom other operations 

##Example
     
```kotlin
  private fun searchPerson(personId: String) {
        v?.getPerson(personId, object : GetUserDetailsListener {
            override fun onCallStarted() {

                setProgressBarVisibility(View.VISIBLE)
            }

            override fun onResponse(person: Person) {
                setProgressBarVisibility(View.GONE)
                showPersonResponseDialog(person)
            }

            override fun onFailure(verifyException: VerifyException) {
                setProgressBarVisibility(View.GONE)
                showError(verifyException)

            }
        })
    }
    
    
   
    
```

Make sure to add internet perissions to your manifest

```xml
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>

```


