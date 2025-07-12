package com.behnamuix.colordetect

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.net.toUri

fun GotoZarinpal(ctx: Context) {
    var url="https://zarinp.al/xplayer2022"
    var i= Intent(Intent.ACTION_VIEW)
    i.setData(url.toUri())
    ctx.startActivity(i)



}
fun gotoHexColorSite(ctx:Context,hex:String){
    var nh=hex.replace("#","" )
    Log.i("Log",nh)
    var url="https://ircolor.ir/hex?hex=$nh"
    var i= Intent(Intent.ACTION_VIEW)
    i.setData(url.toUri())
    ctx.startActivity(i)
}