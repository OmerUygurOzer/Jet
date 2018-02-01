#### Jet Bus Library

[![](https://jitpack.io/v/OmerUygurOzer/jet.svg)](https://jitpack.io/#OmerUygurOzer/jet)

#### How to use: Add the following to your gradle file

```
allprojects {
	repositories {
			maven { url 'https://jitpack.io' }
	}
} 
  ```
  
  ```
  dependencies {
      implementation 'com.github.OmerUygurOzer.jet:jetcore:v1.0.4'
      annotationProcessor 'com.github.OmerUygurOzer.jet:jetprocessor:v1.0.4'
  }
   
   ```
  
  #### Examples:
  
  ##### Set Up:
  
  ```java
  public static Jet jet;

    @Override
    public void onCreate() {
        super.onCreate();
        JetConfigs jetConfigs = new JetConfigs();
        jetConfigs.setEnv(JetEnv.ANDROID);
        jet = Jet.init(jetConfigs);
    }
```

##### Usage : Receiving Events

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jet.dock(this);
}

@NoError
@EventSuccess("event1")
public void receiveEvent1(String message){
    System.out.println(message);
}
```

##### Usage : Sending Events

```java
jet.fire("event1","TEST_MESSAGE");
```


##### Usage : Stop Listening

```java
@Override
protected void onPause() {
        super.onPause();
        jet.undock(this);
}
    
```
