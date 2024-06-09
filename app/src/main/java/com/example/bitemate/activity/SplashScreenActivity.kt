package com.example.bitemate.activity

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.airbnb.lottie.LottieAnimationView
import com.example.bitemate.MainActivity
import com.example.bitemate.R
import com.example.bitemate.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val anim = findViewById<LottieAnimationView>(R.id.logo_animation)
        anim.playAnimation()

        val user = FirebaseAuth.getInstance().currentUser

        Handler(Looper.getMainLooper()).postDelayed({
            if (user == null){
                Log.d(TAG, "User not authenticated, starting LoginActivity")
                startActivity(Intent(this,LoginActivity::class.java))
            }
            else{
                startActivity(Intent(this, MainActivity::class.java))

            }
            finish()
        },4500)
    }
}