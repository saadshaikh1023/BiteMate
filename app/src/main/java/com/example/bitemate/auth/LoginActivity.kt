package com.example.bitemate.auth


import android.content.Intent
import  androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.airbnb.lottie.LottieAnimationView
import com.example.bitemate.MainActivity
import com.example.bitemate.R
import com.example.bitemate.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.concurrent.TimeUnit

class LoginActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val auth = FirebaseAuth.getInstance()
    private var verificationId: String? = null

    private lateinit var dialog : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Log.d("LoginActivity", "onCreate: Started")
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dialog = AlertDialog.Builder(this).setView(R.layout.loading_layout)
            .setCancelable(false)
            .create()

        // Play animation
        val anim = findViewById<LottieAnimationView>(R.id.logo_animation1)
        anim.playAnimation()

        binding.sendOtp.setOnClickListener {
            if (binding.userNumber.text!!.isEmpty()) {
                binding.userNumber.error = "Please enter your number"
            } else {
                sendOtp(binding.userNumber.text.toString())
            }
        }

        binding.verifyOtp.setOnClickListener {
            if (binding.userOtp.text!!.isEmpty()) {
                binding.userOtp.error = "Please enter your OTP"
            } else {
                verifyOTP(binding.userOtp.text.toString())
            }
        }

    }


    private fun verifyOTP(otp: String) {
//    binding.sendOtp.showLoadingButton()

        dialog.show()
        val credential = PhoneAuthProvider.getCredential(verificationId!!, otp)
        signInWithPhoneAuthCredential(credential)
    }

        private fun sendOtp(number: String) {
//            binding.sendOtp.showLoadingButton()
            dialog.show()
            val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                    binding.sendOtp.showNormalButton()
                    signInWithPhoneAuthCredential(credential)
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    Toast.makeText(this@LoginActivity, "Verification failed: ${p0.message}", Toast.LENGTH_SHORT).show()
                }


                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    this@LoginActivity.verificationId = verificationId
//                    binding.sendOtp.showNormalButton()
                    dialog.dismiss()

                    binding.numberLayout.visibility = GONE
                    binding.otpLayout.visibility = VISIBLE

                }
            }

            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber("+91 $number") // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this) // Activity (for callback binding)
                .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)

        }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
//                binding.sendOtp.showNormalButton()
                if (task.isSuccessful) {

                    checkUserExist(binding.userNumber.text.toString())


                        startActivity(Intent(this, MainActivity::class.java))
                       finish()
                } else {
                    dialog.dismiss()
                    Toast.makeText(this, "Invaild OTP", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun checkUserExist(number: String) {

        FirebaseDatabase.getInstance().getReference("users").child(number)
            .addValueEventListener(object :ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    dialog.dismiss()
                    Toast.makeText(this@LoginActivity, p0.message, Toast.LENGTH_SHORT).show()
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if(p0.exists()){
                        dialog.dismiss()
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                    else{

                    }
                }
            })

    }
}


