package com.saucelabs.mydemoapp.android;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Use the command below to run test classes or test methods marked by this annotation only
//
//   ./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.annotation=com.saucelabs.mydemoapp.android.TestOnlyThis
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TestOnlyThis {
}
