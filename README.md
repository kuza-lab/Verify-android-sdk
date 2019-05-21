# Verifysdk

Shows how to use the the verifyke sdk  in your android project

## Authentication
Create a developer account at [Verify Developers Portal ](https://verify.ke/get-started)
When you sign up for an account, you create a team and an app within the team. 
The app is given a consumer key and a secret key. 
You can regenerate secret keys (as you may need to rotate your keys in the future for security reasons). 
The sdk will authenticate to the Verify  by providing a JWT token generated using your consumer key and secret key.

## Using the Library

To use the sdk the following in the build.gradle(project)

**Example  1**
```java
repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
    
```
    
    
Then add the dependancy
**Example  2**

```java
   
       implementation 'com.github.kuza-lab:Verifysdk:v0.0.2Alpha'
```


In the activity you plan to use the sdk, initialize using the builder pattern and provide your consumer secret and secret key 

**Example  3**


```kotlin
    v = Verify.Builder(this)
            .secretKey("secret key here")
            .consumerKey("consumer key here")
            .enviroment(Enviroment.PRODUCTION)
            .build()
     
```

There are two enviroments  **PRODUCTION**  and   **SANDBOX **  .  

     
     
You can then proceed to use the created Verify object to perfom other operations 


**Example  4**

     
```kotlin
    v?.getPerson(personId, object : GetUserDetailsListener {
            override fun onCallStarted() {

                
            }

            override fun onResponse(person: Person) {
            }

            override fun onFailure(verifyException: VerifyException) {

            }
        })
    }
    
    
   
    
```

**Example  5**

```kotlin
    v?.verifyPerson(verifyPersonModel, object : VerifyUserDetailsListener {
            override fun onCallStarted() {
               

            }

            override fun onResponse(paramsResponse: List<ParamsResponse>) {
               
            }

            override fun onFailure(verifyException: VerifyException) {
               
            }
        })
    
    
   
    
```


**Example  6**

```kotlin
    v?.searchNcaContractorById(contractorRegId, object : SearchNcaContractorByIdListener {
            override fun onCallStarted() {
              
            }

            override fun onResponse( ncaContractor: NcaContractor) {
                
            }

            override fun onFailure(verifyException: VerifyException) {
                
            }
        })
    
    
   
    
```

**Example  7**

```kotlin
     v?.searchNcaContractorByName(contractorName, object : SearchNcaContractorByNameListener {
            override fun onCallStarted() {
              
            }

            override fun onResponse(ncaContractor: List<NcaContractor>) {
               
            }

            override fun onFailure(verifyException: VerifyException) {
                
            }
        })
    
    
   
    
```

**Example  8**

```kotlin
     v?.verifyNcaContractor(verifyNcaContractor, object : VerifyNcaContractorListener {
            override fun onCallStarted() {
               
            }

            override fun onResponse(paramsResponse: List<ParamsResponse>) {
                
            }

            override fun onFailure(verifyException: VerifyException) {
               
            }
        })
    
   
    
```
**Cancel an on going request**


To cancel an ongoing request, use the tag returned from the call

**Example 9**

```kotlin
    var tag: String?=null
    
    tag= v?.verifyNcaContractor(verifyNcaContractor, object : VerifyNcaContractorListener {
            override fun onCallStarted() {
               
            }

            override fun onResponse(paramsResponse: List<ParamsResponse>) {
                
            }

            override fun onFailure(verifyException: VerifyException) {
               
            }
        })
        
        
        
        
        v?.cancel(tag)
    
   
    
```





Make sure to add internet perissions to your manifest

```xml
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>

```


