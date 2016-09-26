package com.r21nomi.pinboard.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import com.r21nomi.pinboard.R
import com.r21nomi.pinboard.di.ActivityComponent
import com.r21nomi.pinboard.model.LoginModel
import org.jetbrains.anko.*
import javax.inject.Inject

class LoginActivity : BaseActivity() {

    @Inject
    lateinit var mLoginModel: LoginModel

    companion object {
        fun createIntent(context: Context): Intent {
            val intent = Intent(context, LoginActivity::class.java)
            return intent
        }
    }

    override fun injectDependency(component: ActivityComponent) {
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        relativeLayout {
            textView(getString(R.string.login_btn_label)) {
                setTextColor(getColor(R.color.white))
                setBackgroundColor(getColor(R.color.black))
                setPadding(dip(16), dip(16), dip(16), dip(16))
                gravity = Gravity.CENTER

                onClick {
                    initiateWebLogin()
                }
            }.lparams {
                width = matchParent
                horizontalMargin = dip(16)
                centerInParent()
                centerHorizontally()
            }
        }
    }

    private fun initiateWebLogin() {
        val oauthIntent = mLoginModel.getOAuthIntent()
        startActivity(oauthIntent)
    }
}
