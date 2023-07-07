<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:tools="http://schemas.android.com/tools"
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
<androidx.constraintlayout.widget.ConstraintLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".view.fragments.WebAddressFragment"
    android:padding="16dp">

    <TextView
        android:id="@+id/webViewTV"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="@string/webview_"
        android:textColor="@color/black"
        android:layout_marginTop="@dimen/titleMarginTop"
        android:textSize="@dimen/titleTextSize"
        android:textStyle="bold"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/urlTV"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="@string/url"
        android:textColor="@color/black"
        android:textSize="18dp"
        android:layout_marginTop="24dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/webViewTV" />

    <RelativeLayout
        android:id="@+id/urlRL"
        android:layout_width="match_parent"
        android:layout_height="45dp"
        android:layout_marginTop="8dp"
        android:background="@drawable/edit_bg_grey"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/urlTV">

        <EditText
            android:id="@+id/urlET"
            style="@style/editTextStyle"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:cursorVisible="true"
            android:gravity="center_vertical"
            android:hint="@string/url_hint"
            android:inputType="textUri"
            android:textColor="@color/black" />

        <ImageView
            android:id="@+id/urlErrorIV"
            android:layout_width="24dp"
            android:layout_height="24dp"
            android:layout_marginEnd="12dp"
            android:visibility="gone"
            android:contentDescription="@string/indicates_error"
            android:src="@drawable/alert"
            android:layout_centerVertical="true"
            android:layout_alignParentEnd="true"/>


    </RelativeLayout>

    <TextView
        android:id="@+id/urlErrorTV"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="4dp"
        android:text="@string/please_provide_a_correct_https_url"
        android:textColor="@color/red"
        android:visibility="invisible"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/urlRL" />

    <TextView
        android:id="@+id/enterTV"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="@string/enter_an_https_url"
        android:textColor="@color/black"
        android:textSize="14dp"
        android:layout_marginTop="4dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/urlErrorTV" />

    <android.widget.Button
        android:id="@+id/goBtn"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/enterTV"
        android:layout_marginTop="30dp"
        android:text="@string/go_to_site"
        android:textColor="@color/white"
        android:textSize="16dp"
        android:contentDescription="@string/tap_to_view_content_of_given_url"
        android:textAllCaps="false"
        android:background="@drawable/login_button_bg"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>


</androidx.constraintlayout.widget.ConstraintLayout>
</layout>