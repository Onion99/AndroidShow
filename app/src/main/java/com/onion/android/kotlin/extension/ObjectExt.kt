import android.content.res.Resources
import android.graphics.Color
import android.util.TypedValue


val String.color
    get() = Color.parseColor(this)

val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

val Int.dp
    get() = this.toFloat().dp.toInt()