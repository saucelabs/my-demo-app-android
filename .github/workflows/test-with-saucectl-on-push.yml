name: Test with saucectl

on: [push]

env:
  SAUCE_USERNAME: ${{secrets.SAUCE_USERNAME}}
  SAUCE_ACCESS_KEY: ${{secrets.SAUCE_ACCESS_KEY}}

jobs:
  main:
    timeout-minutes: 20
    runs-on: ubuntu-latest

    steps:
      - name: Checkout
        uses: actions/checkout@v2

      - name: Setup Java
        uses: actions/setup-java@v1
        with:
          java-version: 11

      - name: Setup Android SDK
        uses: android-actions/setup-android@v2

      - name: Compile
        run: |
          ./gradlew app:assembleDebug
          ./gradlew app:assembleDebugAndroidTest
        shell: bash

      - name: Test espresso with saucectl
        uses: saucelabs/saucectl-run-action@v1
        with:
          testing-environment: ""
          concurrency: 10